/***********************************************************************
 * Copyright (c) 2019 Milan Jaitner                                   *
 * Distributed under the MIT software license, see the accompanying    *
 * file COPYING or https://www.opensource.org/licenses/mit-license.php.*
 ***********************************************************************/

package com.alloc64.dsobfuscator.obfuscators;

import com.alloc64.dsobfuscator.proguard.ProguardDictionary;
import com.alloc64.dsobfuscator.smali.model.SmaliClass;
import com.alloc64.dsobfuscator.smali.model.SmaliMethod;
import com.alloc64.dsobfuscator.smali.model.SmaliMethodSignature;
import com.alloc64.dsobfuscator.util.RandomUtils;
import com.alloc64.apktools.apk.DexFile;
import com.alloc64.apktools.apk.SmaliFile;
import com.alloc64.apktools.smali.Smali;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class ArithmeticBranchObfuscator extends SmaliClassInjectingObfuscator
{
    private SmaliMethod initializedMethod;
    private SmaliMethod failMethod;

    public ArithmeticBranchObfuscator(ProguardDictionary proguardDictionary)
    {
        super(proguardDictionary);
    }

    @Override
    protected String name()
    {
        return "ArithmeticBranchObfuscator";
    }

    @Override
    public void obfuscate(DexFile dexFile) throws Exception
    {
        this.setDexFile(dexFile);

        SmaliClass methodGuardClass = injectSmaliClass("smali/MethodGuard.smali", dexFile);

        this.initializedMethod = methodGuardClass
                .findMethodOriginal(new SmaliMethodSignature("initialized", "", "Z"));

        this.failMethod = methodGuardClass
                .findMethodOriginal(new SmaliMethodSignature("fail", "", "V"));

        super.obfuscate(dexFile);
    }

    @Override
    public void obfuscate(int fileNumber, SmaliFile smaliFile, List<String> lines) throws Exception
    {
        List<String> resultLines = new ArrayList<>();

        boolean isInMethod = false;
        boolean isStaticMethod = false;

        boolean fileChanged = false;

        for(int lineNumber = 0; lineNumber < lines.size(); lineNumber++)
        {
            String line = lines.get(lineNumber);

            if(line.startsWith(".method ") && !line.contains(" abstract ") && !line.contains(" constructor ") && !line.contains(" native ") && !isInMethod)
            {
                isStaticMethod = line.contains(" static ");

                // Entering method.
                resultLines.add(line);
                isInMethod = true;
            }
            else if(line.startsWith(".end method") && isInMethod)
            {
                // Exiting method.

                resultLines.add(line);
                isInMethod = false;
                isStaticMethod = false;
            }
            else if(isInMethod)
            {
                // Inside method.
                resultLines.add(line);

                Matcher m = Smali.registersPattern.matcher(line);

                if(m.matches())
                {
                    Integer registerCount = Integer.valueOf(m.group(Smali.REGISTER_COUNT));
                    Integer requiredNewRegisters = 3;

                    int registerOffset = (isStaticMethod ? 0 : 1);

                    if(registerOffset > 0)
                        requiredNewRegisters += registerOffset;

                    Integer newRegisterCount = registerCount + requiredNewRegisters;

                    if(registerCount > 3 && newRegisterCount < 15)
                    {
                        resultLines.set(resultLines.size()-1, ".registers " + newRegisterCount);

                        // Add a fake branch at the beginning of the method: one branch
                        // will continue from here, the other branch will go to
                        // the end of the method and then will return here
                        // through a "goto" instruction.


                        String newRegister0 = "v" + (registerOffset+0); // first integer
                        String newRegister1 = "v" + (registerOffset+1); // second integer
                        String newRegister2 = "v" + (registerOffset+2); // boolean var

                        int newRegister0Value = RandomUtils.randInt(1000000, 32000000);
                        int newRegister1Value = RandomUtils.randInt(1000000, 32000000);

                        String endLabel = RandomStringUtils.randomAlphabetic(32);

                        String condLabel = RandomStringUtils.randomAlphabetic(32);

                        resultLines.add(String.format("\n\tconst %s, %d\n", newRegister0, newRegister0Value));
                        resultLines.add(String.format("\n\tconst %s, %d\n", newRegister1, newRegister1Value));

                        resultLines.add("\tinvoke-static {}, " + initializedMethod.getSignature().getCallSignature() + "\n");
                        resultLines.add(String.format("\tmove-result %s\n",newRegister2));

                        resultLines.add(String.format("\tadd-int %s, %s, %s\n", newRegister0, newRegister0, newRegister1));
                        resultLines.add(String.format("\trem-int %s, %s, %s\n", newRegister0, newRegister0, newRegister1));

                        resultLines.add(String.format("\tif-gtz %s, :%s\n", newRegister0, condLabel));
                        resultLines.add(String.format("\tif-nez %s, :%s\n", newRegister2, condLabel));

                        resultLines.add("\tinvoke-static {}, " + failMethod.getSignature().getCallSignature() + "\n"); //TODO:

                        resultLines.add("\t:" + condLabel + "\n");
                        resultLines.add("\t:" + endLabel + "\n");
                        fileChanged = true;
                    }
                }
            }
            else
            {
                resultLines.add(line);
            }
        }

        if(fileChanged)
            increaseOccurrenceCount();

        write(smaliFile, resultLines);
    }
}

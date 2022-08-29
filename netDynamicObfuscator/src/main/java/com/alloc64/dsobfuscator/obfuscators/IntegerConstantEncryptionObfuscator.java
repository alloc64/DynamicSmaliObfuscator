/***********************************************************************
 * Copyright (c) 2019 Milan Jaitner                                   *
 * Distributed under the MIT software license, see the accompanying    *
 * file COPYING or https://www.opensource.org/licenses/mit-license.php.*
 ***********************************************************************/

package com.alloc64.dsobfuscator.obfuscators;

import com.alloc64.dsobfuscator.proguard.ProguardDictionary;
import com.alloc64.dsobfuscator.smali.model.SmaliMethod;
import com.alloc64.apktools.apk.SmaliFile;
import com.alloc64.apktools.smali.Smali;

import java.util.List;
import java.util.regex.Matcher;

public class IntegerConstantEncryptionObfuscator extends SmaliClassInjectingObfuscator
{
    private SmaliMethod decryptIntegerMethod;

    public IntegerConstantEncryptionObfuscator(ProguardDictionary proguardDictionary)
    {
        super(proguardDictionary);
    }

    @Override
    protected String name()
    {
        return "IntegerConstantEncryptionObfuscator";
    }

    @Override
    public void obfuscate(int fileNumber, SmaliFile smaliFile, List<String> lines) throws Exception
    {
        for (int lineNumber = 0; lineNumber < lines.size(); lineNumber++)
        {
            String line = lines.get(lineNumber);

            Matcher m = constIntPattern.matcher(line);

            if (m.matches())
            {
                String register = m.group(Smali.REGISTER);
                String constantValueString = m.group(CONSTANT_VALUE);
                Integer constantValue = Integer.decode(constantValueString);

                if(constantValue > 0xF)
                {
                    String regType = register.substring(0, 1);
                    Integer regNumber = Integer.parseInt(register.substring(1, register.length()));

                    if ((regType.equals("v") && regNumber <= 15) || (regType.equals("p") && regNumber <= 15))
                    {
                        String encryptedConstantValue = encryptConstant(constantValue);

                        String newLine = String.format(
                                "\n\tconst %s, %s\n" +
                                        "\n\tinvoke-static {%s}, " + decryptIntegerMethod.getSignature().getCallSignature() + "\n" +
                                        "\n\tmove-result %s\n",
                                register,
                                encryptedConstantValue,
                                register,
                                register
                        );

                        lines.set(lineNumber, newLine);

                        // System.out.printf("Replaced number constant from %s\n", constantValueString + " to: " + encryptedConstantValue + " in " + smaliFile);

                        increaseOccurrenceCount();
                    }
                }
            }
        }

        write(smaliFile, lines);
    }

    private String encryptConstant(Integer constantValue)
    {
        return "0x" + Integer.toHexString(
                Integer.reverse(constantValue)
        );
    }

    public void setDecryptIntegerMethod(SmaliMethod decryptIntegerMethod)
    {
        this.decryptIntegerMethod = decryptIntegerMethod;
    }
}

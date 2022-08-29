/***********************************************************************
 * Copyright (c) 2019 Milan Jaitner                                   *
 * Distributed under the MIT software license, see the accompanying    *
 * file COPYING or https://www.opensource.org/licenses/mit-license.php.*
 ***********************************************************************/

package com.alloc64.dsobfuscator.obfuscators;

import com.alloc64.apktools.apk.SmaliFile;
import com.alloc64.apktools.smali.Smali;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class GotoObfuscator extends Obfuscator
{
    @Override
    protected String name()
    {
        return "GotoObfuscator";
    }

    @Override
    public void obfuscate(int fileNumber, SmaliFile smaliFile, List<String> lines) throws Exception
    {
        List<String> resultLines = new ArrayList<>();

        boolean isInMethod = false;
        boolean firstGotoInjected = false;

        boolean fileChanged = false;

        for(int i = 0; i < lines.size(); i++)
        {
            String line = lines.get(i);

            if(line.startsWith(".method ") && !line.contains(" abstract ") && !line.contains(" native ") && !isInMethod)
            {
                // If at the beginning of a non abstract/native method
                // (after the .locals instruction), insert a "goto" to the
                // label at the end of the method and a label to the first
                // instruction of the method.

                resultLines.add(line);
                isInMethod = true;
            }
            else
            {
                Matcher m = Smali.registersPattern.matcher(line);

                if(isInMethod && m.matches())
                {
                    resultLines.add(line);
                    resultLines.add("\n\tgoto/32 :ali\n\n");
                    resultLines.add("\t:bfi\n");

                    firstGotoInjected = true;
                    fileChanged = true;
                }
                else if(isInMethod && firstGotoInjected && line.startsWith(".end method"))
                {
                    // If at the end of the method, insert a label after the
                    // last instruction of the method and a "goto" to the label
                    // at the beginning of the method. This will not cause an
                    // endless loop because the method will return at some point
                    // and the second "goto" won't be called again when the
                    // method finishes.

                    resultLines.add("\n\t:ali\n\n");
                    resultLines.add("\tgoto/32 :bfi\n\n");
                    resultLines.add(line);

                    isInMethod = false;
                    firstGotoInjected = false;
                    fileChanged = true;
                }
                else
                    resultLines.add(line);
            }
        }

        if(fileChanged)
            increaseOccurrenceCount();

        write(smaliFile, resultLines);
    }
}

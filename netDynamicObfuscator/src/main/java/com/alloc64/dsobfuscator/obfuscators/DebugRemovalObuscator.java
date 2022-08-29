/***********************************************************************
 * Copyright (c) 2019 Milan Jaitner                                   *
 * Distributed under the MIT software license, see the accompanying    *
 * file COPYING or https://www.opensource.org/licenses/mit-license.php.*
 ***********************************************************************/

package com.alloc64.dsobfuscator.obfuscators;

import com.alloc64.apktools.apk.SmaliFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class DebugRemovalObuscator extends Obfuscator
{
    private static final List<String> debugOpCodes = Arrays.asList(
            ".source ",
            ".line ",
            ".prologue",
            ".epilogue",
            ".local ",
            ".end local",
            ".restart local",
            ".param "
    );

    private static final Pattern paramPattern = Pattern.compile("\\s+\\.param\\s(?<register>[vp0-9]+)");

    @Override
    protected String name()
    {
        return "DebugRemovalObuscator";
    }

    @Override
    public void obfuscate(int fileNumber, SmaliFile smaliFile, List<String> lines) throws Exception
    {
        boolean fileChanged = false;

        // Keep only the lines not containing debug op codes.
        // ".param <annotation> .end param" shouldn't be removed.

        List<String> linesToKeep = new ArrayList<>();
        boolean insideParamDeclaration = false;

        for(int lineNumber = 0; lineNumber < lines.size(); lineNumber++)
        {
            String line = lines.get(lineNumber);
            String strippedLine = line.trim();

            if(strippedLine.startsWith(".end param"))
            {
                insideParamDeclaration = true;
                linesToKeep.add(line);
            }
            else if(strippedLine.startsWith(".param ") && insideParamDeclaration)
            {
                insideParamDeclaration = false;

                // Remove unnecessary data from param (name and type comment).
                String originalLine = String.valueOf(line);

                line = paramPattern.matcher(line).group() + "\n";
                linesToKeep.add(line);

                if(!originalLine.equals(line))
                {
                    System.out.printf("Removed param name and comment from %s\n", smaliFile.getAbsolutePath());

                    increaseOccurrenceCount();
                    fileChanged = true;
                }
            }
            else if(!insideParamDeclaration)
            {
                boolean isDebugCode = false;

                for(String debugOpCode : debugOpCodes)
                {
                    if (strippedLine.contains(debugOpCode))
                    {
                        isDebugCode = true;
                        System.out.printf("Removed opcode: %s from %s\n", debugOpCode, smaliFile.getAbsolutePath());
                        break;
                    }
                }

                if(!isDebugCode)
                {
                    linesToKeep.add(line);
                }
                else
                {
                    increaseOccurrenceCount();
                    fileChanged = true;
                }
            }
            else
            {
                linesToKeep.add(line);
            }
        }

        // Collections.reverse(linesToKeep);

        if(fileChanged)
        {
            write(smaliFile, linesToKeep);
        }
    }
}

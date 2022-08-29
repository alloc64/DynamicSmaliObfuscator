/***********************************************************************
 * Copyright (c) 2019 Milan Jaitner                                   *
 * Distributed under the MIT software license, see the accompanying    *
 * file COPYING or https://www.opensource.org/licenses/mit-license.php.*
 ***********************************************************************/

package com.alloc64.dsobfuscator.smali.model;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SmaliClass
{
    private SmaliClassSignature classSignature;
    private SmaliSuperClassSignature superClassSignature;
    private SmaliInterfaceSignature interfaceSignature;

    private List<SmaliUnknownInstruction> unknownInstructions = new ArrayList<>();
    private List<SmaliMethod> methodList = new ArrayList<>();

    private int lineCount;

    public SmaliClass()
    {
    }

    public SmaliClassSignature getClassSignature()
    {
        return classSignature;
    }

    public void setClassSignature(SmaliClassSignature classSignature)
    {
        this.classSignature = classSignature;
    }

    public SmaliSuperClassSignature getSuperClassSignature()
    {
        return superClassSignature;
    }

    public void setSuperClassSignature(SmaliSuperClassSignature superClassSignature)
    {
        this.superClassSignature = superClassSignature;
    }

    public SmaliInterfaceSignature getInterfaceSignature()
    {
        return interfaceSignature;
    }

    public void setInterfaceSignature(SmaliInterfaceSignature interfaceSignature)
    {
        this.interfaceSignature = interfaceSignature;
    }

    public List<SmaliUnknownInstruction> getUnknownInstructions()
    {
        return unknownInstructions;
    }

    public List<SmaliMethod> getMethodList()
    {
        return methodList;
    }

    public int getLineCount()
    {
        return lineCount;
    }

    public void setLineCount(int lineCount)
    {
        this.lineCount = lineCount;
    }

    public SmaliMethod findMethodOriginal(SmaliMethodSignature sig)
    {
        return getMethodList()
                .stream()
                .filter(r -> r.getSignature().equalsNoAccessModifier(sig))
                .findAny()
                .orElse(null);
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        if(classSignature != null)
        {
            sb.append(classSignature);
            sb.append("\n");
        }

        if(superClassSignature != null)
        {
            sb.append(superClassSignature);
            sb.append("\n");
        }

        if(interfaceSignature != null)
        {
            sb.append("\n");
            sb.append(interfaceSignature);
            sb.append("\n");
        }

        List<String> lines = new ArrayList<>(Collections.nCopies(lineCount, "")); //TODO: vzhledem k tomu, ze zde se pouzivaji index, muze dojit k OOB EX

        for(SmaliUnknownInstruction unknownInstruction : unknownInstructions)
            lines.add(unknownInstruction.getLineNumber(), unknownInstruction.toString());

        for(SmaliMethod smaliMethod : methodList)
            lines.add(smaliMethod.getLineNumber(), smaliMethod.toString());

        for(String line : lines)
            sb.append(line);

        return sb.toString();
    }

    public File write(File folder) throws Exception
    {
        if(!folder.isDirectory())
            throw new IllegalStateException("Unable to write smali file, invalid directory specified (is directory?): " + folder);

        SmaliClassName className = classSignature.getClassName();

        File packageFolder = new File(folder, className
                .getPackageName()
                .getPackageName());

        packageFolder.mkdirs();

        File outputSmaliFile = new File(packageFolder, className.getClassName() + ".smali");

        Files.write(outputSmaliFile.toPath(), toString().getBytes(StandardCharsets.UTF_8));

        return outputSmaliFile;
    }
}



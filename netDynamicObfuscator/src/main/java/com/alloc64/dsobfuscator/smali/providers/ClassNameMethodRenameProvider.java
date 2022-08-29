/***********************************************************************
 * Copyright (c) 2019 Milan Jaitner                                   *
 * Distributed under the MIT software license, see the accompanying    *
 * file COPYING or https://www.opensource.org/licenses/mit-license.php.*
 ***********************************************************************/


package com.alloc64.dsobfuscator.smali.providers;

import com.alloc64.dsobfuscator.proguard.ProguardDictionary;
import com.alloc64.dsobfuscator.smali.SmaliClassParser;
import com.alloc64.dsobfuscator.smali.model.SmaliClass;
import com.alloc64.dsobfuscator.smali.model.SmaliMethod;
import com.alloc64.dsobfuscator.smali.model.SmaliMethodSignature;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;

//TODO: tento renamer neni dokonceny, neni zde doresena situace nahrazeni referenci v op codes, protoze SmaliClass je zatim neumi parsovat
public class ClassNameMethodRenameProvider
{
    private final ProguardDictionary proguardDictionary;
    private final KnownNameProvider knownFileNameProvider;

    private final SmaliClassParser parser = new SmaliClassParser();

    private String renamedPackageName;

    private int lastValidFilenameIndex;
    private int filenameSuffix;

    private int methodNameSuffix;

    public ClassNameMethodRenameProvider(ProguardDictionary proguardDictionary, KnownNameProvider knownFileNameProvider)
    {
        this.proguardDictionary = proguardDictionary;
        this.knownFileNameProvider = knownFileNameProvider;
    }

    public void setRenamedPackageName(String packageName)
    {
        this.renamedPackageName = packageName;
    }

    public SmaliClass rename(File smaliFile) throws Exception
    {
        return rename(
                FileUtils.readLines(smaliFile, StandardCharsets.UTF_8)
        );
    }

    public SmaliClass rename(String resourceName) throws Exception
    {
        return rename(
                parser.parse(resourceName)
        );
    }

    public SmaliClass rename(List<String> lines)
    {
        return rename(
                parser.parse(lines)
        );
    }

    public SmaliClass rename(SmaliClass smaliClass)
    {
        smaliClass.getClassSignature().getClassName()
                .getPackageName()
                .setPackageName(renamedPackageName);

        smaliClass.getClassSignature()
                .getClassName()
                .setClassName(chooseFilename());

        this.methodNameSuffix = 0;

        List<SmaliMethod> methodList = smaliClass.getMethodList();

        for (int i = 0; i < methodList.size(); i++)
        {
            SmaliMethod method = methodList.get(i);
            SmaliMethodSignature signature = method.getSignature();

            if (!signature.isConstructor() && signature.isPublic()) //TODO: zatim obfuskujeme pouze public metody, do budoucna se to bude muset ridit proguard configem
                signature.setMethodName(chooseMethodName(i, methodList));
        }

        return smaliClass;
    }

    private String chooseMethodName(int idx, List<SmaliMethod> methodList)
    {
        List<String> dict = proguardDictionary.entries();

        String chosenMethodName;

        int offset = 0;
        do
        {
            int i = (idx + offset++);

            chosenMethodName = dict.get(i % dict.size());

            if(i > dict.size())
                methodNameSuffix++;

            if(methodNameSuffix > 0)
                chosenMethodName += methodNameSuffix;
        }
        while(methodExists(chosenMethodName, methodList));

        return chosenMethodName;
    }

    private boolean methodExists(String method, List<SmaliMethod> methodList) //TODO: v budoucnu bude nutne metody predelat na indexed collection. S tim se ale vaze i prejmenovani a vazby v indexech.
    {
        return methodList
                .stream()
                .anyMatch(r -> r.getSignature()
                        .getMethodName()
                        .equals(method)
                );
    }

    private String chooseFilename()
    {
        List<String> dict = proguardDictionary.entries();

        String chosenFilename;

        do
        {
            chosenFilename = dict.get(lastValidFilenameIndex++ % dict.size());

            if(lastValidFilenameIndex >= dict.size())
            {
                lastValidFilenameIndex = 0;
                filenameSuffix++;
            }

            if(filenameSuffix > 0)
                chosenFilename += filenameSuffix;
        }
        while(knownFileNameProvider.nameExists(chosenFilename));

        return chosenFilename;
    }
}

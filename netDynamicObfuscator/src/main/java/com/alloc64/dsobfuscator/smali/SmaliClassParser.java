/***********************************************************************
 * Copyright (c) 2019 Milan Jaitner                                   *
 * Distributed under the MIT software license, see the accompanying    *
 * file COPYING or https://www.opensource.org/licenses/mit-license.php.*
 ***********************************************************************/

package com.alloc64.dsobfuscator.smali;

import com.alloc64.dsobfuscator.smali.model.SmaliClass;
import com.alloc64.dsobfuscator.smali.model.SmaliClassSignature;
import com.alloc64.dsobfuscator.smali.model.SmaliEndMethodSignature;
import com.alloc64.dsobfuscator.smali.model.SmaliInterfaceSignature;
import com.alloc64.dsobfuscator.smali.model.SmaliMethod;
import com.alloc64.dsobfuscator.smali.model.SmaliMethodSignature;
import com.alloc64.dsobfuscator.smali.model.SmaliObject;
import com.alloc64.dsobfuscator.smali.model.SmaliSuperClassSignature;
import com.alloc64.dsobfuscator.smali.model.SmaliUnknownInstruction;
import com.alloc64.dsobfuscator.smali.tokenizer.SmaliClassSignatureTokenizer;
import com.alloc64.dsobfuscator.smali.tokenizer.SmaliEndMethodSignatureTokenizer;
import com.alloc64.dsobfuscator.smali.tokenizer.SmaliInterfaceSignatureTokenizer;
import com.alloc64.dsobfuscator.smali.tokenizer.SmaliMethodSignatureTokenizer;
import com.alloc64.dsobfuscator.smali.tokenizer.SmaliSuperClassSignatureTokenizer;
import com.alloc64.dsobfuscator.smali.tokenizer.SmaliTokenizer;
import com.alloc64.apktools.res.ClassPathResource;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SmaliClassParser
{
    private final Map<String, SmaliTokenizer> tokenizers = new LinkedHashMap<>();

    private SmaliMethod currentMethod;

    public SmaliClassParser()
    {
        addTokenizer(SmaliClassSignature.TOKEN, new SmaliClassSignatureTokenizer());
        addTokenizer(SmaliSuperClassSignature.TOKEN, new SmaliSuperClassSignatureTokenizer());
        addTokenizer(SmaliInterfaceSignature.TOKEN, new SmaliInterfaceSignatureTokenizer());

        addTokenizer(SmaliMethodSignature.TOKEN, new SmaliMethodSignatureTokenizer());
        addTokenizer(SmaliEndMethodSignature.TOKEN, new SmaliEndMethodSignatureTokenizer());
    }

    public SmaliClass parse(File smaliFile) throws Exception
    {
        if (smaliFile == null || !smaliFile.exists())
            throw new FileNotFoundException("Smali file not found: " + smaliFile);

        return parse(
                Files.readAllLines(smaliFile.toPath())
        );
    }

    public SmaliClass parse(String resourceName) throws Exception
    {
        ClassPathResource resource = new ClassPathResource(resourceName);

        if (!resource.exists())
            throw new FileNotFoundException("Resource smali file does not exists:" + resourceName);

        List<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream())))
        {
            String line;

            while ((line = br.readLine()) != null)
                lines.add(line);
        }

        return parse(lines);
    }

    public SmaliClass parse(List<String> lines)
    {
        this.currentMethod = null;

        SmaliClass clazz = new SmaliClass();

        clazz.setLineCount(lines.size());

        int lineNumber = 0;
        for (int i = 0; i < lines.size(); i++)
        {
            String line = lines.get(i);

            lineNumber++;

            if (StringUtils.isEmpty(line))
                continue;

            if (line.trim().charAt(0) != '#')
            {
                String token = parseToken(line);

                if (token != null)
                {
                    SmaliTokenizer tokenizer = tokenizers.get(token);

                    SmaliObject smaliObject = null;

                    if (tokenizer != null)
                        smaliObject = tokenizer.tokenize(line, clazz);

                    boolean tokenized = tokenize(
                            lineNumber,
                            smaliObject == null ? line : smaliObject,
                            clazz
                    );

                    if (tokenized)
                        continue;

                    // else unknown instruction found
                }
                else if (currentMethod != null) // situace, kdy nezname token ale jsme v metode
                {
                    tokenize(lineNumber, line, clazz);
                    continue;
                }
            }

            clazz.getUnknownInstructions().add(new SmaliUnknownInstruction(lineNumber, line + "\n\n"));
        }

        return clazz;
    }

    private String parseToken(String line)
    {
        String token = null;

        int spaceIndex = line.indexOf(" ");

        if (spaceIndex != -1)
        {
            token = line
                    .substring(0, spaceIndex)
                    .trim();

            if (token.equals(".end"))
            {
                String[] arr = line.split(" ");

                if (arr.length == 2)
                    return String.format("%s %s", arr[0], arr[1]);
            }
        }

        return token;
    }

    private boolean tokenize(int lineNumber, Object o, SmaliClass clazz)
    {
        if (o instanceof SmaliMethodSignature)
        {
            this.currentMethod = new SmaliMethod(
                    lineNumber,
                    (SmaliMethodSignature) o,
                    clazz
            );

            return true;
        }
        else if (o instanceof SmaliEndMethodSignature)
        {
            clazz.getMethodList().add(currentMethod);
            this.currentMethod = null;

            return true;
        }
        else if (o instanceof SmaliClassSignature)
        {
            clazz.setClassSignature((SmaliClassSignature) o);

            return true;
        }
        else if (o instanceof SmaliSuperClassSignature)
        {
            clazz.setSuperClassSignature((SmaliSuperClassSignature) o);

            return true;
        }
        else if (o instanceof SmaliInterfaceSignature)
        {
            clazz.setInterfaceSignature((SmaliInterfaceSignature) o);

            return true;
        }
        else if (o instanceof String)
        {
            if (currentMethod != null)
            {
                this.currentMethod.getBody().add((String) o);

                return true;
            }
        }

        return false;
    }

    private void addTokenizer(String token, SmaliTokenizer tokenizer)
    {
        tokenizers.put(token, tokenizer);
    }
}

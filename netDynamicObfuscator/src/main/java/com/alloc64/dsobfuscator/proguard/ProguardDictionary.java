/***********************************************************************
 * Copyright (c) 2019 Milan Jaitner                                   *
 * Distributed under the MIT software license, see the accompanying    *
 * file COPYING or https://www.opensource.org/licenses/mit-license.php.*
 ***********************************************************************/

package com.alloc64.dsobfuscator.proguard;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProguardDictionary extends ResourceAware
{
    private final InputStream inputStream;
    private final List<String> lines = new ArrayList<>();

    public ProguardDictionary(InputStream is)
    {
        this.inputStream = is;
    }

    public static ProguardDictionary defaultDictionary() throws Exception
    {
        ProguardDictionary proguardDictionary = new ProguardDictionary(getResource("proguard/proguard-keywords.txt"));
        proguardDictionary.parse();

        return proguardDictionary;
    }

    public void parse() throws Exception
    {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream)))
        {
            String line;

            while ((line = br.readLine()) != null)
            {
                if(StringUtils.isEmpty(line) || line.startsWith("#"))
                    continue;

                lines.add(line);
            }
        }
    }

    public void shuffle()
    {
        Collections.shuffle(lines);
    }

    public List<String> entries()
    {
        return lines;
    }

    public void save(File file) throws Exception
    {
        FileUtils.writeStringToFile(file, toString(), StandardCharsets.UTF_8);
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        for(String entry : entries())
        {
            sb.append(entry);
            sb.append("\n");
        }

        return sb.toString();
    }
}

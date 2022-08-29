/***********************************************************************
 * Copyright (c) 2019 Milan Jaitner                                   *
 * Distributed under the MIT software license, see the accompanying    *
 * file COPYING or https://www.opensource.org/licenses/mit-license.php.*
 ***********************************************************************/

package com.alloc64.dsobfuscator.obfuscators;

import com.alloc64.dsobfuscator.smali.providers.KnownNameProvider;
import com.alloc64.apktools.apk.DexFile;
import com.alloc64.apktools.apk.SmaliFile;
import com.alloc64.apktools.smali.Smali;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public abstract class Obfuscator
{
    protected static final String CLASS_NAME = "className";
    protected static final String CONSTANT_TYPE = "constantType";
    protected static final String CONSTANT_VALUE = "constantValue";

    public static final Pattern classPattern = Pattern.compile(".class.+?(?<" + CLASS_NAME + ">\\S+?;)");
    protected static final Pattern constIntPattern = Pattern.compile("\\s+(?<" + CONSTANT_TYPE + ">const(/4|/16)?)?\\s(?<" + Smali.REGISTER + ">[vp0-9]+),\\s(?<" + CONSTANT_VALUE + ">[0-9A-f\\-x]+)");

    private DexFile dexFile;
    private int occurrenceCount;

    private KnownNameProvider knownSmaliNameProvider = new KnownNameProvider()
    {
        @Override
        public boolean nameExists(String name)
        {
            return dexFile.getSmaliFileList().stream().anyMatch(r -> r.getClassName().equals(name));
        }
    };

    private Set<File> ignoredClasses = new LinkedHashSet<>();

    protected abstract String name();

    public DexFile dexFile()
    {
        return dexFile;
    }

    public void setDexFile(DexFile dexFile)
    {
        this.dexFile = dexFile;
    }

    protected KnownNameProvider knownSmaliNameProvider()
    {
        return knownSmaliNameProvider;
    }

    public int getOccurrenceCount()
    {
        return occurrenceCount;
    }

    public void obfuscate(DexFile dexFile) throws Exception
    {
        this.setDexFile(dexFile);

        System.out.printf("%s started processing.\n", name());

        int fileNumber = 0;
        for(SmaliFile smaliFile : dexFile.getSmaliFileList())
        {
            File file = smaliFile.getFile();

            if(ignoredClasses.contains(file))
            {
                System.out.println("Ignoring class: " + smaliFile);
                continue;
            }

            if(!smaliFile.exists())
            {
                System.err.println("Ignoring non-existing smali file from obfuscation: " + smaliFile);
                continue;
            }

            obfuscate(fileNumber, smaliFile, FileUtils.readLines(file, StandardCharsets.UTF_8));

            fileNumber++;
        }

        System.out.printf("%s obfuscated %d occurrences.\n", name(), getOccurrenceCount());
    }

    public abstract void obfuscate(int fileNumber, SmaliFile smaliFile, List<String> lines) throws Exception;

    protected void write(SmaliFile smaliFile, List<String> lines) throws IOException
    {
        FileUtils.writeLines(smaliFile.getFile(), lines, false);
    }

    protected void increaseOccurrenceCount()
    {
        occurrenceCount++;
    }

    protected void markClassIgnored(SmaliFile smaliFile)
    {
        ignoredClasses.add(smaliFile.getFile());
    }
}

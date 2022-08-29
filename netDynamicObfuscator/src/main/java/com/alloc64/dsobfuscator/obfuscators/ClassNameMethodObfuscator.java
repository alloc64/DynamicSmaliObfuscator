/***********************************************************************
 * Copyright (c) 2019 Milan Jaitner                                   *
 * Distributed under the MIT software license, see the accompanying    *
 * file COPYING or https://www.opensource.org/licenses/mit-license.php.*
 ***********************************************************************/

package com.alloc64.dsobfuscator.obfuscators;

import com.alloc64.dsobfuscator.proguard.ProguardDictionary;
import com.alloc64.dsobfuscator.smali.providers.ClassNameMethodRenameProvider;
import com.alloc64.apktools.apk.SmaliFile;

import java.io.File;
import java.util.List;

public class ClassNameMethodObfuscator extends Obfuscator
{
    private final File smaliFolder;

    private final ClassNameMethodRenameProvider classNameMethodRenameProvider;

    @Override
    protected String name()
    {
        return "ClassNameMethodObfuscator";
    }

    public ClassNameMethodObfuscator(File smaliFolder, ProguardDictionary proguardDictionary)
    {
        this.smaliFolder = smaliFolder;

        this.classNameMethodRenameProvider = new ClassNameMethodRenameProvider(
                proguardDictionary,
                knownSmaliNameProvider()
        );
    }

    public void setRenamedPackageName(String packageName)
    {
        this.classNameMethodRenameProvider.setRenamedPackageName(packageName);
    }

    @Override
    public void obfuscate(int fileNumber, SmaliFile smaliFile, List<String> lines) throws Exception
    {
        classNameMethodRenameProvider
                .rename(lines)
                .write(smaliFolder);
    }
}

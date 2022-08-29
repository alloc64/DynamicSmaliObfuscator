/***********************************************************************
 * Copyright (c) 2019 Milan Jaitner                                   *
 * Distributed under the MIT software license, see the accompanying    *
 * file COPYING or https://www.opensource.org/licenses/mit-license.php.*
 ***********************************************************************/

package com.alloc64.dsobfuscator.obfuscators;

import com.alloc64.dsobfuscator.proguard.ProguardDictionary;
import com.alloc64.dsobfuscator.smali.model.SmaliClass;
import com.alloc64.dsobfuscator.smali.providers.ClassNameMethodRenameProvider;
import com.alloc64.apktools.apk.DexFile;
import com.alloc64.apktools.apk.SmaliFile;

import java.io.File;

public abstract class SmaliClassInjectingObfuscator extends Obfuscator
{
    private ClassNameMethodRenameProvider classNameMethodRenameProvider;

    public SmaliClassInjectingObfuscator(ProguardDictionary proguardDictionary)
    {
        this.classNameMethodRenameProvider = new ClassNameMethodRenameProvider(proguardDictionary, knownSmaliNameProvider());
    }

    public void setRenamedPackageName(String packageName) // pouzito pro prejmenovani injectovanych smali classes + methods
    {
        this.classNameMethodRenameProvider.setRenamedPackageName(packageName);
    }

    public SmaliClass injectSmaliClass(String resourceName, DexFile dexFile) throws Exception
    {
        SmaliClass smaliClass = classNameMethodRenameProvider
                .rename(resourceName);

        File smaliFile = smaliClass
                .write(dexFile.getDisassembledDexFolder());

        SmaliFile smaliFile0 = new SmaliFile(smaliFile);
        smaliFile0.parse();

        markClassIgnored(smaliFile0);

        dexFile().addFileOnly(smaliFile0); //TODO: tuto funkcionalitu jiz zajistuje SmaliFile, chce to cele sjednotit v budoucnu

        System.out.println("Injecting smali class: " + resourceName);

        return smaliClass;
    }
}

/***********************************************************************
 * Copyright (c) 2019 Milan Jaitner                                   *
 * Distributed under the MIT software license, see the accompanying    *
 * file COPYING or https://www.opensource.org/licenses/mit-license.php.*
 ***********************************************************************/

package com.alloc64.dsobfuscator;

import com.alloc64.dsobfuscator.obfuscators.Obfuscator;
import com.alloc64.dsobfuscator.proguard.ProguardDictionary;
import com.alloc64.apktools.apk.ApkFile;
import com.alloc64.apktools.apk.DexFile;
import com.alloc64.apktools.keystore.KeystoreInfo;

import org.apache.commons.io.FileUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;

public class  NetDynamicObfuscator
{
    private final File targetApkFile;
    private final ProguardDictionary proguardDictionary;
    private final KeystoreInfo keystoreInfo;
    private final File tempFolder;

    private List<Obfuscator> firstPassObfuscatorList = new ArrayList<>();
    private List<Obfuscator> lastPassObfuscatorList = new ArrayList<>();

    static
    {
        Security.addProvider(new BouncyCastleProvider());
    }

    public NetDynamicObfuscator(File targetApkFile,
                                ProguardDictionary proguardDictionary,
                                KeystoreInfo keystoreInfo,
                                //
                                File tempFolder)
    {
        this.targetApkFile = targetApkFile;
        this.proguardDictionary = proguardDictionary; ;
        this.keystoreInfo = keystoreInfo;
        this.tempFolder = tempFolder;
    }

    public void addFirstPassObfuscator(Obfuscator obfuscator)
    {
        if(obfuscator == null)
            throw new NullPointerException("obfuscator");

        this.firstPassObfuscatorList.add(obfuscator);
    }


    public void addLastPassObfuscator(Obfuscator obfuscator)
    {
        if(obfuscator == null)
            throw new NullPointerException("obfuscator");

        this.lastPassObfuscatorList.add(obfuscator);
    }

    public File obfuscateApk() throws Exception
    {
        ensureExists();

        File temporaryProguardDictionaryFile = File.createTempFile("proguard-dict", ".txt", tempFolder);

        try
        {
            FileUtils.deleteDirectory(tempFolder);
            tempFolder.mkdir();

            this.proguardDictionary.save(temporaryProguardDictionaryFile);

            ApkFile apkFile = new ApkFile(tempFolder, targetApkFile);

            apkFile.setDeleteWorkingFolder(true);
            apkFile.disassemble();

            List<DexFile> dexList = apkFile.getDexFileList();

            if(dexList.size() > ApkFile.allowedMaxDexCount)
                throw new IllegalStateException("Ignoring processing of APK with " + dexList.size() + " DEXes");

            for (DexFile dexFile : dexList)
            {
                obfuscateDexSmaliFirstPass(dexFile);
                obfuscateDexSmaliLastPass(dexFile);
                dexFile.assemble();
            }

            return apkFile.assemble(keystoreInfo);
        }
        finally
        {
            temporaryProguardDictionaryFile.delete();
        }
    }

    private void obfuscateDexSmaliFirstPass(DexFile dexFile) throws Exception
    {
        for(Obfuscator obfuscator : firstPassObfuscatorList)
            obfuscator.obfuscate(dexFile);
    }

    private void obfuscateDexSmaliLastPass(DexFile dexFile) throws Exception
    {
        for(Obfuscator obfuscator : lastPassObfuscatorList)
            obfuscator.obfuscate(dexFile);
    }

    private void ensureExists() throws Exception
    {
        if(targetApkFile == null || !targetApkFile.exists())
            throw new FileNotFoundException("APK file does not exists: " + targetApkFile);
    }
}

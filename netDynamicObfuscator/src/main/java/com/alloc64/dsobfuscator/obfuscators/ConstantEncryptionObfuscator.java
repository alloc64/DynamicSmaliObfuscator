/***********************************************************************
 * Copyright (c) 2019 Milan Jaitner                                   *
 * Distributed under the MIT software license, see the accompanying    *
 * file COPYING or https://www.opensource.org/licenses/mit-license.php.*
 ***********************************************************************/

package com.alloc64.dsobfuscator.obfuscators;

import com.alloc64.dsobfuscator.proguard.ProguardDictionary;
import com.alloc64.dsobfuscator.smali.model.SmaliClass;
import com.alloc64.dsobfuscator.smali.model.SmaliMethod;
import com.alloc64.dsobfuscator.smali.model.SmaliMethodSignature;
import com.alloc64.apktools.apk.DexFile;
import com.alloc64.apktools.apk.SmaliFile;

import java.util.List;

public class ConstantEncryptionObfuscator extends SmaliClassInjectingObfuscator
{
    private final StringEncryptionObfuscator stringEncryptionObfuscator;
    private final IntegerConstantEncryptionObfuscator integerEncryptionObfuscator;

    public ConstantEncryptionObfuscator(ProguardDictionary proguardDictionary)
    {
        super(proguardDictionary);

        this.stringEncryptionObfuscator = new StringEncryptionObfuscator(proguardDictionary);
        this.integerEncryptionObfuscator = new IntegerConstantEncryptionObfuscator(proguardDictionary);
    }

    @Override
    protected String name()
    {
        return "ConstantEncryptionObfuscator";
    }

    @Override
    public int getOccurrenceCount()
    {
        return stringEncryptionObfuscator.getOccurrenceCount() + integerEncryptionObfuscator.getOccurrenceCount();
    }

    public void setEncryptionKey(String encryptionKey)
    {
        stringEncryptionObfuscator.setEncryptionKey(encryptionKey);
    }

    public void setEncoderAlphabet(String encoderAlphabet)
    {
        stringEncryptionObfuscator.setEncoderAlphabet(encoderAlphabet);
    }

    @Override
    public void obfuscate(DexFile dexFile) throws Exception
    {
        this.setDexFile(dexFile);

        SmaliClass cryptClass = injectSmaliClass("smali/Crypt.smali", dexFile);

        SmaliMethod decryptStringMethod = cryptClass
                .findMethodOriginal(new SmaliMethodSignature("decrypt", "Ljava/lang/String;", "Ljava/lang/String;"));

        stringEncryptionObfuscator.setDecryptStringMethod(decryptStringMethod);

        SmaliMethod decryptIntegerMethod = cryptClass
                .findMethodOriginal(new SmaliMethodSignature("decrypt", "I", "I"));

        integerEncryptionObfuscator.setDecryptIntegerMethod(decryptIntegerMethod);

        super.obfuscate(dexFile);
    }

    @Override
    public void obfuscate(int fileNumber, SmaliFile smaliFile, List<String> lines) throws Exception
    {
        stringEncryptionObfuscator.obfuscate(fileNumber, smaliFile, lines);
        integerEncryptionObfuscator.obfuscate(fileNumber, smaliFile, lines);
    }

    @Override
    protected void markClassIgnored(SmaliFile smaliFile)
    {
        super.markClassIgnored(smaliFile);

        stringEncryptionObfuscator.markClassIgnored(smaliFile);
        integerEncryptionObfuscator.markClassIgnored(smaliFile);
    }
}

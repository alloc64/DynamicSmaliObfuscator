/***********************************************************************
 * Copyright (c) 2019 Milan Jaitner                                   *
 * Distributed under the MIT software license, see the accompanying    *
 * file COPYING or https://www.opensource.org/licenses/mit-license.php.*
 ***********************************************************************/

package com.alloc64.dsobfuscator;

import com.alloc64.dsobfuscator.obfuscators.ArithmeticBranchObfuscator;
import com.alloc64.dsobfuscator.obfuscators.ConstantEncryptionObfuscator;
import com.alloc64.dsobfuscator.obfuscators.DebugRemovalObuscator;
import com.alloc64.dsobfuscator.obfuscators.GotoObfuscator;
import com.alloc64.dsobfuscator.proguard.ProguardDictionary;
import com.alloc64.dsobfuscator.util.RandomUtils;
import com.alloc64.apktools.keystore.KeystoreInfo;

import java.io.File;

public class NetDynamicObfuscatorTestMain
{
    public static void main(String[] args) throws Exception
    {
        File root = new File("./tests");

        File apkFile = new File("obfuscated-apk.apk");

        File tempFolder = new File(root, "temp");

        KeystoreInfo keystoreInfo = new KeystoreInfo(new File("/Users/user/.android/debug.keystore"), "androiddebugkey", "android");

        ProguardDictionary proguardDictionary = ProguardDictionary.defaultDictionary();

        NetDynamicObfuscator obfuscator = new NetDynamicObfuscator(
                apkFile,
                proguardDictionary,
                keystoreInfo,
                tempFolder
        );

        String renamedPackageName = "renamed/package";

        ConstantEncryptionObfuscator constantEncryptionObfuscator = new ConstantEncryptionObfuscator(proguardDictionary);

        constantEncryptionObfuscator.setEncryptionKey("EpSE5uDgblbUVv9gb4WIqZFHiW");
        constantEncryptionObfuscator.setEncoderAlphabet("͑ϬϭϮϯϰϱϲϳϴϵ϶ϷϸϹϺϻϼϽϾϿЀЁЂЃЄЅІЇЈЉЊЋЌЍЎЏАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪ");
        constantEncryptionObfuscator.setRenamedPackageName(renamedPackageName);

        ArithmeticBranchObfuscator arithmeticBranchObfuscator = new ArithmeticBranchObfuscator(proguardDictionary);
        arithmeticBranchObfuscator.setRenamedPackageName(renamedPackageName);

        obfuscator.addFirstPassObfuscator(constantEncryptionObfuscator);
        obfuscator.addLastPassObfuscator(new DebugRemovalObuscator());
        obfuscator.addLastPassObfuscator(new GotoObfuscator());
        obfuscator.addLastPassObfuscator(arithmeticBranchObfuscator);

        obfuscator.obfuscateApk();
    }

    private static String generateKey()
    {
        /*
         * base key gen
         */
        String key = "";

        do
        {
            String c = new String(Character.toChars(RandomUtils.randInt(500, 1000)));

            if(!key.contains(c))
                key += c;
        }
        while(key.length() < 65);

        return key.substring(0, 63);
    }
}

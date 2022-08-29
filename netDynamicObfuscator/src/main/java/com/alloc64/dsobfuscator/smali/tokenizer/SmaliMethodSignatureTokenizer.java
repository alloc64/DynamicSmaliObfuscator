/***********************************************************************
 * Copyright (c) 2019 Milan Jaitner                                   *
 * Distributed under the MIT software license, see the accompanying    *
 * file COPYING or https://www.opensource.org/licenses/mit-license.php.*
 ***********************************************************************/

package com.alloc64.dsobfuscator.smali.tokenizer;

import com.alloc64.dsobfuscator.smali.model.SmaliAccessModifier;
import com.alloc64.dsobfuscator.smali.model.SmaliClass;
import com.alloc64.dsobfuscator.smali.model.SmaliMethodSignature;

import com.alloc64.apktools.smali.Smali;

import java.util.regex.Matcher;

public class SmaliMethodSignatureTokenizer extends SmaliTokenizer<SmaliMethodSignature>
{
    @Override
    public SmaliMethodSignature tokenize(String line, SmaliClass clazz)
    {
        Matcher m = Smali.methodPattern.matcher(line);

        if(m.matches())
        {
            return new SmaliMethodSignature(
                    new SmaliAccessModifier(m.group(Smali.ACCESS_MODIFIER)),
                    m.group(Smali.METHOD_NAME),
                    m.group(Smali.PARAMTERS),
                    m.group(Smali.RETURN_TYPE),
                    clazz
            );
        }

        return null;
    }
}


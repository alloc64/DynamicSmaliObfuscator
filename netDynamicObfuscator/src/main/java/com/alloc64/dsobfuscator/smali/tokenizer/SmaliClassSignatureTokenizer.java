/***********************************************************************
 * Copyright (c) 2019 Milan Jaitner                                   *
 * Distributed under the MIT software license, see the accompanying    *
 * file COPYING or https://www.opensource.org/licenses/mit-license.php.*
 ***********************************************************************/

package com.alloc64.dsobfuscator.smali.tokenizer;

import com.alloc64.dsobfuscator.smali.model.SmaliAccessModifier;
import com.alloc64.dsobfuscator.smali.model.SmaliClass;
import com.alloc64.dsobfuscator.smali.model.SmaliClassName;
import com.alloc64.dsobfuscator.smali.model.SmaliClassSignature;
import com.alloc64.apktools.smali.Smali;

import java.util.regex.Matcher;

public class SmaliClassSignatureTokenizer extends SmaliTokenizer<SmaliClassSignature>
{
    @Override
    public SmaliClassSignature tokenize(String line, SmaliClass clazz)
    {
        Matcher m = Smali.classPattern.matcher(line);

        if(m.matches())
        {
            return new SmaliClassSignature(
                    new SmaliAccessModifier(m.group(Smali.ACCESS_MODIFIER)),
                    new SmaliClassName(m.group(Smali.FULL_CLASS_NAME))
            );
        }

        return null;
    }
}

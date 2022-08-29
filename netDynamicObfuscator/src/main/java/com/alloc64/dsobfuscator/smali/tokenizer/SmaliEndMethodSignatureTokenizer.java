/***********************************************************************
 * Copyright (c) 2019 Milan Jaitner                                   *
 * Distributed under the MIT software license, see the accompanying    *
 * file COPYING or https://www.opensource.org/licenses/mit-license.php.*
 ***********************************************************************/

package com.alloc64.dsobfuscator.smali.tokenizer;

import com.alloc64.dsobfuscator.smali.model.SmaliClass;
import com.alloc64.dsobfuscator.smali.model.SmaliEndMethodSignature;

public class SmaliEndMethodSignatureTokenizer extends SmaliTokenizer<SmaliEndMethodSignature>
{
    @Override
    public SmaliEndMethodSignature tokenize(String line, SmaliClass clazz)
    {
        return new SmaliEndMethodSignature();
    }
}

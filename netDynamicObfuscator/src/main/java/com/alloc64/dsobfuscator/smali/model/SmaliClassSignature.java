/***********************************************************************
 * Copyright (c) 2019 Milan Jaitner                                   *
 * Distributed under the MIT software license, see the accompanying    *
 * file COPYING or https://www.opensource.org/licenses/mit-license.php.*
 ***********************************************************************/

package com.alloc64.dsobfuscator.smali.model;

public class SmaliClassSignature extends SmaliObject
{
    public static final String TOKEN = ".class";

    private SmaliAccessModifier accessModifier;
    private SmaliClassName className;

    public SmaliClassSignature(SmaliAccessModifier accessModifier, SmaliClassName className)
    {
        this.accessModifier = accessModifier;
        this.className = className;
    }

    public SmaliAccessModifier getAccessModifier()
    {
        return accessModifier;
    }

    public SmaliClassName getClassName()
    {
        return className;
    }

    @Override
    public String toString()
    {
        String accessModifierStr = accessModifier.toString();

        if(accessModifier.size() > 0)
            return String.format("%s %s %s", TOKEN, accessModifierStr, className);

        return String.format("%s %s", TOKEN, className);
    }
}

/***********************************************************************
 * Copyright (c) 2019 Milan Jaitner                                   *
 * Distributed under the MIT software license, see the accompanying    *
 * file COPYING or https://www.opensource.org/licenses/mit-license.php.*
 ***********************************************************************/

package com.alloc64.dsobfuscator.smali.model;

public class SmaliInterfaceSignature extends SmaliObject
{
    public static final String TOKEN = ".implements";

    private SmaliClassName className;

    public SmaliInterfaceSignature(SmaliClassName className)
    {
        this.className = className;
    }

    public SmaliClassName getClassName()
    {
        return className;
    }

    @Override
    public String toString()
    {
        return TOKEN + " " + className;
    }
}

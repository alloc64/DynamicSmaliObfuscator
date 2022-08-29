/***********************************************************************
 * Copyright (c) 2019 Milan Jaitner                                   *
 * Distributed under the MIT software license, see the accompanying    *
 * file COPYING or https://www.opensource.org/licenses/mit-license.php.*
 ***********************************************************************/

package com.alloc64.dsobfuscator.smali.model;

public class SmaliPackageName
{
    private String packageName;
    private String originalPackageName;

    public SmaliPackageName(String packageName)
    {
        this.packageName = this.originalPackageName = packageName;
    }

    public String getPackageName()
    {
        return packageName;
    }

    public void setPackageName(String packageName)
    {
        if(!packageName.endsWith("/"))
            packageName = packageName + "/";

        this.packageName = packageName;
    }

    public String getOriginalPackageName()
    {
        return originalPackageName;
    }

    @Override
    public String toString()
    {
        return "L" + packageName;
    }
}

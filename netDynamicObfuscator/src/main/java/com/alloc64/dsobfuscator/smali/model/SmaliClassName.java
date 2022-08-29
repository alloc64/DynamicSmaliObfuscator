/***********************************************************************
 * Copyright (c) 2019 Milan Jaitner                                   *
 * Distributed under the MIT software license, see the accompanying    *
 * file COPYING or https://www.opensource.org/licenses/mit-license.php.*
 ***********************************************************************/

package com.alloc64.dsobfuscator.smali.model;

public class SmaliClassName
{
    private SmaliPackageName packageName;
    private String className;
    private String originalClassName;

    public SmaliClassName(String fullClassName)
    {
        String[] arr = fullClassName.split("/");

        int classNameIdx = arr.length-1;

        StringBuilder packageName = new StringBuilder();

        if(arr.length > 1)
            for(int i = 0; i < classNameIdx; i++)
            {
                packageName.append(arr[i]);
                packageName.append("/");
            }

        this.packageName = new SmaliPackageName(packageName.toString());
        this.className = this.originalClassName = arr[classNameIdx];
    }

    public SmaliPackageName getPackageName()
    {
        return packageName;
    }

    public String getClassName()
    {
        return className;
    }

    public String getFullClassName()
    {
        return packageName + className + ";";
    }

    public void setClassName(String className)
    {
        this.className = className;
    }

    public String getOriginalClassName()
    {
        return originalClassName;
    }

    public String getFullOriginalClassName()
    {
        return "L" + packageName.getOriginalPackageName() + originalClassName + ";";
    }

    public boolean classNameHasChanged()
    {
        return !className.equals(originalClassName);
    }

    @Override
    public String toString()
    {
        return getFullClassName();
    }
}

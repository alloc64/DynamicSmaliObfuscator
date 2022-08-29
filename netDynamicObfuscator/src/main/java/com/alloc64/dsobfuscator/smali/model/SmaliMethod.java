/***********************************************************************
 * Copyright (c) 2019 Milan Jaitner                                   *
 * Distributed under the MIT software license, see the accompanying    *
 * file COPYING or https://www.opensource.org/licenses/mit-license.php.*
 ***********************************************************************/

package com.alloc64.dsobfuscator.smali.model;

import java.util.ArrayList;
import java.util.List;

public class SmaliMethod
{
    private int lineNumber;
    private SmaliMethodSignature signature;
    private final SmaliClass clazz;

    private List<String> body = new ArrayList<>();

    public SmaliMethod(int lineNumber, SmaliMethodSignature signature, SmaliClass clazz)
    {
        this.lineNumber = lineNumber;
        this.signature = signature;
        this.clazz = clazz;
    }

    public int getLineNumber()
    {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber)
    {
        this.lineNumber = lineNumber;
    }

    public SmaliMethodSignature getSignature()
    {
        return signature;
    }

    public void setSignature(SmaliMethodSignature signature)
    {
        this.signature = signature;
    }

    public List<String> getBody()
    {
        return body;
    }

    public void setBody(List<String> body)
    {
        this.body = body;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        sb.append(signature);
        sb.append("\n");

        boolean classPathRequired = false;

        String fullClassName = null;
        String fullOriginalClassName = null;

        SmaliClassName className = clazz.getClassSignature().getClassName();

        if(className.classNameHasChanged())
        {
            fullClassName = String.format("%s->", className.getFullClassName());
            fullOriginalClassName = String.format("%s->", className.getFullOriginalClassName());

            classPathRequired = true;
        }

        for (int i = 0; i < body.size(); i++)
        {
            String line = body.get(i);

            if(classPathRequired && line.indexOf(fullOriginalClassName) > 0)
                line = line.replace(fullOriginalClassName, fullClassName);

            sb.append(line);
            sb.append("\n");

            if(i < body.size()-1)
                sb.append("\n");
        }

        sb.append(".end method\n\n");

        return sb.toString();
    }
}

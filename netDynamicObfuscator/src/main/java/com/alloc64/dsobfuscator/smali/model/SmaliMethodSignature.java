/***********************************************************************
 * Copyright (c) 2019 Milan Jaitner                                   *
 * Distributed under the MIT software license, see the accompanying    *
 * file COPYING or https://www.opensource.org/licenses/mit-license.php.*
 ***********************************************************************/


package com.alloc64.dsobfuscator.smali.model;

public class SmaliMethodSignature extends SmaliObject
{
    public static final String TOKEN = ".method";

    private SmaliAccessModifier accessModifier;
    private String methodName;
    private String originalMethodName;
    private String parameters;
    private String returnType;
    private SmaliClass clazz;

    public SmaliMethodSignature(String methodName,
                                String parameters,
                                String returnType)
    {
        this.methodName = this.originalMethodName = methodName;
        this.parameters = empty(parameters);
        this.returnType = returnType;
    }

    public SmaliMethodSignature(SmaliAccessModifier accessModifier,
                                String methodName,
                                String parameters,
                                String returnType,
                                SmaliClass clazz)
    {
        this(methodName, parameters, returnType);

        this.accessModifier = accessModifier;
        this.clazz = clazz;
    }

    public boolean isConstructor()
    {
        return accessModifier.contains("constructor");
    }

    public boolean isPublic()
    {
        return accessModifier.contains("public");
    }

    public SmaliAccessModifier getAccessModifier()
    {
        return accessModifier;
    }

    public String getMethodName()
    {
        return methodName;
    }

    public String getOriginalMethodName()
    {
        return originalMethodName;
    }

    public void setMethodName(String methodName)
    {
        this.methodName = methodName;
    }

    public String getParameters()
    {
        return parameters;
    }

    public void setParameters(String parameters)
    {
        this.parameters = parameters;
    }

    public String getReturnType()
    {
        return returnType;
    }

    public void setReturnType(String returnType)
    {
        this.returnType = returnType;
    }

    public boolean equalsNoAccessModifier(SmaliMethodSignature sig)
    {
        return sig.getMethodName().equals(originalMethodName) &&
                sig.getParameters().equals(parameters) &&
                sig.getReturnType().equals(returnType);
    }

    public String getCallSignature()
    {
        return String.format("%s->%s(%s)%s",
                clazz.getClassSignature().getClassName().getFullClassName(),
                methodName,
                parameters,
                returnType
        );
    }

    @Override
    public String toString()
    {
        return String.format("%s %s %s(%s)%s", TOKEN, accessModifier, methodName, parameters, returnType);
    }
}


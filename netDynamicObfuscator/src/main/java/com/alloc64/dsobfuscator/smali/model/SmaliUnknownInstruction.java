/***********************************************************************
 * Copyright (c) 2019 Milan Jaitner                                   *
 * Distributed under the MIT software license, see the accompanying    *
 * file COPYING or https://www.opensource.org/licenses/mit-license.php.*
 ***********************************************************************/

package com.alloc64.dsobfuscator.smali.model;

public class SmaliUnknownInstruction
{
    private int lineNumber;
    private String instruction;

    public SmaliUnknownInstruction(int lineNumber, String instruction)
    {
        this.lineNumber = lineNumber;
        this.instruction = instruction;
    }

    public int getLineNumber()
    {
        return lineNumber;
    }

    public String getInstruction()
    {
        return instruction;
    }

    @Override
    public String toString()
    {
        return instruction;
    }
}

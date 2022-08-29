/***********************************************************************
 * Copyright (c) 2019 Milan Jaitner                                   *
 * Distributed under the MIT software license, see the accompanying    *
 * file COPYING or https://www.opensource.org/licenses/mit-license.php.*
 ***********************************************************************/


package com.alloc64.dsobfuscator.smali.model;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;

public class SmaliAccessModifier extends ArrayList<String>
{
    public SmaliAccessModifier(String accessModifier)
    {
        if(!StringUtils.isEmpty(accessModifier))
            addAll(Arrays.asList(accessModifier.split(" ")));
    }

    @Override
    public String toString()
    {
        String result = String.join(" ", this);

        if(result.length() < 1)
            return "";

        return result;
    }
}

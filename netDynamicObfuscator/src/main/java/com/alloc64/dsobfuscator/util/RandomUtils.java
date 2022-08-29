/***********************************************************************
 * Copyright (c) 2019 Milan Jaitner                                   *
 * Distributed under the MIT software license, see the accompanying    *
 * file COPYING or https://www.opensource.org/licenses/mit-license.php.*
 ***********************************************************************/

package com.alloc64.dsobfuscator.util;

import java.util.List;
import java.util.Random;

public class RandomUtils
{
    private static Random rand = new Random();

    public static int randInt(int min, int max)
    {
        return rand.nextInt((max - min) + 1) + min;
    }

    public static <T> T randomEntry(List<T> list)
    {
        if(list == null || list.size() < 1)
            return null;

        return list.get(RandomUtils.randInt(0, list.size() - 1));
    }
}

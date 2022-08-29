/***********************************************************************
 * Copyright (c) 2019 Milan Jaitner                                   *
 * Distributed under the MIT software license, see the accompanying    *
 * file COPYING or https://www.opensource.org/licenses/mit-license.php.*
 ***********************************************************************/

package net.dynamico;

public class MethodGuard
{
    private static long lastPropertyCheckTimestamp = -1;
    private static boolean isValidState = false;

    public static boolean initialized()
    {
        if(lastPropertyCheckTimestamp < 0 || System.currentTimeMillis() - lastPropertyCheckTimestamp > 10 * 1000)
        {
            lastPropertyCheckTimestamp = System.currentTimeMillis();
            isValidState = System.getProperty("__0", null) == null; // nonempty system property causes crash via fail method in Aritmetic Branch Obfuscator
        }

        return isValidState;
    }

    public static void fail()
    {
        int i = 0;

        StringBuilder sb = new StringBuilder();

        do
        {
            sb.append(193);
            sb.append(3);

            for(int j = 0; j < 8; j++)
            {
                sb.append(13);
                sb.append(45);
                sb.append(17);
                sb.append(93);
                sb.append(182);
                sb.append(0);
                sb.append(60);
                sb.append(22);
                sb.append(99);
                sb.append(49);
                sb.append(sb.toString());
                sb.append(48);
            }
        }
        while(i++ < 0x9994 + 0xFF37);
    }
}

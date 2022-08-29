/***********************************************************************
 * Copyright (c) 2019 Milan Jaitner                                   *
 * Distributed under the MIT software license, see the accompanying    *
 * file COPYING or https://www.opensource.org/licenses/mit-license.php.*
 ***********************************************************************/

package com.android.test;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import net.dynamico.Crypt;
import net.dynamico.MethodGuard;

import java.util.Objects;

public class MainActivity extends Activity
{
    private static final String TAG = "APP";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Crypt.decrypt(1); // sample call for tests

        // String encryption tests
        stringEquals("Test string 1", R.string.test_string1);
        stringEquals("頁設是煵엌嫠쯦案煪㍱從つ浳浤搰㍭煤洳橱橱迎事網計簡大㍵畱煵田煱둻睤㌹", R.string.test_string2);
        stringEquals("頁設是煵엌嫠쯦案煪㍱從つ浳浤搰㍭煤洳橱橱迎事網計簡大㍵畱煵田煱둻睤㌹頁設是煵엌嫠쯦案煪㍱從つ浳浤搰㍭煤洳橱橱迎事網計簡大㍵畱煵田煱둻睤㌹頁設是煵엌嫠쯦案煪㍱從つ浳浤搰㍭煤洳橱橱迎事網計簡大㍵畱煵田煱둻睤㌹頁設是煵엌嫠쯦案煪㍱從つ浳浤搰㍭煤洳橱橱迎事網計簡大㍵畱煵田煱둻睤㌹頁設是煵엌嫠쯦案煪㍱從つ浳浤搰㍭煤洳橱橱迎事網計簡大㍵畱煵田煱둻睤㌹頁設是煵엌嫠쯦案煪㍱從つ浳浤搰㍭煤洳橱橱迎事網計簡大㍵畱煵田煱둻睤㌹頁設是煵엌嫠쯦案煪㍱從つ浳浤搰㍭煤洳橱橱迎事網計簡大㍵畱煵田煱둻睤㌹頁設是煵엌嫠쯦案煪㍱從つ浳浤搰㍭煤洳橱橱迎事網計簡大㍵畱煵田煱둻睤㌹頁設是煵엌嫠쯦案煪㍱從つ浳浤搰㍭煤洳橱橱迎事網計簡大㍵畱煵田煱둻睤㌹頁設是煵엌嫠쯦案煪㍱從つ浳浤搰㍭煤洳橱橱迎事網計簡大㍵畱煵田煱둻睤㌹頁設是煵엌嫠쯦案煪㍱從つ浳浤搰㍭煤洳橱橱迎事網計簡大㍵畱煵田煱둻睤㌹頁設是煵엌嫠쯦案煪㍱從つ浳浤搰㍭煤洳橱橱迎事網計簡大㍵畱煵田煱둻睤㌹頁設是煵엌嫠쯦案煪㍱從つ浳浤搰㍭煤洳橱橱迎事網計簡大㍵畱煵田煱둻睤㌹頁設是煵엌嫠쯦案煪㍱從つ浳浤搰㍭煤洳橱橱迎事網計簡大㍵畱煵田煱둻睤㌹頁設是煵엌嫠쯦案煪㍱從つ浳浤搰㍭煤洳橱橱迎事網計簡大㍵畱煵田煱둻睤㌹頁設是煵엌嫠쯦案煪㍱從つ浳浤搰㍭煤洳橱橱迎事網計簡大㍵畱煵田煱둻睤㌹頁設是煵엌嫠쯦案煪㍱從つ浳浤搰㍭煤洳橱橱迎事網計簡大㍵畱煵田煱둻睤㌹頁設是煵엌嫠쯦案煪㍱從つ浳浤搰㍭煤洳橱橱迎事網計簡大㍵畱煵田煱둻睤㌹頁設是煵엌嫠쯦案煪㍱從つ浳浤搰㍭煤洳橱橱迎事網計簡大㍵畱煵田煱둻睤㌹頁設是煵엌嫠쯦案煪㍱從つ浳浤搰㍭煤洳橱橱迎事網計簡大㍵畱煵田煱둻睤㌹頁設是煵엌嫠쯦案煪㍱從つ浳浤搰㍭煤洳橱橱迎事網計簡大㍵畱煵田煱둻睤㌹頁設是煵엌嫠쯦案煪㍱從つ浳浤搰㍭煤洳橱橱迎事網計簡大㍵畱煵田煱둻睤㌹頁設是煵엌嫠쯦案煪㍱從つ浳浤搰㍭煤洳橱橱迎事網計簡大㍵畱煵田煱둻睤㌹頁設是煵엌嫠쯦案煪㍱從つ浳浤搰㍭煤洳橱橱迎事網計簡大㍵畱煵田煱둻睤㌹頁設是煵엌嫠쯦案煪㍱從つ浳浤搰㍭煤洳橱橱迎事網計簡大㍵畱煵田煱둻睤㌹頁設是煵엌嫠쯦案煪㍱從つ浳浤搰㍭煤洳橱橱迎事網計簡大㍵畱煵田煱둻睤㌹頁設是煵엌嫠쯦案煪㍱從つ浳浤搰㍭煤洳橱橱迎事網計簡大㍵畱煵田煱둻睤㌹頁設是煵엌嫠쯦案煪㍱從つ浳浤搰㍭煤洳橱橱迎事網計簡大㍵畱煵田煱둻睤㌹頁設是煵엌嫠쯦案煪㍱從つ浳浤搰㍭煤洳橱橱迎事網計簡大㍵畱煵田煱둻睤㌹頁設是煵엌嫠쯦案煪㍱從つ浳浤搰㍭煤洳橱橱迎事網計簡大㍵畱煵田煱둻睤㌹頁設是煵엌嫠쯦案煪㍱從つ浳浤搰㍭煤洳橱橱迎事網計簡大㍵畱煵田煱둻睤㌹頁設是煵엌嫠쯦案煪㍱從つ浳浤搰㍭煤洳橱橱迎事網計簡大㍵畱煵田煱둻睤㌹頁設是煵엌嫠쯦案煪㍱從つ浳浤搰㍭煤洳橱橱迎事網計簡大㍵畱煵田煱둻睤㌹頁設是煵엌嫠쯦案煪㍱從つ浳浤搰㍭煤洳橱橱迎事網計簡大㍵畱煵田煱둻睤㌹頁設是煵엌嫠쯦案煪㍱從つ浳浤搰㍭煤洳橱橱迎事網計簡大㍵畱煵田煱둻睤㌹頁設是煵엌嫠쯦案煪㍱從つ浳浤搰㍭煤洳橱橱迎事網計簡大㍵畱煵田煱둻睤㌹頁設是煵엌嫠쯦案煪㍱從つ浳浤搰㍭煤洳橱橱迎事網計簡大㍵畱煵田煱둻睤㌹頁設是煵엌嫠쯦案煪㍱從つ浳浤搰㍭煤洳橱橱迎事網計簡大㍵畱煵田煱둻睤㌹頁設是煵엌嫠쯦案煪㍱從つ浳浤搰㍭煤洳橱橱迎事網計簡大㍵畱煵田煱둻睤㌹頁設是煵엌嫠쯦案煪㍱從つ浳浤搰㍭煤洳橱橱迎事網計簡大㍵畱煵田煱둻睤㌹頁設是煵엌嫠쯦案煪㍱從つ浳浤搰㍭煤洳橱橱迎事網計簡大㍵畱煵田煱둻睤㌹頁設是煵엌嫠쯦案煪㍱從つ浳浤搰㍭煤洳橱橱迎事網計簡大㍵畱煵田煱둻睤㌹頁設是煵엌嫠쯦案煪㍱從つ浳浤搰㍭煤洳橱橱迎事網計簡大㍵畱煵田煱둻睤㌹頁設是煵엌嫠쯦案煪㍱從つ浳浤搰㍭煤洳橱橱迎事網計簡大㍵畱煵田煱둻睤㌹頁設是煵엌嫠쯦案煪㍱從つ浳浤搰㍭煤洳橱橱迎事網計簡大㍵畱煵田煱둻睤㌹頁設是煵엌嫠쯦案煪㍱從つ浳浤搰㍭煤洳橱橱迎事網計簡大㍵畱煵田煱둻睤㌹頁設是煵엌嫠쯦案煪㍱從つ浳浤搰㍭煤洳橱橱迎事網計簡大㍵畱煵田煱둻睤㌹頁設是煵엌嫠쯦案煪㍱從つ浳浤搰㍭煤洳橱橱迎事網計簡大㍵畱煵田煱둻睤㌹頁設是煵엌嫠쯦案煪㍱從つ浳浤搰㍭煤洳橱橱迎事網計簡大㍵畱煵田煱둻睤㌹頁設是煵엌嫠쯦案煪㍱從つ浳浤搰㍭煤洳橱橱迎事網計簡大㍵畱煵田煱둻睤㌹頁設是煵엌嫠쯦案煪㍱從つ浳浤搰㍭煤洳橱橱迎事網計簡大㍵畱煵田煱둻睤㌹頁設是煵엌嫠쯦案煪㍱從つ浳浤搰㍭煤洳橱橱迎事網計簡大㍵畱煵田煱둻睤㌹頁設是煵엌嫠쯦案煪㍱從つ浳浤搰㍭煤洳橱橱迎事網計簡大㍵畱煵田煱둻睤㌹頁設是煵엌嫠쯦案煪㍱從つ浳浤搰㍭煤洳橱橱迎事網計簡大㍵畱煵田煱둻睤㌹頁設是煵엌嫠쯦案煪㍱從つ浳浤搰㍭煤洳橱橱迎事網計簡大㍵畱煵田煱둻睤㌹頁設是煵엌嫠쯦案煪㍱從つ浳浤搰㍭煤洳橱橱迎事網計簡大㍵畱煵田煱둻睤㌹", R.string.test_string3);

        // Integer encryption tests (floats are retained in first phase)
        integerEquals(16, 16f); // Only integers > 0xF (15) are obfuscated
        integerEquals(10000000, 10000000f);
        integerEquals(2147483647, 2147483647f );

        // Method guard test
        methodGuardTest();

        // Aritmetic branch injection test
        aritmeticBranchTest("Parameter 1", "Parameter 2", 143);
    }

    private void stringEquals(String encrypted, int rid)
    {
        String res = getString(rid);

        boolean equals = Objects.equals(encrypted, res);

        if(equals)
            Log.i(TAG, "SUCCESS: " + encrypted + " equals " + res);
        else
            throw new IllegalStateException(encrypted + " not equal " + res);
    }

    private void integerEquals(Integer encrypted, float originalValue)
    {
        Integer res = (int)originalValue;

        boolean equals = Objects.equals(encrypted, res);

        if(equals)
            Log.i(TAG, "SUCCESS: " + encrypted + " equals " + res);
        else
            throw new IllegalStateException(encrypted + " not equal " + res);
    }

    private void methodGuardTest()
    {
        // Force method guard to trigger
        // System.setProperty("__0", "oops");

        if(!MethodGuard.initialized())
        {
            MethodGuard.fail();
        }
    }

    private void aritmeticBranchTest(String p0, String p1, int p2)
    {
        Log.i(TAG, "Aritmetic branch test succeeded.");
    }
}

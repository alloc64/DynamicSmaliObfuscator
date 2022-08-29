package com.alloc64.dsobfuscator.crypt;

import java.util.LinkedHashMap;
import java.util.Map;

public class EncryptionKeys
{
    private static Map keys = new LinkedHashMap();
    private static final String prefix = "p00p";

    public static Object resolve(Object encryptionKey)
    {
        if(encryptionKey instanceof String)
        {
            Object key = keys.get(encryptionKey);

            if(key == null)
            {
                try
                {
                    key = Class.forName("javax.crypto.spec.SecretKeySpec")
                            .getDeclaredConstructor(byte[].class, String.class)
                            .newInstance(((String) prefix + encryptionKey).getBytes(), "AES");

                    keys.put(encryptionKey, key);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            return key;
        }

        return encryptionKey;
    }
}

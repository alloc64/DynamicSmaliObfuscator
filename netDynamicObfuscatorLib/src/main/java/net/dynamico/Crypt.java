/***********************************************************************
 * Copyright (c) 2019 Milan Jaitner                                   *
 * Distributed under the MIT software license, see the accompanying    *
 * file COPYING or https://www.opensource.org/licenses/mit-license.php.*
 ***********************************************************************/

package net.dynamico;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.HashMap;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;

public class Crypt
{
    private static final int BASE = 64;
    private static final byte[] AES = new byte[] { 65, 69, 83 };
    private static final byte[] UTF_8 = new byte[] { 85, 84, 70, 45, 56 };

    private static final String cipherType = createString(new byte[] { 65, 69, 83, 47, 67, 66, 67, 47, 80, 75, 67, 83, 53, 80, 65, 68, 68, 73, 78, 71 });
    private static final byte[] encryptionKeyPrefix = new byte[] { 104, 97, 109, 114, 97, 107 };
    private static final byte[] secretKeySpecClass = new byte[] { 106, 97, 118, 97, 120, 46, 99, 114, 121, 112, 116, 111, 46, 115, 112, 101, 99, 46, 83, 101, 99, 114, 101, 116, 75, 101, 121, 83, 112, 101, 99 };

    private static HashMap<Character, Integer> charPositionMap;
    private static Object encryptionKey;

    static
    {
        // Constants dependent on string obfuscator
        // byte representation can be retrieved with convertStringToByteArrayNotation method

        setEncryptionKey(new byte[]{ 69, 112, 83, 69, 53, 117, 68, 103, 98, 108, 98, 85, 86, 118, 57, 103, 98, 52, 87, 73, 113, 90, 70, 72, 105, 87 });
        setBase64Charset(new byte[] { -49, -84, -49, -83, -49, -82, -49, -81, -49, -80, -49, -79, -49, -78, -49, -77, -49, -76, -49, -75, -49, -74, -49, -73, -49, -72, -49, -71, -49, -70, -49, -69, -49, -68, -49, -67, -49, -66, -49, -65, -48, -128, -48, -127, -48, -126, -48, -125, -48, -124, -48, -123, -48, -122, -48, -121, -48, -120, -48, -119, -48, -118, -48, -117, -48, -116, -48, -115, -48, -114, -48, -113, -48, -112, -48, -111, -48, -110, -48, -109, -48, -108, -48, -107, -48, -106, -48, -105, -48, -104, -48, -103, -48, -102, -48, -101, -48, -100, -48, -99, -48, -98, -48, -97, -48, -96, -48, -95, -48, -94, -48, -93, -48, -92, -48, -91, -48, -90, -48, -89, -48, -88, -48, -87, -48, -86, -48, -85 });

    }

    public static int decrypt(int encrypted)
    {
        return Integer.reverse(encrypted);
    }

    public static String decrypt(String encrypted)
    {
        if(encrypted == null || encrypted.length() < 1)
            return encrypted;

        try
        {
            byte[] data = decrypt(decode(encrypted));

            if(data != null)
                return new String(data);
        }
        catch (Exception e)
        {
            if(BuildConfig.DEBUG)
                e.printStackTrace();
        }

        return null;
    }

    /**
     * Decrypts data with AES with specified @encryptionKey
     *
     * @param data
     * @return decrypted byte[] data
     */
    private static byte[] decrypt(byte[] data)
    {
        if (data == null || data.length < 1)
            return null;

        try
        {
            ByteBuffer byteBuffer = ByteBuffer.wrap(data);

            int ivLength = byteBuffer.getInt();

            if(BuildConfig.DEBUG)
            {
                if (ivLength < 12 || ivLength > 16)
                    throw new IllegalArgumentException("Invalid IV.");
            }

            byte[] iv = new byte[ivLength];
            byteBuffer.get(iv);

            byte[] cipherText = new byte[byteBuffer.remaining()];
            byteBuffer.get(cipherText);

            final Cipher cipher = Cipher.getInstance(cipherType);
            cipher.init(Cipher.DECRYPT_MODE, (Key) encryptionKey, new IvParameterSpec(iv));

            return cipher.doFinal(cipherText);
        }
        catch (Exception e)
        {
            if(BuildConfig.DEBUG)
                e.printStackTrace();
        }

        return null;
    }

    private static byte[] decode(String base64String)
    {
        byte[] decodedBytes = new byte[(int) Math.floor(base64String.length() * 6d / 8d)];

        int missingBits = 2;
        int stringPos = 0;

        for (int i = 0; i < decodedBytes.length; i++)
        {
            decodedBytes[i] = (byte) (int) charPositionMap.get(base64String.charAt(stringPos));
            decodedBytes[i] = (byte) (decodedBytes[i] << missingBits);
            byte b = (byte) (int) charPositionMap.get(base64String.charAt(stringPos + 1));
            b = (byte) (b >> (6 - missingBits));
            decodedBytes[i] = (byte) (decodedBytes[i] | b);

            missingBits = (missingBits + 2) % 8;

            if (missingBits == 0)
            {
                stringPos++;
                missingBits += 2;
            }

            stringPos++;
        }

        return decodedBytes;
    }

    private static String createString(byte[] bytes)
    {
        return new String(bytes);
    }

    private static void setEncryptionKey(byte[] encryptionKey)
    {
        try
        {
            Crypt.encryptionKey = Class.forName(createString(secretKeySpecClass))
                    .getDeclaredConstructor(byte[].class, String.class)
                    .newInstance(((String) createString(encryptionKeyPrefix) + createString(encryptionKey)).getBytes(createString(UTF_8)), createString(AES));
        }
        catch (Exception e)
        {
            if(BuildConfig.DEBUG)
                e.printStackTrace();
        }
    }

    /**
     * The base64Charset must be a String containing exactly 64 distinct characters.<br>
     * If it does not a {@link IllegalStateException} is thrown.
     *
     * @param base64CharsetBytes
     */
    private static void setBase64Charset(byte[] base64CharsetBytes)
    {
        String base64Charset = new String(base64CharsetBytes);

        if(BuildConfig.DEBUG)
        {
            if (base64Charset.length() != BASE)
                throw new IllegalStateException("The charset must be 64 characters long.");
        }

        char[] base64Chars = base64Charset.toCharArray();
        charPositionMap = new HashMap<>();

        for (int i = 0; i < base64Chars.length; i++)
            charPositionMap.put(base64Chars[i], i);

        if(BuildConfig.DEBUG)
        {
            if (charPositionMap.size() != BASE)
                throw new IllegalStateException("The charset must contain 64 unique characters.");
        }
    }

    public static void main(String[] args) {
        String alphabet = "";

        for(int i = 0; i < 64; i++) {
            alphabet += new String(Character.toChars(i + 1004));
        }

        System.out.println(convertStringToByteArrayNotation("EpSE5uDgblbUVv9gb4WIqZFHiW"));
        System.out.println(convertStringToByteArrayNotation(alphabet));
    }

    private static String convertStringToByteArrayNotation(String input)
    {
        byte[] val = input.getBytes(StandardCharsets.UTF_8);

        StringBuilder s = new StringBuilder("{ ");

        for(int i = 0; i < val.length; i++)
        {
            s.append(val[i]);

            if(i < val.length-1)
                s.append(", ");
        }

        s.append(" };");

        return s.toString();
    }
}

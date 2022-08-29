
package com.alloc64.dsobfuscator.encoders;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

/**
 * This CustomBaseEncoder class provides the functionality to encode/decode a byte array to/from a Base64 String.<br>
 * This CustomBaseEncoder class does NOT follow the RFC 3548 or RFC 4648 specification of Base64 as the used charset can be specified
 * and no padding characters are used.<br>
 * So only the characters provided by the user by calling the {@link #setBase64Charset(String)} will appear in the encoded String and no other characters
 * like equal signs '=' (sometimes used for padding in Base64) will appear.
 *
 */
public class BaseEncoder
{
    public static final int BASE = 64;

    private char[] base64Chars;

    private HashMap<Character, Integer> charPositionMap;

    public BaseEncoder(String charset)
    {
        setBase64Charset(charset);
    }

    public String encode(byte[] byteArr) throws UnsupportedEncodingException
    {
        StringBuilder sb = new StringBuilder();

        int bitsToNext = 2;
        byte rem = 0;

        for (int i = 0; i < byteArr.length; i++)
        {
            byte b = byteArr[i];

            if (bitsToNext == 2)
            {
                b = (byte) ((byteArr[i] >> 2) & 0x3f);
                rem = (byte) ((byteArr[i] << (8 - 4)) & 0x3f);
            }
            else if (bitsToNext == 4)
            {
                b = (byte) ((byteArr[i] >> 4) & 0x0f);
                b = (byte) (b | rem);
                rem = (byte) ((byteArr[i] << (8 - 6)) & 0x3f);
            }
            else if (bitsToNext == 6)
            {
                b = (byte) ((byteArr[i] >> 6) & 0x03);
                b = (byte) (b | rem);
                rem = (byte) (byteArr[i] & 0x3f);
            }
            else if (bitsToNext == 0)
            {
                b = rem;
                rem = 0;
            }
            bitsToNext = (bitsToNext + 2) % 8;

            if (bitsToNext == 0)
                i--;

            sb.append(base64Chars[b]);
        }

        if (rem != 0)
        {
            sb.append(base64Chars[rem]);
        }
        else if (sb.length() < (int) Math.ceil(byteArr.length * 8d / 6d))
        {
            sb.append(base64Chars[0]);
        }

        return sb.toString();
    }

    public byte[] decode(String base64String)
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

    /**
     * The base64Charset must be a String containing exactly 64 distinct characters.<br>
     * If it does not a {@link IllegalStateException} is thrown.
     *
     * @param base64Charset
     */
    private void setBase64Charset(String base64Charset)
    {
        if (base64Charset.length() != BASE)
            throw new IllegalStateException("The charset must be 64 characters long.");

        this.base64Chars = base64Charset.toCharArray();
        this.charPositionMap = new HashMap<>();

        for (int i = 0; i < this.base64Chars.length; i++)
            this.charPositionMap.put(this.base64Chars[i], i);

        if (this.charPositionMap.size() != BASE)
            throw new IllegalStateException("The charset must contain 64 unique characters.");
    }
}
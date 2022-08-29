package com.alloc64.dsobfuscator.crypt;


import java.nio.ByteBuffer;
import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.signers.RSADigestSigner;

public class CryptUtils
{
    private static final SecureRandom secureRandom;

    static
    {
        secureRandom = new SecureRandom();
    }

    public static byte[] encrypt(Object encryptionKey, byte[] data)
    {
        return encrypt((SecretKeySpec)EncryptionKeys.resolve(encryptionKey), data);
    }

    public static byte[] decrypt(Object encryptionKey, byte[] data)
    {
        return decrypt((SecretKeySpec)EncryptionKeys.resolve(encryptionKey), data);
    }

    /**
     * Encrypts data with AES with specified @encryptionKey
     *
     * @param encryptionKey
     * @param data
     * @return encrypted byte[] data
     */
    public static byte[] encrypt(Key encryptionKey, byte[] data)
    {
        if (data == null || data.length < 1)
            return null;

        try
        {
            byte[] iv = new byte[16];
            secureRandom.nextBytes(iv);

            final Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, encryptionKey, new IvParameterSpec(iv));

            byte[] encrypted = cipher.doFinal(data);

            ByteBuffer byteBuffer = ByteBuffer.allocate(4 + iv.length + encrypted.length);
            byteBuffer.putInt(iv.length);
            byteBuffer.put(iv);
            byteBuffer.put(encrypted);

            return byteBuffer.array();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Decrypts data with AES with specified @encryptionKey
     *
     * @param encryptionKey
     * @param data
     * @return decrypted byte[] data
     */
    public static byte[] decrypt(Key encryptionKey, byte[] data)
    {
        if (data == null || data.length < 1)
            return null;

        try
        {
            ByteBuffer byteBuffer = ByteBuffer.wrap(data);

            int ivLength = byteBuffer.getInt();

            if (ivLength < 12 || ivLength > 16)
                throw new IllegalArgumentException("Invalid IV.");

            byte[] iv = new byte[ivLength];
            byteBuffer.get(iv);

            byte[] cipherText = new byte[byteBuffer.remaining()];
            byteBuffer.get(cipherText);

            final Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, encryptionKey, new IvParameterSpec(iv));

            return cipher.doFinal(cipherText);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * Signs data with given @privateKeyInfo
     *
     * @param privateKeyInfo
     * @param data
     * @return byte[] representation of the SHA256 signature
     */
    public static byte[] sign(AsymmetricKeyParameter privateKeyInfo, byte[] data)
    {
        return sign(privateKeyInfo, data, 0, data.length);
    }

    /**
     * Signs data with given @privateKeyInfo
     *
     * @param privateKeyInfo
     * @param data
     * @return byte[] representation of the SHA256 signature
     */
    public static byte[] sign(AsymmetricKeyParameter privateKeyInfo, byte[] data, int offset, int length)
    {
        if (data == null || data.length < 1)
            return null;

        RSADigestSigner signer = new RSADigestSigner(new SHA256Digest());
        signer.init(true, privateKeyInfo);
        signer.update(data, offset, length);

        try
        {
            return signer.generateSignature();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * Verifies SHA256 signature of data with given @publicKeyInfo
     *
     * @param publicKeyInfo
     * @param data
     * @param signature
     * @return true if signature matches data
     */
    public static boolean verify(AsymmetricKeyParameter publicKeyInfo, byte[] data, byte[] signature)
    {
        if (data == null || data.length < 1 || signature == null || signature.length < 1)
            return false;

        return verify(publicKeyInfo, data, 0, data.length, signature);
    }

    /**
     * Verifies SHA256 signature of data with given @publicKeyInfo
     *
     * @param publicKeyInfo
     * @param data
     * @param offset
     * @param length
     * @param signature
     * @return true if signature matches data
     */
    public static boolean verify(AsymmetricKeyParameter publicKeyInfo, byte[] data, int offset, int length, byte[] signature)
    {
        if (data == null || data.length < 1 || signature == null || signature.length < 1)
            return false;

        RSADigestSigner signer = new RSADigestSigner(new SHA256Digest());
        signer.init(false, publicKeyInfo);
        signer.update(data, offset, length);

        return signer.verifySignature(signature);
    }
}
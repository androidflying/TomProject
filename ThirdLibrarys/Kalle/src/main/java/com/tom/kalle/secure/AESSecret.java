package com.tom.kalle.secure;

import java.security.GeneralSecurityException;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/9/6
 * 描述：
 */
public class AESSecret implements Secret {

    private Cipher encrypt;
    private Cipher decrypt;

    public AESSecret(String key) throws GeneralSecurityException {
        Key cryptKey = getKey(key.getBytes());
        encrypt = Cipher.getInstance("AES");
        encrypt.init(Cipher.ENCRYPT_MODE, cryptKey);
        decrypt = Cipher.getInstance("AES");
        decrypt.init(Cipher.DECRYPT_MODE, cryptKey);
    }

    @Override
    public String encrypt(String data) throws GeneralSecurityException {
        return Encryption.byteArrayToHex(encrypt(data.getBytes()));
    }

    @Override
    public byte[] encrypt(byte[] data) throws GeneralSecurityException {
        return encrypt.doFinal(data);
    }

    @Override
    public String decrypt(String data) throws GeneralSecurityException {
        return new String(decrypt(Encryption.hexToByteArray(data)));
    }

    @Override
    public byte[] decrypt(byte[] data) throws GeneralSecurityException {
        return decrypt.doFinal(data);
    }

    private Key getKey(byte[] keyData) {
        byte[] arrB = new byte[8];
        for (int i = 0; i < keyData.length && i < arrB.length; i++) {
            arrB[i] = keyData[i];
        }
        return new SecretKeySpec(arrB, "AES");
    }
}
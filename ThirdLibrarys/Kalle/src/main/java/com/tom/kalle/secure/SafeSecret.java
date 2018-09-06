package com.tom.kalle.secure;

import java.security.GeneralSecurityException;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/9/6
 * 描述：
 */
public class SafeSecret implements Secret {
    @Override
    public String encrypt(String data) {
        return data;
    }

    @Override
    public byte[] encrypt(byte[] data) {
        return data;
    }

    @Override
    public String decrypt(String data) {
        return data;
    }

    @Override
    public byte[] decrypt(byte[] data) {
        return data;
    }
}
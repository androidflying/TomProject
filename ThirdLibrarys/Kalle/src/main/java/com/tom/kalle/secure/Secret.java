package com.tom.kalle.secure;

import java.security.GeneralSecurityException;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/9/6
 * 描述：
 */
public interface Secret {

    String encrypt(String data) throws GeneralSecurityException;

    byte[] encrypt(byte[] data) throws GeneralSecurityException;

    String decrypt(String data) throws GeneralSecurityException;

    byte[] decrypt(byte[] data) throws GeneralSecurityException;
}
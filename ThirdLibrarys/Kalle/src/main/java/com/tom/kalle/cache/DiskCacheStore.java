package com.tom.kalle.cache;

import android.text.TextUtils;

import com.tom.kalle.http.Headers;
import com.tom.kalle.secure.Encryption;
import com.tom.kalle.secure.Secret;
import com.tom.kalle.util.IOUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.security.GeneralSecurityException;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/9/6
 * 描述：
 */
public class DiskCacheStore implements CacheStore {

    public static Builder newBuilder(String directory) {
        return new Builder(directory);
    }

    private Secret mSecret;
    private String mDirectory;

    private DiskCacheStore(Builder builder) {
        mDirectory = builder.mDirectory;
        String password = TextUtils.isEmpty(builder.mPassword) ? mDirectory : builder.mPassword;
        mSecret = Encryption.createSecret(password);
    }

    @Override
    public Cache get(String key) {
        key = uniqueKey(key);

        BufferedReader reader = null;
        try {
            File file = new File(mDirectory, key);
            if (!file.exists() || file.isDirectory()) return null;

            Cache cache = new Cache();
            reader = new BufferedReader(new FileReader(file));
            cache.setCode(Integer.parseInt(decrypt(reader.readLine())));
            cache.setHeaders(Headers.fromJSONString(decrypt(reader.readLine())));
            cache.setBody(Encryption.hexToByteArray(decrypt(reader.readLine())));
            cache.setExpires(Long.parseLong(decrypt(reader.readLine())));
            return cache;
        } catch (Exception e) {
            IOUtils.delFileOrFolder(new File(mDirectory, key));
        } finally {
            IOUtils.closeQuietly(reader);
        }
        return null;
    }

    @Override
    public boolean replace(String key, Cache cache) {
        key = uniqueKey(key);

        BufferedWriter writer = null;
        try {
            if (TextUtils.isEmpty(key) || cache == null) return false;
            if (!IOUtils.createFolder(mDirectory)) return false;

            File file = new File(mDirectory, key);
            if (!IOUtils.createNewFile(file)) return false;

            writer = IOUtils.toBufferedWriter(new FileWriter(file));
            writer.write(encrypt(Integer.toString(cache.getCode())));
            writer.newLine();
            writer.write(encrypt(Headers.toJSONString(cache.getHeaders())));
            writer.newLine();
            writer.write(encrypt(Encryption.byteArrayToHex(cache.getBody())));
            writer.newLine();
            writer.write(encrypt(Long.toString(cache.getExpires())));
            writer.flush();
            return true;
        } catch (Exception e) {
            IOUtils.delFileOrFolder(new File(mDirectory, key));
        } finally {
            IOUtils.closeQuietly(writer);
        }
        return false;
    }

    @Override
    public boolean remove(String key) {
        key = uniqueKey(key);
        return IOUtils.delFileOrFolder(new File(mDirectory, key));
    }

    @Override
    public boolean clear() {
        return IOUtils.delFileOrFolder(mDirectory);
    }

    private String encrypt(String encryptionText) throws GeneralSecurityException {
        return mSecret.encrypt(encryptionText);
    }

    private String decrypt(String cipherText) throws GeneralSecurityException {
        return mSecret.decrypt(cipherText);
    }

    private String uniqueKey(String key) {
        return Encryption.getMD5ForString(mDirectory + key);
    }

    public static class Builder {

        private String mDirectory;
        private String mPassword;

        private Builder(String directory) {
            this.mDirectory = directory;
        }

        public Builder password(String password) {
            this.mPassword = password;
            return this;
        }

        public DiskCacheStore build() {
            return new DiskCacheStore(this);
        }
    }

}

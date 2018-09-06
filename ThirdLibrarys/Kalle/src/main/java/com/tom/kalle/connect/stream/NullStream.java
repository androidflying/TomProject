package com.tom.kalle.connect.stream;

import com.tom.kalle.connect.Connection;
import com.tom.kalle.util.IOUtils;

import java.io.InputStream;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/9/6
 * 描述：
 */
public class NullStream extends InputStream {

    private final Connection mConnection;

    public NullStream(Connection connection) {
        this.mConnection = connection;
    }

    @Override
    public int read() {
        return 0;
    }

    @Override
    public int read(byte[] b) {
        return 0;
    }

    @Override
    public int read(byte[] b, int off, int len) {
        return 0;
    }

    @Override
    public void close() {
        IOUtils.closeQuietly(mConnection);
    }

    @Override
    public long skip(long n) {
        return 0;
    }

    @Override
    public int available() {
        return 0;
    }

    @Override
    public void reset() {
    }

    @Override
    public boolean markSupported() {
        return false;
    }

    @Override
    public void mark(int limit) {
    }
}
package com.tom.kalle.connect.stream;

import com.tom.kalle.connect.Connection;
import com.tom.kalle.util.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/9/6
 * 描述：
 */
public class SourceStream extends InputStream {

    private final Connection mConnection;
    private final InputStream mStream;

    public SourceStream(Connection connection, InputStream stream) {
        this.mConnection = connection;
        this.mStream = stream;
    }

    @Override
    public int read() throws IOException {
        return mStream.read();
    }

    @Override
    public int read(byte[] b) throws IOException {
        return mStream.read(b, 0, b.length);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        return mStream.read(b, off, len);
    }

    @Override
    public long skip(long n) throws IOException {
        return mStream.skip(n);
    }

    @Override
    public int available() throws IOException {
        return mStream.available();
    }

    @Override
    public void close() {
        IOUtils.closeQuietly(mStream);
        IOUtils.closeQuietly(mConnection);
    }

    @Override
    public void reset() throws IOException {
        mStream.reset();
    }

    @Override
    public synchronized void mark(int limit) {
        mStream.mark(limit);
    }

    @Override
    public boolean markSupported() {
        return mStream.markSupported();
    }
}
package com.tom.kalle.urlconnect;

import android.text.TextUtils;

import com.tom.kalle.connect.Connection;
import com.tom.kalle.connect.stream.NullStream;
import com.tom.kalle.connect.stream.SourceStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/9/6
 * 描述：
 */
public class URLConnection implements Connection {

    private HttpURLConnection mConnection;

    public URLConnection(HttpURLConnection connection) {
        this.mConnection = connection;
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return mConnection.getOutputStream();
    }

    @Override
    public int getCode() throws IOException {
        return mConnection.getResponseCode();
    }

    @Override
    public Map<String, List<String>> getHeaders() {
        return mConnection.getHeaderFields();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        int code = mConnection.getResponseCode();
        if (!hasBody(mConnection.getRequestMethod(), code)) {
            return new NullStream(this);
        }
        if (code >= 400) {
            return getInputStream(mConnection.getContentEncoding(), new SourceStream(this, mConnection.getErrorStream()));
        }
        return getInputStream(mConnection.getContentEncoding(), new SourceStream(this, mConnection.getInputStream()));
    }

    @Override
    public void disconnect() {
        if (mConnection != null) {
            mConnection.disconnect();
        }
    }

    @Override
    public void close() {
        if (mConnection != null) {
            mConnection.disconnect();
        }
    }

    private static InputStream getInputStream(String contentEncoding, InputStream stream) throws IOException {
        if (!TextUtils.isEmpty(contentEncoding) && contentEncoding.contains("gzip")) {
            stream = new GZIPInputStream(stream);
        }
        return stream;
    }

    private static boolean hasBody(String method, int code) {
        return !"HEAD".equalsIgnoreCase(method) && hasBody(code);
    }

    private static boolean hasBody(int code) {
        return code > 100 && code != 204 && code != 205 && !(code >= 300 && code < 400);
    }
}
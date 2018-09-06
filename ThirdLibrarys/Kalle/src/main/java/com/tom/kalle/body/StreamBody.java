package com.tom.kalle.body;

import android.text.TextUtils;

import com.tom.kalle.http.Headers;
import com.tom.kalle.body.ResponseBody;
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
public class StreamBody implements ResponseBody {

    private String mContentType;
    private InputStream mStream;

    public StreamBody(String contentType, InputStream stream) {
        this.mContentType = contentType;
        this.mStream = stream;
    }

    @Override
    public String string() throws IOException {
        String charset = Headers.parseSubValue(mContentType, "charset", null);
        return TextUtils.isEmpty(charset) ? IOUtils.toString(mStream) : IOUtils.toString(mStream, charset);
    }

    @Override
    public byte[] byteArray() throws IOException {
        return IOUtils.toByteArray(mStream);
    }

    @Override
    public InputStream stream() {
        return mStream;
    }

    @Override
    public void close() throws IOException {
        mStream.close();
    }
}
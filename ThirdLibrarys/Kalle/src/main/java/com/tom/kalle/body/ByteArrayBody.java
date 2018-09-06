package com.tom.kalle.body;

import android.text.TextUtils;

import com.tom.kalle.http.Headers;
import com.tom.kalle.util.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/9/6
 * 描述：
 */
public class ByteArrayBody implements ResponseBody {

    private String mContentType;
    private byte[] mData;

    public ByteArrayBody(String contentType, byte[] data) {
        this.mContentType = contentType;
        this.mData = data;
    }

    @Override
    public String string() {
        String charset = Headers.parseSubValue(mContentType, "charset", null);
        return TextUtils.isEmpty(charset) ? IOUtils.toString(mData) : IOUtils.toString(mData, charset);
    }

    @Override
    public byte[] byteArray() {
        return mData;
    }

    @Override
    public InputStream stream() {
        return new ByteArrayInputStream(mData);
    }

    @Override
    public void close() {
        mData = null;
    }
}
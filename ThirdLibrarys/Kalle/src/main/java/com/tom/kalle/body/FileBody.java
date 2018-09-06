package com.tom.kalle.body;

import android.text.TextUtils;
import android.webkit.MimeTypeMap;

import com.tom.kalle.http.Headers;
import com.tom.kalle.util.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/9/6
 * 描述：文件请求体
 */
public class FileBody extends BasicOutData<FileBody> implements RequestBody {

    private final File mFile;

    public FileBody(File file) {
        this.mFile = file;
    }

    @Override
    public long length() {
        return mFile.length();
    }

    @Override
    public String contentType() {
        String fileName = mFile.getName();
        String extension = MimeTypeMap.getFileExtensionFromUrl(fileName);
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        if (TextUtils.isEmpty(mimeType)) {
            mimeType = Headers.VALUE_APPLICATION_STREAM;
        }
        return mimeType;
    }


    @Override
    protected void onWrite(OutputStream writer) throws IOException {
        InputStream reader = new FileInputStream(mFile);
        IOUtils.write(reader, writer);
        IOUtils.closeQuietly(reader);
    }
}
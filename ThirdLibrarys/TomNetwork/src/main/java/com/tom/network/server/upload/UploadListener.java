package com.tom.network.server.upload;


import com.tom.network.server.ProgressListener;

/**
 * ================================================
 * 描    述：全局的上传监听
 * ================================================
 */
public abstract class UploadListener<T> implements ProgressListener<T> {

    public final Object tag;

    public UploadListener(Object tag) {
        this.tag = tag;
    }
}

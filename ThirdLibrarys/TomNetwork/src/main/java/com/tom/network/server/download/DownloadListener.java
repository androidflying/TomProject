package com.tom.network.server.download;


import com.tom.network.server.ProgressListener;

import java.io.File;

/**
 * ================================================
 * 描    述：全局的下载监听
 * ================================================
 */
public abstract class DownloadListener implements ProgressListener<File> {

    public final Object tag;

    public DownloadListener(Object tag) {
        this.tag = tag;
    }
}

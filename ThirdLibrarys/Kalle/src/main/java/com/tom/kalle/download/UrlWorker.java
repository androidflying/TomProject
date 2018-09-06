package com.tom.kalle.download;

import com.tom.kalle.http.Response;
import com.tom.kalle.http.Call;

import java.io.IOException;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/9/6
 * 描述：
 */
public class UrlWorker extends BasicWorker<UrlDownload> {

    private Call mCall;

    UrlWorker(UrlDownload download) {
        super(download);
    }

    @Override
    protected Response requestNetwork(UrlDownload download) throws IOException {
        mCall = new Call(download);
        return mCall.execute();
    }

    @Override
    public void cancel() {
        if (mCall != null && !mCall.isCanceled()) {
            mCall.cancel();
        }
    }
}
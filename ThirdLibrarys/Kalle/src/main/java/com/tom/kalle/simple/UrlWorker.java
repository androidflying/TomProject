package com.tom.kalle.simple;

import com.tom.kalle.http.Response;
import com.tom.kalle.http.Call;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/9/6
 * 描述：
 */
final class UrlWorker<S, F> extends BasicWorker<SimpleUrlRequest, S, F> {

    private Call mCall;

    UrlWorker(SimpleUrlRequest request, Type succeed, Type failed) {
        super(request, succeed, failed);
    }

    @Override
    protected Response requestNetwork(SimpleUrlRequest request) throws IOException {
        mCall = new Call(request);
        return mCall.execute();
    }

    @Override
    public void cancel() {
        if (mCall != null && !mCall.isCanceled()) {
            mCall.cancel();
        }
    }
}
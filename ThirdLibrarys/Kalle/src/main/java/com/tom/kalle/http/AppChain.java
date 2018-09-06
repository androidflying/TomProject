package com.tom.kalle.http;

import com.tom.kalle.interceptor.Interceptor;

import java.io.IOException;
import java.util.List;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/9/6
 * 描述：
 */
class AppChain implements Chain {

    private final List<Interceptor> mInterceptors;
    private final int mTargetIndex;
    private final Request mRequest;
    private Call mCall;

    AppChain(List<Interceptor> interceptors, int targetIndex, Request request, Call call) {
        this.mInterceptors = interceptors;
        this.mTargetIndex = targetIndex;
        this.mRequest = request;
        this.mCall = call;
    }

    @Override
    public Request request() {
        return mRequest;
    }

    @Override
    public Response proceed(Request request) throws IOException {
        Interceptor interceptor = mInterceptors.get(mTargetIndex);
        Chain chain = new AppChain(mInterceptors, mTargetIndex + 1, request, mCall);
        return interceptor.intercept(chain);
    }

    @Override
    public Call call() {
        return mCall;
    }
}
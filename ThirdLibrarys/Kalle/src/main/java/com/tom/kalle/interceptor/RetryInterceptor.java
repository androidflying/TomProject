package com.tom.kalle.interceptor;

import com.tom.kalle.http.Response;
import com.tom.kalle.http.Chain;

import java.io.IOException;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/9/6
 * 描述：
 */
public class RetryInterceptor implements Interceptor {

    private int mCount;

    public RetryInterceptor(int count) {
        this.mCount = count;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        try {
            return chain.proceed(chain.request());
        } catch (IOException e) {
            if (mCount > 0) {
                mCount--;
                return intercept(chain);
            }
            throw e;
        }
    }
}
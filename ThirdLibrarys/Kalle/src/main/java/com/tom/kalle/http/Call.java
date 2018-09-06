package com.tom.kalle.http;

import com.tom.kalle.Kalle;
import com.tom.kalle.interceptor.ConnectInterceptor;
import com.tom.kalle.interceptor.Interceptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/9/6
 * 描述：
 */
public class Call {

    private final Request mRequest;
    private ConnectInterceptor mConnectInterceptor;
    private boolean isExecuted;
    private boolean isCanceled;

    public Call(Request request) {
        this.mRequest = request;
    }

    /**
     * Execute request.
     */
    public Response execute() throws IOException {
        if (isCanceled) {
            throw new CancellationException("The request has been cancelled.");
        }
        isExecuted = true;

        List<Interceptor> interceptors = new ArrayList<>(Kalle.getConfig().getInterceptor());
        mConnectInterceptor = new ConnectInterceptor();
        interceptors.add(mConnectInterceptor);

        Chain chain = new AppChain(interceptors, 0, mRequest, this);
        try {
            return chain.proceed(mRequest);
        } catch (Exception e) {
            if (isCanceled) {
                throw new CancellationException("The request has been cancelled.");
            } else {
                throw e;
            }
        }
    }

    /**
     * The call is executed.
     *
     * @return true, otherwise is false.
     */
    public boolean isExecuted() {
        return isExecuted;
    }

    /**
     * The call is canceled.
     *
     * @return true, otherwise is false.
     */
    public boolean isCanceled() {
        return isCanceled;
    }

    /**
     * Cancel the call.
     */
    public void cancel() {
        if (!isCanceled) {
            isCanceled = true;
            if (mConnectInterceptor != null) {
                mConnectInterceptor.cancel();
            }
        }
    }
}
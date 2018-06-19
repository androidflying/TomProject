package com.tom.network.callback;


import com.tom.network.model.Progress;
import com.tom.network.model.Response;
import com.tom.network.request.base.Request;
import com.tom.network.utils.OkLogger;

/**
 * ================================================
 * 描   述：抽象的回调接口
 * ================================================
 */
public abstract class AbsCallback<T> implements Callback<T> {

    @Override
    public void onStart(Request<T, ? extends Request> request) {
    }

    @Override
    public void onCacheSuccess(Response<T> response) {
    }

    @Override
    public void onError(Response<T> response) {
        OkLogger.printStackTrace(response.getException());
    }

    @Override
    public void onFinish() {
    }

    @Override
    public void uploadProgress(Progress progress) {
    }

    @Override
    public void downloadProgress(Progress progress) {
    }
}

package com.tom.kalle.interceptor;

import com.tom.kalle.http.BodyRequest;
import com.tom.kalle.http.Headers;
import com.tom.kalle.http.Request;
import com.tom.kalle.http.RequestMethod;
import com.tom.kalle.http.Response;
import com.tom.kalle.http.Url;
import com.tom.kalle.http.UrlRequest;
import com.tom.kalle.http.Chain;
import com.tom.kalle.util.IOUtils;

import java.io.IOException;

import static com.tom.kalle.http.Headers.KEY_COOKIE;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/9/6
 * 描述：
 */
public class RedirectInterceptor implements Interceptor {

    public RedirectInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        if (response.isRedirect()) {
            Url oldUrl = request.url();
            Url url = oldUrl.location(response.headers().getLocation());
            Headers headers = request.headers();
            headers.remove(KEY_COOKIE);

            RequestMethod method = request.method();
            Request newRequest;
            if (method.allowBody()) {
                newRequest = BodyRequest.newBuilder(url, method)
                        .setHeaders(headers)
                        .setParams(request.copyParams())
                        .body(request.body())
                        .build();
            } else {
                newRequest = UrlRequest.newBuilder(url, method)
                        .setHeaders(headers)
                        .build();
            }
            IOUtils.closeQuietly(response);
            return chain.proceed(newRequest);
        }
        return response;
    }
}
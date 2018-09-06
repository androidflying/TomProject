package com.tom.kalle.urlconnect;

import android.os.Build;

import com.tom.kalle.http.Headers;
import com.tom.kalle.http.Request;
import com.tom.kalle.http.RequestMethod;
import com.tom.kalle.connect.ConnectFactory;
import com.tom.kalle.connect.Connection;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

import static com.tom.kalle.http.Headers.KEY_CONNECTION;
import static com.tom.kalle.http.Headers.VALUE_CLOSE;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/9/6
 * 描述：
 */
public class URLConnectionFactory implements ConnectFactory {

    private URLConnectionFactory(Builder builder) {
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public Connection connect(Request request) throws IOException {
        HttpURLConnection connection;
        URL url = new URL(request.url().toString());

        Proxy proxy = request.proxy();
        if (proxy == null) {
            connection = (HttpURLConnection) url.openConnection();
        } else {
            connection = (HttpURLConnection) url.openConnection(proxy);
        }

        connection.setConnectTimeout(request.connectTimeout());
        connection.setReadTimeout(request.readTimeout());
        connection.setInstanceFollowRedirects(false);

        if (connection instanceof HttpsURLConnection) {
            SSLSocketFactory sslSocketFactory = request.sslSocketFactory();
            if (sslSocketFactory != null) {
                ((HttpsURLConnection) connection).setSSLSocketFactory(sslSocketFactory);
            }
            HostnameVerifier hostnameVerifier = request.hostnameVerifier();
            if (hostnameVerifier != null) {
                ((HttpsURLConnection) connection).setHostnameVerifier(hostnameVerifier);
            }
        }

        RequestMethod method = request.method();
        connection.setRequestMethod(method.toString());
        connection.setDoInput(true);
        boolean isAllowBody = isAllowBody(method);
        connection.setDoOutput(isAllowBody);

        Headers headers = request.headers();

        if (isAllowBody) {
            long contentLength = headers.getContentLength();
            if (contentLength <= Integer.MAX_VALUE) {
                connection.setFixedLengthStreamingMode((int) contentLength);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                connection.setFixedLengthStreamingMode(contentLength);
            } else {
                connection.setChunkedStreamingMode(256 * 1024);
            }
        }

        List<String> values = headers.get(KEY_CONNECTION);
        headers.set(KEY_CONNECTION, Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT ? values.get(0) : VALUE_CLOSE);
        Map<String, String> requestHeaders = Headers.getRequestHeaders(headers);
        for (Map.Entry<String, String> entry : requestHeaders.entrySet()) {
            connection.setRequestProperty(entry.getKey(), entry.getValue());
        }

        connection.connect();
        return new URLConnection(connection);
    }

    private boolean isAllowBody(RequestMethod method) {
        boolean allowRequestBody = method.allowBody();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return allowRequestBody && method != RequestMethod.DELETE;
        }
        return allowRequestBody;
    }

    public static class Builder {
        private Builder() {
        }

        public URLConnectionFactory build() {
            return new URLConnectionFactory(this);
        }
    }
}

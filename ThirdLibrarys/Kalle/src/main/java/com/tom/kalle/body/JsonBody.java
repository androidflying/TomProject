package com.tom.kalle.body;

import com.tom.kalle.Kalle;

import java.nio.charset.Charset;

import static com.tom.kalle.http.Headers.VALUE_APPLICATION_JSON;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/9/6
 * 描述：json请求体
 */
public class JsonBody extends StringBody {

    public JsonBody(String body) {
        this(body, Kalle.getConfig().getCharset());
    }

    public JsonBody(String body, Charset charset) {
        super(body, charset, VALUE_APPLICATION_JSON);
    }
}
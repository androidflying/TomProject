package com.tom.kalle.body;

import com.tom.kalle.Kalle;

import java.nio.charset.Charset;

import static com.tom.kalle.http.Headers.VALUE_APPLICATION_XML;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/9/6
 * 描述：
 */
public class XmlBody extends StringBody {

    public XmlBody(String body) {
        this(body, Kalle.getConfig().getCharset());
    }

    public XmlBody(String body, Charset charset) {
        super(body, charset, VALUE_APPLICATION_XML);
    }
}
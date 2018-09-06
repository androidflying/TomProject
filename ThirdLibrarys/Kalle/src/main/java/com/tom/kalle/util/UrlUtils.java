package com.tom.kalle.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/9/6
 * 描述：
 */
public class UrlUtils {

    public static String urlEncode(String target, String charset) {
        try {
            return URLEncoder.encode(target, charset);
        } catch (UnsupportedEncodingException e) {
            return target;
        }
    }

    public static String urlEncode(String target, Charset charset) {
        return urlEncode(target, charset.name());
    }

    public static String urlDecode(String target, String charset) {
        try {
            return URLDecoder.decode(target, charset);
        } catch (UnsupportedEncodingException e) {
            return target;
        }
    }

    public static String urlDecode(String target, Charset charset) {
        return urlDecode(target, charset.name());
    }
}
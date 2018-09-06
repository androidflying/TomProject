package com.tom.kalle.cookie;

import android.text.TextUtils;

import java.io.Serializable;
import java.net.HttpCookie;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/9/6
 * 描述：
 */
public class Cookie implements Serializable {

    private long id = -1;
    private String url;
    private String name;
    private String value;
    private String comment;
    private String commentURL;
    private boolean discard;
    private String domain;
    private long expiry;
    private String path;
    private String portList;
    private boolean secure;
    private int version = 1;

    public Cookie() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommentURL() {
        return commentURL;
    }

    public void setCommentURL(String commentURL) {
        this.commentURL = commentURL;
    }

    public boolean isDiscard() {
        return discard;
    }

    public void setDiscard(boolean discard) {
        this.discard = discard;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public long getExpiry() {
        return expiry;
    }

    public void setExpiry(long expiry) {
        this.expiry = expiry;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPortList() {
        return portList;
    }

    public void setPortList(String portList) {
        this.portList = portList;
    }

    public boolean isSecure() {
        return secure;
    }

    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public static Cookie toCookie(String url, HttpCookie httpCookie) {
        Cookie cookie = new Cookie();
        cookie.setUrl(url);
        cookie.setName(httpCookie.getName());
        cookie.setValue(httpCookie.getValue());
        cookie.setComment(httpCookie.getComment());
        cookie.setCommentURL(httpCookie.getCommentURL());
        cookie.setDiscard(httpCookie.getDiscard());
        cookie.setDomain(httpCookie.getDomain());
        long maxAge = httpCookie.getMaxAge();
        if (maxAge > 0) {
            long expiry = (maxAge * 1000L) + System.currentTimeMillis();
            if (expiry < 0L) {
                expiry = System.currentTimeMillis() + 100L * 365L * 24L * 60L * 60L * 1000L;
            }
            cookie.setExpiry(expiry);
        } else if (maxAge < 0) {
            cookie.setExpiry(-1);
        } else {
            cookie.setExpiry(0);
        }

        String path = httpCookie.getPath();
        if (!TextUtils.isEmpty(path) && path.length() > 1 && path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }
        cookie.setPath(path);
        cookie.setPortList(httpCookie.getPortlist());
        cookie.setSecure(httpCookie.getSecure());
        cookie.setVersion(httpCookie.getVersion());
        return cookie;
    }

    public static HttpCookie toHttpCookie(Cookie cookie) {
        HttpCookie httpCookie = new HttpCookie(cookie.name, cookie.value);
        httpCookie.setComment(cookie.comment);
        httpCookie.setCommentURL(cookie.commentURL);
        httpCookie.setDiscard(cookie.discard);
        httpCookie.setDomain(cookie.domain);
        if (cookie.expiry == 0) {
            httpCookie.setMaxAge(0);
        } else if (cookie.expiry < 0) {
            httpCookie.setMaxAge(-1L);
        } else {
            long expiry = cookie.expiry - System.currentTimeMillis();
            expiry = expiry <= 0 ? 0 : expiry;
            httpCookie.setMaxAge(expiry / 1000L);
        }
        httpCookie.setPath(cookie.path);
        httpCookie.setPortlist(cookie.portList);
        httpCookie.setSecure(cookie.secure);
        httpCookie.setVersion(cookie.version);
        return httpCookie;
    }

    public static boolean isExpired(Cookie entity) {
        return entity.expiry != -1L && entity.expiry < System.currentTimeMillis();
    }
}

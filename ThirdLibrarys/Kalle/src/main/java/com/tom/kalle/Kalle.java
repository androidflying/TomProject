package com.tom.kalle;

import android.util.Log;

import com.tom.kalle.download.BodyDownload;
import com.tom.kalle.download.DownloadManager;
import com.tom.kalle.download.UrlDownload;
import com.tom.kalle.http.RequestMethod;
import com.tom.kalle.http.Url;
import com.tom.kalle.simple.RequestManager;
import com.tom.kalle.simple.SimpleBodyRequest;
import com.tom.kalle.simple.SimpleUrlRequest;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/9/6
 * 描述：
 */
public final class Kalle {

    private static KalleConfig sConfig;

    public static void setConfig(KalleConfig config) {
        if (sConfig == null) {
            synchronized (KalleConfig.class) {
                if (sConfig == null) {
                    sConfig = config == null ? KalleConfig.newBuilder().build() : config;
                } else {
                    Log.w("Kalle", new IllegalStateException("Only allowed to configure once."));
                }
            }
        }
    }

    public static KalleConfig getConfig() {
        setConfig(null);
        return sConfig;
    }

    public static SimpleUrlRequest.Api get(String url) {
        return SimpleUrlRequest.newApi(Url.newBuilder(url).build(), RequestMethod.GET);
    }

    public static SimpleUrlRequest.Api get(Url url) {
        return SimpleUrlRequest.newApi(url, RequestMethod.GET);
    }

    public static SimpleUrlRequest.Api head(String url) {
        return SimpleUrlRequest.newApi(Url.newBuilder(url).build(), RequestMethod.HEAD);
    }

    public static SimpleUrlRequest.Api head(Url url) {
        return SimpleUrlRequest.newApi(url, RequestMethod.HEAD);
    }

    public static SimpleUrlRequest.Api options(String url) {
        return SimpleUrlRequest.newApi(Url.newBuilder(url).build(), RequestMethod.OPTIONS);
    }

    public static SimpleUrlRequest.Api options(Url url) {
        return SimpleUrlRequest.newApi(url, RequestMethod.OPTIONS);
    }

    public static SimpleUrlRequest.Api trace(String url) {
        return SimpleUrlRequest.newApi(Url.newBuilder(url).build(), RequestMethod.TRACE);
    }

    public static SimpleUrlRequest.Api trace(Url url) {
        return SimpleUrlRequest.newApi(url, RequestMethod.TRACE);
    }

    public static SimpleBodyRequest.Api post(String url) {
        return SimpleBodyRequest.newApi(Url.newBuilder(url).build(), RequestMethod.POST);
    }

    public static SimpleBodyRequest.Api post(Url url) {
        return SimpleBodyRequest.newApi(url, RequestMethod.POST);
    }

    public static SimpleBodyRequest.Api put(String url) {
        return SimpleBodyRequest.newApi(Url.newBuilder(url).build(), RequestMethod.PUT);
    }

    public static SimpleBodyRequest.Api put(Url url) {
        return SimpleBodyRequest.newApi(url, RequestMethod.PUT);
    }

    public static SimpleBodyRequest.Api patch(String url) {
        return SimpleBodyRequest.newApi(Url.newBuilder(url).build(), RequestMethod.PATCH);
    }

    public static SimpleBodyRequest.Api patch(Url url) {
        return SimpleBodyRequest.newApi(url, RequestMethod.PATCH);
    }

    public static SimpleBodyRequest.Api delete(String url) {
        return SimpleBodyRequest.newApi(Url.newBuilder(url).build(), RequestMethod.DELETE);
    }

    public static SimpleBodyRequest.Api delete(Url url) {
        return SimpleBodyRequest.newApi(url, RequestMethod.DELETE);
    }

    public static void cancel(Object tag) {
        RequestManager.getInstance().cancel(tag);
    }

    public static class Download {

        public static UrlDownload.Api get(String url) {
            return UrlDownload.newApi(Url.newBuilder(url).build(), RequestMethod.GET);
        }

        public static UrlDownload.Api get(Url url) {
            return UrlDownload.newApi(url, RequestMethod.GET);
        }

        public static UrlDownload.Api head(String url) {
            return UrlDownload.newApi(Url.newBuilder(url).build(), RequestMethod.HEAD);
        }

        public static UrlDownload.Api head(Url url) {
            return UrlDownload.newApi(url, RequestMethod.HEAD);
        }

        public static UrlDownload.Api options(String url) {
            return UrlDownload.newApi(Url.newBuilder(url).build(), RequestMethod.OPTIONS);
        }

        public static UrlDownload.Api options(Url url) {
            return UrlDownload.newApi(url, RequestMethod.OPTIONS);
        }

        public static UrlDownload.Api trace(String url) {
            return UrlDownload.newApi(Url.newBuilder(url).build(), RequestMethod.TRACE);
        }

        public static UrlDownload.Api trace(Url url) {
            return UrlDownload.newApi(url, RequestMethod.TRACE);
        }

        public static BodyDownload.Api post(String url) {
            return BodyDownload.newApi(Url.newBuilder(url).build(), RequestMethod.POST);
        }

        public static BodyDownload.Api post(Url url) {
            return BodyDownload.newApi(url, RequestMethod.POST);
        }

        public static BodyDownload.Api put(String url) {
            return BodyDownload.newApi(Url.newBuilder(url).build(), RequestMethod.PUT);
        }

        public static BodyDownload.Api put(Url url) {
            return BodyDownload.newApi(url, RequestMethod.PUT);
        }

        public static BodyDownload.Api patch(String url) {
            return BodyDownload.newApi(Url.newBuilder(url).build(), RequestMethod.PATCH);
        }

        public static BodyDownload.Api patch(Url url) {
            return BodyDownload.newApi(url, RequestMethod.PATCH);
        }

        public static BodyDownload.Api delete(String url) {
            return BodyDownload.newApi(Url.newBuilder(url).build(), RequestMethod.DELETE);
        }

        public static BodyDownload.Api delete(Url url) {
            return BodyDownload.newApi(url, RequestMethod.DELETE);
        }

        public static void cancel(Object tag) {
            DownloadManager.getInstance().cancel(tag);
        }
    }

    private Kalle() {
    }
}

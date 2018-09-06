package com.tom.kalle.download;

import com.tom.kalle.http.Canceller;
import com.tom.kalle.http.RequestMethod;
import com.tom.kalle.http.Url;
import com.tom.kalle.http.UrlRequest;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/9/6
 * 描述：
 */
public class UrlDownload extends UrlRequest implements Download {

    public static UrlDownload.Api newApi(Url url, RequestMethod method) {
        return new UrlDownload.Api(url, method);
    }

    private final String mDirectory;
    private final String mFileName;

    private final ProgressBar mProgressBar;
    private final Policy mPolicy;

    private UrlDownload(Api api) {
        super(api);
        this.mDirectory = api.mDirectory;
        this.mFileName = api.mFileName;
        this.mProgressBar = api.mProgressBar == null ? ProgressBar.DEFAULT : api.mProgressBar;
        this.mPolicy = api.mPolicy == null ? Policy.DEFAULT : api.mPolicy;
    }

    @Override
    public String directory() {
        return mDirectory;
    }

    @Override
    public String fileName() {
        return mFileName;
    }

    @Override
    public ProgressBar progressBar() {
        return mProgressBar;
    }

    @Override
    public Policy policy() {
        return mPolicy;
    }

    public static class Api extends UrlRequest.Api<UrlDownload.Api> {

        private String mDirectory;
        private String mFileName;

        private ProgressBar mProgressBar;
        private Policy mPolicy;

        private Api(Url url, RequestMethod method) {
            super(url, method);
        }

        public Api directory(String directory) {
            this.mDirectory = directory;
            return this;
        }

        public Api fileName(String fileName) {
            this.mFileName = fileName;
            return this;
        }

        public Api onProgress(ProgressBar bar) {
            this.mProgressBar = bar;
            return this;
        }

        public Api policy(Policy policy) {
            this.mPolicy = policy;
            return this;
        }

        public String perform() throws Exception {
            return new UrlWorker(new UrlDownload(this)).call();
        }

        public Canceller perform(Callback callback) {
            return DownloadManager.getInstance().perform(new UrlDownload(this), callback);
        }
    }
}
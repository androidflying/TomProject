package com.tom.kalle.download;

import com.tom.kalle.http.Headers;
import com.tom.kalle.http.Url;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/9/6
 * 描述：
 */
public interface Download {

    /**
     * Get the file download address.
     */
    Url url();

    /**
     * Get headers.
     */
    Headers headers();

    /**
     * Get the directory where the file is to be saved.
     */
    String directory();

    /**
     * Get the file name.
     */
    String fileName();

    /**
     * Get onProgress bar.
     */
    ProgressBar progressBar();

    /**
     * Get download policy.
     */
    Policy policy();

    interface Policy {

        Policy DEFAULT = new Policy() {
            @Override
            public boolean isRange() {
                return true;
            }

            @Override
            public boolean allowDownload(int code, Headers headers) {
                return true;
            }

            @Override
            public boolean oldAvailable(String path, int code, Headers headers) {
                return false;
            }
        };

        /**
         * Does it support breakpoints?
         */
        boolean isRange();

        /**
         * Can I download it?
         *
         * @param code    http response code.
         * @param headers http response headers.
         * @return return true to continue the download, return false will call back the download failed.
         */
        boolean allowDownload(int code, Headers headers);

        /**
         * Discover old files. The file will be returned if it is available,
         * the file will be deleted if it is not available.
         *
         * @param path    old file path.
         * @param code    http response code.
         * @param headers http response headers.
         * @return return true if the old file is available, other wise is false.
         */
        boolean oldAvailable(String path, int code, Headers headers);
    }

    interface ProgressBar {

        ProgressBar DEFAULT = new ProgressBar() {
            @Override
            public void onProgress(int progress, long byteCount, long speed) {
            }
        };

        /**
         * Download onProgress changes.
         */
        void onProgress(int progress, long byteCount, long speed);
    }
}
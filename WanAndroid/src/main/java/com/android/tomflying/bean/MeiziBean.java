package com.android.tomflying.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/7/12
 * 描述：
 */
public class MeiziBean {


    /**
     * error : false
     * results : [{"_id":"5b0d6946421aa97f0308836b","createdAt":"2018-05-29T22:52:54.29Z","desc":"2018-05-31","publishedAt":"2018-05-31T00:00:00.0Z","source":"web","type":"福利","url":"http://ww1.sinaimg.cn/large/0065oQSqly1frsllc19gfj30k80tfah5.jpg","used":true,"who":"lijinshanmx"},{"_id":"56cc6d1d421aa95caa7076f6","createdAt":"2015-06-10T08:55:44.913Z","desc":"6.11","publishedAt":"2015-06-11T03:30:41.12Z","type":"福利","url":"http://ww2.sinaimg.cn/large/7a8aed7bgw1esz3jq17foj20hs0qodj9.jpg","used":true,"who":"张涵宇"},{"_id":"5b2269a6421aa92a5f2a35f9","createdAt":"2018-06-14T21:12:06.463Z","desc":"2018-06-15","publishedAt":"2018-06-15T00:00:00.0Z","source":"web","type":"福利","url":"http://ww1.sinaimg.cn/large/0065oQSqly1fsb0lh7vl0j30go0ligni.jpg","used":true,"who":"lijinshanmx"},{"_id":"58f6baad421aa954511ebec3","createdAt":"2017-04-19T09:17:33.987Z","desc":"4-19","publishedAt":"2017-04-19T11:44:51.925Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-19-17881407_1845958195665029_1132383288824954880_n.jpg","used":true,"who":"daimajia"},{"_id":"57e477fa421aa95bc338987d","createdAt":"2016-09-23T08:31:54.365Z","desc":"9-23","publishedAt":"2016-09-23T11:38:57.170Z","source":"chrome","type":"福利","url":"http://ww3.sinaimg.cn/large/610dc034jw1f837uocox8j20f00mggoo.jpg","used":true,"who":"daimajia"},{"_id":"56cc6d26421aa95caa708070","createdAt":"2015-12-24T03:01:43.608Z","desc":"12.24","publishedAt":"2015-12-24T04:06:06.105Z","type":"福利","url":"http://ww4.sinaimg.cn/large/7a8aed7bjw1ezak8074s3j20qo0k0adz.jpg","used":true,"who":"张涵宇"},{"_id":"585331db421aa9723d29b95c","createdAt":"2016-12-16T08:14:19.281Z","desc":"12-17","publishedAt":"2016-12-16T11:47:53.776Z","source":"chrome","type":"福利","url":"http://ww3.sinaimg.cn/large/610dc034jw1fasakfvqe1j20u00mhgn2.jpg","used":true,"who":"代码家"},{"_id":"595ad246421aa90ca3bb6a91","createdAt":"2017-07-04T07:24:54.820Z","desc":"7-4","publishedAt":"2017-07-04T11:50:36.484Z","source":"chrome","type":"福利","url":"https://ws1.sinaimg.cn/large/610dc034ly1fh7hwi9lhzj20u011hqa9.jpg","used":true,"who":"daimajia"},{"_id":"56cc6d1d421aa95caa7078b8","createdAt":"2015-09-17T03:52:38.846Z","desc":"9.17","publishedAt":"2015-09-17T03:56:51.346Z","type":"福利","url":"http://ww4.sinaimg.cn/large/610dc034gw1ew5b4ri9mxj20ic0qoq4t.jpg","used":true,"who":"dmj"},{"_id":"59a755a2421aa901c85e5fea","createdAt":"2017-08-31T08:17:38.117Z","desc":"8-31","publishedAt":"2017-08-31T08:22:07.982Z","source":"chrome","type":"福利","url":"https://ws1.sinaimg.cn/large/610dc034ly1fj2ld81qvoj20u00xm0y0.jpg","used":true,"who":"daimajia"},{"_id":"58d7dd53421aa93abb7d4e5a","createdAt":"2017-03-26T23:25:07.975Z","desc":"3-26","publishedAt":"2017-03-27T11:48:52.828Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/2017-03-26-17495078_643307445877569_4485136026821459968_n.jpg","used":true,"who":"dmj"},{"_id":"56cc6d29421aa95caa708266","createdAt":"2016-02-01T13:46:46.600Z","desc":"2.3","publishedAt":"2016-02-03T04:32:45.907Z","type":"福利","url":"http://ww2.sinaimg.cn/large/7a8aed7bgw1f0k6706308j20vg18gqfl.jpg","used":true,"who":"张涵宇"},{"_id":"56cc6d1d421aa95caa7076dc","createdAt":"2015-06-30T01:43:07.201Z","desc":"6.30","publishedAt":"2015-06-30T11:20:10.420Z","type":"福利","url":"http://ww1.sinaimg.cn/large/610dc034jw1etlvfi6vxkj20h20o476h.jpg","used":true,"who":"代码家"},{"_id":"58f6baad421aa954511ebec3","createdAt":"2017-04-19T09:17:33.987Z","desc":"4-19","publishedAt":"2017-04-19T11:44:51.925Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-19-17881407_1845958195665029_1132383288824954880_n.jpg","used":true,"who":"daimajia"},{"_id":"56cc6d1d421aa95caa7076f4","createdAt":"2015-06-14T09:13:53.594Z","desc":"6.14","publishedAt":"2015-06-15T03:46:12.822Z","type":"福利","url":"http://ww4.sinaimg.cn/large/7a8aed7bgw1et3qjtenw1j20qo0hsdj3.jpg","used":true,"who":"张涵宇"},{"_id":"56cc6d23421aa95caa707b3f","createdAt":"2015-08-05T01:48:29.95Z","desc":"8.5","publishedAt":"2015-08-05T03:53:12.649Z","type":"福利","url":"http://ww4.sinaimg.cn/large/7a8aed7bgw1eurhwfc5z7j20hs0qomzz.jpg","used":true,"who":"张涵宇"},{"_id":"5aff4645421aa95f55cab5e7","createdAt":"2018-05-15T00:00:00.0Z","desc":"2018-05-15","publishedAt":"2018-05-15T00:00:00.0Z","source":"web","type":"福利","url":"http://ww1.sinaimg.cn/large/0065oQSqly1frepqtwifwj30no0ti47n.jpg","used":true,"who":"lijinshanmx"},{"_id":"575cbba1421aa96b24382520","createdAt":"2016-06-12T09:32:17.746Z","desc":"跟上一个是一个系列的。","publishedAt":"2016-06-13T11:38:17.247Z","source":"web","type":"福利","url":"http://ww4.sinaimg.cn/mw690/9844520fjw1f4fqrpw1fvj21911wlb2b.jpg","used":true,"who":"龙龙童鞋"},{"_id":"5705c962677659132abfddcd","createdAt":"2016-04-07T10:43:46.879Z","desc":"4.7","publishedAt":"2016-04-07T11:43:11.427Z","source":"chrome","type":"福利","url":"http://ww1.sinaimg.cn/large/7a8aed7bjw1f2nxxvgz7xj20hs0qognd.jpg","used":true,"who":"张涵宇"},{"_id":"59154ae7421aa90c7a8b2b0d","createdAt":"2017-05-12T13:40:55.505Z","desc":"5-13","publishedAt":"2017-05-12T13:44:54.673Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/2017-05-12-18380140_455327614813449_854681840315793408_n.jpg","used":true,"who":"daimajia"}]
     */

    private boolean error;
    private List<Results> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<Results> getResults() {
        return results;
    }

    public void setResults(List<Results> results) {
        this.results = results;
    }

    public static class Results implements Serializable {
        /**
         * _id : 5b0d6946421aa97f0308836b
         * createdAt : 2018-05-29T22:52:54.29Z
         * desc : 2018-05-31
         * publishedAt : 2018-05-31T00:00:00.0Z
         * source : web
         * type : 福利
         * url : http://ww1.sinaimg.cn/large/0065oQSqly1frsllc19gfj30k80tfah5.jpg
         * used : true
         * who : lijinshanmx
         */

        private String _id;
        private String createdAt;
        private String desc;
        private String publishedAt;
        private String source;
        private String type;
        private String url;
        private boolean used;
        private String who;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isUsed() {
            return used;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }

        public String getWho() {
            return who;
        }

        public void setWho(String who) {
            this.who = who;
        }
    }
}

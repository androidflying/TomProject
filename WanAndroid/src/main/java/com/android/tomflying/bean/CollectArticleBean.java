package com.android.tomflying.bean;

import java.util.List;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/5/17
 * 描述：
 */
public class CollectArticleBean {


    /**
     * curPage : 1
     * datas : [{"author":"塞上牧羊空许约","chapterId":260,"chapterName":"RxJava & Retrofit & MVP","courseId":13,"desc":"","envelopePic":"","id":11607,"link":"https://www.jianshu.com/p/b12c3d5e053c","niceDate":"4分钟前","origin":"","originId":2927,"publishTime":1526540570000,"title":"RxJava之最常见的三种场景","userId":5515,"visible":0,"zan":0},{"author":"鸿洋公众号","chapterId":169,"chapterName":"gradle","courseId":13,"desc":"","envelopePic":"","id":11606,"link":"https://mp.weixin.qq.com/s/1UHcYOudViMhpUYeREZzGA","niceDate":"4分钟前","origin":"","originId":2929,"publishTime":1526540569000,"title":"这一次彻底弄明白Gradle相关配置","userId":5515,"visible":0,"zan":0},{"author":"骑小猪看流星","chapterId":308,"chapterName":"多线程","courseId":13,"desc":"","envelopePic":"","id":11605,"link":"https://www.jianshu.com/p/50fffbf21b39","niceDate":"4分钟前","origin":"","originId":2930,"publishTime":1526540567000,"title":"必须要理清的Java线程池","userId":5515,"visible":0,"zan":0}]
     * offset : 0
     * over : true
     * pageCount : 1
     * size : 20
     * total : 3
     */

    private int curPage;
    private int offset;
    private boolean over;
    private int pageCount;
    private int size;
    private int total;
    private List<Data> datas;

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Data> getData() {
        return datas;
    }

    public void setData(List<Data> datas) {
        this.datas = datas;
    }

    public static class Data {
        /**
         * author : 塞上牧羊空许约
         * chapterId : 260
         * chapterName : RxJava & Retrofit & MVP
         * courseId : 13
         * desc :
         * envelopePic :
         * id : 11607
         * link : https://www.jianshu.com/p/b12c3d5e053c
         * niceDate : 4分钟前
         * origin :
         * originId : 2927
         * publishTime : 1526540570000
         * title : RxJava之最常见的三种场景
         * userId : 5515
         * visible : 0
         * zan : 0
         */

        private String author;
        private int chapterId;
        private String chapterName;
        private int courseId;
        private String desc;
        private String envelopePic;
        private int id;
        private String link;
        private String niceDate;
        private String origin;
        private int originId;
        private long publishTime;
        private String title;
        private int userId;
        private int visible;
        private int zan;

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public int getChapterId() {
            return chapterId;
        }

        public void setChapterId(int chapterId) {
            this.chapterId = chapterId;
        }

        public String getChapterName() {
            return chapterName;
        }

        public void setChapterName(String chapterName) {
            this.chapterName = chapterName;
        }

        public int getCourseId() {
            return courseId;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getEnvelopePic() {
            return envelopePic;
        }

        public void setEnvelopePic(String envelopePic) {
            this.envelopePic = envelopePic;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getNiceDate() {
            return niceDate;
        }

        public void setNiceDate(String niceDate) {
            this.niceDate = niceDate;
        }

        public String getOrigin() {
            return origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        public int getOriginId() {
            return originId;
        }

        public void setOriginId(int originId) {
            this.originId = originId;
        }

        public long getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(long publishTime) {
            this.publishTime = publishTime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getVisible() {
            return visible;
        }

        public void setVisible(int visible) {
            this.visible = visible;
        }

        public int getZan() {
            return zan;
        }

        public void setZan(int zan) {
            this.zan = zan;
        }
    }

}

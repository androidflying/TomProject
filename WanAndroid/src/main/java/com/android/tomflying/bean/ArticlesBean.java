package com.android.tomflying.bean;

import java.util.List;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/5/8
 * 描述：文章列表
 */
public class ArticlesBean {

    /**
     * curPage : 2
     * datas : [{"apkLink":"","author":"jia635","chapterId":26,"chapterName":"基础UI控件","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":2874,"link":"https://blog.csdn.net/jia635/article/details/52387658","niceDate":"2018-04-26","origin":"","projectLink":"","publishTime":1524732906000,"superChapterId":26,"superChapterName":"常用控件","tags":[],"title":"为什么Dialog不能用Application的Context","type":0,"visible":1,"zan":0},{"apkLink":"","author":"yjfnypeu","chapterId":358,"chapterName":"项目基础功能","collect":false,"courseId":13,"desc":"可任意定制的app更新组件。 UpdatePlugin是一款用来进行app更新升级的框架。\r\n\r\n","envelopePic":"/resources/image/pc/default_project_img.jpg","fresh":false,"id":2872,"link":"http://www.wanandroid.com/blog/show/2119","niceDate":"2018-04-25","origin":"","projectLink":"https://github.com/yjfnypeu/UpdatePlugin","publishTime":1524649957000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=358"}],"title":"可任意定制的app更新组件 UpdatePlugin","type":0,"visible":1,"zan":0},{"apkLink":"","author":"技术小黑屋","chapterId":89,"chapterName":"app缓存相关","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":2871,"link":"https://droidyue.com/blog/2014/07/12/scan-media-files-in-android-chinese-edition/","niceDate":"2018-04-25","origin":"","projectLink":"","publishTime":1524627148000,"superChapterId":89,"superChapterName":"数据存储","tags":[],"title":"Android扫描多媒体文件剖析","type":0,"visible":1,"zan":0},{"apkLink":"","author":"大头呆","chapterId":169,"chapterName":"gradle","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":2870,"link":"https://mp.weixin.qq.com/s/6UZhaI9cILJiPGYHkXd73g","niceDate":"2018-04-24","origin":"","projectLink":"","publishTime":1524535024000,"superChapterId":60,"superChapterName":"开发环境","tags":[],"title":"弄懂Gradle依赖 Android依赖导入全攻略","type":0,"visible":1,"zan":0},{"apkLink":"","author":"zfman","chapterId":357,"chapterName":"表格类","collect":false,"courseId":13,"desc":"一个功能完善、UI简洁的仿超级课程表的课表控件(目前只维护Android Studio版本)\r\n","envelopePic":"http://www.wanandroid.com/blogimgs/d55b4ca3-9486-4b53-abfb-d5f4fcf76d6b.png","fresh":false,"id":2868,"link":"http://www.wanandroid.com/blog/show/2117","niceDate":"2018-04-23","origin":"","projectLink":"https://github.com/zfman/TimetableView","publishTime":1524463250000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=357"}],"title":"一个功能完善、UI简洁的仿超级课程表的课表控件 TimetableView","type":0,"visible":1,"zan":0},{"apkLink":"","author":"滑板上的老砒霜 ","chapterId":135,"chapterName":"二维码","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":2867,"link":"https://www.jianshu.com/p/a4ba10da4231","niceDate":"2018-04-23","origin":"","projectLink":"","publishTime":1524462310000,"superChapterId":135,"superChapterName":"项目必备","tags":[],"title":"Android扫一扫：zxing的集成与优化","type":0,"visible":1,"zan":0},{"apkLink":"","author":"杨晓彬","chapterId":160,"chapterName":"热修复","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":2866,"link":"https://yangxiaobinhaoshuai.github.io/2018/04/19/support-multidex%E5%8E%9F%E7%90%86/","niceDate":"2018-04-23","origin":"","projectLink":"","publishTime":1524460954000,"superChapterId":79,"superChapterName":"热门专题","tags":[],"title":"Support-multidex原理","type":0,"visible":1,"zan":0},{"apkLink":"","author":"小编","chapterId":352,"chapterName":"资讯","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":2842,"link":"https://www.oschina.net/openapi","niceDate":"2018-04-23","origin":"","projectLink":"","publishTime":1524450097000,"superChapterId":349,"superChapterName":"开放API","tags":[],"title":"开源中国oschina API","type":0,"visible":1,"zan":0},{"apkLink":"","author":"鸿洋","chapterId":249,"chapterName":"干货资源","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":2846,"link":"https://mp.weixin.qq.com/s/NzxdRU1r6bPGFn_gsUFCmw","niceDate":"2018-04-23","origin":"","projectLink":"","publishTime":1524449010000,"superChapterId":249,"superChapterName":"干货资源","tags":[],"title":"推荐三个完整开源项目","type":0,"visible":1,"zan":0},{"apkLink":"","author":"Shawn_Dut","chapterId":98,"chapterName":"WebView","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":2837,"link":"https://juejin.im/post/58a037df86b599006b3fade4","niceDate":"2018-04-22","origin":"","projectLink":"","publishTime":1524378206000,"superChapterId":98,"superChapterName":"网络访问","tags":[],"title":"android WebView详解，常见漏洞详解和安全源码","type":0,"visible":1,"zan":0},{"apkLink":"","author":"LiangLuDev","chapterId":294,"chapterName":"完整项目","collect":false,"courseId":13,"desc":"注册登录、用户信息、用户密码、用户图像修改、书籍分类、本地书籍扫描、书架、书籍搜索（作者名或书籍名）、书籍阅读（仅txt格式，暂不支持PDF等其他格式）、阅读字体、背景颜色、翻页效果等设置、意见反馈（反馈信息发送到我的邮箱）、应用版本更新","envelopePic":"http://www.wanandroid.com/blogimgs/fab6fb8b-c3aa-495f-b6a9-c007d78751c0.gif","fresh":false,"id":2836,"link":"http://www.wanandroid.com/blog/show/2116","niceDate":"2018-04-22","origin":"","projectLink":"https://github.com/LiangLuDev/WeYueReader","publishTime":1524376619000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=294"}],"title":"微Yue电子书阅读 WeYueReader","type":0,"visible":1,"zan":0},{"apkLink":"","author":"小鄧子","chapterId":70,"chapterName":"retrofit","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":2835,"link":"https://www.jianshu.com/p/fca90d0da2b5","niceDate":"2018-04-20","origin":"","projectLink":"","publishTime":1524211674000,"superChapterId":98,"superChapterName":"网络访问","tags":[],"title":"All RxJava - 为Retrofit添加重试","type":0,"visible":1,"zan":0},{"apkLink":"","author":"sunshine8","chapterId":185,"chapterName":"组件化","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":2834,"link":"https://www.jianshu.com/p/8a3eeeaf01e8","niceDate":"2018-04-19","origin":"","projectLink":"","publishTime":1524141536000,"superChapterId":79,"superChapterName":"热门专题","tags":[],"title":"Android 组件化 \u2014\u2014 路由设计最佳实践","type":0,"visible":1,"zan":0},{"apkLink":"","author":"_Ricky_","chapterId":182,"chapterName":"JNI编程","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":2833,"link":"https://www.jianshu.com/p/f20f9bcdd6a4","niceDate":"2018-04-19","origin":"","projectLink":"","publishTime":1524138951000,"superChapterId":182,"superChapterName":"JNI","tags":[],"title":"JNI实现图片压缩","type":0,"visible":1,"zan":0},{"apkLink":"","author":"huburt-Hu","chapterId":347,"chapterName":"图层引导","collect":false,"courseId":13,"desc":"Android 快速实现新手引导层的库，通过简洁链式调用，一行代码实现引导层的显示\r\n","envelopePic":"http://www.wanandroid.com/blogimgs/77cfaf34-d397-4d6e-aac1-83e243e1514a.png","fresh":false,"id":2831,"link":"http://www.wanandroid.com/blog/show/2114","niceDate":"2018-04-19","origin":"","projectLink":"https://github.com/huburt-Hu/NewbieGuide","publishTime":1524115829000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=347"}],"title":" Android 快速实现新手引导层的库 NewbieGuide","type":0,"visible":1,"zan":0},{"apkLink":"","author":"鸿洋","chapterId":298,"chapterName":"我的博客","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":2830,"link":"http://www.wanandroid.com/blog/show/2113","niceDate":"2018-04-18","origin":"","projectLink":"","publishTime":1524062149000,"superChapterId":298,"superChapterName":"原创文章","tags":[],"title":"带你了解腾讯开源的多渠道打包技术 VasDolly源码解析","type":0,"visible":1,"zan":0},{"apkLink":"","author":"xiangcman","chapterId":314,"chapterName":"RV列表动效","collect":false,"courseId":13,"desc":"快速利用RecyclerView的LayoutManager搭建流式布局 ","envelopePic":"http://www.wanandroid.com/blogimgs/36badc79-fb1e-460e-8368-6898c16ba723.png","fresh":false,"id":2829,"link":"http://www.wanandroid.com/blog/show/2112","niceDate":"2018-04-18","origin":"","projectLink":"https://github.com/xiangcman/LayoutManager-FlowLayout","publishTime":1524051620000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=314"}],"title":"快速利用RecyclerView的LayoutManager搭建流式布局 ","type":0,"visible":1,"zan":0},{"apkLink":"","author":"醒着的码者","chapterId":245,"chapterName":"集合相关","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":2826,"link":"https://www.jianshu.com/p/99ad883041d6","niceDate":"2018-04-18","origin":"","projectLink":"","publishTime":1524051390000,"superChapterId":245,"superChapterName":"Java深入","tags":[],"title":"搞懂 HashSet & LinkedHashSet 源码 以及集合常见面试题目","type":0,"visible":1,"zan":0},{"apkLink":"","author":"Allen___","chapterId":334,"chapterName":"Architecture Components","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":2825,"link":"https://www.jianshu.com/p/72c8efc3ad87","niceDate":"2018-04-18","origin":"","projectLink":"","publishTime":1524050639000,"superChapterId":183,"superChapterName":"5.+高新技术","tags":[],"title":"快速掌握Room数据库框架（附Demo）","type":0,"visible":1,"zan":0},{"apkLink":"","author":"zejian_","chapterId":15,"chapterName":"Service","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":2824,"link":"https://blog.csdn.net/javazejian/article/details/52709857","niceDate":"2018-04-18","origin":"","projectLink":"","publishTime":1524040495000,"superChapterId":10,"superChapterName":"四大组件","tags":[],"title":"关于Android Service真正的完全详解，你需要知道的一切","type":0,"visible":1,"zan":0}]
     * offset : 20
     * over : false
     * pageCount : 63
     * size : 20
     * total : 1253
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
         * apkLink :
         * author : jia635
         * chapterId : 26
         * chapterName : 基础UI控件
         * collect : false
         * courseId : 13
         * desc :
         * envelopePic :
         * fresh : false
         * id : 2874
         * link : https://blog.csdn.net/jia635/article/details/52387658
         * niceDate : 2018-04-26
         * origin :
         * projectLink :
         * publishTime : 1524732906000
         * superChapterId : 26
         * superChapterName : 常用控件
         * tags : []
         * title : 为什么Dialog不能用Application的Context
         * type : 0
         * visible : 1
         * zan : 0
         */

        private String apkLink;
        private String author;
        private int chapterId;
        private String chapterName;
        private boolean collect;
        private int courseId;
        private String desc;
        private String envelopePic;
        private boolean fresh;
        private int id;
        private String link;
        private String niceDate;
        private String origin;
        private String projectLink;
        private long publishTime;
        private int superChapterId;
        private String superChapterName;
        private String title;
        private int type;
        private int visible;
        private int zan;
        private List<?> tags;

        public String getApkLink() {
            return apkLink;
        }

        public void setApkLink(String apkLink) {
            this.apkLink = apkLink;
        }

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

        public boolean isCollect() {
            return collect;
        }

        public void setCollect(boolean collect) {
            this.collect = collect;
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

        public boolean isFresh() {
            return fresh;
        }

        public void setFresh(boolean fresh) {
            this.fresh = fresh;
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

        public String getProjectLink() {
            return projectLink;
        }

        public void setProjectLink(String projectLink) {
            this.projectLink = projectLink;
        }

        public long getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(long publishTime) {
            this.publishTime = publishTime;
        }

        public int getSuperChapterId() {
            return superChapterId;
        }

        public void setSuperChapterId(int superChapterId) {
            this.superChapterId = superChapterId;
        }

        public String getSuperChapterName() {
            return superChapterName;
        }

        public void setSuperChapterName(String superChapterName) {
            this.superChapterName = superChapterName;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
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

        public List<?> getTags() {
            return tags;
        }

        public void setTags(List<?> tags) {
            this.tags = tags;
        }
    }
}


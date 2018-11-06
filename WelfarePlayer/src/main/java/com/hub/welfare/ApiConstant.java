package com.hub.welfare;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/4/19
 * 描述：API接口定义
 */
public class ApiConstant {


    public interface Jokes {
        String JOKES_URL = "http://v.juhe.cn/joke/content/text.php?key=5abd39caac680a158e4c17f5388c619a";
    }

    public interface Photo {
        String HUABAN_URL = "http://flymegoc.cn/";
        String MEIZITU_URL = "http://www.mzitu.com/";
        String JIUMEI_URL = "http://www.99mm.me/";
    }

    public interface HuaBan {
        String HUABAN_CATEGORY_URL = Photo.HUABAN_URL + "findCategory";
    }

    public interface MeiZi {
        String HUABAN_URL = Photo.MEIZITU_URL + "";
    }

    public interface JiuMei {
        String HUABAN_URL = Photo.JIUMEI_URL + "";
    }

}

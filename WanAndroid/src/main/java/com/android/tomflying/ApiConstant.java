package com.android.tomflying;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/4/19
 * 描述：API接口定义
 */
public class ApiConstant {


    /**
     * Base接口地址
     */
    public static final String BASE_URL = "http://www.wanandroid.com/";

    public static final String END_URL = "/json";


    public interface Home {

        String BANNER_URL = BASE_URL + "banner" + END_URL;

        String ARTICLES_URL = BASE_URL + "article/list/";


    }

    public interface Guide {
        String TREE_URL = BASE_URL + "tree" + END_URL;

        String ARTICLES_URL = BASE_URL + "article/list/";

        String HOT_KEY_URL = BASE_URL + "hotkey" + END_URL;

        String SEARCH_URL = BASE_URL + "article/query/";
    }

    public interface Project {
        String TREE_URL = BASE_URL + "project/tree" + END_URL;
        String PROJECTS_URL = BASE_URL + "project/list/";
    }

    public interface User {
        String LOGIN_URL = BASE_URL + "user/login";
        String REGISTER_URL = BASE_URL + "user/register";
        String DO_COLLECT_URL = BASE_URL + "lg/collect/";
        String DO_UNCOLLECT_IN_ARTICLE_URL = BASE_URL + "lg/uncollect_originId/";
        String DO_UNCOLLECT_OUT_ARTICLE_URL = BASE_URL + "lg/uncollect/";
        String COLLECT_ARTICLES_URL = BASE_URL + "lg/collect/list/";

    }


}

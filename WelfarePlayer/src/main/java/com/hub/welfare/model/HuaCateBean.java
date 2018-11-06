package com.hub.welfare.model;

import java.util.List;

/**
 * 作者:  tom_flying
 * 邮箱: tom_flying@163.com
 * 博客:  http://www.tianfeifei.com
 * 创建日期: 2018/11/5
 * 描述：
 */
public class HuaCateBean {


    /**
     * code : 200
     * message : 成功
     * data : [{"id":1,"categoryName":"造型美妆","categoryUrl":"http://huaban.com/favorite/modeling_hair/","categoryCreateTime":"2017-11-05 23:26:38","categoryUpdateTime":"2017-11-05 23:26:38","categoryStatus":0},{"id":2,"categoryName":"美食","categoryUrl":"http://huaban.com/favorite/food_drink/","categoryCreateTime":"2017-11-05 23:26:38","categoryUpdateTime":"2017-11-05 23:26:38","categoryStatus":0},{"id":3,"categoryName":"旅行","categoryUrl":"http://huaban.com/favorite/travel_places/","categoryCreateTime":"2017-11-05 23:26:38","categoryUpdateTime":"2017-11-05 23:26:38","categoryStatus":0},{"id":4,"categoryName":"手工布艺","categoryUrl":"http://huaban.com/favorite/diy_crafts/","categoryCreateTime":"2017-11-05 23:26:38","categoryUpdateTime":"2017-11-05 23:26:38","categoryStatus":0},{"id":5,"categoryName":"健身舞蹈","categoryUrl":"http://huaban.com/favorite/fitness/","categoryCreateTime":"2017-11-05 23:26:38","categoryUpdateTime":"2017-11-05 23:26:38","categoryStatus":0},{"id":6,"categoryName":"儿童","categoryUrl":"http://huaban.com/favorite/kids/","categoryCreateTime":"2017-11-05 23:26:38","categoryUpdateTime":"2017-11-05 23:26:38","categoryStatus":0},{"id":7,"categoryName":"宠物","categoryUrl":"http://huaban.com/favorite/pets/","categoryCreateTime":"2017-11-05 23:26:38","categoryUpdateTime":"2017-11-05 23:26:38","categoryStatus":0},{"id":8,"categoryName":"美图","categoryUrl":"http://huaban.com/favorite/quotes/","categoryCreateTime":"2017-11-05 23:26:38","categoryUpdateTime":"2017-11-05 23:26:38","categoryStatus":0},{"id":9,"categoryName":"明星","categoryUrl":"http://huaban.com/favorite/people/","categoryCreateTime":"2017-11-05 23:26:38","categoryUpdateTime":"2017-11-05 23:26:38","categoryStatus":0},{"id":10,"categoryName":"美女","categoryUrl":"http://huaban.com/favorite/beauty/","categoryCreateTime":"2017-11-05 23:26:38","categoryUpdateTime":"2017-11-05 23:26:38","categoryStatus":0},{"id":11,"categoryName":"礼物","categoryUrl":"http://huaban.com/favorite/desire/","categoryCreateTime":"2017-11-05 23:26:38","categoryUpdateTime":"2017-11-05 23:26:38","categoryStatus":0},{"id":12,"categoryName":"极客","categoryUrl":"http://huaban.com/favorite/geek/","categoryCreateTime":"2017-11-05 23:26:38","categoryUpdateTime":"2017-11-05 23:26:38","categoryStatus":0},{"id":13,"categoryName":"动漫","categoryUrl":"http://huaban.com/favorite/anime/","categoryCreateTime":"2017-11-05 23:26:38","categoryUpdateTime":"2017-11-05 23:26:38","categoryStatus":0},{"id":14,"categoryName":"建筑设计","categoryUrl":"http://huaban.com/favorite/architecture/","categoryCreateTime":"2017-11-05 23:26:38","categoryUpdateTime":"2017-11-05 23:26:38","categoryStatus":0},{"id":15,"categoryName":"人文艺术","categoryUrl":"http://huaban.com/favorite/art/","categoryCreateTime":"2017-11-05 23:26:38","categoryUpdateTime":"2017-11-05 23:26:38","categoryStatus":0},{"id":16,"categoryName":"数据图","categoryUrl":"http://huaban.com/favorite/data_presentation/","categoryCreateTime":"2017-11-05 23:26:38","categoryUpdateTime":"2017-11-05 23:26:38","categoryStatus":0},{"id":17,"categoryName":"游戏","categoryUrl":"http://huaban.com/favorite/games/","categoryCreateTime":"2017-11-05 23:26:38","categoryUpdateTime":"2017-11-05 23:26:38","categoryStatus":0},{"id":18,"categoryName":"汽车摩托","categoryUrl":"http://huaban.com/favorite/cars_motorcycles/","categoryCreateTime":"2017-11-05 23:26:39","categoryUpdateTime":"2017-11-05 23:26:39","categoryStatus":0},{"id":19,"categoryName":"电影图书","categoryUrl":"http://huaban.com/favorite/film_music_books/","categoryCreateTime":"2017-11-05 23:26:39","categoryUpdateTime":"2017-11-05 23:26:39","categoryStatus":0},{"id":20,"categoryName":"生活百科","categoryUrl":"http://huaban.com/favorite/tips/","categoryCreateTime":"2017-11-05 23:26:39","categoryUpdateTime":"2017-11-05 23:26:39","categoryStatus":0},{"id":21,"categoryName":"教育","categoryUrl":"http://huaban.com/favorite/education/","categoryCreateTime":"2017-11-05 23:26:39","categoryUpdateTime":"2017-11-05 23:26:39","categoryStatus":0},{"id":22,"categoryName":"运动","categoryUrl":"http://huaban.com/favorite/sports/","categoryCreateTime":"2017-11-05 23:26:39","categoryUpdateTime":"2017-11-05 23:26:39","categoryStatus":0},{"id":23,"categoryName":"搞笑","categoryUrl":"http://huaban.com/favorite/funny/","categoryCreateTime":"2017-11-05 23:26:39","categoryUpdateTime":"2017-11-05 23:26:39","categoryStatus":0}]
     */

    private int code;
    private String message;
    private List<Data> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public static class Data {
        /**
         * id : 1
         * categoryName : 造型美妆
         * categoryUrl : http://huaban.com/favorite/modeling_hair/
         * categoryCreateTime : 2017-11-05 23:26:38
         * categoryUpdateTime : 2017-11-05 23:26:38
         * categoryStatus : 0
         */

        private int id;
        private String categoryName;
        private String categoryUrl;
        private String categoryCreateTime;
        private String categoryUpdateTime;
        private int categoryStatus;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getCategoryUrl() {
            return categoryUrl;
        }

        public void setCategoryUrl(String categoryUrl) {
            this.categoryUrl = categoryUrl;
        }

        public String getCategoryCreateTime() {
            return categoryCreateTime;
        }

        public void setCategoryCreateTime(String categoryCreateTime) {
            this.categoryCreateTime = categoryCreateTime;
        }

        public String getCategoryUpdateTime() {
            return categoryUpdateTime;
        }

        public void setCategoryUpdateTime(String categoryUpdateTime) {
            this.categoryUpdateTime = categoryUpdateTime;
        }

        public int getCategoryStatus() {
            return categoryStatus;
        }

        public void setCategoryStatus(int categoryStatus) {
            this.categoryStatus = categoryStatus;
        }
    }
}

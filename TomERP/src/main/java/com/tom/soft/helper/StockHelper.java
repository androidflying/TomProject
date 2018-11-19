package com.tom.soft.helper;

import com.tom.soft.model.Stock;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 作者:  tom_flying
 * 邮箱: tom_flying@163.com
 * 博客:  http://www.tianfeifei.com
 * 创建日期: 2018/11/19
 * 描述：原料表的相关操作
 */
public class StockHelper {

    /**
     * 添加一种原料
     *
     * @param name
     * @param unit
     * @param minData
     * @param balance
     * @param listener
     */
    public static void addStock(String name, String unit, String minData, Integer balance, SaveListener<String> listener) {
        Stock stock = new Stock();
        stock.setName(name);
        stock.setUnit(unit);
        stock.setMinData(minData);
        stock.setBalance(balance);
        stock.save(listener);
    }

    /**
     * 删除一种原料
     *
     * @param objectId
     * @param listener
     */
    public static void deleteStock(String objectId, UpdateListener listener) {
        Stock stock = new Stock();
        stock.setObjectId(objectId);
        stock.delete(listener);
    }

    /**
     * 修改原料信息
     *
     * @param objectId
     * @param name
     * @param unit
     * @param minData
     * @param balance
     * @param listener
     */
    public static void updateStock(String objectId, String name, String unit, String minData, Integer balance, UpdateListener listener) {
        Stock stock = new Stock();
        stock.setName(name);
        stock.setUnit(unit);
        stock.setMinData(minData);
        stock.setBalance(balance);
        stock.update(objectId, listener);
    }

    /**
     * 获取所有的原料信息
     *
     * @param listener
     */
    public static void getStocks(FindListener<Stock> listener) {
        BmobQuery<Stock> query = new BmobQuery<>();

        query.order("updatedAt");
        boolean isCache = query.hasCachedResult(Stock.class);

        if (isCache) {
            // 如果有缓存的话，则设置策略为CACHE_ELSE_NETWORK
            query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        } else {
            // 如果没有缓存的话，则设置策略为NETWORK_ELSE_CACHE
            query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        }
        query.findObjects(listener);
    }

    /**
     * 获取一个原料的信息
     *
     * @param objectId
     * @param listener
     */
    public static void getStock(String objectId, QueryListener<Stock> listener) {
        BmobQuery<Stock> query = new BmobQuery<>();
        query.getObject(objectId, listener);
    }

    /**
     * 获取所有的原料信息
     *
     * @param listener
     */
    public static void getStock(String name, FindListener<Stock> listener) {
        BmobQuery<Stock> query = new BmobQuery<>();
        query.addWhereEqualTo("name", name);
        query.order("updatedAt");
        boolean isCache = query.hasCachedResult(Stock.class);

        if (isCache) {
            // 如果有缓存的话，则设置策略为CACHE_ELSE_NETWORK
            query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        } else {
            // 如果没有缓存的话，则设置策略为NETWORK_ELSE_CACHE
            query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        }
        query.findObjects(listener);
    }

}

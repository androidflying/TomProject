package com.tom.soft.helper;

import com.tom.soft.model.Record;
import com.tom.soft.model.Stock;
import com.tom.soft.model.TomUser;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * 作者:  tom_flying
 * 邮箱: tom_flying@163.com
 * 博客:  http://www.tianfeifei.com
 * 创建日期: 2018/11/19
 * 描述：操作记录的相关操作
 */
public class RecordHelper {

    /**
     * 增加一条操作记录
     *
     * @param stockName
     * @param operation
     * @param num
     * @param listener
     */
    private static void addRecord(String stockName, String operation, Integer num, SaveListener<String> listener) {
        final Record record = new Record();
        TomUser user = BmobUser.getCurrentUser(TomUser.class);
        record.setUser(user);
        StockHelper.getStock(stockName, new FindListener<Stock>() {
            @Override
            public void done(List<Stock> list, BmobException e) {
                record.setStock(list.get(0));
            }
        });
        record.setOperation(operation);
        record.setNum(num);
        record.save(listener);
    }


    /**
     * 操作记录是添加库存
     *
     * @param stockName
     * @param num
     * @param listener
     */
    public static void addition(String stockName, Integer num, SaveListener<String> listener) {
        addRecord(stockName, "addition", num, listener);
    }

    /**
     * 操作记录是减少库存
     *
     * @param stockName
     * @param num
     * @param listener
     */
    public static void subtraction(String stockName, Integer num, SaveListener<String> listener) {
        addRecord(stockName, "subtraction", num, listener);
    }

    /**
     * 查询某天中所有的操作记录
     *
     * @param time
     * @param listener
     */
    public static void getRecordsByDate(String time, FindListener<Record> listener) {
        BmobQuery<Record> query = new BmobQuery<>();
        query.addWhereEqualTo("time", time);
        query.order("updatedAt");
        boolean isCache = query.hasCachedResult(Record.class);

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
     * 查询某原料所有的操作记录
     *
     * @param stockName
     * @param listener
     */
    public static void getRecordsByStock(String stockName, FindListener<Record> listener) {
        BmobQuery<Record> query = new BmobQuery<>();
        query.addWhereEqualTo("time", stockName);
        query.order("updatedAt");
        boolean isCache = query.hasCachedResult(Record.class);

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

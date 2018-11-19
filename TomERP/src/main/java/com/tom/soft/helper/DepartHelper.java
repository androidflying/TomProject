package com.tom.soft.helper;

import com.tom.soft.model.Department;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobQuery.CachePolicy;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 作者:  tom_flying
 * 邮箱: tom_flying@163.com
 * 博客:  http://www.tianfeifei.com
 * 创建日期: 2018/11/19
 * 描述：部门表相关操作类
 */
public class DepartHelper {

    /**
     * 添加一个部门
     *
     * @param name
     * @param listener
     */
    public static void addDepartment(String name, SaveListener<String> listener) {
        Department department = new Department();
        department.setName(name);
        department.save(listener);
    }

    /**
     * 删除一个部门
     *
     * @param objectId
     * @param listener
     */
    public static void deleteDepartment(String objectId, UpdateListener listener) {
        Department department = new Department();
        department.setObjectId(objectId);
        department.delete(listener);
    }

    /**
     * 修改部门名称
     *
     * @param objectId
     * @param name
     * @param listener
     */
    public static void updateDepartment(String objectId, String name, UpdateListener listener) {
        Department department = new Department();
        department.setName(name);
        department.update(objectId, listener);
    }

    /**
     * 获取所有的部门
     *
     * @return
     */
    public static void getDepartments(FindListener<Department> listener) {
        BmobQuery<Department> query = new BmobQuery<>();

        query.order("updatedAt");
        boolean isCache = query.hasCachedResult(Department.class);

        if (isCache) {
            // 如果有缓存的话，则设置策略为CACHE_ELSE_NETWORK
            query.setCachePolicy(CachePolicy.CACHE_ELSE_NETWORK);
        } else {
            // 如果没有缓存的话，则设置策略为NETWORK_ELSE_CACHE
            query.setCachePolicy(CachePolicy.NETWORK_ELSE_CACHE);
        }
        query.findObjects(listener);
    }

    /**
     * 获取一个部门的信息
     *
     * @param objectId
     * @param listener
     */
    public void getDepartment(String objectId, QueryListener<Department> listener) {
        BmobQuery<Department> query = new BmobQuery<>();
        query.getObject(objectId, listener);
    }
}

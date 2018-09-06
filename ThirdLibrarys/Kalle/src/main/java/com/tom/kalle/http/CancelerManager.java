package com.tom.kalle.http;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/9/6
 * 描述：
 */
public class CancelerManager {

    private final Map<Request, Canceller> mCancelMap;

    public CancelerManager() {
        this.mCancelMap = new ConcurrentHashMap<>();
    }

    /**
     * Add a task to cancel.
     *
     * @param request   target request.
     * @param canceller canceller.
     */
    public void addCancel(Request request, Canceller canceller) {
        mCancelMap.put(request, canceller);
    }

    /**
     * Remove a task.
     *
     * @param request target request.
     */
    public void removeCancel(Request request) {
        mCancelMap.remove(request);
    }

    /**
     * According to the tag to cancel a task.
     *
     * @param tag tag.
     */
    public void cancel(Object tag) {
        for (Map.Entry<Request, Canceller> entry : mCancelMap.entrySet()) {
            Request request = entry.getKey();
            Object oldTag = request.tag();
            if ((tag == oldTag) || (tag != null && tag.equals(oldTag))) {
                entry.getValue().cancel();
            }
        }
    }
}
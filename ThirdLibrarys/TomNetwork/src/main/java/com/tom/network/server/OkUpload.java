package com.tom.network.server;

import com.tom.network.db.UploadManager;
import com.tom.network.model.Progress;
import com.tom.network.request.base.Request;
import com.tom.network.server.task.XExecutor;
import com.tom.network.server.upload.UploadTask;
import com.tom.network.server.upload.UploadThreadPool;
import com.tom.network.utils.OkLogger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * ================================================
 * <p>
 * 描    述：全局的上传管理
 * <p>
 * ================================================
 */
public class OkUpload {
    //所有任务
    private Map<String, UploadTask<?>> taskMap;
    //上传的线程池
    private UploadThreadPool threadPool;

    public static OkUpload getInstance() {
        return OkUploadHolder.instance;
    }

    private static class OkUploadHolder {
        private static final OkUpload instance = new OkUpload();
    }

    private OkUpload() {
        threadPool = new UploadThreadPool();
        taskMap = new LinkedHashMap<>();

        //校验数据的有效性，防止下载过程中退出，第二次进入的时候，由于状态没有更新导致的状态错误
        List<Progress> taskList = UploadManager.getInstance().getUploading();
        for (Progress info : taskList) {
            if (info.status == Progress.WAITING || info.status == Progress.LOADING || info.status == Progress.PAUSE) {
                info.status = Progress.NONE;
            }
        }
        UploadManager.getInstance().replace(taskList);
    }

    public static <T> UploadTask<T> request(String tag, Request<T, ? extends Request> request) {
        Map<String, UploadTask<?>> taskMap = OkUpload.getInstance().getTaskMap();
        //noinspection unchecked
        UploadTask<T> task = (UploadTask<T>) taskMap.get(tag);
        if (task == null) {
            task = new UploadTask<>(tag, request);
            taskMap.put(tag, task);
        }
        return task;
    }

    /**
     * 从数据库中恢复任务
     */
    public static <T> UploadTask<T> restore(Progress progress) {
        Map<String, UploadTask<?>> taskMap = OkUpload.getInstance().getTaskMap();
        //noinspection unchecked
        UploadTask<T> task = (UploadTask<T>) taskMap.get(progress.tag);
        if (task == null) {
            task = new UploadTask<>(progress);
            taskMap.put(progress.tag, task);
        }
        return task;
    }

    /**
     * 从数据库中恢复任务
     */
    public static List<UploadTask<?>> restore(List<Progress> progressList) {
        Map<String, UploadTask<?>> taskMap = OkUpload.getInstance().getTaskMap();
        List<UploadTask<?>> tasks = new ArrayList<>();
        for (Progress progress : progressList) {
            UploadTask<?> task = taskMap.get(progress.tag);
            if (task == null) {
                task = new UploadTask<>(progress);
                taskMap.put(progress.tag, task);
            }
            tasks.add(task);
        }
        return tasks;
    }

    /**
     * 开始所有任务
     */
    public void startAll() {
        for (Map.Entry<String, UploadTask<?>> entry : taskMap.entrySet()) {
            UploadTask<?> task = entry.getValue();
            if (task == null) {
                OkLogger.w("can't find task with tag = " + entry.getKey());
                continue;
            }
            task.start();
        }
    }

    /**
     * 暂停全部任务
     */
    public void pauseAll() {
        //先停止未开始的任务
        for (Map.Entry<String, UploadTask<?>> entry : taskMap.entrySet()) {
            UploadTask<?> task = entry.getValue();
            if (task == null) {
                OkLogger.w("can't find task with tag = " + entry.getKey());
                continue;
            }
            if (task.progress.status != Progress.LOADING) {
                task.pause();
            }
        }
        //再停止进行中的任务
        for (Map.Entry<String, UploadTask<?>> entry : taskMap.entrySet()) {
            UploadTask<?> task = entry.getValue();
            if (task == null) {
                OkLogger.w("can't find task with tag = " + entry.getKey());
                continue;
            }
            if (task.progress.status == Progress.LOADING) {
                task.pause();
            }
        }
    }

    /**
     * 删除所有任务
     */
    public void removeAll() {
        Map<String, UploadTask<?>> map = new HashMap<>(taskMap);
        //先删除未开始的任务
        for (Map.Entry<String, UploadTask<?>> entry : map.entrySet()) {
            UploadTask<?> task = entry.getValue();
            if (task == null) {
                OkLogger.w("can't find task with tag = " + entry.getKey());
                continue;
            }
            if (task.progress.status != Progress.LOADING) {
                task.remove();
            }
        }
        //再删除进行中的任务
        for (Map.Entry<String, UploadTask<?>> entry : map.entrySet()) {
            UploadTask<?> task = entry.getValue();
            if (task == null) {
                OkLogger.w("can't find task with tag = " + entry.getKey());
                continue;
            }
            if (task.progress.status == Progress.LOADING) {
                task.remove();
            }
        }
    }

    public UploadThreadPool getThreadPool() {
        return threadPool;
    }

    public Map<String, UploadTask<?>> getTaskMap() {
        return taskMap;
    }

    public UploadTask<?> getTask(String tag) {
        return taskMap.get(tag);
    }

    public boolean hasTask(String tag) {
        return taskMap.containsKey(tag);
    }

    public UploadTask<?> removeTask(String tag) {
        return taskMap.remove(tag);
    }

    public void addOnAllTaskEndListener(XExecutor.OnAllTaskEndListener listener) {
        threadPool.getExecutor().addOnAllTaskEndListener(listener);
    }

    public void removeOnAllTaskEndListener(XExecutor.OnAllTaskEndListener listener) {
        threadPool.getExecutor().removeOnAllTaskEndListener(listener);
    }
}

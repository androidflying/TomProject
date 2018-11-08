package com.tom.im;

import android.app.Application;

import com.tom.baselib.ApplicationImpl;
import com.tom.baselib.utils.ProcessUtils;
import com.tom.im.util.ChatUtil;

import androidx.annotation.NonNull;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/6/25
 * 描述：
 */
public class AppImpl implements ApplicationImpl {

    @Override
    public void onCreate(@NonNull Application application) {
        if (ProcessUtils.isMainProcess(application)) {
            initIM();
        }
    }

    private void initIM() {
        ChatUtil.registerPush();
        ChatUtil.init();
        ChatUtil.setConnectionStatusListener();
    }

}

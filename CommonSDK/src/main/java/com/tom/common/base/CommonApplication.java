package com.tom.common.base;

import com.tom.baselib.ApplicationImpl;
import com.tom.baselib.BaseApplication;
import com.tom.baselib.utils.Utils;
import com.tom.common.ManifestParser;

import java.util.List;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/6/27
 * 描述：
 */
public class CommonApplication extends BaseApplication {
    private List<ApplicationImpl> mModules;

    @Override
    public void initCrashReport() {

    }

    @Override
    protected void initNetWork() {

    }

    @Override
    public void modulesApplicationInit() {

        //用反射, 将 AndroidManifest.xml 中带有 ConfigModule 标签的 class 转成对象集合（List<ConfigModule>）
        mModules = new ManifestParser(Utils.getApp()).parse();
        //遍历之前获得的集合, 执行每一个 ConfigModule 实现类的某些方法
        for (ApplicationImpl module : mModules) {
            module.onCreate(Utils.getApp());
        }

    }
}

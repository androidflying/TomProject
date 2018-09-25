package com.tom.mrvah.helper;

import android.support.annotation.IntRange;
import android.util.SparseArray;

/**
 * 作者:  tom_flying
 * 邮箱: tom_flying@163.com
 * 博客:  http://www.tianfeifei.com
 * 创建日期: 2018/9/25
 * 描述：loading配置项
 */
public class LoadingConfig {

    private SparseArray<LoadingConfigEntity> mConfigs;

    public LoadingConfig() {
        if (mConfigs == null) {
            mConfigs = new SparseArray<>();
        }
    }

    private LoadingConfig(SparseArray<LoadingConfigEntity> configs) {
        mConfigs = configs;
    }

    public void setLoading(int level, @IntRange(from = 1) int count, boolean isHaveHeader) {
        mConfigs.put(level, new LoadingConfigEntity(count, isHaveHeader));
    }

    public void setLoading(int level, @IntRange(from = 1) int count) {
        mConfigs.put(level, new LoadingConfigEntity(count));
    }

    public void setLoading(int level, boolean isHaveHeader) {
        mConfigs.put(level, new LoadingConfigEntity(isHaveHeader));
    }

    public static class Builder {

        private SparseArray<LoadingConfigEntity> mBuilderConfigs;

        public Builder() {
            if (mBuilderConfigs == null) {
                mBuilderConfigs = new SparseArray<>();
            }
        }

        public Builder setLoading(int level, @IntRange(from = 1) int count, boolean isHaveHeader) {
            mBuilderConfigs.put(level, new LoadingConfigEntity(count, isHaveHeader));
            return this;
        }

        public Builder setLoading(int level, @IntRange(from = 1) int count) {
            mBuilderConfigs.put(level, new LoadingConfigEntity(count));
            return this;
        }

        public Builder setLoading(int level, boolean isHaveHeader) {
            mBuilderConfigs.put(level, new LoadingConfigEntity(isHaveHeader));
            return this;
        }

        public LoadingConfig build() {
            return new LoadingConfig(mBuilderConfigs);
        }
    }

    SparseArray<LoadingConfigEntity> getConfigs() {
        return mConfigs;
    }
}

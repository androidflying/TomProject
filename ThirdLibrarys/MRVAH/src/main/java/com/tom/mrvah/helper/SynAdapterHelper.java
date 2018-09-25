package com.tom.mrvah.helper;

import com.tom.mrvah.entity.HandleBase;
import com.tom.mrvah.entity.MultiTypeEntity;

import java.util.List;

/**
 * 作者:  tom_flying
 * 邮箱: tom_flying@163.com
 * 博客:  http://www.tianfeifei.com
 * 创建日期: 2018/9/25
 * 描述：同步适配器，数据量过大可能会卡顿，甚至出现ANR，相对来说比较稳定
 *      卡顿的原因主要是计算差异数据量过大以及大量UI更新
 *      可以采用异步刷新{@link AsynAdapterHelper}
 */
public abstract class SynAdapterHelper<T extends MultiTypeEntity> extends RecyclerViewAdapterHelper<T> {


    public SynAdapterHelper(List<T> data) {
        super(data);
    }

    public SynAdapterHelper(List<T> data, @RefreshMode int mode) {
        super(data, mode);
    }

    @Override
    protected void startRefresh(HandleBase<T> refreshData) {
        handleResult(handleRefresh(refreshData.getNewData(), refreshData.getNewHeader(),
                refreshData.getNewFooter(), refreshData.getLevel(), refreshData.getRefreshType()));
    }

}

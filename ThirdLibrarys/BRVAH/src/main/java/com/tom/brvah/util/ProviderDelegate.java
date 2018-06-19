package com.tom.brvah.util;

import android.util.SparseArray;

import com.tom.brvah.provider.BaseItemProvider;

public class ProviderDelegate {

    private SparseArray<BaseItemProvider> mItemProviders = new SparseArray<>();

    public void registerProvider(BaseItemProvider provider) {
        if (provider == null) {
            throw new ItemProviderException("ItemProvider can not be null");
        }

        int viewType = provider.viewType();

        if (mItemProviders.get(viewType) == null) {
            mItemProviders.put(viewType, provider);
        }
    }

    public SparseArray<BaseItemProvider> getItemProviders() {
        return mItemProviders;
    }

}

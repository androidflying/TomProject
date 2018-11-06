package com.hub.welfare.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hub.welfare.R;
import com.hub.welfare.base.MyFragment;

import androidx.annotation.NonNull;

/**
 * 作者:  tom_flying
 * 邮箱: tom_flying@163.com
 * 博客:  http://www.tianfeifei.com
 * 创建日期: 2018/11/5
 * 描述：
 */
public class HuaBanFragment extends MyFragment {

    private int cateId;

    public static MyFragment newInstance(int id) {
        HuaBanFragment fragment = new HuaBanFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("cateId", id);
        //fragment保存参数，传入一个Bundle对象
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected void destroyViewAndThing() {

    }

    @Override
    public boolean isNeedRegister() {
        return false;
    }

    @Override
    public void initData(@NonNull Bundle bundle) {
        if (bundle != null) {
            cateId = bundle.getInt("cateId");
        }

    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_huaban;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        TextView textView = contentView.findViewById(R.id.textView);
        textView.setText(cateId + "---");
    }
}

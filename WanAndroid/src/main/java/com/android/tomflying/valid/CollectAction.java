package com.android.tomflying.valid;

import android.widget.ImageView;

import com.android.tomflying.ApiConstant;
import com.android.tomflying.R;
import com.android.tomflying.bean.ArticlesBean;
import com.android.tomflying.bean.SimpleResponse;
import com.android.tomflying.util.JsonCallback;
import com.android.tomflying.util.OkHttpUtil;
import com.google.gson.Gson;
import com.qmuiteam.tom.widget.dialog.QMUITipDialog;
import com.tom.baselib.delay.Action;
import com.tom.network.OkGo;
import com.tom.network.callback.StringCallback;
import com.tom.network.model.Response;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/4/20
 * 描述：收藏操作的判断
 */
public class CollectAction implements Action {

    private ImageView view;
    private ArticlesBean.Data data;

    public CollectAction(ImageView view, ArticlesBean.Data data) {
        this.view = view;
        this.data = data;
    }

    @Override
    public void call() {
        if (data.isCollect()) {
            //取消收藏
            OkHttpUtil.postRequest(ApiConstant.User.DO_UNCOLLECT_IN_ARTICLE_URL + data.getId() + ApiConstant.END_URL, this, null, new JsonCallback<SimpleResponse>() {
                @Override
                public void onSuccess(Response<SimpleResponse> response) {
                    if (response.body().toLzyResponse().errorCode == 0) {
                        view.setImageResource(R.mipmap.fav);
                        showSuccess("取消成功");
                    }
                }
            });

        } else {
            //进行收藏
            OkHttpUtil.postRequest(ApiConstant.User.DO_COLLECT_URL + data.getId() + ApiConstant.END_URL, this, null, new JsonCallback<SimpleResponse>() {
                @Override
                public void onSuccess(Response<SimpleResponse> response) {
                    if (response.body().toLzyResponse().errorCode == 0) {
                        view.setImageResource(R.mipmap.fav_no);
                        showSuccess("收藏成功");
                    }
                }
            });

        }
        data.setCollect(!data.isCollect());

    }

    private void showSuccess(String message) {
        final QMUITipDialog dialog = new QMUITipDialog.Builder(view.getContext())
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                .setTipWord(message)
                .create();

        dialog.show();
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 500);

    }
}

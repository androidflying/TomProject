package com.tom.common.util;

import com.tom.baselib.utils.ToastUtils;
import com.tom.baselib.utils.Utils;
import com.tom.common.base.CommonActivity;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/4/24
 * 描述：社会化分享接口
 */
public class ShareUtil {
    public static boolean isWeixinInstall(CommonActivity mActivity) {
        return UMShareAPI.get(Utils.getApp()).isInstall(mActivity, SHARE_MEDIA.WEIXIN);
    }

    public static boolean isQQInstall(CommonActivity mActivity) {
        return UMShareAPI.get(Utils.getApp()).isInstall(mActivity, SHARE_MEDIA.QQ);
    }

    public static boolean isWeiboInstall(CommonActivity mActivity) {
        return UMShareAPI.get(Utils.getApp()).isInstall(mActivity, SHARE_MEDIA.SINA);
    }

    public static void shareUrl(final CommonActivity mActivity, SHARE_MEDIA platform, String targetUrl, String title, String image, String description) {
        UMWeb web = new UMWeb(targetUrl);
        UMImage thumb = new UMImage(mActivity, image);
        //标题
        web.setTitle(title);
        //缩略图
        web.setThumb(thumb);
        //描述
        web.setDescription(description);
        new ShareAction(mActivity).setPlatform(platform)
                .withMedia(web).setCallback(new UMShareListener() {
            /**
             * @param platform 平台类型
             * @descrption 分享开始的回调
             */
            @Override
            public void onStart(SHARE_MEDIA platform) {
                mActivity.showLoadingDialog();
            }

            /**
             * @param platform 平台类型
             * @descrption 分享成功的回调
             */
            @Override
            public void onResult(SHARE_MEDIA platform) {
                ToastUtils.showShort("分享成功");
                mActivity.missLoadingDialog();
            }

            /**
             * @param platform 平台类型
             * @param t        错误原因
             * @descrption 分享失败的回调
             */
            @Override
            public void onError(SHARE_MEDIA platform, Throwable t) {
                ToastUtils.showShort("分享失败【" + t.getMessage() + "】");
                mActivity.missLoadingDialog();
            }

            /**
             * @param platform 平台类型
             * @descrption 分享取消的回调
             */
            @Override
            public void onCancel(SHARE_MEDIA platform) {
                ToastUtils.showShort("取消分享");
                mActivity.missLoadingDialog();
            }

        }).open();
    }

}

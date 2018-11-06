package com.easy.photo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.easy.photo.builder.AlbumBuilder;
import com.easy.photo.engine.ImageEngine;
import com.easy.photo.models.ad.AdListener;
import com.easy.photo.utils.bitmap.BitmapUtils;
import com.easy.photo.utils.media.MediaScannerConnectionUtils;

import java.io.File;
import java.util.List;

/**
 * EasyPhotos的启动管理器
 * Created by huan on 2017/10/18.
 */
public class EasyPhotos {

    //easyPhotos的返回数据Key
    public static final String RESULT_PHOTOS = "keyOfEasyPhotosResult";
    public static final String RESULT_PATHS = "keyOfEasyPhotosResultPaths";
    public static final String RESULT_SELECTED_ORIGINAL = "keyOfEasyPhotosResultSelectedOriginal";

    /**
     * 创建相机
     *
     * @param activity 上下文
     * @return AlbumBuilder
     */
    public static AlbumBuilder createCamera(Activity activity) {
        return AlbumBuilder.createCamera(activity);
    }

    public static AlbumBuilder createCamera(Fragment fragment) {
        return AlbumBuilder.createCamera(fragment);
    }

    /**
     * 创建相册
     *
     * @param activity     上下文
     * @param isShowCamera 是否显示相机按钮
     * @param imageEngine  图片加载引擎的具体实现
     * @return
     */
    public static AlbumBuilder createAlbum(Activity activity, boolean isShowCamera, @NonNull ImageEngine imageEngine) {
        return AlbumBuilder.createAlbum(activity, isShowCamera, imageEngine);
    }

    public static AlbumBuilder createAlbum(Fragment fragment, boolean isShowCamera, @NonNull ImageEngine imageEngine) {
        return AlbumBuilder.createAlbum(fragment, isShowCamera, imageEngine);
    }

    /**
     * 创建预览
     *
     * @param activity    上下文
     * @param imageEngine 图片加载引擎的具体实现
     * @return
     */
    public static AlbumBuilder createPreview(Activity activity, @NonNull ImageEngine imageEngine) {
        return AlbumBuilder.createPreview(activity, imageEngine);
    }

    public static AlbumBuilder createPreview(Fragment fragment, @NonNull ImageEngine imageEngine) {
        return AlbumBuilder.createPreview(fragment, imageEngine);
    }


//*********************AD************************************


    /**
     * 设置广告监听
     * 内部使用，无需关心
     *
     * @param adListener 广告监听
     */
    public static void setAdListener(AdListener adListener) {
        AlbumBuilder.setAdListener(adListener);
    }

    /**
     * 刷新图片列表广告数据
     */
    public static void notifyPhotosAdLoaded() {
        AlbumBuilder.notifyPhotosAdLoaded();
    }

    /**
     * 刷新专辑项目列表广告
     */
    public static void notifyAlbumItemsAdLoaded() {
        AlbumBuilder.notifyAlbumItemsAdLoaded();
    }


//*************************bitmap功能***********************************/

    /**
     * 回收bitmap
     *
     * @param bitmap 要回收的bitmap
     */
    public static void recycle(Bitmap bitmap) {
        BitmapUtils.recycle(bitmap);
    }

    /**
     * 回收bitmap数组中的所有图片
     *
     * @param bitmaps 要回收的bitmap数组
     */
    public static void recycle(Bitmap... bitmaps) {
        BitmapUtils.recycle(bitmaps);
    }

    /**
     * 回收bitmap集合中的所有图片
     *
     * @param bitmaps 要回收的bitmap集合
     */
    public static void recycle(List<Bitmap> bitmaps) {
        BitmapUtils.recycle(bitmaps);
    }


    //**************更新媒体库***********************

    /**
     * 更新媒体文件到媒体库
     *
     * @param cxt       上下文
     * @param filePaths 更新的文件地址
     */
    public static void notifyMedia(Context cxt, String... filePaths) {
        MediaScannerConnectionUtils.refresh(cxt, filePaths);
    }

    /**
     * 更新媒体文件到媒体库
     *
     * @param cxt   上下文
     * @param files 更新的文件
     */
    public static void notifyMedia(Context cxt, File... files) {
        MediaScannerConnectionUtils.refresh(cxt, files);
    }

    /**
     * 更新媒体文件到媒体库
     *
     * @param cxt      上下文
     * @param fileList 更新的文件地址集合
     */
    public static void notifyMedia(Context cxt, List<String> fileList) {
        MediaScannerConnectionUtils.refresh(cxt, fileList);
    }

}

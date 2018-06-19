package com.tom.common.util;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.tom.common.GlideEngine;
import com.tom.easyphotos.EasyPhotos;
import com.tom.easyphotos.models.album.entity.Photo;

import java.util.ArrayList;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/5/2
 * 描述：选择图片的接口，常用于选择头像、发朋友圈等场景
 */
public class SelectorUtil {

    public static void selectSingle(Activity activity, int requestCode) {
        EasyPhotos.createAlbum(activity, true, GlideEngine.getInstance())
                .setFileProviderAuthority("com.tom.common.fileprovider")
                .setCount(1)
                .setPuzzleMenu(false)
                .start(requestCode);
    }

    public static void selectSingle(Fragment fragment, int requestCode) {
        EasyPhotos.createAlbum(fragment, true, GlideEngine.getInstance())
                .setFileProviderAuthority("com.tom.common.fileprovider")
                .setCount(1)
                .setPuzzleMenu(false)
                .start(requestCode);
    }

    public static void selectMultiplePhoto(Activity activity, int maxCount, ArrayList<Photo> selectedPhotoList, int requestCode) {
        EasyPhotos.createAlbum(activity, true, GlideEngine.getInstance())
                .setFileProviderAuthority("com.tom.common.fileprovider")
                .setCount(maxCount)
                .setSelectedPhotos(selectedPhotoList)
                .setPuzzleMenu(false)
                .start(requestCode);
    }

    public static void selectMultiplePhoto(Fragment fragment, int maxCount, ArrayList<Photo> selectedPhotoList, int requestCode) {
        EasyPhotos.createAlbum(fragment, true, GlideEngine.getInstance())
                .setFileProviderAuthority("com.tom.common.fileprovider")
                .setCount(maxCount)
                .setSelectedPhotos(selectedPhotoList)
                .setPuzzleMenu(false)
                .start(requestCode);
    }


    public static void selectMultipleString(Activity activity, int maxCount, ArrayList<String> selectedPhotoPathList, int requestCode) {
        EasyPhotos.createAlbum(activity, true, GlideEngine.getInstance())
                .setFileProviderAuthority("com.tom.common.fileprovider")
                .setCount(maxCount)
                .setSelectedPhotoPaths(selectedPhotoPathList)
                .setPuzzleMenu(false)
                .start(requestCode);
    }

    public static void selectMultipleString(Fragment fragment, int maxCount, ArrayList<String> selectedPhotoPathList, int requestCode) {
        EasyPhotos.createAlbum(fragment, true, GlideEngine.getInstance())
                .setFileProviderAuthority("com.tom.common.fileprovider")
                .setCount(maxCount)
                .setSelectedPhotoPaths(selectedPhotoPathList)
                .setPuzzleMenu(false)
                .start(requestCode);
    }


}






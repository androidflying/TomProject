package com.tom.common.util;

import android.app.Activity;

import com.easy.photo.EasyPhotos;
import com.easy.photo.builder.AlbumBuilder;
import com.easy.photo.models.album.entity.Photo;
import com.tom.common.GlideEngine;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
                .start(requestCode);
    }

    public static void selectSingle(Fragment fragment, int requestCode) {
        EasyPhotos.createAlbum(fragment, true, GlideEngine.getInstance())
                .setFileProviderAuthority("com.tom.common.fileprovider")
                .setCount(1)
                .start(requestCode);
    }

    public static void selectMultiplePhoto(Activity activity, int maxCount, @Nullable ArrayList<Photo> selectedPhotoList, int requestCode) {
        AlbumBuilder builder = EasyPhotos.createAlbum(activity, true, GlideEngine.getInstance())
                .setFileProviderAuthority("com.tom.common.fileprovider")
                .setCount(maxCount);
        if (selectedPhotoList == null || selectedPhotoList.size() == 0) {

        } else {
            builder.setSelectedPhotos(selectedPhotoList);
        }

        builder.start(requestCode);
    }

    public static void selectMultiplePhoto(Fragment fragment, int maxCount, ArrayList<Photo> selectedPhotoList, int requestCode) {

        AlbumBuilder builder = EasyPhotos.createAlbum(fragment, true, GlideEngine.getInstance())
                .setFileProviderAuthority("com.tom.common.fileprovider")
                .setCount(maxCount);
        if (selectedPhotoList == null || selectedPhotoList.size() == 0) {

        } else {
            builder.setSelectedPhotos(selectedPhotoList);
        }

        builder.start(requestCode);
    }


    public static void selectMultipleString(Activity activity, int maxCount, ArrayList<String> selectedPhotoPathList, int requestCode) {
        AlbumBuilder builder = EasyPhotos.createAlbum(activity, true, GlideEngine.getInstance())
                .setFileProviderAuthority("com.tom.common.fileprovider")
                .setCount(maxCount);


        if (selectedPhotoPathList == null || selectedPhotoPathList.size() == 0) {

        } else {
            builder.setSelectedPhotoPaths(selectedPhotoPathList);
        }

        builder.start(requestCode);
    }

    public static void selectMultipleString(Fragment fragment, int maxCount, ArrayList<String> selectedPhotoPathList, int requestCode) {
        AlbumBuilder builder = EasyPhotos.createAlbum(fragment, true, GlideEngine.getInstance())
                .setFileProviderAuthority("com.tom.common.fileprovider")
                .setCount(maxCount);


        if (selectedPhotoPathList == null || selectedPhotoPathList.size() == 0) {

        } else {
            builder.setSelectedPhotoPaths(selectedPhotoPathList);
        }

        builder.start(requestCode);
    }


}






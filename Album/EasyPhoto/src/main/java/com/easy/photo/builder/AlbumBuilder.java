package com.easy.photo.builder;

import android.app.Activity;
import android.view.View;

import com.easy.photo.engine.ImageEngine;
import com.easy.photo.models.ad.AdListener;
import com.easy.photo.models.album.AlbumModel;
import com.easy.photo.models.album.entity.Photo;
import com.easy.photo.result.Result;
import com.easy.photo.setting.Setting;
import com.easy.photo.ui.EasyPhotosActivity;
import com.easy.photo.ui.PreviewActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * EasyPhotos的启动管理器
 * Created by huan on 2017/10/18.
 */
public class AlbumBuilder {

    /**
     * 启动模式
     * CAMERA-相机
     * ALBUM-相册专辑
     * ALBUM_CAMERA-带有相机按钮的相册专辑
     */
    private enum StartupType {
        CAMERA, ALBUM, ALBUM_CAMERA, PREVIEW
    }

    private static AlbumBuilder instance;
    private WeakReference<Activity> mActivity;
    private WeakReference<Fragment> mFragment;
    private StartupType startupType;
    private WeakReference<AdListener> adListener;

    //私有构造函数，不允许外部调用，真正实例化通过静态方法实现
    private AlbumBuilder(Activity activity, StartupType startupType) {
        mActivity = new WeakReference<>(activity);
        this.startupType = startupType;
    }

    private AlbumBuilder(Fragment fragment, StartupType startupType) {
        mFragment = new WeakReference<>(fragment);
        this.startupType = startupType;
    }


    /**
     * 内部处理相机和相册的实例
     *
     * @param activity Activity的实例
     * @return AlbumBuilder EasyPhotos的实例
     */
    private static AlbumBuilder with(Activity activity, StartupType startupType) {
        clear();
        instance = new AlbumBuilder(activity, startupType);
        return instance;
    }

    private static AlbumBuilder with(Fragment fragment, StartupType startupType) {
        clear();
        instance = new AlbumBuilder(fragment, startupType);
        return instance;
    }

    /**
     * 创建相机
     *
     * @param activity 上下文
     * @return AlbumBuilder
     */
    public static AlbumBuilder createCamera(Activity activity) {
        return AlbumBuilder.with(activity, StartupType.CAMERA);
    }

    public static AlbumBuilder createCamera(Fragment fragment) {
        return AlbumBuilder.with(fragment, StartupType.CAMERA);
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
        if (Setting.imageEngine != imageEngine) {
            Setting.imageEngine = imageEngine;
        }
        if (isShowCamera) {
            return AlbumBuilder.with(activity, StartupType.ALBUM_CAMERA);
        } else {
            return AlbumBuilder.with(activity, StartupType.ALBUM);
        }
    }

    public static AlbumBuilder createAlbum(Fragment fragment, boolean isShowCamera, @NonNull ImageEngine imageEngine) {
        if (Setting.imageEngine != imageEngine) {
            Setting.imageEngine = imageEngine;
        }
        if (isShowCamera) {
            return AlbumBuilder.with(fragment, StartupType.ALBUM_CAMERA);
        } else {
            return AlbumBuilder.with(fragment, StartupType.ALBUM);
        }
    }

    public static AlbumBuilder createPreview(Fragment fragment, ImageEngine imageEngine) {
        if (Setting.imageEngine != imageEngine) {
            Setting.imageEngine = imageEngine;
        }
        return AlbumBuilder.with(fragment, StartupType.PREVIEW);
    }

    public static AlbumBuilder createPreview(Activity activity, ImageEngine imageEngine) {
        if (Setting.imageEngine != imageEngine) {
            Setting.imageEngine = imageEngine;
        }
        return AlbumBuilder.with(activity, StartupType.PREVIEW);
    }


    /**
     * 设置fileProvider字段
     *
     * @param fileProviderAuthority fileProvider字段
     * @return AlbumBuilder
     */
    public AlbumBuilder setFileProviderAuthority(String fileProviderAuthority) {
        Setting.fileProviderAuthority = fileProviderAuthority;
        return AlbumBuilder.this;
    }

    /**
     * 设置选择数
     *
     * @param selectorMaxCount 最大选择数
     * @return AlbumBuilder
     */
    public AlbumBuilder setCount(int selectorMaxCount) {
        Setting.count = selectorMaxCount;
        return AlbumBuilder.this;
    }

    /**
     * 设置显示照片的最小文件大小
     *
     * @param minFileSize 最小文件大小，单位Bytes
     * @return AlbumBuilder
     */
    public AlbumBuilder setMinFileSize(long minFileSize) {
        Setting.minSize = minFileSize;
        return AlbumBuilder.this;
    }

    /**
     * 设置显示照片的最小宽度
     *
     * @param minWidth 照片的最小宽度，单位Px
     * @return AlbumBuilder
     */
    public AlbumBuilder setMinWidth(int minWidth) {
        Setting.minWidth = minWidth;
        return AlbumBuilder.this;
    }

    /**
     * 设置显示照片的最小高度
     *
     * @param minHeight 显示照片的最小高度，单位Px
     * @return AlbumBuilder
     */
    public AlbumBuilder setMinHeight(int minHeight) {
        Setting.minHeight = minHeight;
        return AlbumBuilder.this;
    }

    /**
     * 设置默认选择图片集合
     *
     * @param selectedPhotos 默认选择图片集合
     * @return AlbumBuilder
     */
    public AlbumBuilder setSelectedPhotos(ArrayList<Photo> selectedPhotos) {
        Setting.selectedPhotos.clear();
        if (selectedPhotos.isEmpty()) {
            return AlbumBuilder.this;
        }
        Setting.selectedPhotos.addAll(selectedPhotos);
        Setting.selectedOriginal = selectedPhotos.get(0).selectedOriginal;
        return AlbumBuilder.this;
    }

    /**
     * 设置默认选择图片地址集合
     *
     * @param selectedPhotoPaths 默认选择图片地址集合
     * @return AlbumBuilder
     */
    public AlbumBuilder setSelectedPhotoPaths(ArrayList<String> selectedPhotoPaths) {
        Setting.selectedPhotos.clear();
        ArrayList<Photo> selectedPhotos = new ArrayList<>();
        for (String path : selectedPhotoPaths) {
            Photo photo = new Photo(null, path, 0, 0, 0, 0, "");
            selectedPhotos.add(photo);
        }
        Setting.selectedPhotos.addAll(selectedPhotos);
        return AlbumBuilder.this;
    }


    /**
     * 原图按钮设置,不调用该方法不显示原图按钮
     *
     * @param isChecked    原图选项默认状态是否为选中状态
     * @param usable       原图按钮是否可使用
     * @param unusableHint 原图按钮不可使用时给用户的文字提示
     * @return AlbumBuilder
     */
    public AlbumBuilder setOriginalMenu(boolean isChecked, boolean usable, String unusableHint) {
        Setting.showOriginalMenu = true;
        Setting.selectedOriginal = isChecked;
        Setting.originalMenuUsable = usable;
        Setting.originalMenuUnusableHint = unusableHint;
        return AlbumBuilder.this;
    }

    /**
     * 是否显示gif图
     *
     * @param isShow 是否显示
     * @return @return AlbumBuilder
     */
    public AlbumBuilder setGif(boolean isShow) {
        Setting.showGif = isShow;
        return AlbumBuilder.this;
    }


    /**
     * 设置启动属性
     *
     * @param requestCode startActivityForResult的请求码
     */
    public void start(int requestCode) {
        switch (startupType) {
            case CAMERA:
                Setting.onlyStartCamera = true;
                Setting.isShowCamera = true;
                launchEasyPhotosActivity(requestCode);
                break;
            case ALBUM:
                Setting.isShowCamera = false;
                launchEasyPhotosActivity(requestCode);
                break;
            case ALBUM_CAMERA:
                Setting.isShowCamera = true;
                launchEasyPhotosActivity(requestCode);
                break;
            case PREVIEW:
                launchPreviewActivity(requestCode);
                break;
        }

    }

    /**
     * 此处取了个巧，把requestCode当做当前点击的position
     *
     * @param currIndex
     */
    private void launchPreviewActivity(int currIndex) {
        if (null != mActivity && null != mActivity.get()) {
            PreviewActivity.start(mActivity.get(), currIndex);
            return;
        }
        if (null != mFragment && null != mFragment.get()) {
            PreviewActivity.start(mFragment.get(), currIndex);
            return;
        }
    }

    /**
     * 正式启动
     *
     * @param requestCode startActivityForResult的请求码
     */
    private void launchEasyPhotosActivity(int requestCode) {
        if (null != mActivity && null != mActivity.get()) {
            EasyPhotosActivity.start(mActivity.get(), requestCode);
            return;
        }
        if (null != mFragment && null != mFragment.get()) {
            EasyPhotosActivity.start(mFragment.get(), requestCode);
            return;
        }

    }

    /**
     * 清除所有数据
     */
    private static void clear() {
        Result.clear();
        Setting.clear();
        AlbumModel.clear();
        instance = null;
    }

//*********************AD************************************

    /**
     * 设置广告(不设置该选项则表示不使用广告)
     *
     * @param photosAdView         使用图片列表的广告View
     * @param photosAdIsLoaded     图片列表广告是否加载完毕
     * @param albumItemsAdView     使用专辑项目列表的广告View
     * @param albumItemsAdIsLoaded 专辑项目列表广告是否加载完毕
     * @return AlbumBuilder
     */
    public AlbumBuilder setAdView(View photosAdView, boolean photosAdIsLoaded, View albumItemsAdView, boolean albumItemsAdIsLoaded) {
        Setting.photosAdView = new WeakReference<>(photosAdView);
        Setting.albumItemsAdView = new WeakReference<>(albumItemsAdView);
        Setting.photoAdIsOk = photosAdIsLoaded;
        Setting.albumItemsAdIsOk = albumItemsAdIsLoaded;
        return AlbumBuilder.this;
    }

    /**
     * 设置广告监听
     * 内部使用，无需关心
     *
     * @param adListener 广告监听
     */
    public static void setAdListener(AdListener adListener) {
        if (null == instance) {
            return;
        }
        if (instance.startupType == StartupType.CAMERA) {
            return;
        }
        instance.adListener = new WeakReference<>(adListener);
    }

    /**
     * 刷新图片列表广告数据
     */
    public static void notifyPhotosAdLoaded() {
        if (Setting.photoAdIsOk) {
            return;
        }
        if (null == instance) {
            return;
        }
        if (instance.startupType == StartupType.CAMERA) {
            return;
        }
        if (null == instance.adListener) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (null != instance && null != instance.adListener) {
                        Setting.photoAdIsOk = true;
                        instance.adListener.get().onPhotosAdLoaded();
                    }
                }
            }).start();
            return;
        }
        Setting.photoAdIsOk = true;
        instance.adListener.get().onPhotosAdLoaded();
    }

    /**
     * 刷新专辑项目列表广告
     */
    public static void notifyAlbumItemsAdLoaded() {
        if (Setting.albumItemsAdIsOk) {
            return;
        }
        if (null == instance) {
            return;
        }
        if (instance.startupType == StartupType.CAMERA) {
            return;
        }
        if (null == instance.adListener) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (null != instance && null != instance.adListener) {
                        Setting.albumItemsAdIsOk = true;
                        instance.adListener.get().onAlbumItemsAdLoaded();
                    }
                }
            }).start();
            return;
        }
        Setting.albumItemsAdIsOk = true;
        instance.adListener.get().onAlbumItemsAdLoaded();
    }

}

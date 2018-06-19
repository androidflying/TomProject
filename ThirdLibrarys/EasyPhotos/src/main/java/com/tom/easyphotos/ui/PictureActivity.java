package com.tom.easyphotos.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tom.easyphotos.BuildConfig;
import com.tom.easyphotos.R;
import com.tom.easyphotos.constant.Code;
import com.tom.easyphotos.constant.Key;
import com.tom.easyphotos.engine.ImageEngine;
import com.tom.easyphotos.models.album.AlbumModel;
import com.tom.easyphotos.models.album.entity.Photo;
import com.tom.easyphotos.result.Result;
import com.tom.easyphotos.setting.Setting;
import com.tom.easyphotos.ui.adapter.PicurePhotosAdapter;
import com.tom.easyphotos.ui.adapter.PreviewPhotosAdapter;
import com.tom.easyphotos.ui.widget.PressedTextView;
import com.tom.easyphotos.utils.Color.ColorUtils;
import com.tom.easyphotos.utils.system.SystemUtils;

import java.util.ArrayList;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/5/2
 * 描述：纯预览图片
 */
public class PictureActivity extends AppCompatActivity implements PicurePhotosAdapter.OnClickListener, View.OnClickListener {

    /**
     * 一些旧设备在UI小部件更新之间需要一个小延迟
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    View decorView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @Override
        public void run() {
            SystemUtils.getInstance().systemUiHide(PictureActivity.this, decorView);
        }
    };
    private RelativeLayout mBottomBar;
    private FrameLayout mToolBar;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // 延迟显示UI元素
            mBottomBar.setVisibility(View.VISIBLE);
            mToolBar.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private TextView tvNumber;
    private RecyclerView rvPhotos;
    private PicurePhotosAdapter adapter;
    private PagerSnapHelper snapHelper;
    private LinearLayoutManager lm;
    private int index;
    private ArrayList<String> photos = new ArrayList<>();

    //记录recyclerView最后一次角标位置，用于判断是否转换了item

    private int lastPosition = 0;

    private int statusColor;

    public static void start(Activity act, ImageEngine imageEngine, int currIndex, ArrayList<String> photoList) {
        if (Setting.imageEngine != imageEngine) {
            Setting.imageEngine = imageEngine;
        }
        Intent intent = new Intent(act, PictureActivity.class);
        intent.putExtra(Key.PREVIEW_PHOTO_INDEX, currIndex);
        intent.putExtra(Key.PREVIEW_PHOTO_LIST, photoList);
        act.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        decorView = getWindow().getDecorView();
        SystemUtils.getInstance().systemUiInit(this, decorView);

        setContentView(R.layout.activity_picture_easy_photos);

        hideActionBar();
        adaptationStatusBar();

        initData();
        initView();
    }

    private void hideActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    private void adaptationStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            statusColor = ContextCompat.getColor(this, R.color.colorPrimaryDark);
            if (ColorUtils.isWhiteColor(statusColor)) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
    }

    private void initData() {
        Intent intent = getIntent();
        ArrayList<String> photoList = intent.getStringArrayListExtra(Key.PREVIEW_PHOTO_LIST);
        photos.clear();
        photos.addAll(photoList);
        index = intent.getIntExtra(Key.PREVIEW_PHOTO_INDEX, 0);

        lastPosition = index;
        mVisible = true;
    }

    private void initView() {
        setClick(R.id.iv_back, R.id.tv_download);

        mToolBar = findViewById(R.id.m_top_bar_layout);
        if (!SystemUtils.getInstance().hasNavigationBar(this)) {
            FrameLayout mRootView = findViewById(R.id.m_root_view);
            mRootView.setFitsSystemWindows(true);
            mToolBar.setPadding(0, SystemUtils.getInstance().getStatusBarHeight(this), 0, 0);
            if (ColorUtils.isWhiteColor(statusColor)) {
                SystemUtils.getInstance().setStatusDark(this, true);
            }
        }
        mBottomBar = findViewById(R.id.m_bottom_bar);
        tvNumber = findViewById(R.id.tv_number);

        initRecyclerView();
    }

    private void setClick(@IdRes int... ids) {
        for (int id : ids) {
            findViewById(id).setOnClickListener(this);
        }
    }

    private void initRecyclerView() {
        rvPhotos = findViewById(R.id.rv_photos);
        adapter = new PicurePhotosAdapter(this, photos, this);
        lm = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvPhotos.setLayoutManager(lm);
        rvPhotos.setAdapter(adapter);
        rvPhotos.scrollToPosition(index);
        snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(rvPhotos);
        rvPhotos.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState != RecyclerView.SCROLL_STATE_IDLE) {
                    return;
                }
                int leftViewPosition = snapHelper.findTargetSnapPosition(lm, 1, rvPhotos.getHeight() / 2);
                int rightViewPosition = snapHelper.findTargetSnapPosition(lm, rvPhotos.getWidth() - 1, rvPhotos.getHeight() / 2);
                if (leftViewPosition == rightViewPosition) {
                    if (lastPosition == leftViewPosition - 1) {
                        return;
                    }
                    tvNumber.setText(getString(R.string.preview_current_number_easy_photos, leftViewPosition, photos.size()));
                    lastPosition = leftViewPosition - 1;
                    View view = snapHelper.findSnapView(lm);
                    if (null == view) {
                        return;
                    }
                    PicurePhotosAdapter.PreviewPhotosViewHolder viewHolder = (PicurePhotosAdapter.PreviewPhotosViewHolder) rvPhotos.getChildViewHolder(view);
                    if (viewHolder == null || viewHolder.ivPhoto == null) {
                        return;
                    }
                    if (viewHolder.ivPhoto.getScale() != 1f) {
                        viewHolder.ivPhoto.setScale(1f, true);
                    }
                }
            }
        });
        tvNumber.setText(getString(R.string.preview_current_number_easy_photos, index + 1, photos.size()));
    }

    @Override
    public void onPhotoClick() {
        toggle();
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        AlphaAnimation hideAnimation = new AlphaAnimation(1.0f, 0.0f);
        hideAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mBottomBar.setVisibility(View.GONE);
                mToolBar.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        hideAnimation.setDuration(UI_ANIMATION_DELAY);
        mBottomBar.startAnimation(hideAnimation);
        mToolBar.startAnimation(hideAnimation);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);

        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);

    }

    private void show() {
        // Show the system bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            SystemUtils.getInstance().systemUiShow(this, decorView);
        }

        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.post(mShowPart2Runnable);
    }

    @Override
    public void onPhotoScaleChanged() {
        if (mVisible) {
            hide();
        }
    }

    @Override
    public void onBackPressed() {
        doBack();
    }

    private void doBack() {
        finish();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.iv_back == id) {
            doBack();
        } else if (R.id.tv_download == id) {
            doDownload();
        }
    }

    private void doDownload() {
        if (BuildConfig.DEBUG) {
            Log.e("prcture", "下载。。。");
        }
    }

}

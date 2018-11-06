package com.easy.photo.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
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

import com.easy.photo.R;
import com.easy.photo.adapter.PreviewPhotosAdapter;
import com.easy.photo.constant.Code;
import com.easy.photo.constant.Key;
import com.easy.photo.models.album.AlbumModel;
import com.easy.photo.models.album.entity.Photo;
import com.easy.photo.result.Result;
import com.easy.photo.setting.Setting;
import com.easy.photo.utils.Color.ColorUtils;
import com.easy.photo.utils.SystemUtils;
import com.easy.photo.widget.PressedTextView;

import java.util.ArrayList;

/**
 * 预览页
 */
public class PreviewActivity extends AppCompatActivity implements PreviewPhotosAdapter.OnClickListener, View.OnClickListener, PreviewFragment.OnPreviewFragmentClickListener {

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
            SystemUtils.getInstance().systemUiHide(PreviewActivity.this, decorView);
        }
    };
    private RelativeLayout mBottomBar;
    private FrameLayout mToolBar;
    private boolean mVisible;
    private TextView tvOriginal, tvNumber;
    private PressedTextView tvDone;
    private ImageView ivSelector;
    private RecyclerView rvPhotos;
    private PreviewPhotosAdapter adapter;
    private PagerSnapHelper snapHelper;
    private LinearLayoutManager lm;
    private int index;
    private ArrayList<Photo> photos = new ArrayList<>();
    private int resultCode = RESULT_CANCELED;
    /**
     * 记录recyclerView最后一次角标位置，用于判断是否转换了item
     */
    private int lastPosition = 0;
    private boolean isSingle = Setting.count == 1;
    private boolean unable = Result.count() == Setting.count;
    private FrameLayout flFragment;
    private PreviewFragment previewFragment;
    private int statusColor;
    private boolean isOnlyPreview;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // 延迟显示UI元素
            if (!isOnlyPreview) {
                mBottomBar.setVisibility(View.VISIBLE);
            }
            mToolBar.setVisibility(View.VISIBLE);
        }
    };

    public static void start(Activity act, int albumItemIndex, int currIndex) {
        Intent intent = new Intent(act, PreviewActivity.class);
        intent.putExtra(Key.PREVIEW_ALBUM_ITEM_INDEX, albumItemIndex);
        intent.putExtra(Key.PREVIEW_ONLY, false);
        intent.putExtra(Key.PREVIEW_PHOTO_INDEX, currIndex);
        act.startActivityForResult(intent, Code.REQUEST_PREVIEW_ACTIVITY);
    }

    public static void start(Activity activity, int currIndex) {
        Intent intent = new Intent(activity, PreviewActivity.class);
        intent.putExtra(Key.PREVIEW_ONLY, true);
        intent.putExtra(Key.PREVIEW_PHOTO_INDEX, currIndex);
        activity.startActivity(intent);
    }

    public static void start(Fragment fragment, int currIndex) {
        Intent intent = new Intent(fragment.getActivity(), PreviewActivity.class);
        intent.putExtra(Key.PREVIEW_ONLY, true);
        intent.putExtra(Key.PREVIEW_PHOTO_INDEX, currIndex);
        fragment.getActivity().startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        decorView = getWindow().getDecorView();
        SystemUtils.getInstance().systemUiInit(this, decorView);

        setContentView(R.layout.activity_preview_easy_photos);

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
        isOnlyPreview = intent.getBooleanExtra(Key.PREVIEW_ONLY, true);
        if (isOnlyPreview) {
            if (Setting.selectedPhotos.isEmpty()) {
                throw new RuntimeException("AlbumBuilder" + " : 请执行 setSelectedPhotoPaths()方法");
            }
            photos.clear();
            photos.addAll(Setting.selectedPhotos);
        } else {
            if (null == AlbumModel.instance) {
                finish();
                return;
            }
            int albumItemIndex = intent.getIntExtra(Key.PREVIEW_ALBUM_ITEM_INDEX, 0);
            photos.clear();
            if (albumItemIndex == -1) {
                photos.addAll(Result.photos);
            } else {
                photos.addAll(AlbumModel.instance.getCurrAlbumItemPhotos(albumItemIndex));
            }
        }

        index = intent.getIntExtra(Key.PREVIEW_PHOTO_INDEX, 0);
        lastPosition = index;
        mVisible = true;


    }

    private void initView() {
        setClick(R.id.iv_back, R.id.tv_edit, R.id.tv_selector);

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
        ivSelector = findViewById(R.id.iv_selector);
        tvNumber = findViewById(R.id.tv_number);
        tvDone = findViewById(R.id.tv_done);
        tvOriginal = findViewById(R.id.tv_original);
        flFragment = findViewById(R.id.fl_fragment);
        previewFragment = (PreviewFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_preview);
        if (Setting.showOriginalMenu) {
            processOriginalMenu();
        } else {
            tvOriginal.setVisibility(View.GONE);
        }
        if (isOnlyPreview) {
            mBottomBar.setVisibility(View.GONE);
        } else {
            mBottomBar.setVisibility(View.VISIBLE);
        }
        setClick(tvOriginal, tvDone, ivSelector);

        initRecyclerView();
        shouldShowMenuDone();
    }

    private void setClick(@IdRes int... ids) {
        for (int id : ids) {
            findViewById(id).setOnClickListener(this);
        }
    }

    private void processOriginalMenu() {
        if (!isOnlyPreview) {
            if (Setting.selectedOriginal) {
                tvOriginal.setTextColor(ContextCompat.getColor(this, R.color.easy_photos_fg_accent));
            } else {
                if (Setting.originalMenuUsable) {
                    tvOriginal.setTextColor(ContextCompat.getColor(this, R.color.easy_photos_fg_primary));
                } else {
                    tvOriginal.setTextColor(ContextCompat.getColor(this, R.color.easy_photos_fg_primary_dark));
                }
            }
        }
    }

    private void setClick(View... views) {
        for (View v : views) {
            v.setOnClickListener(this);
        }
    }

    private void initRecyclerView() {
        rvPhotos = findViewById(R.id.rv_photos);
        adapter = new PreviewPhotosAdapter(this, photos, this);
        lm = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvPhotos.setLayoutManager(lm);
        rvPhotos.setAdapter(adapter);
        rvPhotos.scrollToPosition(index);
        toggleSelector();
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
                    previewFragment.setSelectedPosition(-1);
                    tvNumber.setText(getString(R.string.preview_current_number_easy_photos, leftViewPosition, photos.size()));
                    lastPosition = leftViewPosition - 1;
                    View view = snapHelper.findSnapView(lm);
                    toggleSelector();
                    if (null == view) {
                        return;
                    }
                    PreviewPhotosAdapter.PreviewPhotosViewHolder viewHolder = (PreviewPhotosAdapter.PreviewPhotosViewHolder) rvPhotos.getChildViewHolder(view);
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

    private void shouldShowMenuDone() {
        if (isOnlyPreview) {
            tvDone.setVisibility(View.GONE);
        } else {
            if (Result.isEmpty()) {
                if (View.VISIBLE == tvDone.getVisibility()) {
                    ScaleAnimation scaleHide = new ScaleAnimation(1f, 0f, 1f, 0f);
                    scaleHide.setDuration(200);
                    tvDone.startAnimation(scaleHide);
                }
                tvDone.setVisibility(View.GONE);
                flFragment.setVisibility(View.GONE);
            } else {
                if (View.GONE == tvDone.getVisibility()) {
                    ScaleAnimation scaleShow = new ScaleAnimation(0f, 1f, 0f, 1f);
                    scaleShow.setDuration(200);
                    tvDone.startAnimation(scaleShow);
                }
                flFragment.setVisibility(View.VISIBLE);
                tvDone.setVisibility(View.VISIBLE);
                tvDone.setText(getString(R.string.selector_action_done_easy_photos, Result.count(), Setting.count));
            }
        }
    }

    private void toggleSelector() {
        if (!isOnlyPreview) {
            if (photos.get(lastPosition).selected) {
                ivSelector.setImageResource(R.drawable.ic_selector_true_easy_photos);
                if (!Result.isEmpty()) {
                    for (int i = 0; i < Result.count(); i++) {
                        if (photos.get(lastPosition).path.equals(Result.getPhotoPath(i))) {
                            previewFragment.setSelectedPosition(i);
                            break;
                        }
                    }
                }
            } else {
                ivSelector.setImageResource(R.drawable.ic_selector_easy_photos);
            }
            previewFragment.notifyDataSetChanged();
            shouldShowMenuDone();
        }
    }

    @Override
    public void onPhotoClick() {
        toggle();
    }

    @Override
    public void onPhotoLongClick() {
        if (isOnlyPreview) {
            //TODO 添加长按图片的选择，保存图片等操作
            Toast.makeText(this, "长按图片", Toast.LENGTH_SHORT).show();
        }
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
        if (Build.VERSION.SDK_INT >= 16) {
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
        if (!isOnlyPreview) {
            Intent intent = new Intent();
            intent.putExtra(Key.PREVIEW_CLICK_DONE, false);
            setResult(resultCode, intent);
        }
        finish();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.iv_back == id) {
            doBack();
        } else if (R.id.tv_selector == id) {
            updateSelector();
        } else if (R.id.iv_selector == id) {
            updateSelector();
        } else if (R.id.tv_original == id) {
            if (!Setting.originalMenuUsable) {
                Toast.makeText(this, Setting.originalMenuUnusableHint, Toast.LENGTH_SHORT).show();
                return;
            }
            Setting.selectedOriginal = !Setting.selectedOriginal;
            processOriginalMenu();
        } else if (R.id.tv_done == id) {
            Intent intent = new Intent();
            intent.putExtra(Key.PREVIEW_CLICK_DONE, true);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    private void updateSelector() {
        resultCode = RESULT_OK;
        Photo item = photos.get(lastPosition);
        if (isSingle) {
            singleSelector(item);
            return;
        }
        if (unable) {
            if (item.selected) {
                Result.removePhoto(item);
                if (unable) {
                    unable = false;
                }
                toggleSelector();
                return;
            }
            Toast.makeText(this, getString(R.string.selector_reach_max_image_hint_easy_photos, Setting.count), Toast.LENGTH_SHORT).show();
            return;
        }
        item.selected = !item.selected;
        if (item.selected) {
            Result.addPhoto(item);
            if (Result.count() == Setting.count) {
                unable = true;
            }
        } else {
            Result.removePhoto(item);
            previewFragment.setSelectedPosition(-1);
            if (unable) {
                unable = false;
            }
        }
        toggleSelector();
    }

    private void singleSelector(Photo photo) {
        if (!Result.isEmpty()) {
            if (Result.getPhotoPath(0).equals(photo.path)) {
                Result.removePhoto(photo);
                toggleSelector();
            } else {
                Result.removePhoto(0);
                Result.addPhoto(photo);
                toggleSelector();
            }
        } else {
            Result.addPhoto(photo);
            toggleSelector();
        }
    }

    @Override
    public void onPreviewPhotoClick(int position) {
        String path = Result.getPhotoPath(position);
        for (int i = 0; i < photos.size(); i++) {
            if (TextUtils.equals(path, photos.get(i).path)) {
                rvPhotos.scrollToPosition(i);
                lastPosition = i;
                tvNumber.setText(getString(R.string.preview_current_number_easy_photos, lastPosition + 1, photos.size()));
                previewFragment.setSelectedPosition(position);
                toggleSelector();
                return;
            }
        }
    }

}

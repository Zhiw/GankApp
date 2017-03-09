package com.zhiw.gankapp.ui.activity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.zhiw.gankapp.R;
import com.zhiw.gankapp.app.ToolBarActivity;
import com.zhiw.gankapp.config.Constants;
import com.zhiw.gankapp.presenter.MeizhiPresenter;
import com.zhiw.gankapp.ui.view.MeizhiView;
import com.zhiw.gankapp.utils.DateUtil;
import com.zhiw.gankapp.utils.Measure;
import com.zhiw.gankapp.utils.SnackbarUtil;

import android.Manifest;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import butterknife.Bind;
import uk.co.senab.photoview.PhotoViewAttacher;

public class MeizhiActivity extends ToolBarActivity implements MeizhiView {

    private static final int REQUEST_CODE_WRITE_STORAGE = 757;

    @Bind(R.id.app_bar)
    AppBarLayout mAppBar;
    @Bind(R.id.meizhi)
    ImageView mImageView;
    @Bind(R.id.photo_layout)
    LinearLayout mPhotoBackground;


    MeizhiPresenter mPresenter;
    PhotoViewAttacher mAttacher;
    private Bitmap mBitmap;

    private String mDate;
    private boolean mIsFull;
    private String mImageUrl;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_meizhi;
    }

    @Override
    protected void setUpView() {
        mImageUrl = getIntent().getStringExtra(Constants.URL);
        mDate = DateUtil.parseDate(getIntent().getStringExtra(Constants.DATE));

        setupPhotoAttacher();

        setSystemUI();

        setStatueBarColor();

        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(visibility -> {
            if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                showSystemUI();
            } else {
                hideSystemUI();
            }
        });
    }

    private void setupPhotoAttacher() {
        mAttacher = new PhotoViewAttacher(mImageView);
        Glide.with(this)
                .load(mImageUrl)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        mImageView.setImageBitmap(resource);
                        mAttacher.update();
                        mBitmap = resource;
                    }
                });

        mAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float v, float v1) {
                toggleSystemUI();
            }

            @Override
            public void onOutsidePhotoTap() {
                toggleSystemUI();

            }
        });
    }


    @Override
    protected void setUpData() {
        mPresenter = new MeizhiPresenter(this, this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_meizhi, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_WRITE_STORAGE);
                } else {
                    mPresenter.downloadImage(mBitmap, mDate, 0);
                }
                break;
            case R.id.action_share:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_WRITE_STORAGE);
                } else {
                    mPresenter.downloadImage(mBitmap, mDate, 1);
                }
//                mPresenter.shareImage(mUrl);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showImageResult(String text) {
        SnackbarUtil.showSnackbar(mImageView, text);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_WRITE_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mPresenter.saveImage(mBitmap, mDate);

            } else {
                SnackbarUtil.showSnackbar(mImageView, getString(R.string.permission_denied));
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAttacher.cleanup();
    }

    @Override
    public void onBackPressed() {
        if (mIsFull) {
            showSystemUI();
        } else {
            super.onBackPressed();
        }
    }

    private void toggleSystemUI() {
        if (mIsFull) {
            showSystemUI();
        } else {
            hideSystemUI();
        }
    }

    private void setSystemUI() {
        mAppBar.animate()
                .translationY(Measure.getStatusBarHeight(getResources()))
                .setInterpolator(new DecelerateInterpolator())
                .start();

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    private void showSystemUI() {
        runOnUiThread(() -> {
            mAppBar.animate().translationY(Measure.getStatusBarHeight(getResources())).setInterpolator(new DecelerateInterpolator())
                    .setDuration(240).start();
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            mIsFull = false;
            changeBackgroundColor();
        });
    }

    private void hideSystemUI() {
        runOnUiThread(() -> {
            mAppBar.animate().translationY(-mAppBar.getHeight()).setInterpolator(new AccelerateInterpolator())
                    .setDuration(240).start();
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                            | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            | View.SYSTEM_UI_FLAG_IMMERSIVE);

            mIsFull = true;
            changeBackgroundColor();
        });
    }

    private void setStatueBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
    }

    private void changeBackgroundColor() {
        int colorFrom;
        int colorTo;
        if (mIsFull) {
            colorFrom = ContextCompat.getColor(this, R.color.white_70);
            colorTo = ContextCompat.getColor(this, R.color.md_black_1000);
        } else {
            colorFrom = ContextCompat.getColor(this, R.color.md_black_1000);
            colorTo = ContextCompat.getColor(this, R.color.white_70);
        }

        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setDuration(240);
        colorAnimation.addUpdateListener(animation -> mPhotoBackground.setBackgroundColor((Integer) animation.getAnimatedValue()));
        colorAnimation.start();
    }


}

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
import com.zhiw.gankapp.utils.SnackbarUtil;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import butterknife.Bind;
import uk.co.senab.photoview.PhotoViewAttacher;

public class MeizhiActivity extends ToolBarActivity implements MeizhiView {
    private static final int REQUEST_CODE_WRITE_STORAGE = 757;

    @Bind(R.id.meizhi)
    ImageView meizhi;


    MeizhiPresenter mPresenter;
    PhotoViewAttacher mAttacher;
    private Bitmap mBitmap;

    private String date;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_meizhi;
    }

    @Override
    protected void initPresenter() {
        mPresenter = new MeizhiPresenter(this, this);
        mPresenter.init();

    }

    @Override
    public void initView() {
        String url = getIntent().getStringExtra(Constants.URL);
        date = DateUtil.parseDate(getIntent().getStringExtra(Constants.DATE));

        mAttacher = new PhotoViewAttacher(meizhi);
        Glide.with(this)
                .load(url)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        meizhi.setImageBitmap(resource);
                        mAttacher.update();
                        mBitmap = resource;
                    }
                });

        mAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float v, float v1) {
                hideOrShowToolBar();
            }

            @Override
            public void onOutsidePhotoTap() {
                hideOrShowToolBar();

            }
        });

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
                    mPresenter.downloadImage(mBitmap, date, 0);
                }
                break;
            case R.id.action_share:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_WRITE_STORAGE);
                } else {
                    mPresenter.downloadImage(mBitmap, date, 1);
                }
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showImageResult(String text) {
        SnackbarUtil.showSnackbar(meizhi, text);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_WRITE_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mPresenter.saveImage(mBitmap, date);

            } else {
                SnackbarUtil.showSnackbar(meizhi, "没有授权");
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}

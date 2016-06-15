package com.zhiw.gankapp.ui.Activity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.zhiw.gankapp.R;
import com.zhiw.gankapp.app.BaseActivity;
import com.zhiw.gankapp.config.Constants;
import com.zhiw.gankapp.presenter.MeizhiPresenter;
import com.zhiw.gankapp.ui.View.MeizhiView;

import android.graphics.Bitmap;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import butterknife.Bind;
import uk.co.senab.photoview.PhotoViewAttacher;

public class MeizhiActivity extends BaseActivity implements MeizhiView {

    @Bind(R.id.meizhi)
    ImageView mMeizhi;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.app_bar)
    AppBarLayout mAppBar;

    MeizhiPresenter mPresenter;
    PhotoViewAttacher mAttacher;


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
        String url = getIntent().getStringExtra(Constants.MEIZHI);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mToolbar.setNavigationIcon(getDrawable(R.drawable.ic_arrow_back));
        }
        mToolbar.setNavigationOnClickListener(view -> finish());

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        mAttacher = new PhotoViewAttacher(mMeizhi);
        Glide.with(this)
                .load(url)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        mMeizhi.setImageBitmap(resource);
                        mAttacher.update();
                    }
                });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_meizhi,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}

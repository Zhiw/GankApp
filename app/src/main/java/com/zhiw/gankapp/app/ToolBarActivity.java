package com.zhiw.gankapp.app;

import com.zhiw.gankapp.R;
import com.zhiw.gankapp.utils.SnackbarUtil;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import butterknife.Bind;

public abstract class ToolBarActivity extends BaseActivity implements BaseView {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.app_bar)
    AppBarLayout mAppBar;

    private FrameLayout mRootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        if (mToolbar != null) {
            mToolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_arrow_back));
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
            mToolbar.setNavigationOnClickListener(view -> finish());
        }

        mRootView = (FrameLayout) findViewById(android.R.id.content);

    }


    @Override
    public void error(String msg) {
        SnackbarUtil.showSnackbar(mRootView, msg);
    }
}

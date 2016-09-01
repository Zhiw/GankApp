package com.zhiw.gankapp.ui.activity;

import com.zhiw.gankapp.R;
import com.zhiw.gankapp.app.ToolBarActivity;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.widget.TextView;

import butterknife.Bind;

public class AboutActivity extends ToolBarActivity {


    @Bind(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @Bind(R.id.tv_about_content)
    TextView mTvAboutContent;
    @Bind(R.id.fab)
    FloatingActionButton mFab;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initPresenter() {
        mTvAboutContent.setAutoLinkMask(Linkify.ALL);
        mTvAboutContent.setMovementMethod(LinkMovementMethod.getInstance());

    }

}

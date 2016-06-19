package com.zhiw.gankapp.ui.Activity;

import com.orhanobut.logger.Logger;
import com.zhiw.gankapp.R;
import com.zhiw.gankapp.app.ToolBarActivity;
import com.zhiw.gankapp.config.Constants;
import com.zhiw.gankapp.presenter.WebViewPresenter;
import com.zhiw.gankapp.ui.View.WebView;

import android.view.Menu;

import butterknife.Bind;

public class WebViewActivity extends ToolBarActivity implements WebView {

    @Bind(R.id.web_view)
    android.webkit.WebView mWebView;

    private WebViewPresenter mPresenter;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void initPresenter() {
        mPresenter = new WebViewPresenter(this, this);
        mPresenter.init();

    }

    @Override
    public void initView() {

        String url = getIntent().getStringExtra(Constants.URL);
        String des = getIntent().getStringExtra(Constants.DES);
        setTitle(des);
        mPresenter.loadWeb(mWebView, url);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_web, menu);
        return true;
    }


    @Override
    public void showProgress(int progress) {
        Logger.d(""+progress);
    }
}

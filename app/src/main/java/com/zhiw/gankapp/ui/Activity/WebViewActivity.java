package com.zhiw.gankapp.ui.Activity;

import com.zhiw.gankapp.R;
import com.zhiw.gankapp.app.ToolBarActivity;
import com.zhiw.gankapp.config.Constants;
import com.zhiw.gankapp.presenter.WebViewPresenter;
import com.zhiw.gankapp.ui.View.IWebView;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import butterknife.Bind;

public class WebViewActivity extends ToolBarActivity implements IWebView {

    @Bind(R.id.web_view)
    android.webkit.WebView mWebView;
    @Bind(R.id.progress_bar)
    ProgressBar mProgressBar;

    private WebViewPresenter mPresenter;

    private String url;


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

        url = getIntent().getStringExtra(Constants.URL);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                mPresenter.refresh(mWebView);
                break;
            case R.id.action_copy:
                ClipboardManager copy = (ClipboardManager) WebViewActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
                copy.setPrimaryClip(ClipData.newPlainText(null, url));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showProgress(int progress) {
        if (mProgressBar == null) return;
        mProgressBar.setProgress(progress);
        if (progress == 100) {
            mProgressBar.setVisibility(View.GONE);
        } else {
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }
}

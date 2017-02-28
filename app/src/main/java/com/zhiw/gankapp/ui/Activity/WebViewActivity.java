package com.zhiw.gankapp.ui.activity;

import com.zhiw.gankapp.R;
import com.zhiw.gankapp.app.ToolBarActivity;
import com.zhiw.gankapp.config.Constants;
import com.zhiw.gankapp.presenter.WebViewPresenter;
import com.zhiw.gankapp.ui.view.IWebView;
import com.zhiw.gankapp.utils.SnackbarUtil;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;

import butterknife.Bind;

public class WebViewActivity extends ToolBarActivity implements IWebView {

    @Bind(R.id.web_view)
    WebView mWebView;
    @Bind(R.id.progress_bar)
    ProgressBar mProgressBar;

    private WebViewPresenter mPresenter;

    private String url;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void setUpView() {
        url = getIntent().getStringExtra(Constants.URL);
        String des = getIntent().getStringExtra(Constants.DES);
        setTitle(des);
    }

    @Override
    protected void setUpData() {
        mPresenter = new WebViewPresenter(this, this);
        mPresenter.initWebSettings(mWebView);
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
                SnackbarUtil.showSnackbar(mWebView, getString(R.string.url_copy_tip));
                break;
            case R.id.action_share:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.url_share));
                intent.putExtra(Intent.EXTRA_TEXT, url);
                startActivity(Intent.createChooser(intent, "分享到"));
                break;
            case R.id.action_open_with:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                break;


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

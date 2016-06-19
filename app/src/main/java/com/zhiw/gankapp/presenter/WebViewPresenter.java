package com.zhiw.gankapp.presenter;

import com.zhiw.gankapp.app.BasePresenter;
import com.zhiw.gankapp.ui.View.WebView;

import android.content.Context;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

/**
 * ClassName: WebViewPresenter
 * Desc:
 * Created by zhiw on 16/6/16.
 */
public class WebViewPresenter extends BasePresenter<WebView> {
    public WebViewPresenter(Context context, WebView view) {
        super(context, view);
    }

    public void loadWeb(android.webkit.WebView webView, String url){
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setAppCacheEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);
        settings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new MyWebViewClient());
        webView.setWebChromeClient(new MyWebChromeClient());
        webView.loadUrl(url);
    }

    class MyWebViewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(android.webkit.WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    class MyWebChromeClient extends WebChromeClient{
        @Override
        public void onProgressChanged(android.webkit.WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            viewImpl.showProgress(newProgress);
        }
    }
}

package com.zhiw.gankapp.ui.fragment;

import com.zhiw.gankapp.R;
import com.zhiw.gankapp.app.BaseFragment;
import com.zhiw.gankapp.config.Apis;
import com.zhiw.gankapp.presenter.WebViewPresenter;
import com.zhiw.gankapp.ui.view.IWebView;

import android.webkit.WebView;

import butterknife.Bind;

/**
 * ClassName: ReadingFragment
 * Desc:
 * Created by zhiw on 16/12/6.
 */

public class ReadingFragment extends BaseFragment implements IWebView {


    @Bind(R.id.web_view_reading) WebView mReadingWebView;

    private WebViewPresenter mPresenter;

    private String mUrl = Apis.GANK_XIANDU_URL;

    public static ReadingFragment newInstance() {
        return new ReadingFragment();
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_reading;
    }

    @Override
    protected void setUpView() {

    }

    @Override
    protected void setUpData() {
        mPresenter = new WebViewPresenter(fragmentActivity, this);
        mPresenter.initWebSettings(mReadingWebView);
        mPresenter.loadWeb(mReadingWebView, mUrl);
    }


    @Override
    public void showProgress(int progress) {

    }


}

package com.zhiw.gankapp.app;

import com.zhiw.gankapp.http.GankRetrofit;
import com.zhiw.gankapp.http.GankService;

import android.content.Context;

/**
 * ClassName: BasePresenter
 * Desc:
 * Created by zhiw on 16/6/12.
 */

public class BasePresenter<T extends BaseView> {

    public Context context;

    public T viewImpl;

    public GankService gank;

    public BasePresenter(Context context, T view) {
        this.context = context;
        this.viewImpl = view;
        gank = GankRetrofit.getRetrofit()
                .create(GankService.class);
    }

    public void init() {
        viewImpl.initView();
    }
}

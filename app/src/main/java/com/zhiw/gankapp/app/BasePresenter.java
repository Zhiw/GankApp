package com.zhiw.gankapp.app;

import android.content.Context;

/**
 * ClassName: BasePresenter
 * Desc:
 * Created by zhiw on 16/6/12.
 */

public class BasePresenter<T extends BaseView> {

    public Context context;

    public T view;

    public BasePresenter(Context context, T view) {
        this.context = context;
        this.view = view;
    }

    public void init(){
        view.initView();
    }
}

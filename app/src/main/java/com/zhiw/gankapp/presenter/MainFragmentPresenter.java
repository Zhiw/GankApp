package com.zhiw.gankapp.presenter;

import com.orhanobut.logger.Logger;
import com.zhiw.gankapp.app.BasePresenter;
import com.zhiw.gankapp.config.Constants;
import com.zhiw.gankapp.http.GankRetrofit;
import com.zhiw.gankapp.http.GankService;
import com.zhiw.gankapp.ui.View.MainFragmentView;

import android.content.Context;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * ClassName: MainFragmentPresenter
 * Desc:
 * Created by zhiw on 16/6/12.
 */

public class MainFragmentPresenter extends BasePresenter<MainFragmentView> {

    public MainFragmentPresenter(Context context, MainFragmentView view) {
        super(context, view);
    }

    public void getMeizhi(int page){
        GankRetrofit.getRetrofit()
                .create(GankService.class)
                .getGank(Constants.TYPE_MEIZHI,10,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(gankData -> {
                    Logger.d("success");
                    viewImpl.showProgress(false);
                    viewImpl.showListView(gankData.getResults());
                });


    }

    public void getGank(String type,int page){
        GankRetrofit.getRetrofit()
                .create(GankService.class)
                .getGank(type,10,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(gankData -> {
                    viewImpl.showProgress(false);
                    viewImpl.refreshGankList(gankData.getResults());

                });
    }
}

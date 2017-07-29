package com.zhiw.gankapp.presenter;

import com.zhiw.gankapp.app.BasePresenter;
import com.zhiw.gankapp.config.Constants;
import com.zhiw.gankapp.http.GankApi;
import com.zhiw.gankapp.http.GankDataResource;
import com.zhiw.gankapp.model.GankData;
import com.zhiw.gankapp.ui.view.MainFragmentView;

import android.content.Context;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


/**
 * ClassName: MainFragmentPresenter
 * Desc:
 * Created by zhiw on 16/6/12.
 */

public class MainFragmentPresenter extends BasePresenter<MainFragmentView> {

    private GankApi mGankApi;

    public MainFragmentPresenter(Context context, MainFragmentView view) {
        super(context, view);
        mGankApi = new GankDataResource();
    }

    public void getMeizhi(int page) {
        mGankApi.getGank(Constants.TYPE_MEIZHI, Constants.COUNT, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<GankData>() {
                    @Override
                    public void onNext(GankData gankData) {
                        viewImpl.showProgress(false);
                        viewImpl.showListView(gankData.getResults());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    public void getGank(String type, int page) {

        mGankApi.getGank(type, Constants.COUNT, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(gankData -> {
                    viewImpl.showProgress(false);
                    viewImpl.refreshGankList(gankData.getResults());

                });
    }
}

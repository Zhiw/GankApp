package com.zhiw.gankapp.presenter;

import com.zhiw.gankapp.app.BasePresenter;
import com.zhiw.gankapp.http.GankApi;
import com.zhiw.gankapp.http.GankDataResource;
import com.zhiw.gankapp.model.DailyGank;
import com.zhiw.gankapp.ui.view.DailyGankView;

import android.content.Context;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * ClassName: DailyGankPresenter
 * Desc:
 * Created by zhiw on 16/8/26.
 */

public class DailyGankPresenter extends BasePresenter<DailyGankView> {

    private GankApi mGankApi;

    public DailyGankPresenter(Context context, DailyGankView view) {
        super(context, view);
        mGankApi = new GankDataResource();
    }

    public void getDailyGank(int[] date) {
        int year = date[0];
        int month = date[1];
        int day = date[2];

        mGankApi.getDailyData(year, month, day)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<DailyGank>() {
                    @Override
                    public void onNext(DailyGank dailyGank) {
                        viewImpl.refreshUI(dailyGank.getResults());

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}

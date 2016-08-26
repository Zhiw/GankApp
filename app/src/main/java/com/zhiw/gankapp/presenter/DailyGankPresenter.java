package com.zhiw.gankapp.presenter;

import com.zhiw.gankapp.app.BasePresenter;
import com.zhiw.gankapp.model.DailyGank;
import com.zhiw.gankapp.ui.View.DailyGankView;

import android.content.Context;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * ClassName: DailyGankPresenter
 * Desc:
 * Created by zhiw on 16/8/26.
 */

public class DailyGankPresenter extends BasePresenter<DailyGankView> {

    public DailyGankPresenter(Context context, DailyGankView view) {
        super(context, view);
    }

    public void getDailyGank(int[] date){
        int year=date[0];
        int month=date[1];
        int day=date[2];

        gank.getDailyData(year,month,day)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<DailyGank>() {
                    @Override
                    public void call(DailyGank dailyGank) {
                        if (!dailyGank.isError()){
                            viewImpl.refreshUI(dailyGank.getResults());
                        }

                    }
                });

    }
}

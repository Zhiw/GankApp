package com.zhiw.gankapp.presenter;

import com.zhiw.gankapp.app.BasePresenter;
import com.zhiw.gankapp.config.Constants;
import com.zhiw.gankapp.http.GankRetrofit;
import com.zhiw.gankapp.http.GankService;
import com.zhiw.gankapp.model.GankData;
import com.zhiw.gankapp.ui.View.DailyListView;

import android.content.Context;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * ClassName: DailyListPresenter
 * Desc:
 * Created by zhiw on 16/7/19.
 */
public class DailyListPresenter extends BasePresenter<DailyListView> {

    public DailyListPresenter(Context context, DailyListView view) {
        super(context, view);
    }


    public void getData() {
        Observable.zip(GankRetrofit.getRetrofit().create(GankService.class)
                        .getGank(Constants.TYPE_MEIZHI, 10, 1), GankRetrofit.getRetrofit().create(GankService.class)
                        .getGank(Constants.TYPE_VIDEO, 10, 1)
                , this::getDataFromMeizhiAndVideo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(gankData -> {
                    if (gankData.getResults().size() != 0) {
                        viewImpl.showProgress(false);
                        viewImpl.refreshGanData(gankData.getResults());
                    }
                });

    }


    private GankData getDataFromMeizhiAndVideo(GankData meizhi, GankData gankData) {
        int size = Math.min(meizhi.getResults().size(), gankData.getResults().size());
        for (int i = 0; i < size; i++) {
            meizhi.getResults().get(i).setDesc(gankData.getResults().get(i).getDesc());
        }

        return meizhi;
    }

}

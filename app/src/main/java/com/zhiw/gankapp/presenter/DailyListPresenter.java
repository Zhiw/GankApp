package com.zhiw.gankapp.presenter;

import com.zhiw.gankapp.app.BasePresenter;
import com.zhiw.gankapp.config.Constants;
import com.zhiw.gankapp.http.GankApi;
import com.zhiw.gankapp.http.GankDataResource;
import com.zhiw.gankapp.model.GankData;
import com.zhiw.gankapp.ui.view.DailyListView;

import android.content.Context;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * ClassName: DailyListPresenter
 * Desc:
 * Created by zhiw on 16/7/19.
 */
public class DailyListPresenter extends BasePresenter<DailyListView> {

    private GankApi mGankApi;

    public DailyListPresenter(Context context, DailyListView view) {
        super(context, view);
        mGankApi = new GankDataResource();
    }


    public void getData() {
        Observable.zip(
                mGankApi.getGank(Constants.TYPE_MEIZHI, Constants.COUNT, 1),
                mGankApi.getGank(Constants.TYPE_VIDEO, Constants.COUNT, 1),
                this::getDataFromMeizhiAndVideo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(gankData -> {
                    if (gankData.getResults().size() != 0) {
                        viewImpl.showProgress(false);
                        viewImpl.refreshGanData(gankData.getResults());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        viewImpl.error("暂时无网络");
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

package com.zhiw.gankapp.presenter;

import com.zhiw.gankapp.app.BasePresenter;
import com.zhiw.gankapp.config.Constants;
import com.zhiw.gankapp.http.GankApi;
import com.zhiw.gankapp.http.GankDataResource;
import com.zhiw.gankapp.model.GankData;
import com.zhiw.gankapp.ui.view.DailyListView;

import android.content.Context;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


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


    public void getData(int page) {
        Observable.zip(
                mGankApi.getGank(Constants.TYPE_MEIZHI, Constants.COUNT, page),
                mGankApi.getGank(Constants.TYPE_VIDEO, Constants.COUNT, page),
                this::getDataFromMeizhiAndVideo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<GankData>() {
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GankData gankData) {
                        if (gankData.getResults().size() != 0) {
                            viewImpl.showProgress(false);
                            viewImpl.refreshGanData(gankData.getResults());
                        }
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

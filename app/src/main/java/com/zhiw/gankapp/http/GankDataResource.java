package com.zhiw.gankapp.http;

import com.zhiw.gankapp.model.DailyGank;
import com.zhiw.gankapp.model.GankData;
import com.zhiw.gankapp.model.SearchResponse;

import rx.Observable;

/**
 * ClassName: GankDataResource
 * Desc:
 * Created by zhiw on 16/12/14.
 */

public class GankDataResource implements GankApi {

    private GankService mGankService;

    public GankDataResource() {
        mGankService = GankRetrofit.getInstance().create(GankService.class);
    }

    @Override
    public Observable<GankData> getGank(String type, int count, int page) {
        return mGankService.getGank(type, count, page);
    }

    @Override
    public Observable<DailyGank> getDailyData(int year, int month, int day) {
        return mGankService.getDailyData(year, month, day);
    }

    @Override
    public Observable<SearchResponse> search(String keyword, int page) {
        return mGankService.search(keyword, page);
    }
}

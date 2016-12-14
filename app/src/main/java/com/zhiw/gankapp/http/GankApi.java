package com.zhiw.gankapp.http;

import com.zhiw.gankapp.model.DailyGank;
import com.zhiw.gankapp.model.GankData;
import com.zhiw.gankapp.model.SearchResponse;

import rx.Observable;

/**
 * ClassName: GankApi
 * Desc:
 * Created by zhiw on 16/12/14.
 */

public interface GankApi {

    Observable<GankData> getGank(String type, int count, int page);

    Observable<DailyGank> getDailyData(int year, int month, int day);

    Observable<SearchResponse> search(String keyword, int page);
}

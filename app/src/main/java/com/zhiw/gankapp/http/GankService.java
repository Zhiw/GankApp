package com.zhiw.gankapp.http;

import com.zhiw.gankapp.model.DailyGank;
import com.zhiw.gankapp.model.GankData;
import com.zhiw.gankapp.model.SearchResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * ClassName: GankService
 * Desc:
 * Created by zhiw on 16/6/12.
 */

public interface GankService {

    @GET(value = "data/{type}/{count}/{page}")
    Observable<GankData> getGank(
            @Path("type") String type,
            @Path("count") int count,
            @Path("page") int page);


    //请求某天干货数据
    @GET("day/{year}/{month}/{day}")
    Observable<DailyGank> getDailyData(
            @Path("year") int year,
            @Path("month") int month,
            @Path("day") int day);

    /**
     * search
     *
     * @param keyword keyword
     * @param page    page
     */
    @GET("search/query/{keyword}/category/all/count/20/page/{page}")
    Observable<SearchResponse> search(
            @Path("keyword") String keyword,
            @Path("page") int page);

}

package com.zhiw.gankapp.http;

import com.zhiw.gankapp.config.Apis;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;


/**
 * ClassName: GankRetrofit
 * Desc:
 * Created by zhiw on 16/6/12.
 */

public class GankRetrofit extends BaseRetrofit {

    private static Retrofit sRetrofit;

    public static Retrofit getInstance() {
        if (sRetrofit == null) {
            synchronized (GankRetrofit.class) {
                sRetrofit = new GankRetrofit().get();
            }
        }
        return sRetrofit;
    }

    @Override
    public String getEndPoint() {
        return Apis.GANK_BASE_URL;
    }

    @Override
    public OkHttpClient getHttpClient() {
        return new GankHttpClient().get();
    }
}

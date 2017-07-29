package com.zhiw.gankapp.http.core;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * ClassName: BaseRetrofit
 * Desc:
 * Created by zhiw on 16/12/14.
 */

public abstract class BaseRetrofit {

    public Retrofit get() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(getEndPoint())
                .client(getHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

        return builder.build();


    }

    public abstract String getEndPoint();

    public abstract OkHttpClient getHttpClient();

}

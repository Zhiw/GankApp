package com.zhiw.gankapp.http.core;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * ClassName: BaseHttpClient
 * Desc:
 * Created by zhiw on 16/12/14.
 */

public abstract class BaseHttpClient {

    private static final long TIMEOUT_CONNECT = 30 * 1000;

    public OkHttpClient get() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.connectTimeout(TIMEOUT_CONNECT, TimeUnit.MILLISECONDS);

        builder = customize(builder);

        return builder.build();
    }

    public abstract OkHttpClient.Builder customize(OkHttpClient.Builder builder);
}

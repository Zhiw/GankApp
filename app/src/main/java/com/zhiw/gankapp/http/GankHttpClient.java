package com.zhiw.gankapp.http;

import com.zhiw.gankapp.App;
import com.zhiw.gankapp.utils.NetworkUtil;

import java.io.File;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * ClassName: GankHttpClient
 * Desc:
 * Created by zhiw on 16/12/14.
 */

public class GankHttpClient extends BaseHttpClient {

    @Override
    public OkHttpClient.Builder customize(OkHttpClient.Builder builder) {
        File cacheFile = new File(App.getContext().getCacheDir(), "gank");
        int cacheSize = 25 * 1024 * 1024;
        Cache cache = new Cache(cacheFile, cacheSize);
        builder.cache(cache)
                .addNetworkInterceptor(mCacheInterceptor);
        return builder;
    }

    private Interceptor mCacheInterceptor = chain -> {
        Request request = chain.request();
        if (!NetworkUtil.isNetworkAvailable(App.getContext())) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }

        Response response = chain.proceed(request);
        if (NetworkUtil.isNetworkAvailable(App.getContext())) {
            String cacheControl = request.cacheControl().toString();

            return response.newBuilder()
                    .header("Cache-Control", cacheControl)
                    .build();
        } else {
            return response.newBuilder()
                    .header("Cache-Control", CacheControl.FORCE_CACHE.toString())
                    .build();
        }
    };
}

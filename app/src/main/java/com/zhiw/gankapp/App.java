package com.zhiw.gankapp;

import com.orhanobut.logger.AndroidLogTool;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

import android.app.Application;
import android.content.Context;

/**
 * ClassName: App
 * Desc:
 * Created by zhiw on 16/6/12.
 */

public class App extends Application {
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        Logger
                .init()                         // default PRETTYLOGGER or use just init()
                .methodCount(5)                 // default 2
                .hideThreadInfo()               // default shown
                .logLevel(LogLevel.FULL)        // default LogLevel.FULL
                .methodOffset(2)                // default 0
                .logTool(new AndroidLogTool()); // custom log tool, optional
    }

    public static Context getContext() {
        return sContext;
    }
}

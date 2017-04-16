package com.github.news.base;

import android.app.Application;
import android.content.Context;

/**
 * ============================================================
 * Copyright：${TODO}有限公司版权所有 (c) 2017
 * Author：   卢俊霖
 * Email：    briskemen@163.com
 * GitHub：   https://github.com/briskemen
 * Project_Name：News
 * Package_Name：com.github.news.global
 * Version：1.0
 * time：2017/4/4 23:41
 * des ：${TODO}
 * gitVersion：$Rev$
 * updateAuthor：$Author$
 * updateDate：$Date$
 * updateDes：${TODO}
 * ============================================================
 **/

/**
 * 自定义Application
 */
public class App extends Application {
    private static final String TAG = "App";

    private static Context mContext;
    private static App     mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        mContext = getApplicationContext();

        // 设置未捕获异常的处理器
        CrashHandler.getInstance().init(this);
    }

    public static Context getApplication() {
        return mApplication;
    }

    public static Context getContext() {
        return mContext;
    }

}

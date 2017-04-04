package com.hello.newsdemo.global;

import android.app.Application;
import android.os.Environment;

import com.hello.newsdemo.utils.LogUtils;

import java.io.PrintWriter;

/**
 * ============================================================
 * Copyright：${TODO}有限公司版权所有 (c) 2017
 * Author：   卢俊霖
 * Email：    briskemen@163.com
 * GitHub：   https://github.com/briskemen
 * Project_Name：News
 * Package_Name：com.hello.newsdemo.global
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
public class MyApplication extends Application {
    private static final String TAG = "MyApplication";
    @Override
    public void onCreate() {
        super.onCreate();

        // 设置未捕获异常的处理器
        Thread.setDefaultUncaughtExceptionHandler(new MyHandler());
    }

    class MyHandler implements Thread.UncaughtExceptionHandler {

        // 一旦有未捕获的异常,就会回调此方法
        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            LogUtils.i(TAG,"发现一个未处理的异常, 但是被哥捕获了...");
            ex.printStackTrace();

            // 收集崩溃日志, 可以在后台上传给服务器,供开发人员分析
            try {
                PrintWriter err = new PrintWriter(
                        Environment.getExternalStorageDirectory()
                                + "/news.log");
                ex.printStackTrace(err);
                err.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 停止当前进程
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }
}

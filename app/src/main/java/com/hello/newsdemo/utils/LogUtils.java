package com.hello.newsdemo.utils;

/**
 * ============================================================
 * Copyright：${TODO}有限公司版权所有 (c) 2017
 * Author：   卢俊霖
 * Email：    briskemen@163.com
 * GitHub：   https://github.com/briskemen
 * Project_Name：MaterialTest
 * Package_Name：com.hello.materialtest.utils
 * Version：1.0
 * time：2017/3/30 19:02
 * des ：${TODO}
 * gitVersion：$Rev$
 * updateAuthor：$Author$
 * updateDate：$Date$
 * updateDes：${TODO}
 * ============================================================
 **/

import android.util.Log;

/**
 * log日志打印工具类
 */
public class LogUtils {
    // 日志的6个等级
    private static final int VERBOSE = 1;
    private static final int DEBUG   = 2;
    private static final int INFO    = 3;
    private static final int WARN    = 4;
    private static final int ERROR   = 5;
    private static final int NOTHING = 6;

    /**
     * 当开发过程中，只需要将level指定成VERBOSE，即可打印所有的日志
     * 当项目正式上线的时候将level指定成NOTHING就可以了
     */
    private static final int level = VERBOSE;

    public static void v(String tag, String msg) {
        if (level <= VERBOSE) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (level <= DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (level <= INFO) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (level <= WARN) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (level <= ERROR) {
            Log.e(tag, msg);
        }
    }
}

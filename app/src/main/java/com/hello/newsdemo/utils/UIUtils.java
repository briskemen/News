package com.hello.newsdemo.utils;

import android.content.Context;

/**
 * ============================================================
 * Copyright：${TODO}有限公司版权所有 (c) 2017
 * Author：   AllenIverson
 * Email：    815712739@qq.com
 * GitHub：   https://github.com/JackChen1999
 * GitBook：  https://www.gitbook.com/@alleniverson
 * 博客：     http://blog.csdn.net/axi295309066
 * 微博：     AndroidDeveloper
 * <p>
 * Project_Name：ZHBJ
 * Package_Name：com.hello.newsdemo.utils
 * Version：1.0
 * time：2017/4/2 3:06
 * des ：${TODO}
 * gitVersion：2.12.0.windows.1
 * updateAuthor：$Author$
 * updateDate：$Date$
 * updateDes：${TODO}
 * ============================================================
 */
public class UIUtils {

    // dip2px 1:0.75 1:1 1:0.5 1:2 1:3
    public static int dip2px(Context context , int dip) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (density * dip + 0.5);
    }

    // px2dip
    public static int px2dip(Context context, int px) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (px / density + 0.5);
    }
}

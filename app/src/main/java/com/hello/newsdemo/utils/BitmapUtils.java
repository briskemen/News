package com.hello.newsdemo.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hello.zhbj52.R;

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
 * time：2017/4/1 23:35
 * des ：${TODO}
 * gitVersion：2.12.0.windows.1
 * updateAuthor：$Author$
 * updateDate：$Date$
 * updateDes：${TODO}
 * ============================================================
 */
public class BitmapUtils {
    public static void display(Context context, ImageView iv, String url) {
        Glide.with(context).load(url).placeholder(new ColorDrawable(Color.parseColor("#eaeaea")))
                .error(R.mipmap.pic_item_list_default).into(iv);
    }
}

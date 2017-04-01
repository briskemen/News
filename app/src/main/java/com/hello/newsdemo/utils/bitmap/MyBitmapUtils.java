package com.hello.newsdemo.utils.bitmap;

/**
 * ============================================================
 * Copyright：${TODO}有限公司版权所有 (c) 2017
 * Author：   卢俊霖
 * Email：    briskemen@163.com
 * GitHub：   https://github.com/briskemen
 * Project_Name：Zhbj
 * Package_Name：com.itheima.zhbj52.utils.bitmap
 * Version：1.0
 * time：2017/2/20 20:39
 * des ：${TODO}
 * gitVersion：$Rev$
 * updateAuthor：$Author$
 * updateDate：$Date$
 * updateDes：${TODO}
 * ============================================================
 **/

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.hello.zhbj52.R;


/**
 * 自定义图片加载工具类
 */
public class MyBitmapUtils {
    private final NetCacheUtils    mNetCacheUtils;
    private final LocalCacheUtils  mLocalCacheUtils;
    private final MemoryCacheUtils mMenoryCacheUtils;

    public MyBitmapUtils() {
        mNetCacheUtils = new NetCacheUtils();
        mLocalCacheUtils = new LocalCacheUtils();
        mMenoryCacheUtils = new MemoryCacheUtils();
    }

    public void display(ImageView ivPic, String url) {
        ivPic.setImageResource(R.mipmap.news_pic_default);// 设置默认加载图片

        Bitmap bitmap = null;

        // 内存缓存
        bitmap = mMenoryCacheUtils.getBitmapFromMemory(url);
        if (bitmap != null) {
            ivPic.setImageBitmap(bitmap);
            return;
        }

        // 本地缓存
        bitmap = mLocalCacheUtils.getBitmapFromLocal(url);
        if (bitmap != null) {
            ivPic.setImageBitmap(bitmap);
            return;
        }


        // 网络缓存
        mNetCacheUtils.getBitmapFromNet(ivPic, url);
    }
}

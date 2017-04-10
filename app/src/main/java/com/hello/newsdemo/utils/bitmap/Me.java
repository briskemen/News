package com.hello.newsdemo.utils.bitmap;

/**
 * ============================================================
 * Copyright：${TODO}有限公司版权所有 (c) 2017
 * Author：   卢俊霖
 * Email：    briskemen@163.com
 * GitHub：   https://github.com/briskemen
 * Project_Name：News
 * Package_Name：com.hello.newsdemo.utils.bitmap
 * Version：1.0
 * time：2017/4/10 0:36
 * des ：${TODO}
 * gitVersion：$Rev$
 * updateAuthor：$Author$
 * updateDate：$Date$
 * updateDes：${TODO}
 * ============================================================
 **/

import android.graphics.Bitmap;

import com.hello.newsdemo.utils.LruCache;

/**
 * 内存缓存
 */
public class Me {
    private LruCache<String, Bitmap> mMemoryCache;

    public Me() {
        long maxMemory = Runtime.getRuntime().maxMemory() / 8;// Google推荐
        mMemoryCache = new LruCache<String, Bitmap>((int) maxMemory) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                int byteCount = value.getRowBytes() * value.getHeight();
                return byteCount;
            }
        };
    }

    /**
     * 从内存中读取
     *
     * @param url
     * @return
     */
    public Bitmap getBitmapFromMemory(String url) {
        return mMemoryCache.get(url);
    }

    public void setBitmapToMemory(String url, Bitmap bitmap) {
        mMemoryCache.put(url, bitmap);
    }
}

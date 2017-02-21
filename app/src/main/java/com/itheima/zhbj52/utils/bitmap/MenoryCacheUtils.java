package com.itheima.zhbj52.utils.bitmap;

/**
 * ============================================================
 * Copyright：${TODO}有限公司版权所有 (c) 2017
 * Author：   卢俊霖
 * Email：    briskemen@163.com
 * GitHub：   https://github.com/briskemen
 * Project_Name：Zhbj
 * Package_Name：com.itheima.zhbj52.utils.bitmap
 * Version：1.0
 * time：2017/2/20 23:28
 * des ：${TODO}
 * gitVersion：$Rev$
 * updateAuthor：$Author$
 * updateDate：$Date$
 * updateDes：${TODO}
 * ============================================================
 **/

import android.graphics.Bitmap;

import com.itheima.zhbj52.utils.LruCache;

/**
 * 内存缓存
 */
public class MenoryCacheUtils {
    // private HashMap<String, SoftReference<Bitmap>> mMemoryCache = new
    // HashMap<String, SoftReference<Bitmap>>();

    private LruCache<String, Bitmap> mMemoryCache;

    public MenoryCacheUtils() {
        long maxMemory = Runtime.getRuntime().maxMemory() / 8;// 模拟器默认是16m
        mMemoryCache = new LruCache<String, Bitmap>((int) maxMemory) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                int byteCount = value.getRowBytes() * value.getHeight();// 获取图片占用内存大小
                return byteCount;
            }
        };
    }

    /**
     * 从内存读
     *
     * @param url
     */
    public Bitmap getBitmapFromMemory(String url) {
        // SoftReference<Bitmap> softReference = mMemoryCache.get(url);
        // if (softReference != null) {
        // Bitmap bitmap = softReference.get();
        // return bitmap;
        // }
        return mMemoryCache.get(url);
    }

    /**
     * 写内存
     *
     * @param url
     * @param bitmap
     */
    public void setBitmapToMemory(String url, Bitmap bitmap) {
        // SoftReference<Bitmap> softReference = new
        // SoftReference<Bitmap>(bitmap);
        // mMemoryCache.put(url, softReference);
        mMemoryCache.put(url, bitmap);
    }
}

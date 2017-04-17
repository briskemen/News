package com.github.news.utils;

/**
 * ============================================================
 * Copyright：${TODO}有限公司版权所有 (c) 2017
 * Author：   卢俊霖
 * Email：    briskemen@163.com
 * GitHub：   https://github.com/briskemen
 * Project_Name：Zhbj
 * Package_Name：com.itheima.zhbj52.utils
 * Version：1.0
 * time：2017/2/20 8:29
 * des ：${TODO}
 * gitVersion：$Rev$
 * updateAuthor：$Author$
 * updateDate：$Date$
 * updateDes：${TODO}
 * ============================================================
 **/

import android.content.Context;

/**
 * 缓存工具类
 */
public class CacheUtils {
    /**
     * 设置缓存，key是url,value是json
     *
     * @param key
     * @param value
     * @param context
     */
    public static void SetCache(String key, String value, Context context) {
        PrefUtils.setString(context, key, value);//可以将缓存放在文件中, 文件名就是Md5(url), 文件内容是json
    }

    /**
     * 获取缓存,key是url
     *
     * @param key
     * @param ctx
     * @return
     */
    public static String getCache(String key, Context ctx) {
        return PrefUtils.getString(ctx, key, null);
    }
}

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
 * time：2017/2/20 20:48
 * des ：${TODO}
 * gitVersion：$Rev$
 * updateAuthor：$Author$
 * updateDate：$Date$
 * updateDes：${TODO}
 * ============================================================
 **/

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 网络缓存工具类
 */
public class NetCacheUtils {

    public NetCacheUtils() {

    }

    /**
     * 从网络获取图片
     *
     * @param ivPic
     * @param url
     */
    public void getBitmapFromNet(ImageView ivPic, String url) {
        new BitmapTast().execute(ivPic, url);// 启动AsyncTask,参数会在doInbackground中获取
    }


    /**
     * Handler和线程池的封装
     * <p>
     * 第一个泛型：参数类型，第二个泛型：更新进度的泛型，第三个泛型：onPostExecute的返回结果
     */
    class BitmapTast extends AsyncTask<Object, Void, Bitmap> {

        private ImageView ivPic;
        private String    url;

        /**
         * 后台耗时方法在此操作，子线程
         *
         * @param params
         * @return
         */
        @Override
        protected Bitmap doInBackground(Object[] params) {
            ivPic = (ImageView) params[0];
            url = (String) params[1];

            //            ivPic.setTag(url);// 将url和imageview绑定
            return downloadBitmap(url);
        }

        /**
         * 更新进度，主线程
         *
         * @param values
         */
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        /**
         * 耗时方法结束后，执行此方法，主线程
         *
         * @param bitmap
         */
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
        }
    }

    /**
     * 下载图片
     *
     * @param url
     * @return
     */
    private Bitmap downloadBitmap(String url) {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.connect();

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                InputStream is = conn.getInputStream();// 获取输入流

                // 图片压缩处理
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 2;//宽高都压缩为原来的二分之一, 此参数需要根据图片要展示的大小来确定
                options.inPreferredConfig = Bitmap.Config.RGB_565;// 设置图片格式

                Bitmap bitmap = BitmapFactory.decodeStream(is);
                return bitmap;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return null;
    }
}

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
 * time：2017/4/10 0:47
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

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 网络缓存工具
 */
public class N {
    public N() {
    }

    /**
     * 从网络获取图片
     *
     * @param url
     * @param ivPic
     */
    public void getBitmapFromNet(String url, ImageView ivPic) {
        new BitmapTask().execute(ivPic, url);
    }

    private class BitmapTask extends AsyncTask<Object, Void, Bitmap> {

        private ImageView mIvPic;
        private String    mUrl;

        @Override
        protected Bitmap doInBackground(Object... params) {
            mIvPic = (ImageView) params[1];
            mUrl = (String) params[0];
            return downloadBitmap(mUrl);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

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
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.connect();

            if (connection.getResponseCode() == 200) {
                InputStream inputStream = connection.getInputStream();
                // 图上压缩
                BitmapFactory.Options options = new BitmapFactory.Options();
                // true可以在解码的时候避免内存的分配,它会返回一个null的Bitmap，但是可以获取到 outWidth, outHeight 与
                // outMimeType。该技术可以允许你在构造Bitmap之前优先读图片的尺寸与类型。
                options.inJustDecodeBounds = true;
                options.inSampleSize = 2;
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();// 记得关闭连接
        }
        return null;
    }
}

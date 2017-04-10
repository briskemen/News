package com.hello.newsdemo.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.Toast;

import com.google.vr.sdk.widgets.common.VrWidgetView;
import com.google.vr.sdk.widgets.pano.VrPanoramaEventListener;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;
import com.hello.zhbj52.R;

import java.io.IOException;
import java.io.InputStream;

/**
 * ============================================================
 * Copyright：${TODO}有限公司版权所有 (c) 2017
 * Author：   卢俊霖
 * Email：    briskemen@163.com
 * GitHub：   https://github.com/briskemen
 * Project_Name：News
 * Package_Name：com.hello.newsdemo.activity
 * Version：1.0
 * time：2017/4/9 19:58
 * des ：${TODO}
 * gitVersion：$Rev$
 * updateAuthor：$Author$
 * updateDate：$Date$
 * updateDes：${TODO}
 * ============================================================
 **/
public class VRActivity extends AppCompatActivity {
    private VrPanoramaView vrPanoramaView;
    private ImageTask      imageTask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);


    }

    private void initView() {
        setContentView(R.layout.activity_vr);
        vrPanoramaView = (VrPanoramaView) findViewById(R.id.vr_pv);
        //2.2.设置初始化参数
        vrPanoramaView.setDisplayMode(VrWidgetView.DisplayMode.FULLSCREEN_STEREO);
        //删除不需要连接，信息图标
        vrPanoramaView.setInfoButtonEnabled(false);
        //隐藏全屏按钮
        vrPanoramaView.setFullscreenButtonEnabled(false);
        //2.3.创建异步任务加载图片 Bitmap是图片在内存中的表示对象，全景图也可加载成bitmap
        imageTask = new ImageTask();
        imageTask.execute();
    }

    private class ImageTask extends AsyncTask<Void, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(Void... params) {
            //2.4.从资产目录打开一个流
            try {
                InputStream inputStream = getAssets().open("andes.jpg");
                //2.5.使用BitmapFactory转换成Bitmap
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        //2.6任务执行完后,可获取Bitmap图片
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap != null) {
                //loadImageFromBitmap加载bitmap到显示控件 参1.bitmap 参2 显示参数的封装
                VrPanoramaView.Options options = new VrPanoramaView.Options();
                //加载立体图片，上部分显示在左眼，下部分显示在右眼
                options.inputType = VrPanoramaView.Options.TYPE_STEREO_OVER_UNDER;
                if (listener == null) {
                    listener = new VrPanoramaEventListener() {
                        @Override
                        public void onLoadError(String errorMessage) {
                            super.onLoadError(errorMessage);
                            //处理加载失败的情况
                            Toast.makeText(VRActivity.this, "错误消息:" + errorMessage,
                                    Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onLoadSuccess() {
                            super.onLoadSuccess();
                            //成功的情况提示下现在要进行全景图片的展示
                            Toast.makeText(VRActivity.this, "进入vr:", Toast.LENGTH_SHORT).show();
                        }
                    };
                    // 增加加载出错的业务逻辑处理
                    vrPanoramaView.setEventListener(listener);
                }
                //2.7.让控件加载bitmap对象
                vrPanoramaView.loadImageFromBitmap(bitmap, options);
                //2.8.如果loadImageFromBitmap加载失败需要提示用户相关信息则需要添加事件监听器listener
            }
        }
    }

    private VrPanoramaEventListener listener;

    //步骤三。VrPanoramaView控件退到后台，回到屏幕，销毁处理细节
    //3.1.退到后台.暂停显示
    @Override
    protected void onPause() {
        super.onPause();
        if (vrPanoramaView != null) {
            vrPanoramaView.pauseRendering();
        }
    }

    //3.2.回到屏幕,恢复显示
    @Override
    protected void onResume() {
        super.onResume();
        if (vrPanoramaView != null) {
            vrPanoramaView.resumeRendering();
        }
    }

    //3.3.退出界面停止显示
    @Override
    protected void onDestroy() {
        if (vrPanoramaView != null) {
            vrPanoramaView.shutdown();
        }
        if (imageTask != null && !imageTask.isCancelled()) {//销毁任务
            imageTask.cancel(true);
            imageTask = null;
        }
        super.onDestroy();
    }
}

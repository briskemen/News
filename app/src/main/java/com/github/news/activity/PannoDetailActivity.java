package com.github.news.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.github.news.R;
import com.github.news.utils.ToastUtils;
import com.google.vr.sdk.widgets.pano.VrPanoramaEventListener;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;

import butterknife.BindView;
import butterknife.ButterKnife;

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
 * Project_Name：News
 * Package_Name：com.github.news.activity
 * Version：1.0
 * time：2017/4/11 1:37
 * des ：${TODO}
 * gitVersion：2.12.0.windows.1
 * updateAuthor：$Author$
 * updateDate：$Date$
 * updateDes：${TODO}
 * ============================================================
 */
public class PannoDetailActivity extends AppCompatActivity {

    private static final String TAG = PannoDetailActivity.class.getSimpleName();

    @BindView(R.id.tv_title)
    public TextView tv_title;

    @BindView(R.id.tv_summary)
    public TextView tv_summary;

    @BindView(R.id.tv_time)
    public TextView tv_time;

    @BindView(R.id.tv_username)
    public TextView tv_username;

    @BindView(R.id.tv_toolbartitle)
    public TextView tv_toolbartitle;

    @BindView(R.id.toolbar)
    public Toolbar mToolbar;

    @BindView(R.id.vr_panorama)
    public VrPanoramaView mPanoramaView;

    private VrPanoramaView.Options panOptions = new VrPanoramaView.Options();

    public  boolean loadImageSuccessful;
    private String  url;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        setListener();
        loadBitmap();
    }

    private void handleIntent(Intent intent) {
        loadBitmap();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void initViews() {
        setContentView(R.layout.activity_panno);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // 设置初始化参数
        // mPanoramaView.setDisplayMode(VrWidgetView.DisplayMode.FULLSCREEN_STEREO);
        //删除不需要连接，信息图标
        mPanoramaView.setInfoButtonEnabled(false);
        //设置显示全屏按钮
        mPanoramaView.setFullscreenButtonEnabled(true);
    }

    @Override
    protected void onPause() {
        if (mPanoramaView != null) {
            mPanoramaView.pauseRendering();
        }
        Glide.with(this).pauseRequests();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPanoramaView != null) {
            mPanoramaView.resumeRendering();
        }
        Glide.with(this).resumeRequests();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPanoramaView != null) {
            mPanoramaView.shutdown();
        }
        Glide.clear(mTarget);
    }

    private void setListener() {
        mPanoramaView.setEventListener(new ActivityEventListener());
    }

    SimpleTarget<Bitmap> mTarget = new SimpleTarget<Bitmap>() {
        @Override
        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap>
                glideAnimation) {
            if (resource != null) {
                // VrPanoramaView.Options options = new VrPanoramaView.Options();
                //加载立体图片，上部分显示在左眼，下部分显示在右眼
                // options.inputType = VrPanoramaView.Options.TYPE_STEREO_OVER_UNDER;
                // options.inputType = VrPanoramaView.Options.TYPE_MONO;
                panOptions.inputType = VrPanoramaView.Options.TYPE_MONO;
                mPanoramaView.loadImageFromBitmap(resource, panOptions);
            }
        }
    };

    private void loadBitmap() {
        Intent intent = getIntent();
        tv_summary.setText(intent.getStringExtra("summary"));
        tv_time.setText(intent.getStringExtra("time"));
        tv_title.setText(intent.getStringExtra("title"));
        tv_toolbartitle.setText(intent.getStringExtra("title"));
        tv_username.setText(intent.getStringExtra("name"));
        url = intent.getStringExtra("imgurl");
        Glide.with(this).load(url).asBitmap().into(mTarget);
    }

    private class ActivityEventListener extends VrPanoramaEventListener {

        @Override
        public void onLoadSuccess() {
            loadImageSuccessful = true;
        }

        @Override
        public void onLoadError(String errorMessage) {
            loadImageSuccessful = false;
            ToastUtils.showToast(PannoDetailActivity.this, "Error loading panorama: " +
                    errorMessage);
        }
    }
}

package com.hello.newsdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.vr.sdk.widgets.pano.VrPanoramaView;
import com.hello.zhbj52.R;

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
 * Package_Name：com.hello.newsdemo.activity
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

    public VrPanoramaView mPanoramaView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panno);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        Intent intent = getIntent();

        tv_summary.setText(intent.getStringExtra("summary"));
        tv_time.setText(intent.getStringExtra("time"));
        tv_title.setText(intent.getStringExtra("title"));
        tv_toolbartitle.setText(intent.getStringExtra("title"));
        tv_username.setText(intent.getStringExtra("username"));
    }
}

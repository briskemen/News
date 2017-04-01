package com.hello.newsdemo.base.impl;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.TextView;

import com.hello.newsdemo.base.BasePager;

/**
 * 话题页面
 */
public class TopicPager extends BasePager {

    public TopicPager(AppCompatActivity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        TextView text = new TextView(mActivity);
        text.setText("美女");
        text.setTextColor(Color.RED);
        text.setTextSize(25);
        text.setGravity(Gravity.CENTER);

        // 向FrameLayout中动态添加布局
        flContent.addView(text);
    }
}

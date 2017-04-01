package com.hello.newsdemo.base.impl;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.TextView;

import com.hello.newsdemo.base.BasePager;

/**
 * 首页实现
 */
public class RecommendPager extends BasePager {

    public RecommendPager(AppCompatActivity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        TextView text = new TextView(mActivity);
        text.setText("推荐");
        text.setTextColor(Color.RED);
        text.setTextSize(25);
        text.setGravity(Gravity.CENTER);

        // 向FrameLayout中动态添加布局
        flContent.addView(text);
    }
}

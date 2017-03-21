package com.itheima.newsdemo.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.itheima.newsdemo.base.BasePager;

/**
 * 话题页面
 */
public class TopicPager extends BasePager {

    public TopicPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        tvTitle.setText("话题");

        TextView text = new TextView(mActivity);
        text.setText("话题");
        text.setTextColor(Color.RED);
        text.setTextSize(25);
        text.setGravity(Gravity.CENTER);

        // 向FrameLayout中动态添加布局
        flContent.addView(text);
    }

}

package com.itheima.newsdemo.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.itheima.newsdemo.base.BasePager;

/**
 * 首页实现
 */
public class ZBPager extends BasePager {

    public ZBPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {

        tvTitle.setText("直播");// 修改标题

        TextView text = new TextView(mActivity);
        text.setText("直播");
        text.setTextColor(Color.RED);
        text.setTextSize(25);
        text.setGravity(Gravity.CENTER);

        // 向FrameLayout中动态添加布局
        flContent.addView(text);
    }

}

package com.itheima.newsdemo.base;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.hello.zhbj52.R;

/**
 * 主页下5个子页面的基类
 */
public class BasePager {

    public Activity mActivity;
    public View     mRootView;// 布局对象

    public TextView tvTitle;// 标题对象

    public FrameLayout flContent;// 内容


    public BasePager(Activity activity) {
        mActivity = activity;
        initViews();
    }

    /**
     * 初始化布局
     */
    public void initViews() {
        mRootView = View.inflate(mActivity, R.layout.base_pager, null);

        tvTitle = (TextView) mRootView.findViewById(R.id.tv_title);
        flContent = (FrameLayout) mRootView.findViewById(R.id.fl_content);
    }


    /**
     * 初始化数据
     */
    public void initData() {
    }
}

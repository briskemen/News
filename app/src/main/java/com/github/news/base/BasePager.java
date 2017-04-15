package com.github.news.base;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.github.news.R;


/**
 * 主页下4个子页面的基类
 */
public class BasePager {
    public AppCompatActivity mActivity;
    public View              mRootView;// 布局对象
    public FrameLayout       flContent;// 内容
    public boolean isLoading = false; // 取消重复加载数据

    public BasePager(AppCompatActivity activity) {
        mActivity = activity;
        initViews();
    }

    /**
     * 初始化布局
     */
    public void initViews() {
        mRootView = View.inflate(mActivity, R.layout.base_pager, null);
        flContent = (FrameLayout) mRootView.findViewById(R.id.fl_content);
    }

    /**
     * 初始化数据
     */
    public void initData() {

    }
}

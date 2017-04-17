package com.github.news.base;

import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.github.news.R;


/**
 * 主页下4个子页面的基类
 */
public class BasePager {
    protected AppCompatActivity mActivity;
    protected View              mRootView;// 布局对象
    protected FrameLayout       flContent;// 内容
    protected LayoutInflater mInflater;
    protected boolean isLoading = false; // 取消重复加载数据

    public BasePager(AppCompatActivity activity) {
        mActivity = activity;
        mInflater = LayoutInflater.from(mActivity);
        initViews();
    }

    /**
     * 初始化布局
     */
    public void initViews() {
        mRootView = mInflater.inflate(R.layout.base_pager,null,false);
        flContent = (FrameLayout) mRootView.findViewById(R.id.fl_content);
    }

    /**
     * 初始化数据
     */
    public void initData() {

    }
}

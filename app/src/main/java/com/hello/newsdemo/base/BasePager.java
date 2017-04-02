package com.hello.newsdemo.base;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.hello.zhbj52.R;

/**
 * 主页下4个子页面的基类
 */
public class BasePager {
    public  AppCompatActivity     mActivity;
    public  View                  mRootView;// 布局对象
    public  FrameLayout           flContent;// 内容
    //private FloatingActionButton  mFab;
    //private SwipeRefreshLayout    mSwipeRefreshLayout;

    public BasePager(AppCompatActivity activity) {
        mActivity = activity;
        initViews();
    }

    /**
     * 初始化布局
     */
    public void initViews() {
        mRootView = View.inflate(mActivity, R.layout.base_pager, null);
        // tvTitle = (TextView) mRootView.findViewById(R.id.tv_title);
        flContent = (FrameLayout) mRootView.findViewById(R.id.fl_content);

        //mFab = (FloatingActionButton)  mActivity.findViewById(R.id.fab);

       /* mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
    }

    /**
     * 初始化数据
     */
    public void initData() {

    }
}

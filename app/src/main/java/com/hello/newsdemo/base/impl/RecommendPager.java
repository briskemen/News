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

    /*private ArrayList<BaseMenuDetailPager> mPagers;// 4个菜单详情页的集合

    private int    index       = 0;
    private String mTopNewsUrl = GlobalUrl.getTopNewsUrl(index);

    public RecommendPager(AppCompatActivity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        String cache = CacheUtils.getCache(mTopNewsUrl, mActivity);
        if (!TextUtils.isEmpty(cache)) {// 如果缓存存在,直接解析数据, 无需访问网路
            parseData(cache);
        }

        getDataFromServer();// 不管有没有缓存, 都获取最新数据, 保证数据最新
    }

    *//**
     * 从服务器获取数据
     *//*
    private void getDataFromServer() {

        HttpUtils.get(mActivity.getApplicationContext(), mTopNewsUrl, new Callback() {
            @Override
            public void onResponse(String response) {
                parseData(response);
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                ToastUtils.showToast(mActivity, "网络出错，请稍后再试");
            }
        });

    }

    *//**
     * 解析网络数据
     *
     * @param result
     *//*
    protected void parseData(String result) {
        Gson gson = new Gson();
        RecommendNewsData topNewsData = gson.fromJson(result, RecommendNewsData.class);

        mPagers = new ArrayList<>();
        mPagers.add(new RecommendMenuDetailPager(mActivity, topNewsData.T1348647909107));
        setCurrentMenuDetailPager(1);
    }

    *//**
     * 设置当前菜单详情页
     *//*
    public void setCurrentMenuDetailPager(int position) {
        BaseMenuDetailPager pager = mPagers.get(position);// 获取当前要显示的菜单详情页
        flContent.removeAllViews();// 清除之前的布局
        flContent.addView(pager.mRootView);// 将菜单详情页的布局设置给帧布局
        pager.initData();// 初始化当前页面的数据
    }*/
}

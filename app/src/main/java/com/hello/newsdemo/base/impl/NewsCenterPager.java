package com.hello.newsdemo.base.impl;

import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.hello.newsdemo.base.BaseMenuDetailPager;
import com.hello.newsdemo.base.BasePager;
import com.hello.newsdemo.base.menudetail.NewsMenuDetailPager;
import com.hello.newsdemo.global.GlobalUrl;
import com.hello.newsdemo.http.Callback;
import com.hello.newsdemo.http.HttpUtils;
import com.hello.newsdemo.json.WYTabListJson;
import com.hello.newsdemo.utils.CacheUtils;
import com.hello.newsdemo.utils.ToastUtils;

import java.util.ArrayList;

/**
 * 新闻中心
 */
public class NewsCenterPager extends BasePager {
    private ArrayList<BaseMenuDetailPager> mPagers;// 4个菜单详情页的集合

    public NewsCenterPager(AppCompatActivity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        String cache = CacheUtils.getCache(GlobalUrl.tListUrl, mActivity);
        if (!TextUtils.isEmpty(cache)) {// 如果缓存存在,直接解析数据, 无需访问网路
            parseData(cache);
        }

        getDataFromServer();// 不管有没有缓存, 都获取最新数据, 保证数据最新
    }

    /**
     * 从服务器获取数据
     */
    private void getDataFromServer() {

        HttpUtils.get(mActivity.getApplicationContext(), GlobalUrl.tListUrl, new Callback() {
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

    /**
     * 解析网络数据
     *
     * @param result
     */
    protected void parseData(String result) {
        Gson gson = new Gson();
        WYTabListJson mWYNewsData = gson.fromJson(result, WYTabListJson.class);

        mPagers = new ArrayList<>();
        mPagers.add(new NewsMenuDetailPager(mActivity, mWYNewsData.tList));
        setCurrentMenuDetailPager(0);
    }

    /**
     * 设置当前菜单详情页
     */
    public void setCurrentMenuDetailPager(int position) {
        BaseMenuDetailPager pager = mPagers.get(position);// 获取当前要显示的菜单详情页
        flContent.removeAllViews();// 清除之前的布局
        flContent.addView(pager.mRootView);// 将菜单详情页的布局设置给帧布局
        pager.initData();// 初始化当前页面的数据
    }
}

package com.itheima.newsdemo.base.impl;

import android.app.Activity;
import android.widget.Toast;

import com.google.gson.Gson;
import com.itheima.newsdemo.base.BaseMenuDetailPager;
import com.itheima.newsdemo.base.BasePager;
import com.itheima.newsdemo.base.menudetail.NewsMenuDetailPager;
import com.itheima.newsdemo.domain.WYTabListData;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;

/**
 * 新闻中心
 */
public class NewsCenterPager extends BasePager {

    // private WYNewsData mWYNewsData;// 网易新闻头条数据

    public NewsCenterPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        tvTitle.setText("新闻");

        // http://192.168.0.100:8080/zhbj/categories.json
       /* String cache = CacheUtils.getCache(GlobalContants.CATEGORIES_URL,
                mActivity);

        if (!TextUtils.isEmpty(cache)) {// 如果缓存存在,直接解析数据, 无需访问网路
            parseData(cache);
        }*/

        getDataFromServer();// 不管有没有缓存, 都获取最新数据, 保证数据最新
    }

    /**
     * 从服务器获取数据
     */
    private void getDataFromServer() {
        HttpUtils utils = new HttpUtils();

        // 使用xutils发送请求
        utils.send(HttpRequest.HttpMethod.GET, "http://c.m.163" +
                ".com/nc/topicset/android/subscribe/manage/listspecial.html",
                new RequestCallBack<String>() {

                    // 访问成功, 在主线程运行
                    @Override
                    public void onSuccess(ResponseInfo responseInfo) {
                        String result = (String) responseInfo.result;
                        // System.out.println("返回结果:" + result);

                        parseData(result);
                    }

                    // 访问失败, 在主线程运行
                    @Override
                    public void onFailure(HttpException error, String msg) {
                        error.printStackTrace();
                        Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT)
                                .show();
                    }

                });
    }

    private ArrayList<BaseMenuDetailPager> mPagers;// 4个菜单详情页的集合

    /**
     * 解析网络数据
     *
     * @param result
     */
    protected void parseData(String result) {
        Gson gson = new Gson();
        WYTabListData mWYNewsData = gson.fromJson(result, WYTabListData.class);
        //System.out.println("解析结果:" + mWYNewsData);

        mPagers = new ArrayList<>();
        //ArrayList<WYTabListData.TListEntity> tList
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

        // 设置当前页的标题
        //WYNewsData.TopNews menuData = mWYNewsData.T1348647909107.get(position);
        tvTitle.setText("新闻");

        pager.initData();// 初始化当前页面的数据
    }
}

package com.itheima.newsdemo.base.impl;

import android.app.Activity;
import android.widget.Toast;

import com.google.gson.Gson;
import com.itheima.newsdemo.MainActivity;
import com.itheima.newsdemo.base.BaseMenuDetailPager;
import com.itheima.newsdemo.base.BasePager;
import com.itheima.newsdemo.domain.NewsData;
import com.itheima.newsdemo.domain.WYNewsData;
import com.itheima.newsdemo.global.GlobalContants;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;

/**
 * 新闻中心
 */
public class WYNewsPager extends BasePager {

    private ArrayList<BaseMenuDetailPager> mPagers;// 4个菜单详情页的集合
    private NewsData                       mNewsData;
    private WYNewsData                     mWYNewsData;// 网易新闻头条数据

    public WYNewsPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        tvTitle.setText("新闻");
        getDataFromServer();
    }

    /**
     * 从服务器获取数据
     */
    private void getDataFromServer() {
        HttpUtils utils = new HttpUtils();

        // 使用xutils发送请求
        utils.send(HttpRequest.HttpMethod.GET, GlobalContants.CATEGORIES_URL,
                new RequestCallBack<String>() {

                    // 访问成功, 在主线程运行
                    @Override
                    public void onSuccess(ResponseInfo responseInfo) {
                        String result = (String) responseInfo.result;
                        System.out.println("返回结果:" + result);

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

    /**
     * 解析网络数据
     *
     * @param result
     */
    protected void parseData(String result) {
        Gson gson = new Gson();
       mNewsData = gson.fromJson(result, NewsData.class);

        mWYNewsData = gson.fromJson(result, WYNewsData.class);
        System.out.println("解析结果:" + mWYNewsData);
        System.out.println("解析结果:" + mNewsData);

        // 刷新测边栏的数据
        MainActivity mainUi = (MainActivity) mActivity;

        // 准备4个菜单详情页
        mPagers = new ArrayList();
       // mPagers.add(new NewsMenuDetailPager(mActivity, mNewsData.data.get(0).children));

       // mPagers.add(new NewsMenuDetailPager(mActivity, mWYNewsData.T1348647909107));

        setCurrentMenuDetailPager(0);// 设置菜单详情页-新闻为默认当前页
    }

    /**
     * 设置当前菜单详情页
     */
    public void setCurrentMenuDetailPager(int position) {
        BaseMenuDetailPager pager = mPagers.get(position);// 获取当前要显示的菜单详情页
        flContent.removeAllViews();// 清除之前的布局
        flContent.addView(pager.mRootView);// 将菜单详情页的布局设置给帧布局

        // 设置当前页的标题
        NewsData.NewsMenuData menuData = mNewsData.data.get(position);
        tvTitle.setText(menuData.title);

        pager.initData();// 初始化当前页面的数据
    }
}

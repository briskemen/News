package com.hello.newsdemo.base.impl;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;

import com.hello.newsdemo.base.BasePager;
import com.hello.newsdemo.fragment.VRFragment;
import com.hello.zhbj52.R;

/**
 * 首页实现
 */
public class VRPager extends BasePager {

    private ViewPager vp;
    private TabLayout tabLayout;
    private FragmentAdapter mFragmentAdapter;
    private static final  String[] titles = new String[]{"精品推荐","舞台世界","全景高校","时空转移","空中全景","观光旅游"};

    public VRPager(AppCompatActivity activity) {
        super(activity);
    }

    @Override
    public void initViews() {
        mRootView = LayoutInflater.from(mActivity).inflate(R.layout.layout_vr, null, false);
        vp = (ViewPager) mRootView.findViewById(R.id.vp);
        tabLayout = (TabLayout) mRootView.findViewById(R.id.tab_layout);
        mFragmentAdapter = new FragmentAdapter(mActivity.getSupportFragmentManager());
        vp.setAdapter(mFragmentAdapter);
        tabLayout.setupWithViewPager(vp);

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // ((VRFragment)mFragmentAdapter.getItem(position)).initData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void initData() {
        //Log.e("tag","initData");
        //((VRFragment)mFragmentAdapter.getItem(0)).initData();
    }

    private class FragmentAdapter extends FragmentStatePagerAdapter {

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Log.e("tag","创建fragment："+position);
            VRFragment fragment = new VRFragment();
            fragment.setCategory(titles[position]);
            return fragment;
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

    /*private ArrayList<BaseMenuDetailPager> mPagers;// 4个菜单详情页的集合

    private int    index       = 0;
    private String mTopNewsUrl = GlobalUrl.getTopNewsUrl(index);

    public VRPager(AppCompatActivity activity) {
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

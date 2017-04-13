package com.github.news;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hello.zhbj52.R;

import java.util.ArrayList;
import java.util.List;

public class TabLayoutActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private Toolbar   mToolbar;

    private LayoutInflater mInflater;

    private List<String> mTitleList = new ArrayList<>();//页卡标题集合
    private View view1, view2;//页卡视图
    private List<View> mViewList = new ArrayList<>();//页卡视图集合
    private MyTabPagerAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout);
        initView();
        initData();
        initListener();
    }

    private void initListener() {
        mViewPager.addOnPageChangeListener(this);
    }

    private void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        mToolbar.setTitle("TabLayout");
    }

    private void initData() {
        mInflater = LayoutInflater.from(this);
        view1 = mInflater.inflate(R.layout.tab_pager, null);
        view2 = mInflater.inflate(R.layout.tab_pager, null);

        // 添加页卡视图
        mViewList.add(view1);
        mViewList.add(view2);
        // 设置页卡title
        mTitleList.add("图片");
        mTitleList.add("视频");
        // 设置tab模式
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);// 默认模式
        // 添加tab选项卡
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(1)));

        mAdapter = new MyTabPagerAdapter(mViewList);
        mViewPager.setAdapter(mAdapter);
        // 将tabLayout与viewPager关联
        mTabLayout.setupWithViewPager(mViewPager);
        // 给Tab设置适配器
        //mTabLayout.setTabsFromPagerAdapter(mAdapter);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class MyTabPagerAdapter extends PagerAdapter {
        private List<View> mViewList;

        public MyTabPagerAdapter(List<View> viewList) {
            mViewList = viewList;
        }

        @Override
        public int getCount() {
            return mViewList.size();// 页卡数
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // 添加页卡
            container.addView(mViewList.get(position));
            return mViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // 删除页卡
            container.removeView(mViewList.get(position));
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position);// 页卡标题
        }
    }
}

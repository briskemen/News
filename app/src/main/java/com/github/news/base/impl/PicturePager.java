package com.github.news.base.impl;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

import com.github.news.R;
import com.github.news.base.BasePager;
import com.github.news.factory.PicFragmentFactory;


public class PicturePager extends BasePager {

    private ViewPager  vp;
    private TabLayout  tabLayout;
    private PicAdapter mPicAdapter;

    private static final String[] titles = new String[]{"小清新", "美女", "新闻"};


    public PicturePager(AppCompatActivity activity) {
        super(activity);
    }

    @Override
    public void initViews() {
        mRootView = LayoutInflater.from(mActivity).inflate(R.layout.layout_pic, null, false);
        vp = (ViewPager) mRootView.findViewById(R.id.viewpager_pic);
        tabLayout = (TabLayout) mRootView.findViewById(R.id.tab_pic);
        mPicAdapter = new PicAdapter(mActivity.getSupportFragmentManager());
        vp.setAdapter(mPicAdapter);
        tabLayout.setupWithViewPager(vp);
        setListener();
    }

    private void setListener() {
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {

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

    }

    private class PicAdapter extends FragmentPagerAdapter {

        public PicAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PicFragmentFactory.getFragment(position);
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
}

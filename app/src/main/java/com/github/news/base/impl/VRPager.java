package com.github.news.base.impl;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

import com.github.news.base.BasePager;
import com.github.news.fragment.VRMFragment;
import com.github.news.R;

/**
 * 首页实现
 */
public class VRPager extends BasePager {

    private ViewPager       vp;
    private TabLayout       tabLayout;
    private FragmentAdapter mFragmentAdapter;

    private static final String[] titles = new String[]{"精品推荐", "舞台世界", "全景高校", "时空转移", "空中全景",
            "观光旅游"};

    public VRPager(AppCompatActivity activity) {
        super(activity);
    }

    @Override
    public void initViews() {
        mRootView = LayoutInflater.from(mActivity).inflate(R.layout.layout_vr, null, false);
        tabLayout = (TabLayout) mRootView.findViewById(R.id.tab_vr);
        vp = (ViewPager) mRootView.findViewById(R.id.viewpager_vr);
        mFragmentAdapter = new FragmentAdapter(mActivity.getSupportFragmentManager());
        vp.setAdapter(mFragmentAdapter);
        tabLayout.setupWithViewPager(vp);

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
        //((VRFragment)mFragmentAdapter.getItem(0)).initData();
    }

    private class FragmentAdapter extends FragmentStatePagerAdapter {

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            VRMFragment fragment = new VRMFragment();
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
}

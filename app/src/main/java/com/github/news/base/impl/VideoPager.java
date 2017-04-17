package com.github.news.base.impl;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.github.news.R;
import com.github.news.base.BasePager;
import com.github.news.factory.VideoFragmentFactory;

/**
 * 设置页面
 * 
 */
public class VideoPager extends BasePager implements ViewPager.OnPageChangeListener{

	private TabLayout       mTabLayout;
	private ViewPager       mViewPager;
	private FragmentAdapter mAdapter;
	private String[]        mTabText;

	public VideoPager(AppCompatActivity activity) {
		super(activity);
	}

	@Override
	public void initViews() {
		mRootView = mInflater.inflate(R.layout.layout_video, null, false);
		mTabLayout = (TabLayout) mRootView.findViewById(R.id.tab_video);
		mViewPager = (ViewPager) mRootView.findViewById(R.id.vp_video);
		mViewPager.addOnPageChangeListener(this);
		mAdapter = new FragmentAdapter(mActivity.getSupportFragmentManager());
		mTabText = getStringArr(R.array.video);
		mViewPager.setAdapter(mAdapter);
		mTabLayout.setupWithViewPager(mViewPager);
	}

	public String[] getStringArr(int resId) {
		return mActivity.getResources().getStringArray(resId);
	}

	@Override
	public void initData() {

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

	private class FragmentAdapter extends FragmentPagerAdapter {

		public FragmentAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return VideoFragmentFactory.getFragment(position);
		}

		@Override
		public int getCount() {
			return mTabText.length;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return mTabText[position];
		}
	}

}

package com.hello.newsdemo.base.impl;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

import com.hello.newsdemo.activity.MainActivity;
import com.hello.newsdemo.base.BasePager;
import com.hello.newsdemo.factory.VideoFragmentFactory;
import com.hello.zhbj52.R;

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
		//super.initViews();
		//mActivity.getWindow().setStatusBarColor(Color.parseColor("#ffffff"));
		mRootView = LayoutInflater.from(mActivity).inflate(R.layout.layout_video, null, false);
		mTabLayout = (TabLayout) mRootView.findViewById(R.id.tab_video);
		mViewPager = (ViewPager) mRootView.findViewById(R.id.vp_video);
		mViewPager.addOnPageChangeListener(this);
		MainActivity mainActivity = (MainActivity) mActivity;
		// ContentFragment contentFragment = mainActivity.getContentFragment();
		mAdapter = new FragmentAdapter(mActivity.getSupportFragmentManager());
		mViewPager.setOffscreenPageLimit(4);

		mTabText = getStringArr(R.array.video);
		mViewPager.setAdapter(mAdapter);
		mTabLayout.setupWithViewPager(mViewPager);
		//flContent.addView(videoView);
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

	private class FragmentAdapter extends FragmentStatePagerAdapter {

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

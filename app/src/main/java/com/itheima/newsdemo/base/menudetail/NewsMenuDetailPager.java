package com.itheima.newsdemo.base.menudetail;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;

import com.hello.zhbj52.R;
import com.itheima.newsdemo.base.BaseMenuDetailPager;
import com.itheima.newsdemo.base.TabDetailPager;
import com.itheima.newsdemo.domain.WYNewsData;
import com.itheima.newsdemo.domain.WYTabListData;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单详情页-新闻
 */
public class NewsMenuDetailPager extends BaseMenuDetailPager implements
        OnPageChangeListener {

    private ViewPager mViewPager;

    private ArrayList<TabDetailPager> mPagerList;

    private ArrayList<WYNewsData.AdsData> mWYNewsData;// 页签网络数据

    private TabPageIndicator mIndicator;

    List<WYNewsData.TopNews> mData;

    // 头条边栏
    public List<WYTabListData.TListEntity> mTListEntity;

    public NewsMenuDetailPager(Activity activity, List<WYTabListData.TListEntity> tList) {
        super(activity);
        mTListEntity = tList;
    }

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.news_menu_detail, null);
        mViewPager = (ViewPager) view.findViewById(R.id.vp_menu_detail);

        ViewUtils.inject(this, view);

        mIndicator = (TabPageIndicator) view.findViewById(R.id.indicator);

        // mViewPager.setOnPageChangeListener(this);注意:当viewpager和Indicator绑定时,
        // 滑动监听需要设置给Indicator而不是viewpager
        mIndicator.setOnPageChangeListener(this);

        return view;
    }

    @Override
    public void initData() {
        mPagerList = new ArrayList();

        // 初始化页签数据
        for (int i = 0; i < mTListEntity.size(); i++) {
            TabDetailPager pager = new TabDetailPager(mActivity, mTListEntity.get(i));
            mPagerList.add(pager);
        }

        // TabDetailPager pager = new TabDetailPager(mActivity,);
        // mPagerList.add(pager);

        mViewPager.setAdapter(new MenuDetailAdapter());
        mIndicator.setViewPager(mViewPager);// 将viewpager和mIndicator关联起来,必须在viewpager设置完adapter后才能调用
    }

    // 跳转下一个页面
    @OnClick(R.id.btn_next)
    public void nextPage(View view) {
        int currentItem = mViewPager.getCurrentItem();
        mViewPager.setCurrentItem(++currentItem);
    }

    class MenuDetailAdapter extends PagerAdapter {

        /**
         * 重写此方法,返回页面标题,用于viewpagerIndicator的页签显示
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return mTListEntity.get(position).tname;
        }

        @Override
        public int getCount() {
            return mPagerList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TabDetailPager pager = mPagerList.get(position);
            container.addView(pager.mRootView);
            pager.initData();
            return pager.mRootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int arg0) {
    }

}

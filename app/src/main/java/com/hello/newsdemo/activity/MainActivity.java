package com.hello.newsdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.hello.newsdemo.base.BasePager;
import com.hello.newsdemo.base.impl.NewsCenterPager;
import com.hello.newsdemo.base.impl.RecommendPager;
import com.hello.newsdemo.base.impl.SettingPager;
import com.hello.newsdemo.base.impl.TopicPager;
import com.hello.zhbj52.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页面
 */
public class MainActivity extends AppCompatActivity {

    @ViewInject(R.id.rg_group)
    private RadioGroup rgGroup;

    @ViewInject(R.id.vp_content)
    private ViewPager mViewPager;

    private List<BasePager> mPagerList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this); // 注入view和事件
        initData();
    }

    public void initData() {
        rgGroup.check(R.id.rb_news);// 默认勾选首页

        // 初始化4个子页面
        mPagerList = new ArrayList();
        mPagerList.add(new NewsCenterPager(this));
        mPagerList.add(new RecommendPager(this));
        mPagerList.add(new TopicPager(this));
        mPagerList.add(new SettingPager(this));

        mViewPager.setAdapter(new ContentAdapter());

        // 监听RadioGroup的选择事件
        rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_news:
                        mViewPager.setCurrentItem(0);// 设置当前页面
                        mViewPager.setCurrentItem(0, false);// 去掉切换页面的动画
                        break;
                    case R.id.rb_recommend:
                         mViewPager.setCurrentItem(1, false);// 设置当前页面
                        break;
                    case R.id.rb_women:
                        //mViewPager.setCurrentItem(2, false);// 设置当前页面
                        Intent intent = new Intent(MainActivity.this, PicActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.rb_setting:
                        mViewPager.setCurrentItem(4, false);// 设置当前页面
                        break;

                    default:
                        break;
                }
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                mPagerList.get(arg0).initData();// 获取当前被选中的页面, 初始化该页面数据
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });

        mPagerList.get(0).initData();// 初始化首页数据
    }

    class ContentAdapter extends PagerAdapter {

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
            BasePager pager = mPagerList.get(position);
            container.addView(pager.mRootView);
            // pager.initData();// 初始化数据.... 不要放在此处初始化数据, 否则会预加载下一个页面
            return pager.mRootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
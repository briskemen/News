package com.hello.newsdemo.base;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.hello.newsdemo.utils.ToastUtils;
import com.hello.zhbj52.R;

/**
 * 主页下4个子页面的基类
 */
public class BasePager {
    public  AppCompatActivity     mActivity;
    public  View                  mRootView;// 布局对象
    private DrawerLayout          mDrawerLayout;
    private NavigationView        mNavigationMenu;
    private ActionBarDrawerToggle mToggle;
    private Toolbar               mToolbar;
    public  FrameLayout           flContent;// 内容
    //private FloatingActionButton  mFab;
    //private SwipeRefreshLayout    mSwipeRefreshLayout;

    public BasePager(AppCompatActivity activity) {
        mActivity = activity;
        initViews();
    }

    /**
     * 初始化布局
     */
    public void initViews() {
        mRootView = View.inflate(mActivity, R.layout.base_pager, null);
        // tvTitle = (TextView) mRootView.findViewById(R.id.tv_title);
        flContent = (FrameLayout) mRootView.findViewById(R.id.fl_content);

        mToolbar = (Toolbar) mRootView.findViewById(R.id.toolbar);
        mToolbar.setTitle("news");
        mActivity.setSupportActionBar(mToolbar);
        mDrawerLayout = (DrawerLayout) mActivity.findViewById(R.id.drawer_layout);
        mNavigationMenu = (NavigationView) mActivity.findViewById(R.id.nav_view);
        //mFab = (FloatingActionButton)  mActivity.findViewById(R.id.fab);
        ActionBar actionBar = mActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);// 让导航按钮显示
            actionBar.setHomeAsUpIndicator(R.mipmap.img_menu);// 设置导航按钮图标
        }
        initActionBarToggle();

        mNavigationMenu.setCheckedItem(R.id.nav_news);// 将news设为默认选中
        mNavigationMenu.setNavigationItemSelectedListener(new NavigationView
                .OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_news:
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_recommend:
                        ToastUtils.showToast(mActivity, "推荐");
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_women:
                        ToastUtils.showToast(mActivity, "美女");
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_setting:
                        ToastUtils.showToast(mActivity, "设置");
                        mDrawerLayout.closeDrawers();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

       /* mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
    }

    private void initActionBarToggle() {
        mToggle = new ActionBarDrawerToggle(mActivity, mDrawerLayout, mToolbar, R.string.open, R
                .string.close);
        mToggle.syncState();// 同步状态的方法
        mDrawerLayout.addDrawerListener(mToggle); //设置mDrawerLayout拖动的监听
    }

    /**
     * 初始化数据
     */
    public void initData() {

    }
}

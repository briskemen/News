package com.github.news.base.menudetail;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.github.news.base.BaseMenuDetailPager;
import com.github.news.domain.RecommendNewsData;
import com.hello.zhbj52.R;

import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================
 * Copyright：${TODO}有限公司版权所有 (c) 2017
 * Author：   卢俊霖
 * Email：    briskemen@163.com
 * GitHub：   https://github.com/briskemen
 * Project_Name：News
 * Package_Name：com.github.news.base.menudetail
 * Version：1.0
 * time：2017/4/3 8:24
 * des ：${TODO}
 * gitVersion：$Rev$
 * updateAuthor：$Author$
 * updateDate：$Date$
 * updateDes：${TODO}
 * ============================================================
 **/
public class RecommendMenuDetailPager extends BaseMenuDetailPager {
    private ViewPager mViewPager;

    public List<RecommendNewsData.T1348647909107Entry> mTopNewsData;

    public RecommendMenuDetailPager(Activity activity, ArrayList<RecommendNewsData
            .T1348647909107Entry> T1348647909107) {
        super(activity);
        mTopNewsData = T1348647909107;
    }

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.recommend_menu_detail, null);
        mViewPager = (ViewPager) view.findViewById(R.id.vp_recommend__detail);
        return view;
    }

    @Override
    public void initData() {

    }

    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }
}

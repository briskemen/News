package com.github.news.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.news.activity.ImageActivity;
import com.github.news.adapter.recyclerview.CommonAdapter;
import com.github.news.adapter.recyclerview.base.ViewHolder;
import com.github.news.domain.BeautifulGirl;
import com.github.news.http.Callback;
import com.github.news.http.HttpUtils;
import com.github.news.http.RequestUrl;
import com.github.news.utils.BitmapUtils;
import com.github.news.utils.GsonUtil;
import com.github.news.utils.UIUtils;
import com.hello.zhbj52.R;

import java.util.ArrayList;

/**
 * ============================================================
 * Copyright：${TODO}有限公司版权所有 (c) 2017
 * Author：   AllenIverson
 * Email：    815712739@qq.com
 * GitHub：   https://github.com/JackChen1999
 * GitBook：  https://www.gitbook.com/@alleniverson
 * 博客：     http://blog.csdn.net/axi295309066
 * 微博：     AndroidDeveloper
 * <p>
 * Project_Name：News
 * Package_Name：com.github.news.fragment
 * Version：1.0
 * time：2017/4/12 12:32
 * des ：${TODO}
 * gitVersion：2.12.0.windows.1
 * updateAuthor：$Author$
 * updateDate：$Date$
 * updateDes：${TODO}
 * ============================================================
 */
public class PicGirlFragment extends Fragment {

    private Context              mContext;
    private LRecyclerView        mRecyclerView;
    private LRecyclerViewAdapter mRecyclerViewAdapter;
    private PicAdapter           mAdapter;
    private int                  offset;
    private int size = 20;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        return initViews(inflater, container);
    }

    private View initViews(LayoutInflater inflater, ViewGroup container) {
        View rootView = inflater.inflate(R.layout.fragment_beautifulgirl, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (LRecyclerView) view.findViewById(R.id.rv_beautifulgirl);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));
        mAdapter = new PicAdapter(mContext, R.layout.list_item_beautifulgirl);
        mRecyclerViewAdapter = new LRecyclerViewAdapter(mAdapter);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mRecyclerView.refresh();
        setListener();
    }

    private void setListener() {
        mRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.clear();
                offset = 0;
                requestData();
            }
        });

        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                offset += 20;
                requestData();
            }
        });

        mRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                enterImageScaleActivity(position);
            }
        });
    }

    private void enterImageScaleActivity(int position) {
        Intent intent = new Intent(mContext, ImageActivity.class);
        intent.putExtra("position", position);
        ArrayList<String> data = new ArrayList<>();
        for (int i = 0; i < mAdapter.getDatas().size(); i++) {
            data.add(mAdapter.getDatas().get(i).imgsrc);
        }
        intent.putStringArrayListExtra("imageUrls", data);
        startActivity(intent);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        requestData();
    }

    private void requestData() {
        HttpUtils.get(mContext, RequestUrl.getBeautifulGirl(size, offset), new Callback() {
            @Override
            public void onResponse(String response) {
                parseData(response);
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

    }

    private void parseData(String response) {
        BeautifulGirl girl = GsonUtil.changeGsonToBean(response, BeautifulGirl.class);
        mAdapter.addAll(girl.美女);
        mRecyclerView.refreshComplete(size);
    }

    private class PicAdapter extends CommonAdapter<BeautifulGirl.GirlEntity> {

        public PicAdapter(Context context, int layoutId) {
            super(context, layoutId);
        }

        @Override
        protected void convert(ViewHolder holder, BeautifulGirl.GirlEntity beauty, int position) {
            ImageView iv = holder.getView(R.id.iv_girl);

           /* if (position % 2 == 0) {
                iv.getLayoutParams().height = UIUtils.dip2px(mContext, 200);
            } else {
                iv.getLayoutParams().height = UIUtils.dip2px(mContext, 250);
            }*/

            int randomheight = UIUtils.dip2px(mContext, (int) (Math.random() * 100));
            int height = UIUtils.dip2px(mContext, 150) + randomheight;

            iv.getLayoutParams().height = height;
            BitmapUtils.display(mContext, iv, beauty.imgsrc);
            holder.setText(R.id.tv_title, beauty.title);
        }
    }
}

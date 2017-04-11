package com.hello.newsdemo.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.hello.newsdemo.activity.VideoPlayActivity;
import com.hello.newsdemo.adapter.recyclerview.CommonAdapter;
import com.hello.newsdemo.adapter.recyclerview.base.ViewHolder;
import com.hello.newsdemo.domain.VideoSelection;
import com.hello.newsdemo.http.Callback;
import com.hello.newsdemo.http.HttpUtils;
import com.hello.newsdemo.http.RequestUrl;
import com.hello.newsdemo.utils.GsonUtil;
import com.hello.zhbj52.R;

import java.util.List;

/**
 * ============================================================
 * Copyright：Google有限公司版权所有 (c) 2017
 * Author：   陈冠杰
 * Email：    815712739@qq.com
 * GitHub：   https://github.com/JackChen1999
 * 博客：     http://blog.csdn.net/axi295309066
 * 微博：     AndroidDeveloper
 * <p>
 * Project_Name：SmartCity
 * Package_Name：com.google.smartcity
 * Version：1.0
 * time：2016/2/16 10:06
 * des ：${TODO}
 * gitVersion：$Rev$
 * updateAuthor：$Author$
 * updateDate：$Date$
 * updateDes：${TODO}
 * ============================================================
 **/

public class VideoSelectionFragment extends Fragment {

    private LRecyclerView        mRecyclerView;
    private LRecyclerViewAdapter mRecyclerViewAdapter;
    private VideoAdapter         mAdapter;
    private int page = 1;
    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        return initView(inflater, container);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    public View initView(LayoutInflater inflater, ViewGroup container) {
        View rootView = inflater.inflate(R.layout.layout_video_hot, container, false);
        mRecyclerView = (LRecyclerView) rootView.findViewById(R.id.rv_funnyvideo);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new VideoAdapter(mContext, R.layout.list_item_video_selection);
        mRecyclerViewAdapter = new LRecyclerViewAdapter(mAdapter);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mRecyclerView.refresh();
        initListener();
        return rootView;
    }

    private void initListener() {
        mRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.clear();
                page = 1;
                initData();
            }
        });

        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                page++;
                initData();
            }
        });

        mRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                enterVideoActivity(mAdapter.getDatas().get(position));
            }
        });
    }

    private void initData() {
        requestData(RequestUrl.getVideoSelectionUrl(page));
    }

    public void requestData(String url) {

        HttpUtils.get(mContext, url, new Callback() {
            @Override
            public void onResponse(String response) {
                parseData(response);
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    private void parseData(String result) {
        List<VideoSelection> data = GsonUtil.changeGsonToList(result, VideoSelection.class);
        mAdapter.addAll(data.get(0).item);
        mRecyclerView.refreshComplete(data.get(0).item.size());
    }

    public void enterVideoActivity(VideoSelection.VideoData data) {
        Bundle bundle = new Bundle();
        bundle.putString("videoUrl", data.video_url.replace("https", "http"));
        bundle.putString("title", data.title);
        Intent intent = new Intent(mContext, VideoPlayActivity.class);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    private class VideoAdapter extends CommonAdapter<VideoSelection.VideoData> {

        public VideoAdapter(Context context, int layoutId) {
            super(context, layoutId);
        }

        @Override
        protected void convert(ViewHolder holder, VideoSelection.VideoData videoData, int
                position) {
            ImageView iv = holder.getView(R.id.iv_video);
            Glide.with(mContext).load(videoData.image).into(iv);
            holder.setText(R.id.tv_title, videoData.title);
        }
    }

}

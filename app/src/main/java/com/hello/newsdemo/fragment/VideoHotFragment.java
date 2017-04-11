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
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.hello.newsdemo.activity.VideoPlayActivity;
import com.hello.newsdemo.adapter.recyclerview.CommonAdapter;
import com.hello.newsdemo.adapter.recyclerview.base.ViewHolder;
import com.hello.newsdemo.domain.VideoNews;
import com.hello.newsdemo.http.Callback;
import com.hello.newsdemo.http.HttpUtils;
import com.hello.newsdemo.http.RequestUrl;
import com.hello.newsdemo.utils.BitmapUtils;
import com.hello.newsdemo.utils.GsonUtil;
import com.hello.zhbj52.R;

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

public class VideoHotFragment extends Fragment{

    private LRecyclerView mRecyclerView;
    private LRecyclerViewAdapter mRecyclerViewAdapter;
    private VideoAdapter  mAdapter;
    private int index        = 0;
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
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new VideoAdapter(mContext,R.layout.list_item_video);
        mRecyclerViewAdapter = new LRecyclerViewAdapter(mAdapter);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mRecyclerView.refresh();
        setListener();
        return rootView;
    }

    private void setListener() {
        mRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                enterVideoActivity(mAdapter.getDatas().get(position));
            }
        });

        mRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.clear();
                index = 0;
                initData();
            }
        });

        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                index += 20;
                initData();
            }
        });
    }

    private void initData() {

        HttpUtils.get(mContext, RequestUrl.getVideoUrl("Video_Scene", index), new Callback() {
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
        VideoNews data = GsonUtil.changeGsonToBean(result,VideoNews.class);
        mAdapter.addAll(data.视频);
        mRecyclerView.refreshComplete(20);
    }

    public void enterVideoActivity(VideoNews.VideoData data) {
        Bundle bundle = new Bundle();
        bundle.putString("videoUrl", data.mp4_url);
        bundle.putString("title", data.title);
        Intent intent = new Intent(mContext, VideoPlayActivity.class);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    private class VideoAdapter extends CommonAdapter<VideoNews.VideoData> {

        public VideoAdapter(Context context, int layoutId) {
            super(context, layoutId);
        }

        @Override
        protected void convert(ViewHolder holder, VideoNews.VideoData videoData, int position) {
            ImageView iv = holder.getView(R.id.iv_video);
            BitmapUtils.display(mContext,iv,videoData.cover);
            holder.setText(R.id.tv_title, videoData.title);
        }
    }

}

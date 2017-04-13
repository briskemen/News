package com.github.news.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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
import com.github.news.activity.VideoPlayActivity;
import com.github.news.adapter.recyclerview.CommonAdapter;
import com.github.news.adapter.recyclerview.base.ViewHolder;
import com.github.news.domain.VideoNews;
import com.github.news.http.Callback;
import com.github.news.http.HttpUtils;
import com.github.news.http.RequestUrl;
import com.github.news.utils.BitmapUtils;
import com.github.news.utils.GsonUtil;
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

public class VideoFunnyFragment extends Fragment {

    private LRecyclerView mRecyclerView;
    private VideoAdapter  mAdapter;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private String        url;
    private int offset;
    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mAdapter = new VideoAdapter(getActivity(), R.layout.list_item_videonews);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(mAdapter);
        mRecyclerView.setAdapter(mLRecyclerViewAdapter);
        mRecyclerView.refresh();
        initListener();
        return rootView;
    }

    private void initData() {

        HttpUtils.get(mContext, RequestUrl.getVideoUrl("Video_Funny", offset), new Callback() {
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
        VideoNews mData = GsonUtil.changeGsonToBean(result,VideoNews.class);
        mAdapter.addAll(mData.视频);
        mRecyclerView.refreshComplete(20);
    }

    public void initListener() {

        mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                enterVideoActivity(mAdapter.getDatas().get(position));
            }
        });

        mRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.clear();
                offset = 0;
                initData();
            }
        });

        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                offset += 20;
                initData();
            }
        });

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
            ImageView iv = holder.getView(R.id.iv_cover);
            ImageView iv_user_avatar = holder.getView(R.id.iv_user_avatar);
            BitmapUtils.display(mContext,iv,videoData.cover);
            BitmapUtils.display(mContext,iv_user_avatar,videoData.videoTopic.topic_icons);

            holder.setText(R.id.count, videoData.playCount + "播放");
            holder.setText(R.id.des, videoData.title);
            holder.setText(R.id.username, videoData.videoTopic.tname);
        }
    }

}

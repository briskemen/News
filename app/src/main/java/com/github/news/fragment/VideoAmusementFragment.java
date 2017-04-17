package com.github.news.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.news.R;
import com.github.news.activity.VideoPlayActivity;
import com.github.news.adapter.recyclerview.CommonAdapter;
import com.github.news.adapter.recyclerview.base.ViewHolder;
import com.github.news.domain.VideoNews;
import com.github.news.http.Callback;
import com.github.news.http.HttpUtils;
import com.github.news.http.RequestUrl;
import com.github.news.utils.BitmapUtils;
import com.github.news.utils.GsonUtil;

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

public class VideoAmusementFragment extends BaseFragment {

    private LRecyclerView        mRecyclerView;
    private VideoAdapter         mAdapter;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private int                  offset;

    @Override
    protected int setContentView() {
        return R.layout.layout_video_hot;
    }

    @Override
    protected void onInvisible() {

    }

    @Override
    protected void loadData() {
        HttpUtils.get(mContext, RequestUrl.getVideoUrl("Video_Recom", offset), new Callback() {
            @Override
            public void onResponse(String response) {
                parseData(response);
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    @Override
    protected void initViews() {
        mRecyclerView = findViewById(R.id.rv_funnyvideo);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new VideoAdapter(mContext, R.layout.list_item_video2);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(mAdapter);
        mRecyclerView.setAdapter(mLRecyclerViewAdapter);
        mRecyclerView.refresh();
    }

    @Override
    protected void setListener() {
        mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                enterVideoActivity(mAdapter.getData().get(position));
            }
        });

        mRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.clear();
                offset = 0;
                loadData();
            }
        });

        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                offset += 20;
                loadData();
            }
        });
    }

    private void parseData(String result) {
        VideoNews mData = GsonUtil.changeGsonToBean(result, VideoNews.class);
        mAdapter.addAll(mData.视频);
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
            BitmapUtils.display(mContext, iv, videoData.cover);
            holder.setText(R.id.tv_title, videoData.title);
        }
    }

}

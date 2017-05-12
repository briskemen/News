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
import com.github.jdsjlzx.interfaces.OnNetWorkErrorListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.news.R;
import com.github.news.activity.VideoPlayActivity;
import com.github.news.adapter.recyclerview.CommonAdapter;
import com.github.news.adapter.recyclerview.base.ViewHolder;
import com.github.news.domain.VideoSelection;
import com.github.news.http.Callback;
import com.github.news.http.HttpUtils;
import com.github.news.http.RequestUrl;
import com.github.news.utils.BitmapUtils;
import com.github.news.utils.GsonUtil;

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

public class VideoSelectionFragment extends BaseFragment {

    private LRecyclerView        mRecyclerView;
    private LRecyclerViewAdapter mRecyclerViewAdapter;
    private VideoAdapter         mAdapter;
    private int page = 1;

    @Override
    protected int setContentView() {
        return R.layout.layout_video_hot;
    }

    @Override
    protected void onInvisible() {

    }

    @Override
    protected void loadData() {
        requestData(RequestUrl.getVideoSelectionUrl(page));
        // mRecyclerView.refresh();
    }

    @Override
    protected void initViews() {
        mRecyclerView = findViewById(R.id.rv_funnyvideo);
        // mRecyclerView.setEmptyView(mEmptyView);//设置在setAdapter之前才能生效
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new VideoAdapter(mContext, R.layout.list_item_video_selection);
        mRecyclerViewAdapter = new LRecyclerViewAdapter(mAdapter);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        // mRecyclerView.refresh();
    }

     protected void setListener() {
        mRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.clear();
                page = 1;
                requestData(RequestUrl.getVideoSelectionUrl(page));
            }
        });

        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                page++;
                requestData(RequestUrl.getVideoSelectionUrl(page));
            }
        });

        mRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                enterVideoActivity(mAdapter.getData().get(position));
            }
        });
    }

    public void requestData(String url) {

        HttpUtils.get(mContext, url, new Callback() {
            @Override
            public void onResponse(String response) {
                parseData(response);
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                mRecyclerView.refreshComplete(20);
                mRecyclerViewAdapter.notifyDataSetChanged();
                mRecyclerView.setOnNetWorkErrorListener(new OnNetWorkErrorListener() {
                    @Override
                    public void reload() {
                        requestData(RequestUrl.getVideoSelectionUrl(page));
                    }
                });
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
            BitmapUtils.display(mContext,iv,videoData.image);
            holder.setText(R.id.tv_title, videoData.title);
        }
    }

}

package com.github.news.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.news.activity.ImageActivity;
import com.github.news.adapter.MultipleItemAdapter;
import com.github.news.domain.PictureNews;
import com.github.news.http.Callback;
import com.github.news.http.HttpUtils;
import com.github.news.http.RequestUrl;
import com.github.news.utils.GsonUtil;
import com.github.news.utils.ToastUtils;
import com.github.news.R;

import java.util.ArrayList;
import java.util.List;

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
public class PicNewsFragment extends Fragment {

    private Context              mContext;
    private LRecyclerView        mRecyclerView;
    private LRecyclerViewAdapter mRecyclerViewAdapter;
    private MultipleItemAdapter  mMultipleItemAdapter;

    private String url = RequestUrl.getPicUrl();

    private int pageSize = 10;
    private int index    = -1;

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
        View rootView = inflater.inflate(R.layout.fragment_pic, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        requestData();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (LRecyclerView) view.findViewById(R.id.rv_pic);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mMultipleItemAdapter = new MultipleItemAdapter(mContext);
        mRecyclerViewAdapter = new LRecyclerViewAdapter(mMultipleItemAdapter);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mRecyclerView.refresh();
        setListener();
    }

    private void setListener() {
        mRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mMultipleItemAdapter.clear();
                url = RequestUrl.getPicUrl();
                index = -1;
                requestData();
            }
        });

        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                index += 10;
                int lastIndex = mMultipleItemAdapter.getDataList().size() - 1;
                url = RequestUrl.getMorePicUrl(mMultipleItemAdapter.getDataList().get(lastIndex)
                        .setid);
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
        try {
            Intent intent = new Intent(mContext, ImageActivity.class);
            intent.putExtra("position", position);
            ArrayList<String> data = new ArrayList<>();
            for (int i = 0; i < mMultipleItemAdapter.getDataList().size(); i++) {
                data.add(mMultipleItemAdapter.getDataList().get(i).cover);
            }
            intent.putStringArrayListExtra("imageUrls", data);
            startActivity(intent);
        } catch (Exception e) {
            ToastUtils.showToast(mContext, e.getMessage());
            e.printStackTrace();
        }
    }

    private void requestData() {
        HttpUtils.get(mContext, url, new Callback() {
            @Override
            public void onResponse(String response) {
                parseData(response);
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

    }

    private void parseData(String response) {
        try {
            List<PictureNews> data = GsonUtil.changeGsonToList(response, PictureNews.class);
            mMultipleItemAdapter.addAll(data);
            mRecyclerView.refreshComplete(pageSize);
        } catch (Exception e) {
            ToastUtils.showToast(mContext, e.getMessage());
            e.printStackTrace();
        }
    }

}

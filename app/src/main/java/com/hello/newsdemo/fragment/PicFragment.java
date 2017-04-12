package com.hello.newsdemo.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
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
import com.hello.newsdemo.activity.ImageActivity;
import com.hello.newsdemo.adapter.recyclerview.CommonAdapter;
import com.hello.newsdemo.adapter.recyclerview.base.ViewHolder;
import com.hello.newsdemo.domain.Beauty;
import com.hello.newsdemo.http.Callback;
import com.hello.newsdemo.http.HttpUtils;
import com.hello.newsdemo.http.RequestUrl;
import com.hello.newsdemo.utils.BitmapUtils;
import com.hello.newsdemo.utils.GsonUtil;
import com.hello.newsdemo.utils.UIUtils;
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
 * Package_Name：com.hello.newsdemo.fragment
 * Version：1.0
 * time：2017/4/12 12:32
 * des ：${TODO}
 * gitVersion：2.12.0.windows.1
 * updateAuthor：$Author$
 * updateDate：$Date$
 * updateDes：${TODO}
 * ============================================================
 */
public class PicFragment extends Fragment {

    private Context              mContext;
    private LRecyclerView        mRecyclerView;
    private LRecyclerViewAdapter mRecyclerViewAdapter;
    private PicAdapter           mAdapter;
    private int                  pn;
    private int    rn      = 20;
    private String keyword = "美女 小清新";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pic, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (LRecyclerView) view.findViewById(R.id.rv_pic);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        mAdapter = new PicAdapter(mContext, R.layout.item_stragger);
        mRecyclerViewAdapter = new LRecyclerViewAdapter(mAdapter);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mRecyclerView.refresh();
        setListener();
    }

    private void setListener() {
        mRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });

        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

            }
        });

        mRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
    }

    private void enterImageScaleActivity(int position) {
        Intent intent = new Intent(mContext, ImageActivity.class);
        intent.putExtra("position", position);
        ArrayList<String> data = new ArrayList<>();
        for (int i = 0; i < mAdapter.getDatas().size(); i++) {
            data.add(mAdapter.getDatas().get(i).obj_url);
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
        HttpUtils.get(mContext, RequestUrl.getImagesUrl(pn, rn, keyword), new Callback() {
            @Override
            public void onResponse(String response) {
                Log.e("tag", "response" + response);
                parseData(response);
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

    }

    private void parseData(String response) {
        Beauty beauty = GsonUtil.changeGsonToBean(response, Beauty.class);
        mAdapter.addAll(beauty.data);
        mRecyclerView.refreshComplete(rn);
    }

    private class PicAdapter extends CommonAdapter<Beauty.DataEntity> {

        public PicAdapter(Context context, int layoutId) {
            super(context, layoutId);
        }

        @Override
        protected void convert(ViewHolder holder, Beauty.DataEntity beauty, int position) {
            ImageView iv = holder.getView(R.id.item_straggered_iv);

            if (position%2 == 0){
                iv.getLayoutParams().height = UIUtils.dip2px(mContext,200);
            }else {
                iv.getLayoutParams().height = UIUtils.dip2px(mContext,250);
            }

            BitmapUtils.display(mContext, iv, beauty.obj_url);
            holder.setText(R.id.item_straggered_tv, "文艺小清新");
        }
    }
}

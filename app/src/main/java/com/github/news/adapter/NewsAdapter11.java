package com.github.news.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
 * Project_Name：ZHBJ
 * Package_Name：com.github.news.adapter
 * Version：1.0
 * time：2017/4/2 15:05
 * des ：${TODO}
 * gitVersion：2.12.0.windows.1
 * updateAuthor：$Author$
 * updateDate：$Date$
 * updateDes：${TODO}
 * ============================================================
 */
public class NewsAdapter11<T> extends RecyclerView.Adapter<NewsAdapter11.ViewHolder> {

    public Context context;
    public List<T> mData = new ArrayList<>();

    public NewsAdapter11(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.list_news_item, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NewsAdapter11.ViewHolder holder, int position) {
        setDataAndRefreshUI(holder,mData.get(position));
    }

   /* @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // holder.setDataAndRefreshUI(position);
        setDataAndRefreshUI(holder,mData.get(position),context);
    }*/

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }

    public void setDataList(List<T> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public List<T> getDataList() {
        return mData;
    }

    public void addAll(List<T> data) {
        int lastIndex = mData.size();
        if (mData.addAll(data)) {
            notifyItemRangeInserted(lastIndex, data.size());
        }
    }

    public void remove(int position) {
        mData.remove(position);
        notifyItemRemoved(position);

        if (position != (mData.size())) { // 如果移除的是最后一个，忽略
            notifyItemRangeChanged(position, mData.size() - position);
        }
    }

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    public void setDataAndRefreshUI(ViewHolder holder, T t){

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView  tvTitle;
        public TextView  tvDate;
        public TextView  tvHeelStick;
        public ImageView ivPic;

        public ViewHolder(View itemView) {
            super(itemView);
            ivPic = (ImageView) itemView.findViewById(R.id.iv_pic);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvDate = (TextView) itemView.findViewById(R.id.tv_date);
            tvHeelStick = (TextView) itemView.findViewById(R.id.tv_heel_stick);
        }
    }
}

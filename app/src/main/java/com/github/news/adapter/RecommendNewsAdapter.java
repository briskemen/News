package com.github.news.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.news.R;
import com.github.news.domain.RecommendNewsData;
import com.github.news.utils.BitmapUtils;

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
public class RecommendNewsAdapter extends RecyclerView.Adapter<RecommendNewsAdapter.ViewHolder> {

    private Context context;
    private List<RecommendNewsData.T1348647909107Entry> mData = new ArrayList<>();

    public RecommendNewsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.list_news_item, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setDataAndRefreshUI(position);
    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }

    public void setDataList(List<RecommendNewsData.T1348647909107Entry> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public List<RecommendNewsData.T1348647909107Entry> getDataList() {
        return mData;
    }

    public void addAll(List<RecommendNewsData.T1348647909107Entry> data) {
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

        public void setDataAndRefreshUI(int pos) {
            RecommendNewsData.T1348647909107Entry data = mData.get(pos);
            tvTitle.setText(data.title);
            tvDate.setText(data.source);
            tvHeelStick.setText(data.replyCount + "跟贴");
            BitmapUtils.display(context, ivPic, data.imgsrc);
            // String ids = PrefUtils.getString(context, "read_ids", "");
        }
    }
}

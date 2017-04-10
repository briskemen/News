package com.hello.newsdemo.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.hello.newsdemo.activity.PannoDetailActivity;
import com.hello.newsdemo.domain.VRData;
import com.hello.newsdemo.global.MyApplication;
import com.hello.newsdemo.http.Callback;
import com.hello.newsdemo.http.HttpUtils;
import com.hello.newsdemo.http.RequestUrl;
import com.hello.newsdemo.utils.BitmapUtils;
import com.hello.newsdemo.utils.GsonUtil;
import com.hello.newsdemo.utils.UIUtils;
import com.hello.zhbj52.R;

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
 * Package_Name：com.hello.newsdemo.fragment
 * Version：1.0
 * time：2017/4/10 19:48
 * des ：${TODO}
 * gitVersion：2.12.0.windows.1
 * updateAuthor：$Author$
 * updateDate：$Date$
 * updateDes：${TODO}
 * ============================================================
 */
public class VRFragment extends Fragment {

    private LRecyclerView        mLRecyclerView;
    private LRecyclerViewAdapter mAdapter;
    private VRAdapter            mVRAdapter;
    private int page = 1;
    private Context mContext;
    // private boolean isLoadMore;

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
        Log.e("tag", "initViews");
        View view = inflater.inflate(R.layout.fragment_vr, container, false);
        mLRecyclerView = (LRecyclerView) view.findViewById(R.id.rv);
        mLRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        mVRAdapter = new VRAdapter(mContext);
        mAdapter = new LRecyclerViewAdapter(mVRAdapter);
        mLRecyclerView.setAdapter(mAdapter);
        mLRecyclerView.refresh();
        setListener();
        return view;
    }

    private void setListener() {
        // 下拉刷新监听
        mLRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                // isLoadMore = false;
                mVRAdapter.clear();
                page = 1;
                initData();
            }
        });

        // 加载更多监听
        mLRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                // isLoadMore = true;
                page++;
                initData();
            }
        });

        // 条目点击监听
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(mContext, PannoDetailActivity.class);
                intent.putExtra("title", mVRAdapter.getDataList().get(position).name);
                intent.putExtra("summary", mVRAdapter.getDataList().get(position).summary);
                intent.putExtra("thumb", mVRAdapter.getDataList().get(position).thumb);
                intent.putExtra("time", mVRAdapter.getDataList().get(position).releasetime);
                intent.putExtra("title", mVRAdapter.getDataList().get(position).name);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("tag", "onActivityCreated");
        initData();
    }

    public void initData() {
        HttpUtils.get(MyApplication.getContext(), RequestUrl.getVRData(page, 21, 13, "精品推荐"),
                new Callback() {
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
        VRData vrData = GsonUtil.changeGsonToBean(response, VRData.class);
        Log.e("tag", vrData.data.size() + "parseData");
        mVRAdapter.addAll(vrData.data);
        mLRecyclerView.refreshComplete(21);
    }


    public class VRAdapter extends RecyclerView.Adapter<VRAdapter.ViewHolder> {

        private Context context;
        private List<VRData.DataEntity> mData = new ArrayList<>();

        public VRAdapter(Context context) {
            this.context = context;
        }

        @Override
        public VRAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.list_item_vr, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(VRAdapter.ViewHolder holder, int position) {
            holder.setDataAndRefreshUI(position);
        }

        @Override
        public int getItemCount() {
            if (mData != null) {
                return mData.size();
            }
            return 0;
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {

            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof GridLayoutManager) {
                final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
                final GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager
                        .getSpanSizeLookup();

                gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        if (position != 0 && (position - 1) % 7 != 0) {
                            return 1;
                        } else {
                            return gridLayoutManager.getSpanCount();
                        }
                    }
                });
                gridLayoutManager.setSpanCount(gridLayoutManager.getSpanCount());
            }

        }

        public void setDataList(List<VRData.DataEntity> data) {
            mData.clear();
            mData.addAll(data);
            notifyDataSetChanged();
        }

        public List<VRData.DataEntity> getDataList() {
            return mData;
        }

        public void addAll(List<VRData.DataEntity> data) {
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

            public ImageView      iv_thumb;
            public TextView       tv_date;
            public TextView       tv_title;
            public RelativeLayout mRelativeLayout;

            public ViewHolder(View itemView) {
                super(itemView);
                iv_thumb = (ImageView) itemView.findViewById(R.id.iv_thumb);
                tv_date = (TextView) itemView.findViewById(R.id.tv_date);
                tv_title = (TextView) itemView.findViewById(R.id.tv_title);
                mRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.rl);
            }

            public void setDataAndRefreshUI(int pos) {
                if (pos % 7 == 0) {
                    iv_thumb.getLayoutParams().height = UIUtils.dip2px(mContext, 200);
                    /*GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager.LayoutParams)
                            mRelativeLayout.getLayoutParams();
                    layoutParams.leftMargin = 0;
                    layoutParams.rightMargin = 0;*/
                } else {
                    iv_thumb.getLayoutParams().height = UIUtils.dip2px(mContext, 100);
                }
                BitmapUtils.display(context, iv_thumb, mData.get(pos).thumb);
                tv_date.setText(mData.get(pos).releasetime);
                tv_title.setText(mData.get(pos).name);
            }
        }
    }
}

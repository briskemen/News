package com.github.news.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.news.activity.PannoDetailActivity;
import com.github.news.activity.VRVideoActivity;
import com.github.news.base.App;
import com.github.news.domain.VRDetail;
import com.github.news.domain.VRMData;
import com.github.news.http.Callback;
import com.github.news.http.HttpUtils;
import com.github.news.http.RequestUrl;
import com.github.news.utils.BitmapUtils;
import com.github.news.utils.DateUtils;
import com.github.news.utils.GsonUtil;
import com.github.news.utils.UIUtils;
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
 * time：2017/4/10 19:48
 * des ：${TODO}
 * gitVersion：2.12.0.windows.1
 * updateAuthor：$Author$
 * updateDate：$Date$
 * updateDes：${TODO}
 * ============================================================
 */
public class VRMFragment extends Fragment {

    private LRecyclerView        mLRecyclerView;
    private LRecyclerViewAdapter mAdapter;
    private VRAdapter            mVRAdapter;
    private int pageindex = 1;
    private Context mContext;
    private String  category;
    private int     channelId;

    // private boolean isLoadMore;
    // private List<VRDetail> mVRDetails = new ArrayList<>();

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
        View view = inflater.inflate(R.layout.fragment_vr, container, false);
        mLRecyclerView = (LRecyclerView) view.findViewById(R.id.rv_vr);
        mLRecyclerView.setHasFixedSize(true);
        mLRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        mVRAdapter = new VRAdapter(mContext);
        mAdapter = new LRecyclerViewAdapter(mVRAdapter);
        mLRecyclerView.setAdapter(mAdapter);
        mLRecyclerView.refresh();
        setListener();
        return view;
    }

    public void setCategory(String category) {
        this.category = category;
        if (category.equals("精品推荐")) {
            channelId = 13;
        } else if (category.equals("舞台世界")) {
            channelId = 20;
        } else if (category.equals("全景高校")) {
            channelId = 24;
        } else if (category.equals("观光旅游")) {
            channelId = 16;
        } else if (category.equals("空中全景")) {
            channelId = 17;
        } else if (category.equals("时空转移")) {
            channelId = 15;
        }
    }

    private void setListener() {
        // 下拉刷新监听
        mLRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                // isLoadMore = false;
                mVRAdapter.clear();
                pageindex = 1;
                initData();
            }
        });

        // 加载更多监听
        mLRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                // isLoadMore = true;
                pageindex++;
                initData();
            }
        });

        // 条目点击监听
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                goToDetailActivity(position);
            }
        });
    }

    private void goToDetailActivity(final int position) {

        final Intent intent = new Intent();
        intent.putExtra("name", mVRAdapter.getDataList().get(position).author.nickname);
        intent.putExtra("title", mVRAdapter.getDataList().get(position).name);
        intent.putExtra("summary", mVRAdapter.getDataList().get(position).imagedes);
        intent.putExtra("time", mVRAdapter.getDataList().get(position).uploadtime);

        String videourl = mVRAdapter.getDataList().get(position).original_offline;

        if (videourl !=null) {
            intent.putExtra("mp4url", videourl);
            intent.setClass(mContext, VRVideoActivity.class);
            mContext.startActivity(intent);
        } else {
            String url = RequestUrl.getVRDetail(mVRAdapter.getDataList().get(position).id);

            HttpUtils.get(mContext, url, new Callback() {

                @Override
                public void onResponse(String response) {
                    VRDetail vrDetail = GsonUtil.changeGsonToBean(response, VRDetail.class);
                    intent.setClass(mContext, PannoDetailActivity.class);
                    intent.putExtra("imgurl", vrDetail.scenes.get(0).thumburl);
                    mContext.startActivity(intent);
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    public void initData() {

        HttpUtils.get(App.getContext(), RequestUrl.getVRMData(21,pageindex,channelId),
                new Callback() {
                    @Override
                    public void onResponse(String response) {
                        parseData(response);
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mContext,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void parseData(String response) {
        VRMData vrData = GsonUtil.changeGsonToBean(response, VRMData.class);
        mVRAdapter.addAll(vrData.data);
        mLRecyclerView.refreshComplete(21);
    }


    public class VRAdapter extends RecyclerView.Adapter<VRAdapter.ViewHolder> {

        private Context context;
        private List<VRMData.DataEntity> mData = new ArrayList<>();

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

        public void setDataList(List<VRMData.DataEntity> data) {
            mData.clear();
            mData.addAll(data);
            notifyDataSetChanged();
        }

        public List<VRMData.DataEntity> getDataList() {
            return mData;
        }

        public void addAll(List<VRMData.DataEntity> data) {
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
                BitmapUtils.display(context, iv_thumb, mData.get(pos).thumburl);
                tv_date.setText(DateUtils.timeStampToDate(mData.get(pos).uploadtime*1000));
                tv_title.setText(mData.get(pos).name);
            }
        }
    }
}

package com.hello.newsdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hello.newsdemo.domain.Girl;
import com.hello.newsdemo.utils.BitmapUtils;
import com.hello.newsdemo.utils.UIUtils;
import com.hello.zhbj52.R;

import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================
 * Copyright：${TODO}有限公司版权所有 (c) 2017
 * Author：   卢俊霖
 * Email：    briskemen@163.com
 * GitHub：   https://github.com/briskemen
 * Project_Name：Zhbj
 * Package_Name：com.hello.newsdemo.adapter
 * Version：1.0
 * time：2017/4/1 14:53
 * des ：${TODO}
 * gitVersion：$Rev$
 * updateAuthor：$Author$
 * updateDate：$Date$
 * updateDes：${TODO}
 * ============================================================
 **/
public class StaggeredAdapter extends RecyclerView.Adapter<StaggeredAdapter.MyHolder> {

    private Context         context;
    private List<Girl.DataEntity> mData = new ArrayList<>();

    public StaggeredAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup viewGroup, int i) {//决定根布局
        View itemView = View.inflate(context, R.layout.item_stragger, null);
        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyHolder myHolder, int position) {//填充数据
        if (position%2 == 0){
            myHolder.mIvIcon.getLayoutParams().height = UIUtils.dip2px(context,200);
        }else {
            myHolder.mIvIcon.getLayoutParams().height = UIUtils.dip2px(context,250);
        }

        myHolder.setDataAndRefreshUI(mData.get(position));
    }

    @Override
    public int getItemCount() {//条目总数
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }

    public void setDataList(List<Girl.DataEntity> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public List<Girl.DataEntity> getDataList() {
        return mData;
    }

    public void addAll(List<Girl.DataEntity> data) {
        int lastIndex = mData.size();
        if (mData.addAll(data)) {
            notifyItemRangeInserted(lastIndex, data.size());
        }
    }

    public void remove(int position) {
        mData.remove(position);
        notifyItemRemoved(position);

        if(position != (mData.size())){ // 如果移除的是最后一个，忽略
            notifyItemRangeChanged(position,mData.size()-position);
        }
    }

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        //孩子对象
        private TextView  mTvName;
        private ImageView mIvIcon;

        public MyHolder(View itemView) {
            super(itemView);
            //初始化孩子对象
            mTvName = (TextView) itemView.findViewById(R.id.item_straggered_tv);
            mIvIcon = (ImageView) itemView.findViewById(R.id.item_straggered_iv);
        }

        /**
         * 设置itemView的数据展示
         *
         * @param dataBean
         */
        public void setDataAndRefreshUI(Girl.DataEntity dataBean) {
            if (dataBean.image_url == null){
                dataBean.image_url = "http://a.hiphotos.baidu.com/image/pic/item/359b033b5bb5c9ea05f973e2d739b6003af3b3ac.jpg";
            }

            mTvName.setText(dataBean.date);
            BitmapUtils.display(context,mIvIcon,dataBean.image_url);
        }
    }
}
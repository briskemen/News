package com.hello.newsdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hello.newsdemo.domain.WomenBean;
import com.hello.zhbj52.R;

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
    private List<WomenBean> data;

    public StaggeredAdapter(Context context, List<WomenBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup viewGroup, int i) {//决定根布局
        // TextView tv = new TextView(context);//根布局
        View itemView = View.inflate(context, R.layout.item_stragger, null);
        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyHolder myHolder, int position) {//填充数据
        myHolder.setDataAndRefreshUI(data.get(position));
    }

    @Override
    public int getItemCount() {//条目总数
        if (data != null) {
            return data.size();
        }
        return 0;
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
        public void setDataAndRefreshUI(WomenBean dataBean) {
            mTvName.setText(dataBean.text);
            mIvIcon.setImageResource(dataBean.iconId);
        }
    }
}
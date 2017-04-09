package com.hello.newsdemo.adapter;

import android.content.Context;

import com.hello.newsdemo.domain.RecommendNewsData;
import com.hello.newsdemo.utils.BitmapUtils;

/**
 * ============================================================
 * Copyright：${TODO}有限公司版权所有 (c) 2017
 * Author：   卢俊霖
 * Email：    briskemen@163.com
 * GitHub：   https://github.com/briskemen
 * Project_Name：News
 * Package_Name：com.hello.newsdemo.adapter
 * Version：1.0
 * time：2017/4/9 20:20
 * des ：${TODO}
 * gitVersion：$Rev$
 * updateAuthor：$Author$
 * updateDate：$Date$
 * updateDes：${TODO}
 * ============================================================
 **/
public class Adapter13 extends NewsAdapter11<RecommendNewsData.T1348647909107Entry> {

    public Adapter13(Context context) {
        super(context);
    }

    @Override
    public void setDataAndRefreshUI(ViewHolder holder, RecommendNewsData.T1348647909107Entry tabNewsData) {
        holder.tvTitle.setText(tabNewsData.title);
        holder.tvDate.setText(tabNewsData.source);
        holder.tvHeelStick.setText(tabNewsData.replyCount + "跟贴");
        BitmapUtils.display(context, holder.ivPic, tabNewsData.imgsrc);
    }

}

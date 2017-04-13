package com.hello.newsdemo.adapter;

import android.content.Context;

import com.hello.newsdemo.domain.PictureNews;
import com.hello.newsdemo.domain.TabNewsData;
import com.hello.zhbj52.R;

import java.text.DecimalFormat;

import static com.hello.zhbj52.R.id.iv_pic;
import static com.hello.zhbj52.R.id.tv_date;
import static com.hello.zhbj52.R.id.tv_heel_stick;
import static com.hello.zhbj52.R.id.tv_title;

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
 * Package_Name：com.hello.newsdemo.adapter
 * Version：1.0
 * time：2017/4/13 9:38
 * des ：${TODO}
 * gitVersion：2.12.0.windows.1
 * updateAuthor：$Author$
 * updateDate：$Date$
 * updateDes：${TODO}
 * ============================================================
 */
public class NewsAdapter extends BaseMultiAdapter<TabNewsData> {

    public NewsAdapter(Context context) {
        super(context);
        addItemType(TabNewsData.TYPE_NORMAL, R.layout.list_news_item);
        addItemType(TabNewsData.TYPE_PHOTOSET, R.layout.item_image);
        addItemType(TabNewsData.TYPE_SPECIAL, R.layout.list_item_special);
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        TabNewsData item = getDataList().get(position);
        switch (item.getItemType(position)) {
            case PictureNews.TYPE1:
                bindTypeNormalItem(holder, item);
                break;
            case PictureNews.TYPE2:
                bindTypeImageItem(holder, item);
                break;
            case PictureNews.TYPE3:
                bindTypeSpecialItem(holder, item);
                break;
            default:
                break;
        }
    }

    private void bindTypeNormalItem(SuperViewHolder holder, TabNewsData item) {
        holder.setText(tv_title, item.title);
        holder.setText(tv_date, item.source);
        holder.setText(tv_heel_stick, item.replyCount + "跟贴");
        holder.setImage(iv_pic, item.imgsrc);
        // String ids = PrefUtils.getString(mContext, "read_ids", "");
    }

    private void bindTypeImageItem(SuperViewHolder holder, TabNewsData item) {

        holder.setText(R.id.tv_title, item.title);
        holder.setText(R.id.tv_source_typeimage, item.source);
        holder.setText(R.id.tv_replyCount_typeimage, item.replyCount + "跟帖");
        holder.setImage(R.id.imageView1, item.imgextra.get(0).imgsrc);
        holder.setImage(R.id.imageView2, item.imgsrc);
        holder.setImage(R.id.imageView3, item.imgextra.get(1).imgsrc);
    }

    private void bindTypeSpecialItem(SuperViewHolder holder, TabNewsData item) {
        holder.setText(R.id.tv_title, item.title);
        holder.setText(R.id.tv_source_typespecial, item.source);
        if (Integer.parseInt(item.replyCount) > 10000) {
            holder.setText(R.id.tv_replyCount_typespecial, new DecimalFormat(".0")
                    .format(Integer.parseInt(item.replyCount) * 1.0 / 10000) + "万跟帖");
        } else {
            holder.setText(R.id.tv_replyCount_typespecial, item.replyCount + "跟帖");
        }
        holder.setImage(R.id.iv_typespecial, item.imgsrc);
    }
}

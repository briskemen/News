package com.hello.newsdemo.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.hello.newsdemo.domain.PictureNews;
import com.hello.newsdemo.utils.BitmapUtils;
import com.hello.zhbj52.R;

public class MultipleItemAdapter extends BaseMultiAdapter<PictureNews> {

    public MultipleItemAdapter(Context context) {
        super(context);
        addItemType(PictureNews.TYPE1, R.layout.list_item_pic1);
        addItemType(PictureNews.TYPE2, R.layout.list_item_pic2);
        addItemType(PictureNews.TYPE3, R.layout.list_item_pic3);
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        PictureNews item = getDataList().get(position);
        switch (item.getItemType(position)) {
            case PictureNews.TYPE1:
                bindType1Item(holder, item);
                break;
            case PictureNews.TYPE2:
                bindType2Item(holder, item);
                break;
            case PictureNews.TYPE3:
                bindType3Item(holder, item);
                break;
            default:
                break;
        }

    }

    private void bindType1Item(SuperViewHolder holder, PictureNews item) {

        ImageView iv = holder.getView(R.id.iv_pic1);
        TextView tv = holder.getView(R.id.tv_pic1);

        BitmapUtils.display(mContext, iv, item.cover);
        tv.setText(item.setname);
    }

    private void bindType2Item(SuperViewHolder holder, PictureNews item) {
        ImageView iv1 = holder.getView(R.id.iv1);
        ImageView iv2 = holder.getView(R.id.iv2);
        ImageView iv3 = holder.getView(R.id.iv3);
        TextView tv2 = holder.getView(R.id.tv2);

        BitmapUtils.display(mContext, iv1, item.pics.get(0));
        BitmapUtils.display(mContext, iv2, item.pics.get(1));
        BitmapUtils.display(mContext, iv3, item.pics.get(2));
        tv2.setText(item.setname);
    }

    private void bindType3Item(SuperViewHolder holder, PictureNews item) {
        ImageView iv4 = holder.getView(R.id.iv4);
        ImageView iv5 = holder.getView(R.id.iv5);
        ImageView iv6 = holder.getView(R.id.iv6);
        TextView tv3 = holder.getView(R.id.tv3);

        BitmapUtils.display(mContext, iv4, item.pics.get(0));
        BitmapUtils.display(mContext, iv5, item.pics.get(1));
        BitmapUtils.display(mContext, iv6, item.pics.get(2));
        tv3.setText(item.setname);
    }

}

package com.github.news.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.news.R;
import com.github.news.domain.PhotoSet;
import com.github.news.http.Callback;
import com.github.news.http.HttpUtils;
import com.github.news.http.RequestUrl;
import com.github.news.utils.BitmapUtils;
import com.github.news.utils.GsonUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

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
 * Package_Name：com.github.news.activity
 * Version：1.0
 * time：2017/4/2 12:03
 * des ：${TODO}
 * gitVersion：2.12.0.windows.1
 * updateAuthor：$Author$
 * updateDate：$Date$
 * updateDes：${TODO}
 * ============================================================
 */
public class PhotoSetActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.vp)
    ViewPager mViewPager;

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.tv_des)
    TextView tv_des;

    @BindView(R.id.tv_pos)
    TextView tv_pos;

    PhotoSet photoSet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initData();
    }

    private void initViews() {
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);
    }

    private void initData() {
        String[] photosetID = new String[]{};
        Intent intent = getIntent();
        if (null != intent) {
            photosetID = intent.getStringExtra("photosetID").split("\\|");
        }

        String url = RequestUrl.getPhotoId(photosetID[0].substring(4),photosetID[1]);

        HttpUtils.get(this, url, new Callback() {
            @Override
            public void onResponse(String response) {
                processData(response);
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

    }

    private void processData(String response) {
        photoSet = GsonUtil.changeGsonToBean(response,PhotoSet.class);
        mViewPager.setAdapter(new ImageAdapter());
        mViewPager.addOnPageChangeListener(this);
        mViewPager.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {

                    @Override
                    public void onGlobalLayout() {
                        onPageSelected(0);
                        mViewPager.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                });
    }


    private class ImageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return photoSet.photos.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            /*View view = View.inflate(ImageActivity.this,R.layout.image,null);
            ImageView iv = (ImageView) view.findViewById(R.id.iv);
            BitmapUtils.display(ImageActivity.this,iv,mdata.get(position).image_url);
            container.addView(view);*/
            PhotoView photoView = new PhotoView(container.getContext());
            // photoView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            BitmapUtils.display(PhotoSetActivity.this, photoView, photoSet.photos.get(position).imgurl);
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                    .LayoutParams.WRAP_CONTENT);
            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (photoSet != null){
            tv_des.setText(photoSet.photos.get(position).note);
            tv_title.setText(photoSet.setname);
            tv_pos.setText((position+1) + "/" + photoSet.photos.size());
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}

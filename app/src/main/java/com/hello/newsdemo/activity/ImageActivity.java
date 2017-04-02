package com.hello.newsdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.github.chrisbanes.photoview.PhotoView;
import com.hello.newsdemo.utils.BitmapUtils;
import com.hello.zhbj52.R;

import java.util.List;

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
 * Package_Name：com.hello.newsdemo.activity
 * Version：1.0
 * time：2017/4/2 12:03
 * des ：${TODO}
 * gitVersion：2.12.0.windows.1
 * updateAuthor：$Author$
 * updateDate：$Date$
 * updateDes：${TODO}
 * ============================================================
 */
public class ImageActivity extends AppCompatActivity {

    private List<String> mdata;
    private int mPostion;

    @BindView(R.id.vp)
    ViewPager mViewPager;

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
        Intent intent = getIntent();
        if(null != intent){
            mdata = intent.getStringArrayListExtra("imageUrls");
            // mPostion = intent.getStringExtra("position");
            mPostion = intent.getIntExtra("position", 0);
        }
        mViewPager.setAdapter(new ImageAdapter());
        //设置viewpager的位置
        mViewPager.setCurrentItem(mPostion, false);
    }


    private class ImageAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return mdata.size();
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
            BitmapUtils.display(ImageActivity.this,photoView,mdata.get(position));
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

}
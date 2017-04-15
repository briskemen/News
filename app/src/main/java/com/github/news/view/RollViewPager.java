package com.github.news.view;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.news.R;
import com.github.news.utils.BitmapUtils;

import java.util.List;


public class RollViewPager extends ViewPager implements ViewPager.OnPageChangeListener {

    private List<String> mImageLists;
    private List<String> mTitleLists;
    private List<View>   mDotLists;
    public  Context      mContext;
    private TextView     mTopNewsTitle;
    private Task         mTask;
    private boolean hasAdapter   = false;
    private int     oldPosition  = 0;
    private int     mCurrentItem = 0;

    // 是否滑动
    private boolean isMove = false;

    public RollViewPager(Context context, List<View> mDotLists) {
        super(context);
        this.mDotLists = mDotLists;
        this.mContext = context;
        mTask = new Task();

        // 设置viewpager 的触摸事件
        RollViewPager.this.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                // 当手指触摸到屏幕的时候。viewpage 停止跳动
                // 当手指离开屏幕的时候。viewpage 继续跳动

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // 获取到down 的时间

                    downTimeMillis = System.currentTimeMillis();

                    // 停止跳动
                    // 删除消息
                    handler.removeCallbacksAndMessages(null);

                    // 删除任务
                    //handler.removeCallbacks(mTask);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // 获取的是当前时间
                    long currentTimeMillis = System.currentTimeMillis();
                    if (currentTimeMillis - downTimeMillis < 500) {
                        // Toast.makeText(mContext, "我被点击了", 0).show();

                    }
                    // 继续跳动
                    start();
                } else if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                    System.out.println("MotionEvent.ACTION_CANCEL");
                    start();
                }

                return false;
            }
        });
    }

    public void start() {
        if (!hasAdapter) {
            hasAdapter = true;
            RollViewPagerAdapter adapter = new RollViewPagerAdapter();
            RollViewPager.this.setAdapter(adapter);
            RollViewPager.this.setOnPageChangeListener(this);
        }
        handler.postDelayed(mTask, 2000);
    }

    public void stop() {
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        super.onPageScrolled(position, positionOffset, positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position) {
        mCurrentItem = position;

        if (null != mTitleLists && mTitleLists.size() > 0
                && null != mTopNewsTitle) {
            mTopNewsTitle.setText(mTitleLists.get(position));
        }

        if (null != mDotLists && mDotLists.size() > 0) {
            mDotLists.get(position).setBackgroundResource(R.drawable.dot_focus);
            mDotLists.get(oldPosition).setBackgroundResource(
                    R.drawable.dot_normal);
        }
        oldPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) ev.getX();
                downY = (int) ev.getY();
                // isMove = false;
                break;

            case MotionEvent.ACTION_MOVE:

                int currentX = (int) ev.getX();
                int currentY = (int) ev.getY();

                if (Math.abs(currentX - downX) > Math.abs(currentY - downY)) {
                    // 左右滑动viewPage

                    isMove = false;
                } else {
                    // 上下滑动listview
                    isMove = true;
                }

                break;
        }
        // 请求父类不要拦截我
        getParent().requestDisallowInterceptTouchEvent(!isMove);

        return super.dispatchTouchEvent(ev);
    }

    private class RollViewPagerAdapter extends PagerAdapter {

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(mContext, R.layout.viewpager_item, null);
            ImageView image = (ImageView) view.findViewById(R.id.image);
            BitmapUtils.display(mContext, image, mImageLists.get(position));
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return mImageLists.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

    }

    /**
     * 设置轮播图上面的标题
     *
     * @param mTopNewsTitle
     * @param mTitleLists
     */
    public void setTextTitle(TextView mTopNewsTitle, List<String> mTitleLists) {
        if (null != mTopNewsTitle && null != mTitleLists && mTitleLists.size() > 0) {
            this.mTopNewsTitle = mTopNewsTitle;
            this.mTitleLists = mTitleLists;
            mTopNewsTitle.setText(mTitleLists.get(0));
        }

    }

    /**
     * 设置背景图片
     *
     * @param mImageLists
     */
    public void setImageRes(List<String> mImageLists) {
        this.mImageLists = mImageLists;
    }

    private class Task implements Runnable {

        @Override
        public void run() {
            mCurrentItem = (mCurrentItem + 1) % mImageLists.size();
            handler.obtainMessage().sendToTarget();
        }

    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            RollViewPager.this.setCurrentItem(mCurrentItem, false);
            start();
        }
    };

}
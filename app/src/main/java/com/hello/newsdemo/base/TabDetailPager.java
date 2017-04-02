package com.hello.newsdemo.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hello.newsdemo.activity.NewsDetailActivity;
import com.hello.newsdemo.adapter.NewsAdapter;
import com.hello.newsdemo.domain.TabData;
import com.hello.newsdemo.domain.TabNewsData;
import com.hello.newsdemo.global.GlobalUrl;
import com.hello.newsdemo.http.Callback;
import com.hello.newsdemo.http.HttpUtils;
import com.hello.newsdemo.utils.BitmapUtils;
import com.hello.newsdemo.utils.CacheUtils;
import com.hello.newsdemo.utils.PrefUtils;
import com.hello.newsdemo.utils.ToastUtils;
import com.hello.newsdemo.view.TopNewsViewPager;
import com.hello.zhbj52.R;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 页签详情页
 */
public class TabDetailPager extends BaseMenuDetailPager implements OnPageChangeListener {

    private static final String TAG = "TabDetailPager";

    private TopNewsViewPager mViewPager;

    private TextView tvTitle;// 头条新闻的标题

    private CirclePageIndicator mIndicator;// 头条新闻位置指示器

    private LRecyclerView        mRecyclerView;
    private NewsAdapter          mAdapter;
    private LRecyclerViewAdapter mLAdapter;
    private TopNewsAdapter       mTopNewsAdapter;
    // 头条新闻图片新闻数据集合
    private ArrayList<TabNewsData.AdsData> mPhotoData = new ArrayList<>();
    public TabData.TabList mTabData;

    private int index = 0;
    private boolean isLoadMore;

    private Handler                   mHandler;

    public TabDetailPager(Activity activity, TabData.TabList tabList) {
        super(activity);
        mTabData = tabList;
    }

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.tab_detail_pager, null);
        // 加载头布局
        View headerView = View.inflate(mActivity, R.layout.list_header_topnews, null);

        mRecyclerView = (LRecyclerView) view.findViewById(R.id.rv);
        mViewPager = (TopNewsViewPager) headerView.findViewById(R.id.vp_news);
        tvTitle = (TextView) headerView.findViewById(R.id.tv_title);
        mIndicator = (CirclePageIndicator) headerView.findViewById(R.id.indicator);

        mAdapter = new NewsAdapter(mActivity);
        mLAdapter = new LRecyclerViewAdapter(mAdapter);
        mRecyclerView.setAdapter(mLAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));

        // 将头条新闻以头布局的形式加给RecyclerView
        mLAdapter.addHeaderView(headerView);
        mRecyclerView.refresh();
        setListener();

        return view;
    }

    private void setListener() {

        // 下拉刷新监听
        mRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                isLoadMore = false;
                mAdapter.clear();
                index = 0;
                getDataFromServer();
            }
        });

        // 加载更多监听
        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                isLoadMore = true;
                index += 20;
                getDataFromServer();
            }
        });

        // 条目点击监听
        mLAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // 在本地记录已读状态
                String ids = PrefUtils.getString(mActivity, "read_ids", "");
                String docid = mAdapter.getDataList().get(position).docid;
                if (!ids.contains(docid)) {
                    ids = ids + docid + ",";
                    PrefUtils.setString(mActivity, "read_ids", ids);
                }

                if (docid.contains("_")) {
                    String[] docids = docid.split("_");
                    docid = docids[0];
                }

                changeReadState(view);// 实现局部界面刷新, 这个view就是被点击的item布局对象
                // 跳转新闻详情页面
                Intent intent = new Intent();
                intent.setClass(mActivity, NewsDetailActivity.class);
                intent.putExtra("url", GlobalUrl.getNewsDetailUrl(docid));
                mActivity.startActivity(intent);
            }
        });
    }

    /**
     * 改变已读新闻的颜色
     *
     * @param view
     */
    private void changeReadState(View view) {
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tvTitle.setTextColor(Color.GRAY);
    }

    @Override
    public void initData() {
        String cache = CacheUtils.getCache(GlobalUrl.getNewsUrl(mTabData.tid, index), mActivity);
        if (!TextUtils.isEmpty(cache)) {
            parseData(cache);
        }
        getDataFromServer();
    }

    /**
     * 从服务器获取数据
     */
    private void getDataFromServer() {

        Log.e("url", "getDataFromServer");

        HttpUtils.get(mActivity.getApplicationContext(), GlobalUrl.getNewsUrl(mTabData.tid, index), new
                Callback() {
            @Override
            public void onResponse(String response) {
                parseData(response);
                // 设置缓存
                CacheUtils.SetCache(GlobalUrl.getNewsUrl(mTabData.tid, index), response, mActivity);
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                ToastUtils.showToast(mActivity, error.getMessage());
                // lvList.onRefreshComplete(true);
            }
        });
    }

    /**
     * 从服务器获取更多数据
     */
    /*private void getMoreDataFromServer() {

        HttpUtils.get(mActivity.getApplicationContext(), mMoreUrl, new Callback() {
            @Override
            public void onResponse(String response) {
                parseData(response, true);
                // lvList.onRefreshComplete(true);// 收起下拉刷新控件
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                ToastUtils.showToast(mActivity, error.getMessage());
                // lvList.onRefreshComplete(false);
            }
        });
    }*/

    /**
     * 解析数据
     *
     * @param responseData
     */
    protected void parseData(String responseData) {
        Gson gson = new Gson();
        String result = null;
        try {
            // 解决方法1
            JSONObject jsonObject = new JSONObject(responseData);
            result = jsonObject.getString(mTabData.tid);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        List<TabNewsData> data = gson.fromJson(result, new TypeToken<List<TabNewsData>>() {}.getType());
        /**
         * data:[com.itheima.newsdemo.json.WYNewsJson$TopNews@3d56a9e8, com.itheima.newsdemo.json
         * .WYNewsJson$TopNews@171b3801,...]
         */

       /* // 解决方法2
        JsonArray jsonArray = new JsonParser().parse(responseData).getAsJsonObject().getAsJsonArray
                (mTabId);
        List<WYNewsJson.TopNews> data = gson.fromJson(jsonArray, new TypeToken<List<WYNewsJson
                .TopNews>>() {
        }.getType());*/

        if (!isLoadMore) {
            if (data.get(0).ads == null) {
                data.get(0).ads = new ArrayList<>();
                TabNewsData.AdsData adsData = new TabNewsData.AdsData();
                adsData.imgsrc = data.get(0).imgsrc;
                adsData.title = data.get(0).title;
                data.get(0).ads.add(adsData);
            }
            mPhotoData = data.get(0).ads;

            if (mPhotoData != null) {
                mTopNewsAdapter = new TopNewsAdapter(mActivity,mPhotoData);
                mViewPager.setAdapter(mTopNewsAdapter);

                mIndicator.setViewPager(mViewPager);
                mIndicator.setSnap(true);// 支持快照显示
                mIndicator.setOnPageChangeListener(this);
                mIndicator.onPageSelected(0);// 让指示器重新定位到第一个点

                tvTitle.setText(mPhotoData.get(0).title);
            }
        }

        // 自动轮播条显示
        if (mHandler == null) {
            mHandler = new Handler() {
                public void handleMessage(android.os.Message msg) {
                    int currentItem = mViewPager.getCurrentItem();
                    if (currentItem < mPhotoData.size() - 1) {
                        currentItem++;
                    } else {
                        currentItem = 0;
                    }
                    mViewPager.setCurrentItem(currentItem);// 切换到下一个页面
                    mHandler.sendEmptyMessageDelayed(0, 3000);// 继续延时3秒发消息,形成循环
                }
            };

            mHandler.sendEmptyMessageDelayed(0, 3000);// 延时3秒后发消息
        }

        addItems(data);
        mRecyclerView.refreshComplete(20);
    }

    private void notifyDataSetChanged() {
        mLAdapter.notifyDataSetChanged();
    }

    private void addItems(List<TabNewsData> data) {
        mAdapter.addAll(data);
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int pos) {
        tvTitle.setText(mPhotoData.get(pos).title);
    }

    private class TopNewsAdapter extends PagerAdapter {

        private Context                   mContext;
        private List<TabNewsData.AdsData> mData;

        public TopNewsAdapter(Context context, List<TabNewsData.AdsData> data) {
            mContext = context;
            mData = data;
        }

        @Override
        public int getCount() {
            if (mData != null) {
                return mData.size();
            }
            return 0;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView image = new ImageView(mContext);
            image.setScaleType(ImageView.ScaleType.FIT_XY);// 基于控件大小填充图片

            String url = mData.get(position).imgsrc;
            BitmapUtils.display(mContext, image, url);

            container.addView(image);
            image.setOnTouchListener(new TopNewsTouchListener());// 设置触摸监听
            return image;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }

    /**
     * 头条新闻的触摸监听
     */
    private  class TopNewsTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN://按下
                    mHandler.removeCallbacksAndMessages(null);// 删除Handler中的所有消息
                    break;
                case MotionEvent.ACTION_CANCEL://取消
                    mHandler.sendEmptyMessageDelayed(0, 3000);
                    break;
                case MotionEvent.ACTION_UP://抬起
                    mHandler.sendEmptyMessageDelayed(0, 3000);
                    break;
            }
            return true;
        }
    }
}

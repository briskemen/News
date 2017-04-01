package com.hello.newsdemo.base;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hello.newsdemo.activity.NewsDetailActivity;
import com.hello.newsdemo.global.GlobalUrl;
import com.hello.newsdemo.http.Callback;
import com.hello.newsdemo.http.HttpUtils;
import com.hello.newsdemo.json.WYNewsJson;
import com.hello.newsdemo.json.WYTabListJson;
import com.hello.newsdemo.utils.BitmapUtils;
import com.hello.newsdemo.utils.CacheUtils;
import com.hello.newsdemo.utils.LogUtils;
import com.hello.newsdemo.utils.PrefUtils;
import com.hello.newsdemo.utils.ToastUtils;
import com.hello.newsdemo.view.RefreshListView;
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
public class TabDetailPager extends BaseMenuDetailPager implements
        OnPageChangeListener {

    private static final String TAG = "TabDetailPager";

    private TopNewsViewPager mViewPager;

    private TextView tvTitle;// 头条新闻的标题

    private CirclePageIndicator mIndicator;// 头条新闻位置指示器

    private RefreshListView lvList;// 新闻列表

    private ArrayList<WYNewsJson.AdsData> mPhotoData = new ArrayList<>(); // 头条新闻图片新闻数据集合

    private List<WYNewsJson.TopNews> mNewsList = new ArrayList<>();// 头条新闻数据集合

    private NewsAdapter   mNewsAdapter;
    private WYNewsAdapter mTopNewsAdapter;

    private int index = 0;
    private String mUrl;// 新闻URL
    private String mMoreUrl;// 更多页面地址

    private boolean isPullRefresh;// 是否下拉刷新

    private Handler mHandler;

    public WYTabListJson.TListEntity mTabData;

    private String mTabId;

    public TabDetailPager(Activity activity, WYTabListJson.TListEntity tListEntity, String tabId) {
        super(activity);
        mTabData = tListEntity;
        // mUrl = GlobalUrl.NewsUrl + mTabData.tid + "/" + index + GlobalUrl.endUrl;
        mUrl = GlobalUrl.getNewsUrl(mTabData.tid, index);
        mTabId = tabId;
    }

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.tab_detail_pager, null);
        // 加载头布局
        View headerView = View.inflate(mActivity, R.layout.list_header_topnews, null);

        lvList = (RefreshListView) view.findViewById(R.id.lv_list);
        mViewPager = (TopNewsViewPager) headerView.findViewById(R.id.vp_news);
        tvTitle = (TextView) headerView.findViewById(R.id.tv_title);
        mIndicator = (CirclePageIndicator) headerView.findViewById(R.id.indicator);

        // 将头条新闻以头布局的形式加给listView
        lvList.addHeaderView(headerView);

        // 设置下拉监听
        lvList.SetOnRefreshListener(new RefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isPullRefresh = true;
                /**
                 * 这里下拉刷新，如何让他去加载最新的新闻内容呢？难道还需要再写一个向服务器请求最新资讯的方法？
                 */
                getDataFromServer();
            }

            @Override
            public void onLoadMore() {
                /**
                 * 这里出现一个bug，我想设置最多加载5次或者是最多能加载到120条html，可是现在能加载
                 * 更多，但是index可以无限++，这显然不是我想要的
                 */
                index += 20;
                mMoreUrl = GlobalUrl.getNewsUrl(mTabData.tid, index);
                LogUtils.i(TAG, "mMoreUrl:" + mMoreUrl);
                if (mMoreUrl != null) {
                    getMoreDataFromServer();
                } else {
                    ToastUtils.showToast(mActivity, "最后一页了。。。");
                    lvList.onRefreshComplete(false);// 收起加载更多的布局
                }
            }

        });

        // 设置点击监听事件
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 在本地记录已读状态
                String ids = PrefUtils.getString(mActivity, "read_ids", "");
                // LogUtils.i(TAG, "ids1:" + ids);

                position = position - 2;

                String docid = mNewsList.get(position).docid;
                // LogUtils.i(TAG, "readIds:" + readIds);
                // LogUtils.i(TAG, "readIds:" + readIds);
                if (!ids.contains(docid)) {
                    ids = ids + docid + ",";
                    PrefUtils.setString(mActivity, "read_ids", ids);
                }

                if (docid.contains("_")){
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

        return view;
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
        String cache = CacheUtils.getCache(mUrl, mActivity);
        if (!TextUtils.isEmpty(cache)) {
            parseData(cache, false);
        }
        getDataFromServer();
    }

    /**
     * 从服务器获取数据
     */
    private void getDataFromServer() {

        HttpUtils.get(mActivity.getApplicationContext(), mUrl, new Callback() {
            @Override
            public void onResponse(String response) {
                parseData(response, false);
                // 设置缓存
                CacheUtils.SetCache(mUrl, response, mActivity);
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                ToastUtils.showToast(mActivity, error.getMessage());
                lvList.onRefreshComplete(true);
            }
        });
    }

    /**
     * 从服务器获取更多数据
     */
    private void getMoreDataFromServer() {

        HttpUtils.get(mActivity.getApplicationContext(), mMoreUrl, new Callback() {
            @Override
            public void onResponse(String response) {
                parseData(response, true);
                lvList.onRefreshComplete(true);// 收起下拉刷新控件
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                ToastUtils.showToast(mActivity, error.getMessage());
                lvList.onRefreshComplete(false);
            }
        });
    }

    /**
     * 解析数据
     *
     * @param responseData
     * @param isMore
     */
    protected void parseData(String responseData, boolean isMore) {
        Gson gson = new Gson();
        String result = null;
        try {
            // 解决方法1
            JSONObject jsonObject = new JSONObject(responseData);
            result = jsonObject.getString(mTabId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        List<WYNewsJson.TopNews> data = gson.fromJson(result, new TypeToken<List<WYNewsJson
                .TopNews>>() {
        }.getType());
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

        LogUtils.i(TAG, "data:" + data);

        if (!isMore) {
            if (isPullRefresh) {
                mNewsList.clear();
                mPhotoData.clear();
                lvList.onRefreshComplete(true);
            }

            mNewsList = data;
            if (data.get(0).ads == null) {
                data.get(0).ads = new ArrayList<>();
                WYNewsJson.AdsData adsData = new WYNewsJson.AdsData();
                adsData.imgsrc = data.get(0).imgsrc;
                adsData.title = data.get(0).title;
                data.get(0).ads.add(adsData);
            }
            mPhotoData = data.get(0).ads;

            if (mPhotoData != null) {
                mTopNewsAdapter = new WYNewsAdapter();
                mViewPager.setAdapter(mTopNewsAdapter);

                mIndicator.setViewPager(mViewPager);
                mIndicator.setSnap(true);// 支持快照显示
                mIndicator.setOnPageChangeListener(this);
                mIndicator.onPageSelected(0);// 让指示器重新定位到第一个点

                tvTitle.setText(mPhotoData.get(0).title);
            }

            if (mNewsList != null) {
                mNewsAdapter = new NewsAdapter();
                lvList.setAdapter(mNewsAdapter);
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
        } else {// 如果是加载下一页，需要将数据加入到原来的集合
            /**
             * 这里应该是把加载出来的数据重新加入到原来的集合
             */
            // ArrayList<TabNewsData> news = mTabDetailData.data.news;
            // mNewsList.addAll(news);
            mNewsList.addAll(data);
            mNewsAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 头条新闻的触摸监听
     */
    class TopNewsTouchListener implements View.OnTouchListener {

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

    /**
     * 头条新闻适配器
     */
    class WYNewsAdapter extends PagerAdapter {

        public WYNewsAdapter() {
        }

        @Override
        public int getCount() {
            ArrayList<WYNewsJson.AdsData> adsData = mNewsList.get(0).ads;
            return adsData.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView image = new ImageView(mActivity);
            image.setScaleType(ScaleType.FIT_XY);// 基于控件大小填充图片

            String url = mNewsList.get(0).ads.get(position).imgsrc;
            BitmapUtils.display(mActivity,image,url);

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
     * 新闻列表的适配器
     */
    class NewsAdapter extends BaseAdapter {
        private BitmapUtils utils;

        public NewsAdapter() {
        }

        @Override
        public int getCount() {
            return mNewsList.size();
        }

        @Override
        public WYNewsJson.TopNews getItem(int position) {
            return mNewsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(mActivity, R.layout.list_news_item, null);
                holder = new ViewHolder();
                holder.ivPic = (ImageView) convertView.findViewById(R.id.iv_pic);
                holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
                holder.tvDate = (TextView) convertView.findViewById(R.id.tv_date);
                holder.tvHeelStick = (TextView) convertView.findViewById(R.id.tv_heel_stick);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            WYNewsJson.TopNews item = getItem(position);

            holder.tvTitle.setText(item.title);
            holder.tvDate.setText(item.ptime);
            holder.tvHeelStick.setText(item.replyCount + "跟贴");

            BitmapUtils.display(mActivity,holder.ivPic, item.imgsrc);

            String ids = PrefUtils.getString(mActivity, "read_ids", "");
            /**
             * CGRQH2000001885B,CGQMDTDR00018M4D,CGQAV31G000187OR,CGRPTUTB0005877U,
             * CGS0NSRR0005877U,CGSD97C40001875P....]
             */
            LogUtils.i(TAG, "ids:" + ids);
          /*  if (ids.contains(getItem(position).postid)) {
                holder.tvTitle.setTextColor(Color.GRAY);
            } else {
                holder.tvTitle.setTextColor(Color.BLACK);
            }*/

            return convertView;
        }
    }

    static class ViewHolder {
        public TextView  tvTitle;
        public TextView  tvDate;
        public TextView  tvHeelStick;
        public ImageView ivPic;
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
}

package com.itheima.newsdemo.base;

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
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hello.zhbj52.R;
import com.itheima.newsdemo.NewsDetailActivity;
import com.itheima.newsdemo.domain.WYNewsData;
import com.itheima.newsdemo.domain.WYTabListData;
import com.itheima.newsdemo.global.GlobalContants;
import com.itheima.newsdemo.utils.CacheUtils;
import com.itheima.newsdemo.utils.PrefUtils;
import com.itheima.newsdemo.utils.ToastUtils;
import com.itheima.newsdemo.view.RefreshListView;
import com.itheima.newsdemo.view.TopNewsViewPager;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
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

    @ViewInject(R.id.vp_news)
    private TopNewsViewPager mViewPager;

    @ViewInject(R.id.tv_title)
    private TextView tvTitle;// 头条新闻的标题

    @ViewInject(R.id.indicator)
    private CirclePageIndicator mIndicator;// 头条新闻位置指示器

    @ViewInject(R.id.lv_list)
    private RefreshListView lvList;// 新闻列表

    private ArrayList<WYNewsData.AdsData> mPhotoData = new ArrayList<>(); // 头条新闻图片新闻数据集合

    private List<WYNewsData.TopNews> mTopNewses = new ArrayList<>();// 头条新闻数据集合

    private TextView tvText;

    //    private ArrayList<TabData.TopNewsData> mTopNewsList;// 头条新闻数据集合
    //     private ArrayList<TabData.TabNewsData> mNewsList; // 新闻数据集合

    private NewsAdapter    mNewsAdapter;
    private TopNewsAdapter mTopNewsAdapter;

    private int index = 0;
    private String mUrl;// 新闻URL
    //private TabData mTabDetailData;
    private String mMoreUrl;// 更多页面地址

    private boolean isPullRefresh;// 是否下拉刷新

    private Handler mHandler;

    public WYTabListData.TListEntity mTabData;

    private String mtabid;

    public TabDetailPager(Activity activity, WYTabListData.TListEntity tListEntity, String tabid) {
        super(activity);
        mTabData = tListEntity;
        mUrl = GlobalContants.NewsUrl + mTabData.tid + "/" + index + GlobalContants.endUrl;
        mtabid = tabid;
    }

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.tab_detail_pager, null);
        // 加载头布局
        View headerView = View.inflate(mActivity, R.layout.list_header_topnews, null);

        ViewUtils.inject(this, view);
        ViewUtils.inject(this, headerView);

        // 将头条新闻以头布局的形式加给listview
        lvList.addHeaderView(headerView);

        // 设置下拉监听
        lvList.SetOnRefreshListener(new RefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isPullRefresh = true;
                getDataFromServer();
            }

            @Override
            public void onLoadMore() {
                index += 20;
                mMoreUrl = GlobalContants.NewsUrl + mTabData.tid + index + GlobalContants.endUrl;
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
                /**
                 * 这里修改了，不过这个id不确定
                 */
                String readIds = mTopNewses.get(position).cid;
                if (!ids.contains(readIds)) {
                    ids = ids + readIds + ",";
                    PrefUtils.setString(mActivity, "read_ids", ids);
                }
                changeReadState(view);// 实现局部界面刷新, 这个view就是被点击的item布局对象

                String docid = mTopNewses.get(position - 1).docid;

               // System.out.println("docid:"+docid);

                // 跳转新闻详情页面
                Intent intent = new Intent();
                intent.setClass(mActivity, NewsDetailActivity.class);
                intent.putExtra("url", "https://c.m.163.com/news/a/" + docid + ".html");
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
        HttpUtils utils = new HttpUtils();
        utils.send(HttpMethod.GET, mUrl, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                System.out.println("页签详情页返回结果:" + result);
                parseData(result, false);
                lvList.onRefreshComplete(true);
                // 设置缓存
                CacheUtils.SetCache(mUrl, result, mActivity);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
                error.printStackTrace();

                lvList.onRefreshComplete(false);
            }
        });
    }

    /**
     * 从服务器获取更多数据
     */
    private void getMoreDataFromServer() {
        HttpUtils utils = new HttpUtils();
        utils.send(HttpMethod.GET, mMoreUrl, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;

                parseData(result, true);
                lvList.onRefreshComplete(true);// 收起下拉刷新控件
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(mActivity, s, Toast.LENGTH_SHORT).show();
                e.printStackTrace();// 捕捉错误信息
                lvList.onRefreshComplete(false);
            }
        });
    }

    private WYNewsData mWYNewsData;// 网易新闻头条数据


    /**
     * 解析数据
     *
     * @param result
     * @param isMore
     */
    protected void parseData(String result, boolean isMore) {

        String s = null;
        try {

            // 解决方法1
            JSONObject jsonObject = new JSONObject(result);
            s = jsonObject.getString(mtabid);
            // System.out.println("result: " + s);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        List<WYNewsData.TopNews> data = gson.fromJson(s, new TypeToken<List<WYNewsData.TopNews>>() {
        }.getType());

        // 解决方法2
        // JsonArray jsonArray = new JsonParser().parse(result).getAsJsonObject().getAsJsonArray
        // (mtabid);
        // List<WYNewsData.TopNews> data = gson.fromJson(jsonArray, new TypeToken<List<WYNewsData
        // .TopNews>>() {}.getType());

        if (!isMore) {
            if (isPullRefresh) {
                mTopNewses.clear();
                mPhotoData.clear();
            }

            mTopNewses = data;
            if (data.get(0).ads == null) {
                data.get(0).ads = new ArrayList<>();
                WYNewsData.AdsData adsData = new WYNewsData.AdsData();
                adsData.imgsrc = data.get(0).imgsrc;
                adsData.title = data.get(0).title;
                data.get(0).ads.add(adsData);
            }
            mPhotoData = data.get(0).ads;

            if (mPhotoData != null) {
                mTopNewsAdapter = new TopNewsAdapter();
                mViewPager.setAdapter(mTopNewsAdapter);

                mIndicator.setViewPager(mViewPager);
                mIndicator.setSnap(true);// 支持快照显示
                mIndicator.setOnPageChangeListener(this);
                mIndicator.onPageSelected(0);// 让指示器重新定位到第一个点

                tvTitle.setText(mPhotoData.get(0).title);
            }

            if (mTopNewses != null) {
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
            // ArrayList<TabData.TabNewsData> news = mTabDetailData.data.news;
            // mNewsList.addAll(news);
            // 这里应该加载的是新闻列表对象
            mTopNewses.addAll(data);
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
    class TopNewsAdapter extends PagerAdapter {

        private BitmapUtils utils;

        public TopNewsAdapter() {
            utils = new BitmapUtils(mActivity);
            utils.configDefaultLoadingImage(R.mipmap.topnews_item_default);// 设置默认图片
        }

        @Override
        public int getCount() {
            ArrayList<WYNewsData.AdsData> adsDatas = mTopNewses.get(0).ads;
            return adsDatas.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView image = new ImageView(mActivity);
            image.setScaleType(ScaleType.FIT_XY);// 基于控件大小填充图片

            String url = mTopNewses.get(0).ads.get(position).imgsrc;
            utils.display(image, url);// 传递imagView对象和图片地址

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
        //private MyBitmapUtils utils;

        public NewsAdapter() {
            utils = new BitmapUtils(mActivity);
            utils.configDefaultLoadingImage(R.mipmap.pic_item_list_default);
            //utils = new MyBitmapUtils();
            //utils.configDefaultLoadingImage(R.mipmap.pic_item_list_default);
        }

        @Override
        public int getCount() {
            return mTopNewses.size();
        }

        @Override
        public WYNewsData.TopNews getItem(int position) {
            return mTopNewses.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(mActivity, R.layout.list_news_item,
                        null);
                holder = new ViewHolder();
                holder.ivPic = (ImageView) convertView
                        .findViewById(R.id.iv_pic);
                holder.tvTitle = (TextView) convertView
                        .findViewById(R.id.tv_title);
                holder.tvDate = (TextView) convertView
                        .findViewById(R.id.tv_date);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            WYNewsData.TopNews item = getItem(position);

            holder.tvTitle.setText(item.title);
            holder.tvDate.setText(item.ptime);

            utils.display(holder.ivPic, item.imgsrc);

            /*String ids = PrefUtils.getString(mActivity, "read_ids", "");
            if (ids.contains(getItem(position).id)) {
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
        //TopNewsData topNewsData = mTopNewsList.get(arg0);
        tvTitle.setText(mPhotoData.get(pos).title);
    }
}

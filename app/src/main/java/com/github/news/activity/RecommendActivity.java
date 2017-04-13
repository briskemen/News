package com.github.news.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.news.R;
import com.github.news.adapter.RecommendNewsAdapter;
import com.github.news.domain.RecommendNewsData;
import com.github.news.global.GlobalUrl;
import com.github.news.utils.BitmapUtils;
import com.github.news.utils.LogUtils;
import com.github.news.utils.PrefUtils;
import com.github.news.utils.ShareUtils;
import com.github.news.utils.ToastUtils;
import com.github.news.view.TopNewsViewPager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class RecommendActivity extends AppCompatActivity {
    private static final String TAG = "RecommendActivity";

    private LRecyclerView       mRecyclerView;
    private CirclePageIndicator mIndicator;// 头条新闻位置指示器
    private TextView            tvTitle;// 头条新闻的标题
    private TopNewsViewPager    mViewPager;
    private View                headerView;

    private RecommendNewsAdapter mAdapter;
    private LRecyclerViewAdapter mLAdapter;
    private MyNewsAdapter        mMyNewsAdapter;

    private int    index       = 0;
    private String mTopNewsUrl = GlobalUrl.getTopNewsUrl(index);

    // 头条新闻图片新闻数据集合
    private ArrayList<RecommendNewsData.AdsData> mPhotoData = new ArrayList<>();

    private boolean isLoadMore;

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        setListener();
        getDataFromServer();
    }

    private void initView() {
        setContentView(R.layout.activity_recommend);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        headerView = View.inflate(this, R.layout.list_header_topnews, null);
        mRecyclerView = (LRecyclerView) findViewById(R.id.rv);
        mViewPager = (TopNewsViewPager) headerView.findViewById(R.id.vp_news);
        tvTitle = (TextView) headerView.findViewById(R.id.tv_title);
        mIndicator = (CirclePageIndicator) headerView.findViewById(R.id.indicator);

        // 添加分割线
        DividerDecoration divider = new DividerDecoration.Builder(this)
                .setHeight(R.dimen.default_divider_height)
                .setPadding(R.dimen.default_divider_padding)
                .setColorResource(R.color.split)
                .build();
        mRecyclerView.addItemDecoration(divider);
        mRecyclerView.setHasFixedSize(true);

        // mAdapter = new RecommendNewsAdapter(this);
        mAdapter = new RecommendNewsAdapter(this);
        mLAdapter = new LRecyclerViewAdapter(mAdapter);
        mRecyclerView.setAdapter(mLAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        //设置头部加载颜色
        mRecyclerView.setHeaderViewColor(R.color.colorAccent, R.color.dark, android.R.color.white);
        //设置底部加载颜色
        mRecyclerView.setFooterViewColor(R.color.colorAccent, R.color.dark, android.R.color.white);
        mRecyclerView.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");

        // 将头条新闻以头布局的形式加给RecyclerView
        mLAdapter.addHeaderView(headerView);
        mRecyclerView.refresh();
    }

    public void initData() {
       /* String cache = CacheUtils.getCache(GlobalUrl.getNewsUrl(mTopNewsList.cid, index), this);
        if (!TextUtils.isEmpty(cache)) {
            parseData(cache);
        }*/
        getDataFromServer();
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
                String ids = PrefUtils.getString(RecommendActivity.this, "read_ids", "");
                String docId = mAdapter.getDataList().get(position).docid;
                if (!ids.contains(docId)) {
                    ids = ids + docId + ",";
                    PrefUtils.setString(RecommendActivity.this, "read_ids", ids);
                }

                if (docId.contains("_")) {
                    String[] docIds = docId.split("_");
                    docId = docIds[0];
                }

                changeReadState(view);// 实现局部界面刷新, 这个view就是被点击的item布局对象
                // 跳转新闻详情页面
                Intent intent = new Intent();
                intent.setClass(RecommendActivity.this, NewsDetailActivity.class);
                intent.putExtra("url", GlobalUrl.getNewsDetailUrl(docId));
                RecommendActivity.this.startActivity(intent);
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


    /**
     * 创建菜单
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toobar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                String site = getString(R.string.app_name);
                ShareUtils.showShare(RecommendActivity.this, site);
                break;
            case R.id.setting:
                ToastUtils.showToast(this, "setting");
                break;
        }
        return true;
    }

    /**
     * 从服务器获取数据
     */
    private void getDataFromServer() {

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(mTopNewsUrl, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                parseData(response);
                LogUtils.i(TAG, "response:" + response);
                // 设置缓存
               /* CacheUtils.SetCache(GlobalUrl.getNewsUrl(mTopNewsList.cid, index), response,
                        RecommendActivity.this);*/
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ToastUtils.showToast(RecommendActivity.this, "网络出错，请稍后再试");
            }
        });

        requestQueue.add(request);
    }

    /**
     * 解析网络数据
     *
     * @param responseData
     */
    protected void parseData(String responseData) {
        Gson gson = new Gson();
        String result = null;
        try {
            // 解决方法1
            JSONObject jsonObject = new JSONObject(responseData);
            result = jsonObject.getString("T1348647909107");
            LogUtils.i(TAG, "result:" + result);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        List<RecommendNewsData.T1348647909107Entry> data = gson.fromJson(result, new
                TypeToken<List<RecommendNewsData.T1348647909107Entry>>() {
                }.getType());
        LogUtils.i(TAG, "data:" + data);

        if (!isLoadMore) {
            if (data.get(0).ads == null) {
                data.get(0).ads = new ArrayList<>();
                RecommendNewsData.AdsData adsData = new RecommendNewsData.AdsData();
                adsData.imgsrc = data.get(0).imgsrc;
                adsData.title = data.get(0).title;
                data.get(0).ads.add(adsData);
            }
            mPhotoData = data.get(0).ads;

            if (mPhotoData != null) {
                mMyNewsAdapter = new MyNewsAdapter(this, mPhotoData);
                mViewPager.setAdapter(mMyNewsAdapter);

                mIndicator.setViewPager(mViewPager);
                mIndicator.setSnap(true);// 支持快照显示
                mIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int
                            positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        tvTitle.setText(mPhotoData.get(position).title);
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });

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

    private void addItems(List<RecommendNewsData.T1348647909107Entry> data) {
        mAdapter.addAll(data);
    }


    private class MyNewsAdapter extends PagerAdapter {

        private Context                         mContext;
        private List<RecommendNewsData.AdsData> mData;

        public MyNewsAdapter(Context context, List<RecommendNewsData.AdsData> data) {
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

    // 头条新闻的触摸监听

    public class TopNewsTouchListener implements View.OnTouchListener {

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

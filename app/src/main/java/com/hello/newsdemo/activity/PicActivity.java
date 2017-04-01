package com.hello.newsdemo.activity;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.hello.newsdemo.adapter.StaggeredAdapter;
import com.hello.newsdemo.domain.WomenBean;
import com.hello.newsdemo.global.GlobalUrl;
import com.hello.newsdemo.utils.CacheUtils;
import com.hello.newsdemo.utils.LogUtils;
import com.hello.newsdemo.utils.ShareUtils;
import com.hello.newsdemo.utils.ToastUtils;
import com.hello.zhbj52.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;

/**
 * 美女相片
 */
public class PicActivity extends AppCompatActivity {
    private static final String TAG = "PicActivity";

    private ArrayList<WomenBean> mStaggeredDataList = new ArrayList<>();

    private FloatingActionButton mFab;
    private SwipeRefreshLayout   mSwipeRefreshLayout;
    private RecyclerView         mRecyclerView;

    private int[] mStaggeredIcons = new int[]{R.mipmap.p1, R.mipmap.p2, R.mipmap.p3, R
            .mipmap.p4, R.mipmap.p5, R.mipmap.p6, R.mipmap.p7, R.mipmap.p8, R.mipmap.p9, R
            .mipmap.p10, R.mipmap.p11, R.mipmap.p12, R.mipmap.p13, R.mipmap.p14, R.mipmap
            .p15, R.mipmap.p16, R.mipmap.p17, R.mipmap.p18, R.mipmap.p19, R.mipmap.p20, R
            .mipmap.p21, R.mipmap.p22, R.mipmap.p23, R.mipmap.p24, R.mipmap.p25, R.mipmap
            .p26, R.mipmap.p27, R.mipmap.p28, R.mipmap.p29, R.mipmap.p30, R.mipmap.p31, R
            .mipmap.p32, R.mipmap.p33, R.mipmap.p34, R.mipmap.p35, R.mipmap.p36, R.mipmap
            .p37, R.mipmap.p38, R.mipmap.p39, R.mipmap.p40, R.mipmap.p41, R.mipmap.p42, R
            .mipmap.p43, R.mipmap.p44};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_pic);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mFab = (FloatingActionButton) findViewById(R.id.fab);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish();
                ToastUtils.showToast(PicActivity.this, "点我点我");
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        initData();
        initStaggeredGridAdapterV();

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id
                .swipe_refresh);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        // 设置下拉刷新监听器
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(2000);
                        PicActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //停止刷新操作
                                mSwipeRefreshLayout.setRefreshing(false);
                                //得到adapter.然后刷新
                                mRecyclerView.getAdapter().notifyDataSetChanged();
                            }
                        });

                    }
                }).start();
            }
        });
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
                ShareUtils.showShare(PicActivity.this, site);
                break;
            case R.id.setting:
                ToastUtils.showToast(this, "setting");
                break;
        }
        return true;
    }

    /**
     * 初始化纵向的瀑布流
     */
    private void initStaggeredGridAdapterV() {
        //设置layoutManager
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);

        //设置adapter
        StaggeredAdapter adapter = new StaggeredAdapter(PicActivity.this, mStaggeredDataList);
        mRecyclerView.setAdapter(adapter);
    }

    private void initData() {
        for (int i = 0; i < mStaggeredIcons.length; i++) {
            int iconId = mStaggeredIcons[i];
            WomenBean dataBean = new WomenBean();
            dataBean.iconId = iconId;
            dataBean.text = "我是第几" + i + "张";
            mStaggeredDataList.add(dataBean);
        }

        String cache = CacheUtils.getCache(GlobalUrl.womenPicUrl, PicActivity.this);
        if (!TextUtils.isEmpty(cache)) {// 如果缓存存在,直接解析数据, 无需访问网路
            parseData(cache);
        }

        getDataFromServer();// 不管有没有缓存, 都获取最新数据, 保证数据最新
    }

    /**
     * 从服务器获取数据
     */
    private void getDataFromServer() {
        HttpUtils utils = new HttpUtils();
        // 使用xUtils发送请求
        utils.send(HttpRequest.HttpMethod.GET, GlobalUrl.womenPicUrl,
                new RequestCallBack<String>() {

                    // 访问成功, 在主线程运行
                    @Override
                    public void onSuccess(ResponseInfo responseInfo) {
                        String result = (String) responseInfo.result;
                        LogUtils.e(TAG, "result:" + result);
                        parseData(result);
                    }

                    // 访问失败, 在主线程运行
                    @Override
                    public void onFailure(HttpException error, String msg) {
                        error.printStackTrace();
                        ToastUtils.showToast(PicActivity.this, "网络出错，请稍后再试");
                    }
                });
    }

    /**
     * 解析网络数据
     *
     * @param responseData
     */
    protected void parseData(String responseData) {
        Gson gson = new Gson();
        /*String result = null;
        try {
            // 解决方法1
            JSONObject jsonObject = new JSONObject(responseData);
            result = jsonObject.getString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        List<WomenPicJson.DataEntity> data = gson.fromJson(result, new TypeToken<List<WomenPicJson
                .DataEntity>>() {
        }.getType());*/

    }
}

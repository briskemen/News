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

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.hello.newsdemo.adapter.StaggeredAdapter;
import com.hello.newsdemo.domain.Girl;
import com.hello.newsdemo.global.GlobalUrl;
import com.hello.newsdemo.http.Callback;
import com.hello.newsdemo.http.HttpUtils;
import com.hello.newsdemo.utils.CacheUtils;
import com.hello.newsdemo.utils.ShareUtils;
import com.hello.newsdemo.utils.ToastUtils;
import com.hello.zhbj52.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 美女相片
 */
public class PicActivity extends AppCompatActivity {
    private static final String TAG = "PicActivity";

    private FloatingActionButton mFab;
    private SwipeRefreshLayout   mSwipeRefreshLayout;
    private RecyclerView         mRecyclerView;

    StaggeredAdapter adapter;

    private List<Girl.DataEntity> mdata = new ArrayList<>();

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
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);

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

        initData();
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
        adapter = new StaggeredAdapter(PicActivity.this, mdata);
        mRecyclerView.setAdapter(adapter);
    }

    private void initData() {

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

        HttpUtils.get(getApplicationContext(), GlobalUrl.womenPicUrl, new Callback() {
            @Override
            public void onResponse(String response) {
                parseData(response);
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                ToastUtils.showToast(PicActivity.this, "网络出错，请稍后再试");
            }
        });

    }

    /**
     * 解析网络数据
     *
     * @param result
     */
    protected void parseData(String result) {
        Gson gson = new Gson();
        Girl data = gson.fromJson(result,Girl.class);
        mdata = data.data;
        initStaggeredGridAdapterV();
    }
}

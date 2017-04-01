package com.hello.newsdemo.activity;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.hello.newsdemo.global.GlobalUrl;
import com.hello.newsdemo.utils.LogUtils;
import com.hello.newsdemo.utils.ShareUtils;
import com.hello.newsdemo.utils.ToastUtils;
import com.hello.zhbj52.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

public class RecommendActivity extends AppCompatActivity {
    private static final String TAG = "RecommendActivity";

    private FloatingActionButton mFab;
    private SwipeRefreshLayout   mSwipeRefreshLayout;
    private RecyclerView         mRecyclerView;

    private int    index       = 0;
    private String mTopNewsUrl = GlobalUrl.getTopNewsUrl(index);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mFab = (FloatingActionButton) findViewById(R.id.fab);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish();
                ToastUtils.showToast(RecommendActivity.this, "点我点我");
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        initData();

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
                        RecommendActivity.this.runOnUiThread(new Runnable() {
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

    private void initData() {

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
        HttpUtils utils = new HttpUtils();
        // 使用xUtils发送请求
        utils.send(HttpRequest.HttpMethod.GET, mTopNewsUrl, new RequestCallBack<String>() {
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
                ToastUtils.showToast(RecommendActivity.this, "网络出错，请稍后再试");
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

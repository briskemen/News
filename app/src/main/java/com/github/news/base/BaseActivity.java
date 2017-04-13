package com.github.news.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.news.activity.MainActivity;
import com.github.news.utils.ToastUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * ============================================================
 * Copyright：${TODO}有限公司版权所有 (c) 2017
 * Author：   卢俊霖
 * Email：    briskemen@163.com
 * GitHub：   https://github.com/briskemen
 * Project_Name：News
 * Package_Name：com.github.news.base
 * Version：1.0
 * time：2017/4/4 23:44
 * des ：${TODO}
 * gitVersion：$Rev$
 * updateAuthor：$Author$
 * updateDate：$Date$
 * updateDes：${TODO}
 * ============================================================
 **/
public class BaseActivity extends AppCompatActivity {
    // 共同属性
    // 共同的方法
    private List<AppCompatActivity> activities = new LinkedList<>();
    private long     mPreTime;
    private Activity mCurActivity;

    /**
     * 得到最上层activity
     *
     * @return
     */
    public Activity getCurActivity() {
        return mCurActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activities.add(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activities.remove(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCurActivity = this;// 最上层的一个activity
    }

    /**
     * 完全退出
     */
    public void exit() {
        for (AppCompatActivity activity : activities) {
            activity.finish();
        }
    }

    /**
     * 统一退出控制
     */
    @Override
    public void onBackPressed() {
        if (this instanceof MainActivity) {
            if (System.currentTimeMillis() - mPreTime > 2000) {// 两次点击间隔大于2s
                ToastUtils.showToast(getApplicationContext(), "再按一次，退出新闻客户端");
                mPreTime = System.currentTimeMillis();
                return;
            }
        }
        super.onBackPressed();// finish();
    }
}

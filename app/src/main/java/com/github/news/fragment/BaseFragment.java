package com.github.news.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
 * Project_Name：News
 * Package_Name：com.github.news.fragment
 * Version：1.0
 * time：2017/4/17 16:25
 * des ：${TODO}
 * gitVersion：2.12.0.windows.1
 * updateAuthor：$Author$
 * updateDate：$Date$
 * updateDes：${TODO}
 * ============================================================
 */
public abstract class BaseFragment extends Fragment {

    protected boolean isLoad;
    protected boolean isViewCreated;
    protected View    view;
    protected Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        view = inflater.inflate(setContentView(), container, false);
        isViewCreated = true;
        initViews();
        setListener();
        isCanLoadData();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isViewCreated = false;
        isLoad = false;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isCanLoadData();
    }

    private void isCanLoadData() {
        if (!isViewCreated) {
            return;
        }

        if (getUserVisibleHint()) {
            loadData();
            isLoad = true;
        } else {
            if (isLoad) {
                onInvisible();
            }
        }
    }

    protected abstract int setContentView();

    protected View getContentView() {
        return view;
    }

    protected <T extends View> T findViewById(int id) {

        return (T) getContentView().findViewById(id);
    }

    protected abstract void onInvisible();

    protected abstract void loadData();

    protected abstract void initViews();

    protected abstract void setListener();

}

package com.hello.newsdemo.factory;

import android.support.v4.app.Fragment;
import android.util.SparseArray;

import com.hello.newsdemo.fragment.PicFragment;
import com.hello.newsdemo.fragment.PicGirlFragment;
import com.hello.newsdemo.fragment.PicNewsFragment;


/**
 * ============================================================
 * Copyright：Google有限公司版权所有 (c) 2017
 * Author：   陈冠杰
 * Email：    815712739@qq.com
 * GitHub：   https://github.com/JackChen1999
 * 博客：     http://blog.csdn.net/axi295309066
 * 微博：     AndroidDeveloper
 * <p>
 * Project_Name：SmartCity
 * Package_Name：com.google.smartcity
 * Version：1.0
 * time：2016/2/16 10:06
 * des ：${TODO}
 * gitVersion：$Rev$
 * updateAuthor：$Author$
 * updateDate：$Date$
 * updateDes：${TODO}
 * ============================================================
 **/
public class PicFragmentFactory {

    private static SparseArray<Fragment> cachesFragment = new SparseArray<>();

    public static Fragment getFragment(int position) {

        // 如果缓存里面有对应的fragment,就直接取出返回
        Fragment fragment = cachesFragment.get(position);
        if (fragment != null) {
            return fragment;
        }

        switch (position) {
            case 0:
                fragment = new PicFragment();
                break;
            case 1:
                fragment = new PicGirlFragment();
                break;
            case 2:
                fragment = new PicNewsFragment();
                break;
        }

        cachesFragment.put(position, fragment);
        return fragment;
    }

}

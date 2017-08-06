package com.syl.androidart.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Bright on 2017/5/20.
 *
 * @Describe Fragment的基类
 * Fragment 的生命周期onCreate()-->onCreateView()-->onActivityCreated(),其他方法暂时略去
 * @Called
 */

public abstract class BaseFragment extends Fragment {

    protected View mRootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        init();//
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //缓存Fragment页面的内容,如果mRootView存在就直接返回mRootView对象
        if (mRootView == null) {
            mRootView = initView();
        }
        return mRootView;//初始化视图
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        initData();//初始化数据
        initListener();//初始化监听
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * 初始化视图 onCreateView()
     *
     * @return
     */
    public abstract View initView();

    public void init() {

    }

    /**
     * 初始化数据
     */
    public void initData() {

    }

    /**
     * 初始化监听
     */
    public void initListener() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}

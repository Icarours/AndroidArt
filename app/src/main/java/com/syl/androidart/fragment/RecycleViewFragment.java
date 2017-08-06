package com.syl.androidart.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.syl.androidart.R;
import com.syl.androidart.base.BaseFragment;

/**
 * Created by Bright on 2017/8/6.
 *
 * @Describe RecycleView举例
 * @Called
 */

public class RecycleViewFragment extends BaseFragment {
    private View mRootView;

    @Override
    public View initView() {
        return null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //缓存Fragment页面的内容,如果mRootView存在就直接返回mRootView对象
        if (mRootView == null) {
            mRootView = View.inflate(getContext(), R.layout.fragment_recycle_view, null);
        }
        return mRootView;
    }
}

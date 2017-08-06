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
 * @Describe
 * @Called
 */

public class ListViewFragment extends BaseFragment {

    private View mRootView;

    @Override
    public View initView() {
        return null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            //mRootView如果为null,就创建mRootView,否则直接显示
            mRootView = View.inflate(getContext(), R.layout.fragment_list_view, null);
        }
        return mRootView;
    }
}

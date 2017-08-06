package com.syl.androidart.fragment;

import android.view.View;

import com.syl.androidart.R;
import com.syl.androidart.base.BaseFragment;

/**
 * Created by Bright on 2017/8/5.
 *
 * @Describe CardView控件举例
 * 阴影效果,圆角效果
 * @Called
 */

public class CardViewFragment extends BaseFragment {

    private View mRootView;

    @Override
    public View initView() {
        //缓存Fragment页面的内容,如果mRootView存在就直接返回mRootView对象
        if (mRootView == null) {
            mRootView = View.inflate(getContext(), R.layout.fragment_card_view, null);
        }
        return mRootView;
    }
}

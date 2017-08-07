package com.syl.androidart.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.syl.androidart.R;
import com.syl.androidart.adapter.PageFragmentPagerAdapter;
import com.syl.androidart.base.BaseFragment;
import com.syl.androidart.utils.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Bright on 2017/8/5.
 *
 * @Describe TabLayout控件举例
 * @Called
 */

public class TabLayoutFragment extends BaseFragment {
    public static final String TAG = TabLayoutFragment.class.getSimpleName();
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    Unbinder unbinder;
    private View mRootView;

    @Override
    public View initView() {
        return null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //缓存Fragment页面的内容,如果mRootView存在就直接返回mRootView对象
//        if (mRootView == null) {
        //特别注意,这个地方不能使用缓存复用,否则会报空指针,也有可能是butterKnife的问题
        mRootView = View.inflate(getActivity(), R.layout.fragment_tablayout, null);
//        }
        unbinder = ButterKnife.bind(this, mRootView);

        return mRootView;
    }

    @Override
    public void initData() {
        PageFragmentPagerAdapter pagerAdapter = new PageFragmentPagerAdapter(getActivity().getSupportFragmentManager(), getActivity());
        LogUtil.d(TAG, "mViewPager==" + mViewPager);
        LogUtil.d(TAG, "pagerAdapter==" + pagerAdapter);
        mViewPager.setAdapter(pagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager, true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

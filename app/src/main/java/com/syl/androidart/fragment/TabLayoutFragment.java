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
        mRootView = View.inflate(getContext(), R.layout.fragment_tablayout, null);
        unbinder = ButterKnife.bind(this, mRootView);

        PageFragmentPagerAdapter pagerAdapter = new PageFragmentPagerAdapter(getActivity().getSupportFragmentManager(), getContext());
        mViewPager.setAdapter(pagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager, true);
        return mRootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

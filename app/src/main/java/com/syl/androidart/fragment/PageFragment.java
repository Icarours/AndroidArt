package com.syl.androidart.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.syl.androidart.R;
import com.syl.androidart.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Bright on 2017/8/5.
 *
 * @Describe TabLayout控件举例中ViewPager中的子Fragment
 * @Called
 */

public class PageFragment extends BaseFragment {
    private static final String PAGE_ARGS = "page_args";
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    Unbinder unbinder;
    private int mPage;
    private View mRootView;

    @Override
    public View initView() {
        return null;
    }

    private PageFragment() {
    }

    public static PageFragment getInstance(int args) {
        Bundle bundle = new Bundle();
        bundle.putInt(PAGE_ARGS, args);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt("page_args");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = View.inflate(getContext(), R.layout.fragment_page, null);
        unbinder = ButterKnife.bind(this, mRootView);
        mTvTitle.setText("第" + mPage + "页");
        return mRootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

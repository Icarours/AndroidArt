package com.syl.androidart.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.syl.androidart.R;
import com.syl.androidart.activity.ContentActivity;
import com.syl.androidart.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Bright on 2017/8/7.
 *
 * @Describe 其他的框架
 * @Called
 */

public class FrameworksFragment extends BaseFragment implements View.OnClickListener {

    private static final int BTN_OKHTTP = 11;
    private static final int BTN_RETROFIT = 12;
    private static final int BTN_GREENDAO = 13;
    @BindView(R.id.btn_okHttp)
    Button mBtnOkHttp;
    @BindView(R.id.btn_retrofit)
    Button mBtnRetrofit;
    @BindView(R.id.btn_greendao)
    Button mBtnGreendao;
    Unbinder unbinder;
    private View mRootView;

    @Override
    public View initView() {
        return null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = View.inflate(getContext(), R.layout.fragment_frameworks, null);
        }
        unbinder = ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    @Override
    public void initListener() {
        mBtnOkHttp.setOnClickListener(this);
        mBtnRetrofit.setOnClickListener(this);
        mBtnGreendao.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), ContentActivity.class);
        switch (v.getId()) {
            case R.id.btn_okHttp:
                intent.putExtra("fragment_tag", BTN_OKHTTP);
                startActivity(intent);
                break;
            case R.id.btn_retrofit:
                intent.putExtra("fragment_tag", BTN_RETROFIT);
                startActivity(intent);
                break;
            case R.id.btn_greendao:
                intent.putExtra("fragment_tag", BTN_GREENDAO);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}

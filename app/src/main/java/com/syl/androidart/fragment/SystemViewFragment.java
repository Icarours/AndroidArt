package com.syl.androidart.fragment;

import android.content.Intent;
import android.os.Bundle;
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
 * Created by Bright on 2017/8/5.
 *
 * @Describe 系统提供的新控件
 * @Called
 */

public class SystemViewFragment extends BaseFragment implements View.OnClickListener {

    private static final int BTN_TAB_LAYOUT = 1;
    private static final int BTN_CARD_VIEW = 2;
    @BindView(R.id.btn_tab_layout)
    Button mBtnTabLayout;
    @BindView(R.id.btn_card_view)
    Button mBtnCardView;
    Unbinder unbinder;
    private View mRootView;

    @Override
    public View initView() {
        mRootView = View.inflate(getContext(), R.layout.fragment_system_view, null);
        return mRootView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void initListener() {
        super.initListener();
        mBtnTabLayout.setOnClickListener(this);
        mBtnCardView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getContext(), ContentActivity.class);
        switch (v.getId()) {
            case R.id.btn_tab_layout:
                intent.putExtra("fragment_tag", BTN_TAB_LAYOUT);
                startActivity(intent);
                break;
            case R.id.btn_card_view:
                intent.putExtra("fragment_tag", BTN_CARD_VIEW);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}

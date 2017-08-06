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
 * Created by Bright on 2017/8/6.
 *
 * @Describe 常见的图片加载框架
 * @Called
 */

public class ImageLoadFragment extends BaseFragment implements View.OnClickListener {

    private static final int BTN_GLIDE = 7;
    private static final int BTN_FRESCO = 8;
    private static final int BTN_PICASSO = 9;
    private static final int BTN_UIL = 10;
    @BindView(R.id.btn_glide)
    Button mBtnGlide;
    @BindView(R.id.btn_fresco)
    Button mBtnFresco;
    @BindView(R.id.btn_picasso)
    Button mBtnPicasso;
    @BindView(R.id.btn_uil)
    Button mBtnUil;
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
            mRootView = View.inflate(getContext(), R.layout.fragment_image_load, null);
        }
        unbinder = ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    @Override
    public void initListener() {
        mBtnFresco.setOnClickListener(this);
        mBtnGlide.setOnClickListener(this);
        mBtnPicasso.setOnClickListener(this);
        mBtnUil.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getContext(), ContentActivity.class);
        switch (v.getId()) {
            case R.id.btn_glide:
                intent.putExtra("fragment_tag", BTN_GLIDE);
                startActivity(intent);
                break;
            case R.id.btn_fresco:
                intent.putExtra("fragment_tag", BTN_FRESCO);
                startActivity(intent);
                break;
            case R.id.btn_picasso:
                intent.putExtra("fragment_tag", BTN_PICASSO);
                startActivity(intent);
                break;
            case R.id.btn_uil:
                intent.putExtra("fragment_tag", BTN_UIL);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}

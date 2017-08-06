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
 * @Describe 动画举例
 * @Called
 */

public class AnimationFragment extends BaseFragment implements View.OnClickListener {

    private static final int BTN_VIEW_ANIMATION = 4;
    private static final int BTN_DRAWABLE_ANIMATION = 5;
    private static final int BTN_PROPERTY_ANIMATION = 6;
    @BindView(R.id.btn_view_animation_java)
    Button mBtnViewAnimation;
    @BindView(R.id.btn_drawable_animation)
    Button mBtnDrawableAnimation;
    @BindView(R.id.btn_property_animation)
    Button mBtnPropertyAnimation;
    Unbinder unbinder;
    private View mRootView;

    @Override
    public View initView() {
        return null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = View.inflate(getContext(), R.layout.fragment_animation, null);
        unbinder = ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    @Override
    public void initListener() {
        mBtnViewAnimation.setOnClickListener(this);
        mBtnDrawableAnimation.setOnClickListener(this);
        mBtnPropertyAnimation.setOnClickListener(this);
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
            case R.id.btn_view_animation_java:
                intent.putExtra("fragment_tag", BTN_VIEW_ANIMATION);
                startActivity(intent);
                break;
            case R.id.btn_drawable_animation:
                intent.putExtra("fragment_tag", BTN_DRAWABLE_ANIMATION);
                startActivity(intent);
                break;
            case R.id.btn_property_animation:
                intent.putExtra("fragment_tag", BTN_PROPERTY_ANIMATION);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}

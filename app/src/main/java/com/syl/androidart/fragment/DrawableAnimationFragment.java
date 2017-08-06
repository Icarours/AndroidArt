package com.syl.androidart.fragment;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.syl.androidart.R;
import com.syl.androidart.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Bright on 2017/8/6.
 *
 * @Describe Drawable, Frame帧动画举例
 * @Called
 */

public class DrawableAnimationFragment extends BaseFragment {

    @BindView(R.id.iv_img_fight)
    ImageView mIvImgFight;
    Unbinder unbinder;
    @BindView(R.id.iv_img_bomb)
    ImageView mIvImgBomb;
    private View mRootView;

    @Override
    public View initView() {
        return null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = View.inflate(getContext(), R.layout.fragment_drawable_animation, null);
        }
        unbinder = ButterKnife.bind(this, mRootView);
        AnimationDrawable animationDrawableFight = (AnimationDrawable) mIvImgFight.getDrawable();
        AnimationDrawable animationDrawableBomb = (AnimationDrawable) mIvImgBomb.getDrawable();
        if (!animationDrawableFight.isRunning()) {
            animationDrawableFight.start();
        }
        if (!animationDrawableBomb.isRunning()) {
            animationDrawableBomb.start();
        }
        return mRootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

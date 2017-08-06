package com.syl.androidart.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.syl.androidart.R;
import com.syl.androidart.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Bright on 2017/8/6.
 *
 * @Describe View, Tween动画举例java代码
 * @Called
 */

public class ViewAnimationFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.btn_translate_x)
    Button mBtnTranslateX;
    @BindView(R.id.btn_translate_y)
    Button mBtnTranslateY;
    @BindView(R.id.btn_rotate)
    Button mBtnRotate;
    @BindView(R.id.btn_scale)
    Button mBtnScale;
    @BindView(R.id.btn_alpha)
    Button mBtnAlpha;
    @BindView(R.id.btn_set)
    Button mBtnSet;
    @BindView(R.id.iv_img)
    ImageView mIvImg;
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
            mRootView = View.inflate(getContext(), R.layout.fragment_view_animation, null);
        }
        unbinder = ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    @Override
    public void initListener() {
        mBtnTranslateX.setOnClickListener(this);
        mBtnTranslateY.setOnClickListener(this);
        mBtnRotate.setOnClickListener(this);
        mBtnScale.setOnClickListener(this);
        mBtnAlpha.setOnClickListener(this);
        mBtnSet.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_translate_x:
                TranslateAnimation translateAnimationX = new TranslateAnimation(0, 300, 0, 0);
                translateAnimationX.setDuration(1000);
                translateAnimationX.setRepeatMode(Animation.REVERSE);
                translateAnimationX.setFillBefore(true);
                mIvImg.startAnimation(translateAnimationX);
                break;
            case R.id.btn_translate_y:
                TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0, 0, 300);
                translateAnimationY.setDuration(1000);
                translateAnimationY.setRepeatMode(Animation.REVERSE);
                translateAnimationY.setFillBefore(true);
                mIvImg.startAnimation(translateAnimationY);
                break;
            case R.id.btn_rotate:
                RotateAnimation rotateAnimation = new RotateAnimation(0, 90);
                rotateAnimation.setDuration(1000);
                rotateAnimation.setRepeatMode(Animation.REVERSE);
                rotateAnimation.setFillBefore(true);
                mIvImg.startAnimation(rotateAnimation);
                break;
            case R.id.btn_scale:
                ScaleAnimation scaleAnimation = new ScaleAnimation(0.5f, 2f, 0.5f, 2f);
                scaleAnimation.setFillBefore(true);
                scaleAnimation.setDuration(1000);
                scaleAnimation.setRepeatMode(Animation.REVERSE);
                mIvImg.startAnimation(scaleAnimation);
                break;
            case R.id.btn_alpha:
                AlphaAnimation alphaAnimation = new AlphaAnimation(0.2f, 1);
                alphaAnimation.setDuration(1000);
                alphaAnimation.setFillBefore(true);
                alphaAnimation.setRepeatMode(Animation.REVERSE);
                mIvImg.startAnimation(alphaAnimation);
                break;
            case R.id.btn_set:
                AnimationSet animationSet = new AnimationSet(true);

                TranslateAnimation translateSet = new TranslateAnimation(0, 300, 0, 300);
                translateSet.setDuration(1000);
                translateSet.setRepeatMode(Animation.REVERSE);
                translateSet.setFillBefore(true);

                AlphaAnimation alphaSet = new AlphaAnimation(0.2f, 1);
                alphaSet.setDuration(1000);
                alphaSet.setFillBefore(true);
                alphaSet.setRepeatMode(Animation.REVERSE);

                animationSet.addAnimation(translateSet);
                animationSet.addAnimation(alphaSet);
                mIvImg.startAnimation(animationSet);

                break;
            default:
                break;
        }
    }
}

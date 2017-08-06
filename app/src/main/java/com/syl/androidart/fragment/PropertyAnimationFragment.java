package com.syl.androidart.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
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
 * @Describe 属性动画举例ValueAnimator, ObjectAnimator
 * @Called
 */

public class PropertyAnimationFragment extends BaseFragment implements View.OnClickListener {

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
            mRootView = View.inflate(getContext(), R.layout.fragment_property_animation, null);
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
                ObjectAnimator animatorTranslateX = ObjectAnimator.ofFloat(mIvImg, "translationX", 0, 200, 300, 400, 100, 0);
                animatorTranslateX.setDuration(3000);
                animatorTranslateX.setStartDelay(500);
                animatorTranslateX.start();
                break;
            case R.id.btn_translate_y:
                ObjectAnimator animatorTranslateY = ObjectAnimator.ofFloat(mIvImg, "translationY", 0, 200, 300, 400, 100, 0);
                animatorTranslateY.setDuration(3000);
                animatorTranslateY.setStartDelay(500);
                animatorTranslateY.start();
                break;
            case R.id.btn_rotate:
                ObjectAnimator objectAnimatorRotate = ObjectAnimator.ofFloat(mIvImg, "rotation", 0, 45, 90, 180, 270, 90, 0);
                objectAnimatorRotate.setDuration(3000);
                objectAnimatorRotate.setStartDelay(500);
                objectAnimatorRotate.start();
                break;
            case R.id.btn_scale:
                ObjectAnimator objectAnimatorScale = ObjectAnimator.ofFloat(mIvImg, "scaleX", 0, 0.5f, 2.5f, 3f, 2f, 1);
                objectAnimatorScale.setDuration(3000);
                objectAnimatorScale.setStartDelay(500);
                objectAnimatorScale.start();
                break;
            case R.id.btn_alpha:
                ObjectAnimator objectAnimatorAlpha = ObjectAnimator.ofFloat(mIvImg, "alpha", 1, 0.5f, 0.2f, 0.5f, 1);
                objectAnimatorAlpha.setDuration(3000);
                objectAnimatorAlpha.setStartDelay(500);
                objectAnimatorAlpha.start();
                break;
            case R.id.btn_set:
                ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(mIvImg, "alpha", 1.0f, 0f,1.0f);
                ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(mIvImg, "translationY", 0f, 30f, 0f);
                ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(mIvImg, "translationX", 0f, 30f, 0f);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.setDuration(5000);
                animatorSet.setInterpolator(new LinearInterpolator());
                // animatorSet.playTogether(objectAnimator1, objectAnimator2. objectAnimator3); //三个动画同时执行
                // 12同时执行，3接着执行
                animatorSet.play(objectAnimator1).with(objectAnimator2);
                animatorSet.play(objectAnimator3).after(objectAnimator2);
                animatorSet.start();
                break;
            default:
                break;
        }
    }
}

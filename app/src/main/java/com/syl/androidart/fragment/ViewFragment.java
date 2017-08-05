package com.syl.androidart.fragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.syl.androidart.R;
import com.syl.androidart.base.BaseFragment;
import com.syl.androidart.utils.LogUtil;

/**
 * Created by Bright on 2017/7/24.
 *
 * @Describe 自定义View
 * @Called
 */

public class ViewFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = ViewFragment.class.getSimpleName();
    private View rootView;
    private TextView mTvContent;
    private Button mBtnMoveParams;
    private int mScaledTouchSlop;
    private ImageView mImg;
    private MotionEvent mImgEvent;
    private Button mBtnMoveProperty;
    private Button mBtnRestImg;
    private int mLeftMarginStart;
    private int mTopMarginStart;
    private int mRightMarginStart;
    private int mBottomMarginStart;
    private Button mBtnMoveProperty2;

    @Override
    public View initView() {
        rootView = View.inflate(getContext(), R.layout.fragment_view, null);
        mTvContent = (TextView) rootView.findViewById(R.id.tv_content);
        mBtnRestImg = (Button) rootView.findViewById(R.id.btn_reset_img);
        mBtnMoveParams = (Button) rootView.findViewById(R.id.btn_move_params);
        mBtnMoveProperty = (Button) rootView.findViewById(R.id.btn_move_property);
        mBtnMoveProperty2 = (Button) rootView.findViewById(R.id.btn_move_property2);
        mImg = (ImageView) rootView.findViewById(R.id.img);

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mImg.getLayoutParams();
        mLeftMarginStart = params.leftMargin;
        mTopMarginStart = params.topMargin;
        mRightMarginStart = params.rightMargin;
        mBottomMarginStart = params.bottomMargin;
        return rootView;
    }

    @Override
    public void init() {
        //获得TouchSlop,设备识别的最小滑动距离
        mScaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    @Override
    public void initData() {
//        getVelocity();
        mTvContent.setText("获得TouchSlop,设备识别的最小滑动距离 :" + mScaledTouchSlop);
    }

    /**
     * 获得当前速度
     */
    private void getVelocity() {
        //获得当前的速度追踪器VelocityTracker
        VelocityTracker velocityTracker = VelocityTracker.obtain();
        velocityTracker.addMovement(mImgEvent);//这个mImgEvent不能这么用,要用OnTouchEvent

        //计算,获得当前的滑动速度
        velocityTracker.computeCurrentVelocity(1000);//获取速度之前必须先计算速度
        int xVelocity = (int) velocityTracker.getXVelocity();
        int yVelocity = (int) velocityTracker.getYVelocity();

        //速度 = (终点位置-起点位置)/时间段,速度有正负之分,和坐标轴同向,速度为正,反向为负.
        //不需要的时候,清除回收VelocityTracker
        velocityTracker.clear();
        velocityTracker.recycle();

        mTvContent.setText("获得TouchSlop,设备识别的最小滑动距离 :" + mScaledTouchSlop + "\n"
                + "xVelocity =" + xVelocity + "\n"
                + "yVelocity =" + yVelocity + "\n");
    }

    @Override
    public void initListener() {
        mBtnRestImg.setOnClickListener(this);
        mBtnMoveProperty.setOnClickListener(this);
        mBtnMoveProperty2.setOnClickListener(this);
        mBtnMoveParams.setOnClickListener(this);
        mImg.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                    default:
                        break;
                }
                mImgEvent = event;
                return true;
            }
        });

        mGestureDetector.setIsLongpressEnabled(false);
        //boolean consumeEvent = mGestureDetector.onTouchEvent(mImgEvent);//这个mImgEvent不能这么用,要用OnTouchEvent
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_reset_img:
                resetImg();//将图片返回原来位置
                break;
            case R.id.btn_move_property:
                movePropertyImg();//通过属性动画移动图片
                break;
            case R.id.btn_move_property2:
                movePropertyImg2();//通过属性动画移动图片
                break;
            case R.id.btn_move_params:
                moveParamsImg();//通过设置参数移动图片
                break;
            default:
                break;
        }
    }

    /**
     * 通过属性动画移动图片 ValueAnimator
     */
    private void movePropertyImg2() {
        final int startX = 0;
        final int deltaX = -100;
        final ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 1).setDuration(100);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = valueAnimator.getAnimatedFraction();//百分比
                //scrollTo()方法中移动的是图片,ImageView本身不会动,但是为了思考的方便,可以认为移动的是ImageView,图片没动
                mImg.scrollTo(startX + (int) (deltaX * fraction), 0);
            }
        });
        valueAnimator.start();
    }

    /**
     * 将图片返回原来位置
     * 通过设置位置参数的方法只能让通过位置参数移动的图片回到原来的位置,对属性动画产生的位移无效
     */
    private void resetImg() {
        LogUtil.d(TAG, "resetImg()");
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mImg.getLayoutParams();
        params.leftMargin = mLeftMarginStart;
        params.topMargin = mTopMarginStart;
        params.rightMargin = mRightMarginStart;
        params.bottomMargin = mBottomMarginStart;
        mImg.setLayoutParams(params);
    }

    /**
     * 通过属性动画移动图片 ObjectAnimator
     */
    private void movePropertyImg() {
        ObjectAnimator.ofFloat(mImg, "translationX", 0, 200).setDuration(100).start();
    }

    /**
     * 通过设置参数移动图片
     * ImageView的大小一直没变,通过MarginLayoutParams设置margin和padding调整ImageView及其中的图片
     */
    private void moveParamsImg() {

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mImg.getLayoutParams();
        mImg.getWidth();
        mImg.getHeight();
        LogUtil.d(TAG, "重绘前: mImg.getWidth() = " + mImg.getWidth() + ", mImg.getHeight() = " + mImg.getHeight());
        params.leftMargin += 200;
//                params.width += 300;//相当于padding
        params.height += 300;
        mImg.requestLayout();//重绘,重新布局

        mImg.getWidth();
        mImg.getHeight();
        LogUtil.d(TAG, "重绘后: mImg.getWidth() = " + mImg.getWidth() + ", mImg.getHeight() = " + mImg.getHeight());

        //mImg.setLayoutParams(params);//也可以使用该代码实现重绘
    }

    //创建手势识别器
    GestureDetector mGestureDetector = new GestureDetector(getContext(), new GestureDetector.OnGestureListener() {
        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
    });
}

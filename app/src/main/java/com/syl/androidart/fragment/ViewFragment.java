package com.syl.androidart.fragment;

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
 * @Describe
 * @Called
 */

public class ViewFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = ViewFragment.class.getSimpleName();
    private View rootView;
    private TextView mTvContent;
    private Button mBtn;
    private int mScaledTouchSlop;
    private ImageView mImg;
    private MotionEvent mImgEvent;

    @Override
    public View initView() {
        rootView = View.inflate(getContext(), R.layout.fragment_view, null);
        mTvContent = (TextView) rootView.findViewById(R.id.tv_content);
        mBtn = (Button) rootView.findViewById(R.id.btn_move);
        mImg = (ImageView) rootView.findViewById(R.id.img);
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

        mBtn.setOnClickListener(this);
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
            case R.id.btn_move:
                moveImg();//通过设置参数移动图片
                break;
            default:
                break;
        }
    }

    /**
     * 通过设置参数移动图片
     * ImageView的大小一直没变,通过MarginLayoutParams设置margin和padding调整ImageView及其中的图片
     */
    private void moveImg() {
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

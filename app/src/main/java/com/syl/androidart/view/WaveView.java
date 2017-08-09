package com.syl.androidart.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

/**
 * author   Bright
 * date     2017/8/8 17:13
 * desc
 * 波浪 贝塞尔曲线
 */
public class WaveView extends View {

    /**
     * 波峰
     */
    private float mWavePeak = 35f;
    /**
     * 波槽
     */
    private float mWaveTrough = 35f;
    /**
     * 水位
     */
    private float mWaveHeight = 250f;

    private Paint mPaint;

    private Path mPath;

    private int mWaterColor = 0xBB0000FF;

    public WaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public WaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WaveView(Context context) {
        super(context);
        init();
    }

    private void init() {
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mWaterColor);
    }

    private float mWidth, mHeight;

    private PointF mStart;

    private PointF mLeft1, mLeft2;

    private PointF mFirst, mSecond;

    private PointF mRight;

    private PointF mControlLeft1, mControlLeft2;

    private PointF mControlFirst;

    private PointF mControlSecond;

    private boolean mIsRunning = false;

    private boolean mHasInit = false;

    /**
     * @param w    Current width of this view.当前view的宽
     * @param h    Current height of this view.当前view的高
     * @param oldw Old width of this view.先前view的宽
     * @param oldh Old height of this view.先前view的高
     * @called 布局尺寸改变时调用
     * @des 设置view的宽高
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (!mHasInit) {
            mWidth = w;
            mHeight = h;
            mHasInit = true;
        }
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!mIsRunning || !mHasInit)
            return;
        mPath.reset();
        mPath.moveTo(mLeft1.x, mLeft1.y);
        mPath.quadTo(mControlLeft1.x, mControlLeft1.y, mLeft2.x, mLeft2.y);
        mPath.quadTo(mControlLeft2.x, mControlLeft2.y, mFirst.x, mFirst.y);
        mPath.quadTo(mControlFirst.x, mControlFirst.y, mSecond.x, mSecond.y);
        mPath.quadTo(mControlSecond.x, mControlSecond.y, mRight.x, mRight.y);
        mPath.lineTo(mRight.x, mHeight);//逆向连接曲线
        mPath.lineTo(mLeft1.x, mHeight);
        mPath.lineTo(mLeft1.x, mLeft1.y);
        canvas.drawPath(mPath, mPaint);
    }

    private void reset() {
        mStart = new PointF(-mWidth, mHeight - mWaveHeight);//点1(常量)
        mLeft1 = new PointF(-mWidth, mHeight - mWaveHeight);//点1
        mLeft2 = new PointF(-mWidth / 2f, mHeight - mWaveHeight);//点2
        mFirst = new PointF(0, mHeight - mWaveHeight);//点3
        mSecond = new PointF(mWidth / 2f, mHeight - mWaveHeight);//点4
        mRight = new PointF(mWidth, mHeight - mWaveHeight);//点5
        mControlLeft1 = new PointF(mLeft1.x + mWidth / 4f, mLeft1.y + mWavePeak);//控制点1
        mControlLeft2 = new PointF(mLeft2.x + mWidth / 4f, mLeft2.y - mWaveTrough);//控制点2
        mControlFirst = new PointF(mFirst.x + mWidth / 4f, mFirst.y + mWavePeak);//控制点3
        mControlSecond = new PointF(mSecond.x + mWidth / 4f, mSecond.y - mWaveTrough);//控制点4
    }

    private class WaveHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == ANIM_START) {
                reset();
                startAnim();
                mIsRunning = true;
            }
        }
    }

    private WaveHandler mWaveHandler = new WaveHandler();

    private static final int ANIM_START = 1;

    //开启一个线程来播动画
    public void setRunning() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!mHasInit) {//如果没有初始化
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                mWaveHandler.sendEmptyMessage(ANIM_START);
            }
        }).start();
    }

    private void startAnim() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(mStart.x, 0);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(2000);
        valueAnimator.setRepeatCount(Animation.INFINITE);//无限次循环
        //动画效果重复
//        valueAnimator.setRepeatMode(Animation.RESTART);
        //设置监听
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mLeft1.x = (float) animation.getAnimatedValue();
                mLeft2 = new PointF(mLeft1.x + mWidth / 2f, mHeight - mWaveHeight);
                mFirst = new PointF(mLeft2.x + mWidth / 2f, mHeight - mWaveHeight);
                mSecond = new PointF(mFirst.x + mWidth / 2f, mHeight - mWaveHeight);
                mRight = new PointF(mSecond.x + mWidth / 2f, mHeight - mWaveHeight);
                mControlLeft1 = new PointF(mLeft1.x + mWidth / 4f, mLeft1.y + mWavePeak);
                mControlLeft2 = new PointF(mLeft2.x + mWidth / 4f, mLeft2.y - mWaveTrough);
                mControlFirst = new PointF(mFirst.x + mWidth / 4f, mFirst.y + mWavePeak);
                mControlSecond = new PointF(mSecond.x + mWidth / 4f, mSecond.y - mWaveTrough);
                invalidate();
            }
        });
        valueAnimator.start();
    }
}

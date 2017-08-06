package com.syl.androidart.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;

import com.syl.androidart.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author   Bright
 * date     2017/8/6 12:11
 * desc
 * 常用系统属性
 */
public class SystemSettingActivity extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.btn_window_focus_changed)
    Button mBtnWindowFocusChanged;
    @BindView(R.id.btn_start)
    Button mBtnStart;
    private String mResultMeasureInStart;
    private String mResultMeasureInWindowFocusChanged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_setting);
        ButterKnife.bind(this);
        mBtnWindowFocusChanged.setOnClickListener(this);
        mBtnStart.setOnClickListener(this);
    }

    /**
     * 方法一:
     * 在Activity刚启动时获取控件的宽高
     *
     * @param hasFocus
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            int width = mTvContent.getMeasuredWidth();
            int height = mTvContent.getMeasuredHeight();
            mResultMeasureInWindowFocusChanged = "控件的width = " + width + ",height = " + height;
        }
    }

    /**
     * 方法二:
     * 在Activity刚启动时获取控件的宽高,在子线程中获取控件的宽高
     */
    /*@Override
    protected void onStart() {
        super.onStart();
        mTvContent.post(new Runnable() {
            @Override
            public void run() {
                int width = mTvContent.getMeasuredWidth();
                int height = mTvContent.getMeasuredHeight();
            }
        });
    }*/

    /**
     * 方法三:
     * 在Activity刚启动时获取控件的宽高,使用ViewTreeObserver,布局完成之后获得控件的高宽
     */
    @Override
    protected void onStart() {
        super.onStart();
        final ViewTreeObserver observer = mTvContent.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int width = mTvContent.getMeasuredWidth();
                int height = mTvContent.getMeasuredHeight();
                mResultMeasureInStart = "控件的width = " + width + ",height = " + height;

                mTvContent.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_window_focus_changed:
                mTvContent.setText("");
                mTvContent.setText(mResultMeasureInWindowFocusChanged);
                break;
            case R.id.btn_start:
                mTvContent.setText("");
                mTvContent.setBackgroundColor(Color.BLUE);
                mTvContent.setText(mResultMeasureInStart);
                break;
            default:
                break;
        }
    }
}

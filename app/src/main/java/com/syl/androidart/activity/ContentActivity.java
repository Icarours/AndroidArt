package com.syl.androidart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.syl.androidart.R;
import com.syl.androidart.fragment.CardViewFragment;
import com.syl.androidart.fragment.TabLayoutFragment;

/**
 * author   Bright
 * date     2017/7/22 22:36
 * desc
 * 以后详情用Fragment来代替Activity
 * 在Activity刚启动时获取控件的宽高
 */
public class ContentActivity extends AppCompatActivity {

    private TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        Intent intent = getIntent();
        int fragment_tag = intent.getIntExtra("fragment_tag", 1);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (fragment_tag) {
            case 1:
                transaction.replace(R.id.fl_content, new TabLayoutFragment());
                transaction.commit();
                break;
            case 2:
                transaction.replace(R.id.fl_content, new CardViewFragment());
                transaction.commit();
                break;
            default:
                break;
        }
        mTv = new TextView(this);
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
            int width = mTv.getMeasuredWidth();
            int height = mTv.getMeasuredHeight();
        }
    }

    /**
     * 方法二:
     * 在Activity刚启动时获取控件的宽高
     */
    /*@Override
    protected void onStart() {
        super.onStart();
        mTv.post(new Runnable() {
            @Override
            public void run() {
                int width = mTv.getMeasuredWidth();
                int height = mTv.getMeasuredHeight();
            }
        });
    }*/

    /**
     * 方法三:
     * 在Activity刚启动时获取控件的宽高
     */
    @Override
    protected void onStart() {
        super.onStart();
        final ViewTreeObserver observer = mTv.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int width = mTv.getMeasuredWidth();
                int height = mTv.getMeasuredHeight();
                mTv.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }
}

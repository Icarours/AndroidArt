package com.syl.testmodule;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.syl.testmodule.activity.SecondActivity;
import com.syl.testmodule.utils.LogUtil;

import java.util.List;

/**
 * author   Bright
 * date     2017/7/10 23:36
 * desc
 * 测试,各种试验
 * 开启多进程
 * 隐式匹配:action,category,data
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView mTvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogUtil.d(TAG, "onCreate()");

        logRunningTasks();//打印任务栈信息
        mTvContent = (TextView) findViewById(R.id.tv_content);
        if (savedInstanceState != null) {
            String tvContent = savedInstanceState.getString("tvContent", "");
            mTvContent.setText(tvContent);
        }
    }

    /**
     * 打印任务栈信息
     */
    private void logRunningTasks() {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTasks = activityManager.getRunningTasks(10);
        for (ActivityManager.RunningTaskInfo runningTask : runningTasks) {
            LogUtil.d(TAG, runningTask.topActivity.toString());
        }
    }

    public void jump(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
//        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("time", System.currentTimeMillis());
        startActivity(intent);
        //finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.d(TAG, "onPause()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.d(TAG, "onResume()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.d(TAG, "onStart()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.d(TAG, "onStop()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtil.d(TAG, "onRestart()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.d(TAG, "onDestroy()");
    }

    /**
     * Activity被销毁,但是又有可能重新显示的情况下onSaveInstanceState()会被调用,
     * 例如:1.旋转屏幕,销毁Activity再创建Activity
     * 2.内存不足,导致优先级低的Activity被杀死
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        LogUtil.d(TAG, "onSaveInstanceState()");
        outState.putString("tvContent", "hello android");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String tvContent = savedInstanceState.getString("tvContent", "");

        LogUtil.d(TAG, "onRestoreInstanceState()--tvContent" + tvContent);
    }

    /**
     * 系统配置改变时调用
     *
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LogUtil.d(TAG, "onConfigurationChanged()");
    }

    /**
     * 启动模式
     *
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        long time = intent.getLongExtra("time", 0);
        LogUtil.d(TAG, "onNewIntent()---time==" + time);
    }
}

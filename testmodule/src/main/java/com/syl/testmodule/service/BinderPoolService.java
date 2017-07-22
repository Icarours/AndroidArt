package com.syl.testmodule.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.syl.testmodule.utils.LogUtil;

/**
 * author   Bright
 * date     2017/7/22 23:03
 * desc
 * 连接池 service
 */
public class BinderPoolService extends Service {
    private static final String TAG = BinderPoolService.class.getSimpleName();
    private Binder mBinderPool = new BinderPool.BinderPoolIml();//拿到Binder对象

    public BinderPoolService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.d(TAG, "BinderPoolService onBind()");
        return mBinderPool;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

package com.syl.androidart.service;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.syl.androidart.utils.LogUtil;
import com.syl.testmodule.aidl.IBinderPool;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Bright on 2017/7/22.
 *
 * @Describe Binder连接池, 根据binderCode返回相应的Binder
 * @Called
 */

public class BinderPool {
    public static final int BINDER_NOE = -1;
    public static final int BINDER_SECURITY_CENTER = 1;
    public static final int BINDER_COMPUTE = 0;
    private static final String TAG = BinderPool.class.getSimpleName();
    private Context mContext;
    private IBinderPool mIBinderPool;//返回的接口IBinderPool
    @SuppressLint("StaticFieldLeak")
    private static volatile BinderPool sBinderPool;
    private CountDownLatch mCountDownLatch;//确保线程同步
    private ServiceConnection mBinderPoolConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mIBinderPool = IBinderPool.Stub.asInterface(service);
            try {
                mIBinderPool.asBinder().linkToDeath(mDeathRecipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            mCountDownLatch.countDown();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            LogUtil.d(TAG, "BinderPool binderDied()");
            mIBinderPool.asBinder().unlinkToDeath(mDeathRecipient, 0);
            mIBinderPool = null;
            connectBinderPoolService();
        }
    };

    /**
     * 私有化构造方法
     *
     * @param context
     */
    private BinderPool(Context context) {
        mContext = context.getApplicationContext();
        connectBinderPoolService();//连接服务
    }

    /**
     * 对外界提供单例,获取BinderPool对象
     *
     * @param context
     * @return
     */
    public static BinderPool getInstance(Context context) {
        if (sBinderPool == null) {
            synchronized (BinderPool.class) {
                sBinderPool = new BinderPool(context);
            }
        }
        return sBinderPool;
    }

    /**
     * 连接BinderPoolService
     */
    private synchronized void connectBinderPoolService() {
        mCountDownLatch = new CountDownLatch(1);//CountDownLatch是一个同步工具类，它允许一个或多个线程一直等待，直到其他线程的操作执行完后再执行
        Intent intent = new Intent(mContext, BinderPoolService.class);//通过Intent连接BinderPoolService
        mContext.bindService(intent, mBinderPoolConnection, Context.BIND_AUTO_CREATE);
        try {
            mCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据binderCode返回对应Binder
     *
     * @param binderCode
     * @return
     */
    public IBinder queryBinder(int binderCode) {
        IBinder binder = null;
        if (mIBinderPool != null) {
            try {
                binder = mIBinderPool.queryBinder(binderCode);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return binder;
    }

    /**
     * author   Bright
     * date     2017/7/22 23:42
     * desc
     * aidl 继承IBinderPool.Stub
     */
    static class BinderPoolIml extends IBinderPool.Stub {
        /**
         * 根据binderCode返回相应的Binder
         *
         * @param binderCode
         * @return
         * @throws RemoteException
         */
        @Override
        public IBinder queryBinder(int binderCode) throws RemoteException {
            IBinder binder = null;
            switch (binderCode) {
                case BINDER_SECURITY_CENTER:
                    binder = new ISecurityCenterImpl();
                    break;
                case BINDER_COMPUTE:
                    binder = new IComputeImpl();
                    break;
                default:
                    break;
            }
            return binder;
        }
    }
}

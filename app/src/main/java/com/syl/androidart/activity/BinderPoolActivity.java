package com.syl.androidart.activity;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;

import com.syl.androidart.R;
import com.syl.androidart.service.BinderPool;
import com.syl.androidart.service.IComputeImpl;
import com.syl.androidart.service.ISecurityCenterImpl;
import com.syl.androidart.utils.LogUtil;
import com.syl.testmodule.aidl.ICompute;
import com.syl.testmodule.aidl.ISecurityCenter;


/**
 * author   Bright
 * date     2017/7/23 0:45
 * desc
 * 测试Binder连接池
 */
public class BinderPoolActivity extends AppCompatActivity {

    private static final String TAG = BinderPoolActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binder_activiy);
        LogUtil.d(TAG, "BinderPoolActivity onCreate()");
        doWork();
    }

    /**
     * 调用BinderPoolService服务中的方法
     * 进程间通信比较耗时,需要放在子线程中
     */
    private void doWork() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BinderPool binderPool = BinderPool.getInstance(BinderPoolActivity.this);
                IBinder securityBinder = binderPool.queryBinder(BinderPool.BINDER_SECURITY_CENTER);
                ISecurityCenter securityCenter = ISecurityCenterImpl.asInterface(securityBinder);
                LogUtil.d(TAG, "visit ISecurityCenter");
                String msg = "hello 安卓";
                LogUtil.d(TAG, "Content :" + msg);
                try {
                    String encrypt = securityCenter.encrypt(msg);
                    String decrypt = securityCenter.decrypt(encrypt);
                    LogUtil.d(TAG, "encrypt :" + encrypt);
                    LogUtil.d(TAG, "decrypt :" + decrypt);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                LogUtil.d(TAG, "visit ISecurityCenter");
                IBinder computeBinder = binderPool.queryBinder(BinderPool.BINDER_COMPUTE);
                ICompute compute = IComputeImpl.asInterface(computeBinder);
                try {
                    LogUtil.d(TAG, "3+5 = " + compute.add(3, 5));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

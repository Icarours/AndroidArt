package com.syl.androidart.service;

import android.os.RemoteException;

import com.syl.testmodule.aidl.ICompute.Stub;

/**
 * Created by Bright on 2017/7/22.
 *
 * @Describe aidl继承ICompute.Stub
 * 可以添加多个方法,主要为了说明Binder连接池
 * @Called
 */

public class IComputeImpl extends Stub {
    /**
     * 加法
     *
     * @param a
     * @param b
     * @return
     * @throws RemoteException
     */
    @Override
    public int add(int a, int b) throws RemoteException {
        return a + b;
    }
}

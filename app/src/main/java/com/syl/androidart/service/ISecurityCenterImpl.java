package com.syl.androidart.service;

import android.os.RemoteException;

import com.syl.testmodule.aidl.ISecurityCenter;

/**
 * Created by Bright on 2017/7/22.
 *
 * @Describe aidl, 继承ISecurityCenter.Stub
 * 可以添加多个方法,主要为了说明Binder连接池
 * @Called
 */

public class ISecurityCenterImpl extends ISecurityCenter.Stub {
    public static final char SECRET_CODE = '^';

    /**
     * 加密
     *
     * @param content
     * @return
     * @throws RemoteException
     */
    @Override
    public String encrypt(String content) throws RemoteException {
        char[] chars = content.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] ^= SECRET_CODE;
        }
        return new String(chars);
    }

    /**
     * 解密
     *
     * @param password
     * @return
     * @throws RemoteException
     */
    @Override
    public String decrypt(String password) throws RemoteException {
        return encrypt(password);
    }
}

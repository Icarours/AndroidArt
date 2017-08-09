package com.syl.androidart.service;

/**
 * Created by Bright on 2017/4/26.
 *
 * @Describe 服务对外提供的方法
 * 只要服务的内部类实现该接口就行
 * @Called
 */

public interface IXiaoMi {
    void banzheng(String name, long cost);
    long getCurrentTime();
}

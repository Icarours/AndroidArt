package com.syl.androidart.factory;

import android.util.SparseArray;

import com.syl.androidart.base.BaseFragment;
import com.syl.androidart.fragment.CustomViewFragment;
import com.syl.androidart.fragment.FileFragment;
import com.syl.androidart.fragment.MvcFragment;
import com.syl.androidart.fragment.ServiceFragment;
import com.syl.androidart.fragment.ShareFileFragment;
import com.syl.androidart.fragment.ViewFragment;


/**
 * Created by Bright on 2017/5/20.
 *
 * @Describe Fragment 的工厂
 * 缓存管理,如果工厂缓存中有指定的Fragment对象,就直接从缓存拿到该对象
 * 展示具体内容的Fragment
 * @Called
 */

public class FragmentFactory {
    //创建Fragment的缓存
    public static SparseArray<BaseFragment> mFragmentCache = new SparseArray<>();

    public static BaseFragment createFragment(int position) {
        BaseFragment baseFragment;
        //先从缓存中拿baseFragment,如果拿到的baseFragment不是null,就返回该对象.
        baseFragment = mFragmentCache.get(position);
        if (baseFragment != null) {
            return baseFragment;
        }
        //如果baseFragment为null,根据position创建对应的fragment
        switch (position) {
            case 0:
                baseFragment = new FileFragment();//0文件存储(openFileOutput)
                break;
            case 1:
                baseFragment = new ShareFileFragment();//1进程间通信(文件共享)
                break;
            case 2:
                baseFragment = new ViewFragment();//2.文件共享
                break;
            case 3:
                baseFragment = new CustomViewFragment();//3.自定义View
                break;
            case 4:
                baseFragment = new MvcFragment();//4.Mvc
                break;
            case 5:
                baseFragment = new ServiceFragment();//4.Mvc
                break;
            default:
                break;
        }
        mFragmentCache.put(position, baseFragment);
        return baseFragment;
    }
}

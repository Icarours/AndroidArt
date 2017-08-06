package com.syl.androidart.factory;

import android.util.SparseArray;

import com.syl.androidart.base.BaseFragment;
import com.syl.androidart.fragment.CardViewFragment;
import com.syl.androidart.fragment.DrawableAnimationFragment;
import com.syl.androidart.fragment.FrescoFragment;
import com.syl.androidart.fragment.GlideFragment;
import com.syl.androidart.fragment.ListViewFragment;
import com.syl.androidart.fragment.PicassoFragment;
import com.syl.androidart.fragment.PropertyAnimationFragment;
import com.syl.androidart.fragment.RecycleViewFragment;
import com.syl.androidart.fragment.TabLayoutFragment;
import com.syl.androidart.fragment.UILFragment;
import com.syl.androidart.fragment.ViewAnimationFragment;


/**
 * Created by Bright on 2017/5/20.
 *
 * @Describe Activity 的Content Fragment 的工厂
 * 缓存管理,如果工厂缓存中有指定的Fragment对象,就直接从缓存拿到该对象
 * 展示具体内容的Fragment
 * @Called
 */

public class ContentFragmentFactory {
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
            /*
             *系统提供的控件
             */
            case 0:
                baseFragment = new CardViewFragment();//0 CardView
                break;
            case 1:
                baseFragment = new TabLayoutFragment();//1 TabLayout
                break;
            case 2:
                baseFragment = new RecycleViewFragment();//2 RecycleView
                break;
            case 3:
                baseFragment = new ListViewFragment();//3 ListView
                break;
            /*
             * 动画举例
             */
            case 4:
                baseFragment = new ViewAnimationFragment();//4 ViewAnimation
                break;
            case 5:
                baseFragment = new DrawableAnimationFragment();//5 DrawableAnimation
                break;
            case 6:
                baseFragment = new PropertyAnimationFragment();//6 PropertyAnimation
                break;
            /*
             * 常见的图片加载框架
             */
            case 7:
                baseFragment = new GlideFragment();//7 Glide
                break;
            case 8:
                baseFragment = new FrescoFragment();//8 Fresco
                break;
            case 9:
                baseFragment = new PicassoFragment();//9 Picasso
                break;
            case 10:
                baseFragment = new UILFragment();//10 uil
                break;
            default:
                break;
        }
        mFragmentCache.put(position, baseFragment);
        return baseFragment;
    }
}

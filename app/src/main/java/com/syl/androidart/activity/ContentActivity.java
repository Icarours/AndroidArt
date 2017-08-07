package com.syl.androidart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.syl.androidart.R;
import com.syl.androidart.factory.ContentFragmentFactory;

/**
 * author   Bright
 * date     2017/7/22 22:36
 * desc
 * 以后详情用Fragment来代替Activity
 * 在Activity刚启动时获取控件的宽高
 */
public class ContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        //通过ActionBar展示当前Activity的类名
        getSupportActionBar().setTitle(this.getClass().getSimpleName());
        /*
        从SystemViewFragment跳转过来.根据Intent携带的参数判断ContentActivity应该加载那个Fragment
         */
        Intent intent = getIntent();
        int fragment_tag = intent.getIntExtra("fragment_tag", 0);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (fragment_tag) {//Fragment可以使用一个Fragment工厂来提供
            case 0:
                transaction.replace(R.id.fl_content, ContentFragmentFactory.createFragment(0));
                transaction.commit();
                break;
            case 1:
                transaction.replace(R.id.fl_content, ContentFragmentFactory.createFragment(1));
                transaction.commit();
                break;
            case 2:
                transaction.replace(R.id.fl_content, ContentFragmentFactory.createFragment(2));
                transaction.commit();
                break;
            case 3:
                transaction.replace(R.id.fl_content, ContentFragmentFactory.createFragment(3));
                transaction.commit();
                break;
            case 4:
                transaction.replace(R.id.fl_content, ContentFragmentFactory.createFragment(4));
                transaction.commit();
                break;
            case 5:
                transaction.replace(R.id.fl_content, ContentFragmentFactory.createFragment(5));
                transaction.commit();
                break;
            case 6:
                transaction.replace(R.id.fl_content, ContentFragmentFactory.createFragment(6));
                transaction.commit();
                break;
            case 7:
                transaction.replace(R.id.fl_content, ContentFragmentFactory.createFragment(7));
                transaction.commit();
                break;
            case 8:
                transaction.replace(R.id.fl_content, ContentFragmentFactory.createFragment(8));
                transaction.commit();
                break;
            case 9:
                transaction.replace(R.id.fl_content, ContentFragmentFactory.createFragment(9));
                transaction.commit();
                break;
            case 10:
                transaction.replace(R.id.fl_content, ContentFragmentFactory.createFragment(10));
                transaction.commit();
                break;
            case 11:
                transaction.replace(R.id.fl_content, ContentFragmentFactory.createFragment(11));
                transaction.commit();
                break;
            case 12:
                transaction.replace(R.id.fl_content, ContentFragmentFactory.createFragment(12));
                transaction.commit();
                break;
            case 13:
                transaction.replace(R.id.fl_content, ContentFragmentFactory.createFragment(13));
                transaction.commit();
                break;
            case 14:
                transaction.replace(R.id.fl_content, ContentFragmentFactory.createFragment(14));
                transaction.commit();
                break;
            default:
                break;
        }
    }
}

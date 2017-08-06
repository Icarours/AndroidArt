package com.syl.androidart.config;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Process;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by j3767 on 2016/11/16.
 *
 * @Describe 全局的单例, 系统提供的可以很方便的创建单例
 * @Called
 */

public class MyApplication extends Application {

    private static Context mContext;
    private static Handler mMainThreadHandler;
    private static int mMainThreadId;
    private Map<String, String> mMemProtocolMap = new HashMap<>();

    public Map<String, String> getProtocolMap() {
        return mMemProtocolMap;
    }
    /*------------------ 提供对外访问变量的方法(只需要创建一次) -----------------*/

    /**
     * 得到application全局的上下文Context
     *
     * @return
     */
    public static Context getContext() {
        return mContext;
    }

    /**
     * 得到application全局的主线程mMainThreadHandler
     *
     * @return
     */
    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    /**
     * 得到application全局的主线程mMainThreadId
     *
     * @return
     */
    public static int getMainThreadId() {
        return mMainThreadId;
    }

    /*------------------ 提供对外方法的方法 -----------------*/
    @Override
    public void onCreate() {//程序的入口方法,获取常用的一些变量
        //1.application级别的上下文
        mContext = getApplicationContext();
        //2,主线程的id
        mMainThreadId = Process.myTid();
        /**
         * Process.myTid();Thread
         * Process.myUid();User
         * Process.myPid();Process
         */
        //3.Handler
        mMainThreadHandler = new Handler();

        //初始化Fresco
        Fresco.initialize(this);
        //初始化ImageLoader
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
        // method.
        //		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)//
        //				.threadPriority(Thread.NORM_PRIORITY - 2)//
        //				.denyCacheImageMultipleSizesInMemory()//
        //				.diskCacheFileNameGenerator(new Md5FileNameGenerator())//
        //				.diskCacheSize(50 * 1024 * 1024) // 50 Mb
        //				.memoryCache(new LruMemoryCache(4 * 1024 * 1024)).tasksProcessingOrder(QueueProcessingType.LIFO)//
        //				.writeDebugLogs() // Remove for release app
        //				.build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(configuration);

        super.onCreate();
    }
}

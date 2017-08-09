package com.syl.androidart.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

/**
 * author   Bright
 * date     2017/4/26 11:29
 * desc
 * 办证服务
 */
public class BanzhengService extends Service {
    public BanzhengService() {
    }

    /**
     * 直接返回一个内线对象
     *
     * @param intent
     * @return
     */
    @Override
    public IBinder onBind(Intent intent) {
        System.out.println(this.getClass().getSimpleName() + "服务绑定onBind(Intent intent)");
        return new XiaoMi();
    }

    class XiaoMi extends Binder implements IXiaoMi {
        @Override
        public void banzheng(String name, long cost) {
            if (cost < 100) {//不满足条件,返回,给用户友好提示
                System.out.println(name + "," + cost + "不够,我们要按制度办事,一个证100元..");
                return;
            }
            banzhengInService(name, cost);

        }

        @Override
        public long getCurrentTime() {
            return System.currentTimeMillis();
        }

        public void eat() {
            System.out.println("和小蜜一起吃饭");
        }

        public void fun() {
            System.out.println("和小蜜一起happy");
        }
    }


    /**
     * Service中的方法
     *
     * @param name
     * @param cost
     */
    public void banzhengInService(String name, long cost) {
        System.out.println(name + "你的证办好了,花费" + cost + "元");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println(this.getClass().getSimpleName() + "服务创建onCreate()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println(this.getClass().getSimpleName() + "服务销毁onDestroy()");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        System.out.println(this.getClass().getSimpleName() + "服务解除绑定onUnbind(Intent intent)");
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println(this.getClass().getSimpleName() + "服务开始onStartCommand(Intent intent, int flags, int startId)");
        return super.onStartCommand(intent, flags, startId);
    }
}

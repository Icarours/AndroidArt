package com.syl.androidart.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.syl.androidart.config.Constant;
import com.syl.androidart.utils.LogUtil;


/**
 * author   Bright
 * date     2017/7/21 21:09
 * desc
 * 同一个APP,不同的进程
 * 服务器端,处理客户端返回的消息
 */
public class MessengerService extends Service {
    public static final String TAG = MessengerService.class.getSimpleName();
    private static final Messenger mMessenger = new Messenger(new MessengerHandler());

    public MessengerService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.d(TAG, "MessengerService onCreate()");
    }

    /**
     * 返回Binder
     * @param intent
     * @return
     */
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }

    /**
     * author   Bright
     * date     2017/7/21 21:57
     * desc
     * 处理客户端返回的消息的类
     */
    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.MSG_FROM_CLIENT:
                    LogUtil.d(TAG, "receive from client :" + msg.getData().getString("msg"));
                    Messenger messengerClient = msg.replyTo;//拿到客户端的处理消息的Messenger
                    Message replyMsg = Message.obtain(null, Constant.MSG_FROM_SERVICE);//此处使用obtain()方法获取Message,不要new
                    Bundle data = new Bundle();
                    data.putString("reply", "消息已收到,待会儿回复你---Service");
                    replyMsg.setData(data);
                    try {
                        messengerClient.send(replyMsg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    }
}

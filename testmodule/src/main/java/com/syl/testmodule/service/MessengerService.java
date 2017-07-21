package com.syl.testmodule.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;

import com.syl.testmodule.config.Constant;
import com.syl.testmodule.utils.LogUtil;

/**
 * author   Bright
 * date     2017/7/21 21:09
 * desc
 * 服务器端,处理客户端返回的消息
 */
public class MessengerService extends Service {
    public static final String TAG = MessengerService.class.getSimpleName();
    private static final Messenger mMessenger = new Messenger(new MessengerHandler());

    public MessengerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }

    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.MSG_FROM_CLIENT:
                    LogUtil.d(TAG, "receive from client :" + msg.getData().getString("msg"));
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    }
}

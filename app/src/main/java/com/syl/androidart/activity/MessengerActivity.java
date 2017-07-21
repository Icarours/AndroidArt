package com.syl.androidart.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.syl.androidart.R;
import com.syl.androidart.config.Constant;
import com.syl.androidart.service.MessengerService;
import com.syl.androidart.utils.LogUtil;

/**
 * author   Bright
 * date     2017/7/21 22:55
 * desc
 * 进程间通信,Messenger(信使)
 */
public class MessengerActivity extends AppCompatActivity {
    private static final String TAG = MessengerActivity.class.getSimpleName();
    //    @BindView(R.id.tv_content)
    static TextView mTvContent;
    private Messenger mService;
    private Messenger mGetReplayMessenger = new Messenger(new MessengerHandler());//处理服务器返回的消息

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
//        ButterKnife.bind(this);

        mTvContent = (TextView) findViewById(R.id.tv_content);

        Intent intent = new Intent(this, MessengerService.class);
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);

    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = new Messenger(service);
            Message msg = Message.obtain(null, Constant.MSG_FROM_CLIENT);
            Bundle data = new Bundle();
            data.putString("msg", "hello , this is client");
            msg.setData(data);

            //把客户端负责处理消息的Messenger发给服务器端,(mGetReplayMessenger接收服务器回复消息的Messenger)
            msg.replyTo = mGetReplayMessenger;

            try {
                mService.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    /**
     * author   Bright
     * date     2017/7/21 22:09
     * desc
     * 处理服务器返回的消息
     */

    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
//            LogUtil.d(TAG, "MessengerHandler handleMessage()");
            switch (msg.what) {
                case Constant.MSG_FROM_SERVICE:
                    LogUtil.d(TAG, "从服务器返回的消息 --" + msg.getData().getString("reply"));
                    mTvContent.setText(msg.getData().getString("reply"));
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        unbindService(mServiceConnection);
        super.onDestroy();
    }
}

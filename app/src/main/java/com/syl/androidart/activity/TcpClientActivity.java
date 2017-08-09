package com.syl.androidart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.syl.androidart.R;
import com.syl.androidart.service.TcpServerService;
import com.syl.androidart.utils.LogUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author   Bright
 * date     2017/7/22 16:20
 * desc
 * 进程间通信Socket tcp
 * <p>
 * socket tcp通信一直接收不到服务器端返回的数据
 */
public class TcpClientActivity extends AppCompatActivity {
    public static final int MSG_RECEIVE_NEW_MSG = 1;
    public static final int MSG_SOCKET_CONNECTED = 2;
    private static final String TAG = TcpClientActivity.class.getSimpleName();
    private Socket mClientSocket;
    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.et_msg)
    EditText mEtMsg;
    @BindView(R.id.btn_send)
    Button mBtnSend;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_RECEIVE_NEW_MSG:
                    mTvContent.setText(mTvContent.getText() + (String) msg.obj);
                    break;
                case MSG_SOCKET_CONNECTED:
                    mBtnSend.setEnabled(true);
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    };
    private PrintWriter mPrintWriter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcp_client);
        ButterKnife.bind(this);

        Intent intent = new Intent(this, TcpServerService.class);
        startService(intent);
        new Thread(new Runnable() {
            @Override
            public void run() {
                connectedTcpServer();
            }
        }).start();
    }

    /**
     * 连接获取服务器端传输的数据
     * (失败一直连接不上服务器端,郁闷)
     */
    private void connectedTcpServer() {
//        Socket socket = null;
        while (mClientSocket == null) {
            try {//监视端口,获取连接
                mClientSocket = new Socket("localhost", 8688);
                // TODO: 2017/7/22 一直连接不上服务器端,郁闷
//                mClientSocket = socket;
                mPrintWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(mClientSocket.getOutputStream())), true);
                mHandler.sendEmptyMessage(MSG_SOCKET_CONNECTED);
                LogUtil.d(TAG, "server connected success");
            } catch (IOException e) {
                SystemClock.sleep(1000);
                LogUtil.d(TAG, "connect tcp server failed , retry ....");
                e.printStackTrace();
            }
        }

        //接收服务器端获得的消息
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(mClientSocket.getInputStream()));
            while (!TcpClientActivity.this.isFinishing()) {
                String msg = br.readLine();
                LogUtil.d(TAG, "receive from server :" + msg);
                if (msg != null) {
                    String time = new SimpleDateFormat("(HH:mm:ss)").format(new Date(System.currentTimeMillis()));
                    String showedMsg = "server " + time + ":" + msg + "\n";
                    Message message = Message.obtain(mHandler);
                    message.obj = showedMsg;
                    message.what = MSG_RECEIVE_NEW_MSG;
                    mHandler.sendMessage(message);
//                    mHandler.obtainMessage(MSG_RECEIVE_NEW_MSG, showedMsg).sendToTarget();
                }
            }
            LogUtil.d(TAG, "quit");
            mPrintWriter.close();
            br.close();
            mClientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_send)
    public void btn_send(View view) {
        String etMsg = mEtMsg.getText().toString();
        if (!TextUtils.isEmpty(etMsg) && mPrintWriter != null) {
            mPrintWriter.println(etMsg);
            mEtMsg.setText("");
            String time = new SimpleDateFormat("(HH:mm:ss)").format(new Date(System.currentTimeMillis()));
            String showedMsg = "client " + time + ":" + etMsg + "\n";
            mTvContent.setText(mTvContent.getText() + showedMsg);
        }
    }

    @Override
    protected void onDestroy() {
        if (mClientSocket != null) {
            try {
                mClientSocket.shutdownInput();
                mClientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onDestroy();
    }
}

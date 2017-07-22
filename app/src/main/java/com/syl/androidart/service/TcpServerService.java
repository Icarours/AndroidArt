package com.syl.androidart.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.syl.androidart.utils.LogUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 * author   Bright
 * date     2017/7/22 15:33
 * desc
 * 进程间通信socket tcp
 */
public class TcpServerService extends Service {
    private static final String TAG = TcpServerService.class.getSimpleName();
    private boolean mIsServiceDestroyed = false;
    private String[] mDefinedMessages = {
            "你好,"
            , "请问你叫什么名字?"
            , "今天的天气不错"
            , "你知道吗?我可以和多人来聊天"
            , "给你讲个笑话吧,据说爱笑的人运气不会太差"
    };

    public TcpServerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public void onCreate() {
        new Thread(new TcpServer()).start();
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        mIsServiceDestroyed = true;
        super.onDestroy();
    }

    /**
     * author   Bright
     * date     2017/7/22 15:40
     * desc
     * 服务器端
     */
    private class TcpServer implements Runnable {

        @Override
        public void run() {
            ServerSocket serverSocket ;
            try {
                serverSocket = new ServerSocket(8688);//监听本地端口
            } catch (IOException e) {
                LogUtil.d(TAG, "establish tcp server failed , port : 8688");
                e.printStackTrace();
                return;
            }
            while (!mIsServiceDestroyed) {
                try {//接收客户端消息
                    final Socket client = serverSocket.accept();
                    LogUtil.d(TAG, "client accept");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                responseClient(client);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * 回复客户端
         *
         * @param client
         */
        private void responseClient(Socket client) throws IOException {
            //接收客户消息
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            //向客户端发送消息
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), false);
            out.println("welcome chatRoom");
            while (!mIsServiceDestroyed) {
                String str = in.readLine();
                LogUtil.d(TAG, "message from client " + str);
                if (str == null) {
                    //客户端断开
                    break;
                }
                int i = new Random().nextInt(mDefinedMessages.length);
                String msg = mDefinedMessages[i];
                out.println(msg);
                LogUtil.d(TAG, "server send msg :" + msg);
            }
            LogUtil.d(TAG, "client quit");
            out.close();
            in.close();
            client.close();
        }
    }
}

package com.syl.androidart.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.syl.androidart.R;
import com.syl.androidart.utils.LogUtil;
import com.syl.androidart.utils.ServiceUtils;
import com.syl.testmodule.aidl.Book;
import com.syl.testmodule.aidl.IBookManager;
import com.syl.testmodule.aidl.IOnNewBookArrivedListener;

import java.util.List;

/**
 * author   Bright
 * date     2017/7/22 0:35
 * desc
 * 进程间通信(远程aidl,两个APP之间交换数据,一个APP从另一个APP中获取数据)
 */
public class BookManagerActivity extends AppCompatActivity {

    private static final String TAG = BookManagerActivity.class.getSimpleName();
    private TextView mTvContent;
    private static final int MESSAGE_NEW_BOOK_ARRIVED = 1;
    private IOnNewBookArrivedListener mIOnNewBookArrivedListener = new IOnNewBookArrivedListener.Stub() {
        //接收服务器端发送的新书到达的通知
        @Override
        public void onNewBookArrived(Book newBook) throws RemoteException {
            mHandler.obtainMessage(MESSAGE_NEW_BOOK_ARRIVED, newBook).sendToTarget();
        }
    };
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBookManager = IBookManager.Stub.asInterface(service);
            try {
                List<Book> bookList = mBookManager.getBookList();
                LogUtil.d(TAG, "query book list ,list type :" + bookList.getClass().getCanonicalName());
                LogUtil.d(TAG, "query book list :" + bookList.toString());

                Book book = new Book(4, "android");
                mBookManager.add(book);
                List<Book> bookListNew = mBookManager.getBookList();
                LogUtil.d(TAG, "query book list :" + bookListNew.toString());

                mTvContent.setText(bookListNew.toString());
                mBookManager.registerListener(mIOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBookManager = null;
            LogUtil.d(TAG, "binder died");
        }
    };
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_NEW_BOOK_ARRIVED:
                    LogUtil.d(TAG, "new book received " + msg.obj);
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    };
    private IBookManager mBookManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_manager);
        mTvContent = (TextView) findViewById(R.id.tv_content_book_manager);
// 在此处加载远程调用的数据,刷新UI会报错,可能是因为远程服务的数据还没有拿到,因此要在onServiceConnected()方法中刷新UI
//        mTvContent.setText(mBookList.toString());
        remoteService();
    }

    private void remoteService() {
        /**远程调用隐式服务*/
        Intent intent = new Intent();
        intent.setAction("com.syl.BOOK_MANAGER");
        Intent intent1 = new Intent(ServiceUtils.createExplicitFromImplicitIntent(this, intent));
        bindService(intent1, mConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        if (mBookManager != null && mBookManager.asBinder().isBinderAlive()) {
            try {
                LogUtil.d(TAG, "listener unregister " + mIOnNewBookArrivedListener);
                mBookManager.unRegisterListener(mIOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(mConnection);
        super.onDestroy();
    }
}

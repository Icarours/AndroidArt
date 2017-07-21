package com.syl.testmodule.service;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SystemClock;

import com.syl.testmodule.aidl.Book;
import com.syl.testmodule.aidl.IBookManager;
import com.syl.testmodule.aidl.IOnNewBookArrivedListener;
import com.syl.testmodule.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * author   Bright
 * date     2017/7/21 23:16
 * desc
 * book管理的Service,提供远程服务
 */
public class BookManagerService extends Service {
    public static final String TAG = BookManagerService.class.getSimpleName();
    private List<Book> mBookList = new ArrayList<>();
    //    private List<IOnNewBookArrivedListener> mListenerList = new ArrayList<>();//List接口不能使IBookManager正确解绑,用RemoteCallbackList代替
    private RemoteCallbackList<IOnNewBookArrivedListener> mListenerList = new RemoteCallbackList<>();
    private AtomicBoolean mIsServiceDestroyed = new AtomicBoolean(false);
    private IBinder mBinder = new IBookManager.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void add(Book book) throws RemoteException {
            mBookList.add(book);
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
//            if (!mListenerList.contains(listener)) {
//                mListenerList.add(listener);
//            } else {
//                LogUtil.d(TAG, "listener already exist");
//            }

            mListenerList.register(listener);
            //getRegisteredCallbackCount返回观察者的数量
            LogUtil.d(TAG, "register listener ,size " + mListenerList.getRegisteredCallbackCount());
        }

        @Override
        public void unRegisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
//            if (mListenerList.contains(listener)) {
//                mListenerList.remove(listener);
//                LogUtil.d(TAG, "unregister listener success");
//            } else {
//                LogUtil.d(TAG, "not found , can not unregister");
//            }
            mListenerList.unregister(listener);
            LogUtil.d(TAG, "unregister Listener ,current size " + mListenerList.getRegisteredCallbackCount());
        }

        /**
         * 此处也可以进行权限校验的设置
         * @param code
         * @param data
         * @param reply
         * @param flags
         * @return
         * @throws RemoteException
         */
        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            return super.onTransact(code, data, reply, flags);
        }
    };

    public BookManagerService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book(1, "book 1"));
        mBookList.add(new Book(2, "book 2"));
        mBookList.add(new Book(3, "book 3"));

        new Thread(new ServiceWork()).start();
    }

    /**
     * author   Bright
     * date     2017/7/22 2:44
     * desc
     * 每五秒增加一本书,并通知客户端
     */
    private class ServiceWork implements Runnable {
        @Override
        public void run() {
            //doing background progress here
            while (!mIsServiceDestroyed.get()) {
                SystemClock.sleep(5000);
                int bookId = mBookList.size() + 1;
                Book newBook = new Book(bookId, "new Book ---" + bookId);
                try {
                    onNewBookArrived(newBook);//通知新书到来
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 通知新书到来
     *
     * @param newBook
     * @throws RemoteException
     */
    private void onNewBookArrived(Book newBook) throws RemoteException {
        mBookList.add(newBook);
        LogUtil.d(TAG, "onNewBookArrived notify listener " + mBookList.size());
        int N = mListenerList.beginBroadcast();
        for (int i = 0; i < N; i++) {
            IOnNewBookArrivedListener listener = mListenerList.getBroadcastItem(i);
            LogUtil.d(TAG, "onNewBookArrived notify listener " + listener);
            if (listener != null) {
                listener.onNewBookArrived(newBook);
            }
        }
        mListenerList.finishBroadcast();
    }

    /**
     * 返回一个IBinder对象,必须返回
     * 验证权限
     *
     * @param intent
     * @return
     */
    @Override
    public IBinder onBind(Intent intent) {
        int checkCode = checkCallingOrSelfPermission("com.syl.testmodule.permission.ACCESS_BOOK_MANAGER_SERVICE");
        if (checkCode == PackageManager.PERMISSION_DENIED) {
            return null;
        }
        return mBinder;
    }

    @Override
    public void onDestroy() {
        mIsServiceDestroyed.set(true);
        super.onDestroy();
    }
}

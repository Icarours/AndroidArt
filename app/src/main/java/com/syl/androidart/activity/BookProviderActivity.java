package com.syl.androidart.activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.syl.androidart.R;
import com.syl.androidart.bean.Book;
import com.syl.androidart.bean.User1;
import com.syl.androidart.provider.BookProvider;
import com.syl.androidart.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author   Bright
 * date     2017/7/22 12:00
 * desc
 * provider内容提供者
 */
public class BookProviderActivity extends AppCompatActivity {

    private static final String TAG = BookProviderActivity.class.getSimpleName();
    private static final int MSG_DATA = 1;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_DATA:
                    mTvContent.setText(mList.toString());
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    };
    private List<Parcelable> mList = new ArrayList<>();
    @BindView(R.id.tv_content)
    TextView mTvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_provider);
        ButterKnife.bind(this);

        queryProvider();//添加,查询数据provider
    }

    /**
     * 添加,查询数据provider
     */
    private void queryProvider() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                insertAndQueryBook();
                queryUser();
                Message msg = Message.obtain(mHandler);
                msg.what = MSG_DATA;
                msg.obj = mList;
                mHandler.sendMessage(msg);
            }
        }).start();
    }

    /**
     * 查询用户
     * getContentResolver().query()方法API>=16
     */
    private void queryUser() {
        Uri userUri = Uri.parse("content://com.syl.androidart.book.provider/user");//创建userUri
        Cursor cursorUser = getContentResolver().query(userUri, new String[]{"_id", "name", "sex"}, null, null, null);
        while (cursorUser.moveToNext()) {
            User1 user1 = new User1();
            user1.setUserId(cursorUser.getInt(0));
            user1.setUserName(cursorUser.getString(1));
            user1.setMale(cursorUser.getInt(2) == 1);
//            user1.setMale(cursorUser.getInt(2) == 0 ? true : false);
            mList.add(user1);
            LogUtil.d(TAG, "query user1 " + user1.toString());
        }
        cursorUser.close();
    }

    /**
     * 添加和查询书籍
     * getContentResolver().query()方法API>=16
     */
    private void insertAndQueryBook() {
        //                Uri bookUri = Uri.parse("content://com.syl.androidart.book.provider/book");//创建bookUri,不同的APP下,就要使用这种方法了
        Uri bookUri = BookProvider.BOOK_CONTENT_URI;//创建bookUri,同一个APP下可以直接调用
        ContentValues values = new ContentValues();
        values.put("_id", 7);
        values.put("name", "Android基础");
        getContentResolver().insert(bookUri, values);
        Cursor bookCursor = getContentResolver().query(bookUri, new String[]{"_id", "name"}, null, null, null);
        while (bookCursor.moveToNext()) {
            Book book = new Book();
            book.setBookId(bookCursor.getInt(0));
            book.setBookName(bookCursor.getString(1));
            mList.add(book);
            LogUtil.d(TAG, "query book " + book.toString());
        }
        bookCursor.close();
    }
}

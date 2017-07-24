package com.syl.androidart.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.syl.androidart.activity.BookProviderActivity;
import com.syl.androidart.db.DbOpenHelper;
import com.syl.androidart.utils.LogUtil;

/**
 * author   Bright
 * date     2017/7/22 11:56
 * desc
 * provider内容提供者,不需要再清单文件中注册,其他三大组件都要在清单文件中注册
 * 除了onCreate()方法运行在main线程中外,其他5个回调都运行在binder线程池中
 */
public class BookProvider extends ContentProvider {
    public static final String TAG = BookProviderActivity.class.getSimpleName();
    public static final String AUTHORITY = "com.syl.androidart.book.provider";
    public static final Uri BOOK_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/book");
    public static final Uri USER_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/user");
    public static final int BOOK_URI_CODE = 0;
    public static final int USER_URI_CODE = 1;
    private SQLiteDatabase mDb;//声明数据库
    private Context mContext;
    public static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    /*
      provide匹配器
     */
    static {
        sUriMatcher.addURI(AUTHORITY, "book", BOOK_URI_CODE);
        sUriMatcher.addURI(AUTHORITY, "user", USER_URI_CODE);
    }

    public BookProvider() {
    }

    /**
     * 删除,刷新数据库
     *
     * @param uri
     * @param selection
     * @param selectionArgs
     * @return
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        LogUtil.d(TAG, "delete()--current Thread ==" + Thread.currentThread().getName());
        String tableName = getTableName(uri);
        if (tableName == null) {
            throw new IllegalArgumentException("Unsupported Uri " + uri);
        }
        int deleteCount = mDb.delete(tableName, selection, selectionArgs);
        if (deleteCount > 0) {
            mContext.getContentResolver().notifyChange(uri, null);//刷新数据库
        }
        return deleteCount;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        LogUtil.d(TAG, "getType()--current Thread ==" + Thread.currentThread().getName());
        return null;
    }

    /**
     * 插入数据后provider已经改变,记得要刷新
     *
     * @param uri
     * @param values
     * @return
     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        LogUtil.d(TAG, "insert()--current Thread ==" + Thread.currentThread().getName());
        String tableName = getTableName(uri);
        if (tableName == null) {
            throw new IllegalArgumentException("Unsupported Uri " + uri);
        }
        mDb.insert(tableName, null, values);
        mContext.getContentResolver().notifyChange(uri, null);//插入数据后要刷新
        return uri;
    }

    /**
     * 返回值为true代表provider加载成功
     *
     * @return
     */
    @Override
    public boolean onCreate() {
        LogUtil.d(TAG, "onCreate()--current Thread ==" + Thread.currentThread().getName());
        mContext = getContext();
        initProvider();//provider中可能会有一些耗时操作,最好不要在主线程中操作
        return true;
    }

    /**
     * 初始化provider
     * provider中可能会有一些耗时操作,最好不要在主线程中操作
     */
    private void initProvider() {
        mDb = new DbOpenHelper(mContext).getWritableDatabase();
        mDb.execSQL("delete from " + DbOpenHelper.BOOK_TABLE_NAME);
        mDb.execSQL("delete from " + DbOpenHelper.USER_TABLE_NAME);
        mDb.execSQL("insert into book values(1,'android');");
        mDb.execSQL("insert into book values(2,'ios');");
        mDb.execSQL("insert into book values(3,'html5');");
        mDb.execSQL("insert into user values(4,'tom',1);");
        mDb.execSQL("insert into user values(5,'jack',1);");
        mDb.execSQL("insert into user values(6,'张三',0);");
    }

    /**
     * 数据库查询,除了查询外,其他三个操作都要刷新数据库
     *
     * @param uri
     * @param projection
     * @param selection
     * @param selectionArgs
     * @param sortOrder
     * @return
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        LogUtil.d(TAG, "query()--current Thread ==" + Thread.currentThread().getName());
        String tableName = getTableName(uri);
        if (tableName == null) {
            throw new IllegalArgumentException("Unsupported Uri " + uri);
        }
        return mDb.query(tableName, projection, selection, selectionArgs, null, null, sortOrder, null);
    }

    /**
     * 刷新数据库
     *
     * @param uri
     * @param values
     * @param selection
     * @param selectionArgs
     * @return
     */
    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
//        throw new UnsupportedOperationException("Not yet implemented");
        LogUtil.d(TAG, "update()--current Thread ==" + Thread.currentThread().getName());
        String tableName = getTableName(uri);
        if (tableName == null) {
            throw new IllegalArgumentException("Unsupported Uri " + uri);
        }
        int updateRow = mDb.update(tableName, values, selection, selectionArgs);
        if (updateRow > 0) {
            mContext.getContentResolver().notifyChange(uri, null);//刷新数据库
        }
        return updateRow;
    }

    /**
     * 获取数据库的表的名称
     *
     * @param uri
     * @return
     */
    private String getTableName(Uri uri) {
        String tableName = null;
        switch (sUriMatcher.match(uri)) {
            case BOOK_URI_CODE:
                tableName = DbOpenHelper.BOOK_TABLE_NAME;
                break;
            case USER_URI_CODE:
                tableName = DbOpenHelper.USER_TABLE_NAME;
                break;
            default:
                break;
        }
        return tableName;
    }
}

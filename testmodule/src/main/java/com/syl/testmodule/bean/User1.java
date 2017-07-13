package com.syl.testmodule.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Bright on 2017/7/13.
 *
 * @Describe Parcelable序列化举例
 * @Called
 */

public class User1 implements Parcelable {
    private int userId;
    private String userName;
    private boolean isMale;

    Book mBook;

    /**
     * 构造方法自己实现
     *
     * @param userId
     * @param userName
     * @param isMale
     */
    public User1(int userId, String userName, boolean isMale) {
        this.userId = userId;
        this.userName = userName;
        this.isMale = isMale;
    }

    /**
     * 私有构造方法自己实现
     *
     * @param in
     */
    private User1(Parcel in) {
        userId = in.readInt();
        userName = in.readString();
        isMale = in.readInt() == 1;
        mBook = in.readParcelable(Thread.currentThread().getContextClassLoader());
    }

    /**
     * as自动实现
     * 反序列化,创建序列化对象和序列化数组
     */
    public static final Creator<User1> CREATOR = new Creator<User1>() {
        @Override
        public User1 createFromParcel(Parcel in) {
            return new User1(in);
        }

        @Override
        public User1[] newArray(int size) {
            return new User1[size];
        }
    };

    /**
     * as自动实现
     * 功能描述,一般返回0,只有存在描述文档的情况下返回1
     *
     * @return
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * 必须自己实现
     * 序列化,由Parcel的一系列read()方法完成
     *
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(userId);
        dest.writeString(userName);
        dest.writeInt(isMale ? 1 : 0);
        dest.writeParcelable(mBook, 0);
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }
}

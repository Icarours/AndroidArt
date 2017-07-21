package com.syl.androidart.bean;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by Bright on 2017/7/11.
 *
 * @Describe bean   Serializable序列化接口
 * @Called
 */

public class User implements Serializable {
    private int userId;
    private String userName;
    private boolean isMale;

    public User(int userId, String userName, boolean isMale) {
        this.userId = userId;
        this.userName = userName;
        this.isMale = isMale;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (getUserId() != user.getUserId()) return false;
        if (isMale() != user.isMale()) return false;
        return getUserName().equals(user.getUserName());

    }

    @Override
    public int hashCode() {
        int result = getUserId();
        result = 31 * result + getUserName().hashCode();
        result = 31 * result + (isMale() ? 1 : 0);
        return result;
    }

    /**
     * 将对象写到cache.txt文件中
     *
     * @throws IOException
     */
    void serialize() throws IOException {
        User user = new User(1, "tom", true);
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("cache.txt"));
        oos.writeObject(user);
        oos.close();
    }

    /**
     * 将cache.txt文件中的数据读进对象
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    void unSerialize() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("cache.txt"));
        User user = (User) ois.readObject();
        ois.close();
    }
}

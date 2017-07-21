// IBookManager.aidl
package com.syl.testmodule.aidl;

// Declare any non-default types here with import statements
import com.syl.testmodule.aidl.Book;
import com.syl.testmodule.aidl.IOnNewBookArrivedListener;//一定要导包
interface IBookManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
    List<Book> getBookList();
    void add(in Book book);
    //aidl文件除了使用基本数据类型,实现了Parcelable接口的数据,ArrayList,HashMap,还能使用aidl文件
    void registerListener(IOnNewBookArrivedListener listener);
    void unRegisterListener(IOnNewBookArrivedListener listener);
}

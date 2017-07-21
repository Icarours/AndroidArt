// IOnNewBookArrivedListener.aidl
package com.syl.testmodule.aidl;

// Declare any non-default types here with import statements
import com.syl.testmodule.aidl.Book;//一定要导包
interface IOnNewBookArrivedListener {
//实现Parcelable接口的对象要标明输入输出类型:in,out,inout
    void onNewBookArrived(in Book newBook);
}

package com.syl.androidart.config;

/**
 * Created by Bright on 2017/7/10.
 *
 * @Describe
 * @Called
 */

public class Constant {
    public static final int MSG_FROM_SERVICE = 1;//服务器端的识别码
    public static final int MSG_FROM_CLIENT = 2;//客户器端的识别码
    public static final String DIR = "/sdcard/test.txt";
    //Fragment 的标题
    public static String[] mTitles = {
            "文件存储(openFileOutput)",
            "进程间通信(文件共享)",
            "跟View相关的",
            "自定义View",
            "mvc",
            "service",
    };
    //Fragment的状态,是否处于运行状态
    public static boolean[] mFragmentIsActives = {false, false, false,
            false,
            false,
            false,};
    //控制日志打印工具是否打印日志
    public static boolean isLog = true;

    public static class URLS {
        public static final String BASEURL = "http://192.168.31.133:8080/";
    }
}

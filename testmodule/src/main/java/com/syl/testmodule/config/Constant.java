package com.syl.testmodule.config;

/**
 * Created by Bright on 2017/7/10.
 *
 * @Describe
 * @Called
 */

public class Constant {
    public static final int MSG_FROM_SERVICE = 1;//服务器端的识别码
    public static final int MSG_FROM_CLIENT = 2;//客户器端的识别码
    //Fragment 的标题
    public static String[] mTitles = {
            "文件存储(openFileOutput)",
            "文件存储sp",
            "文件存储sqlite",
            "RadioButton/RadioGroup",
            "ContentProvider/联系人",
            "WebView",
            "HttpURLConnection",
            "okHttp_xml",
            "okHttp_json",
            "Alarm机制",
    };
    //Fragment的状态,是否处于运行状态
    public static boolean[] mFragmentIsActives = {false, false, false, false,
            false,
            false,
            false,
            false,
            false,
            false,};
    //控制日志打印工具是否打印日志
    public static boolean isLog = true;

    public static class URLS {
        public static final String BASEURL = "http://192.168.31.133:8080/";
    }
}

package com.syl.androidart.utils;

import android.content.Context;
import android.net.Uri;

/**
 * Created by Bright on 2017/8/10.
 *
 * @Describe Uri的帮助类
 * @Called
 */

public class UriUtils {
    public static final String ANDROID_RESOURCE = "android.resource://";
    public static final String FOREWARD_SLASH = "/";

    /**
     * 将resourceid转换为Uri ,android.resource://+ "context.getPackageName()"+"/"+"resourceId"
     * @param context
     * @param resourceId
     * @return
     */
    public static Uri resourceIdToUri(Context context, int resourceId) {
        return Uri.parse(ANDROID_RESOURCE + context.getPackageName() + FOREWARD_SLASH + resourceId);
    }
}

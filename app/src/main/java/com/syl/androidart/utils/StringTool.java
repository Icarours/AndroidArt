package com.syl.androidart.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Bright on 2017/4/24.
 *
 * @Describe inputStream2String
 * @Called
 */

public class StringTool {
    public static String inputStream2String(InputStream inputStream) throws IOException {
        //底层流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int len;
        byte[] buf = new byte[1024 * 8];
        while ((len = inputStream.read(buf)) > 0) {
            baos.write(buf, 0, len);
        }
        return baos.toString();
    }
}

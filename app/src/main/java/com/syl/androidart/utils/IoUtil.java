package com.syl.androidart.utils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Bright on 2017/7/13.
 *
 * @Describe io流读写工具类
 * @Called
 */

public class IoUtil {
    /**
     * 关闭ObjectOutputStream流
     *
     * @param oos
     */
    public static void close(ObjectOutputStream oos) {
        if (oos != null) {
            try {
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭ObjectInputStream流
     *
     * @param ois
     */
    public static void close(ObjectInputStream ois) {
        if (ois != null) {
            try {
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

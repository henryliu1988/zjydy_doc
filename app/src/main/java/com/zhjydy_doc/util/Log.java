package com.zhjydy_doc.util;

/**
 * Created by admin on 2016/7/29.
 */
public class Log {
    /**
     * 是否开启debug
     */
    public static boolean isDebug = true;

    private static String head = "zhuanjia121_doc";

    /**
     * 打印错误
     *
     * @param clazz
     * @param msg
     */
    public static void e(Class<?> clazz, String msg) {
        if (isDebug) {
            android.util.Log.i(head + clazz.getSimpleName(), msg + "");
        }
    }

    /**
     * 打印信息
     *
     * @param clazz
     * @param msg
     */
    public static void i(Class<?> clazz, String msg) {
        if (isDebug) {
            android.util.Log.i(head + clazz.getSimpleName(), msg + "");
        }
    }

    /**
     * 打印警告
     *
     * @param clazz
     * @param msg
     */
    public static void w(Class<?> clazz, String msg) {
        if (isDebug) {
            android.util.Log.w(head + clazz.getSimpleName(), msg + "");
        }
    }

    /**
     * 打印debug
     *
     * @param clazz
     * @param msg
     */
    public static void d(Class<?> clazz, String msg) {
        if (isDebug) {
            android.util.Log.d(head + clazz.getSimpleName(), msg + "");
        }
    }
}

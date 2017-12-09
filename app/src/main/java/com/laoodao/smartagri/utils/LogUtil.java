package com.laoodao.smartagri.utils;

import android.util.Log;


public class LogUtil {

    public static String TAG = "SCMall";
    private static boolean enable = true;

    public LogUtil() {
    }

    public static void debug(boolean enable) {
        LogUtil.enable = enable;
    }

    public static void d(String msg) {
        if (enable)
            Log.d(TAG, format(msg));
    }

    public static final void d(String msg, Throwable throwable) {
        if (enable)
            Log.d(TAG, format(msg), throwable);
    }

    public static void e(String msg) {
        if (enable)
            Log.e(TAG, format(msg));
    }

    public static void e(String msg, Throwable throwable) {
        if (enable)
            Log.e(TAG, format(msg), throwable);
    }

    public static void i(String msg) {
        if (enable) Log.i(TAG, format(msg));
    }

    public static void v(String msg) {
        if (enable)
            Log.v(TAG, format(msg));
    }

    public static void w(String msg) {
        if (enable) Log.w(TAG, format(msg));
    }

    private static final String format(String msg) {
        StackTraceElement element = null;

        try {
            element = Thread.currentThread().getStackTrace()[4];
            String[] items = element.getClassName().split("[.]");
            msg = String.format("[%s.%s:%s]:%s", new Object[]{items[items.length - 1], element.getMethodName(), Integer.valueOf(element.getLineNumber()), msg});
        } catch (Exception var3) {
            ;
        }

        return msg;
    }

}

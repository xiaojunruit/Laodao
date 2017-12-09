package com.laoodao.smartagri.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileFilter;

/**
 * Created by WORK on 2017/4/3.
 */

public class Cleaner {

    private static FileFilter filter = new FileFilter() {
        public boolean accept(File file) {
            return file.getPath().toLowerCase().contains("webview");
        }
    };

    public static String getTotalCacheSize(Context context) {
        long size = 0L;

        try {
            size += context.getDatabasePath("webview.db").length();
            size += context.getDatabasePath("webviewCache.db").length();
        } catch (Exception var7) {
            var7.printStackTrace();
        }

        File[] e = context.getDir(".", 0).listFiles(filter);
        int var4 = e.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            File dir = e[var5];
            size += FileUtil.calcDirectorySize(dir);
        }

        size += FileUtil.calcDirectorySize(context.getCacheDir());
        if(Environment.getExternalStorageState().equals("mounted")) {
            size += FileUtil.calcDirectorySize(context.getExternalCacheDir());
        }

        return FileUtil.size(size);
    }


    public static void cleanAllCache(Context context) {
        cleanInternalCache(context);
        cleanExternalCache(context);
        cleanWebViewCache(context);
    }

    public static void cleanInternalCache(Context context) {
        FileUtil.delete(context.getCacheDir());
    }

    public static void cleanExternalCache(Context context) {
        if (Environment.getExternalStorageState().equals("mounted")) {
            FileUtil.delete(context.getExternalCacheDir());
        }
    }

    public static void cleanWebViewCache(Context context) {
        try {
            context.deleteDatabase("webview.db");
            context.deleteDatabase("webviewCache.db");
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        File[] e = context.getCacheDir().listFiles(filter);
        int var2 = e.length;

        int var3;
        File dir;
        for (var3 = 0; var3 < var2; ++var3) {
            dir = e[var3];
            FileUtil.delete(dir);
        }

        e = context.getDir(".", 0).listFiles(filter);
        var2 = e.length;

        for (var3 = 0; var3 < var2; ++var3) {
            dir = e[var3];
            FileUtil.delete(dir);
        }

    }


}

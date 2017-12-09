package com.laoodao.smartagri.utils;

import android.text.TextUtils;

import java.io.File;
import java.text.DecimalFormat;

/**
 * Created by WORK on 2017/4/3.
 */

public class FileUtil {
    public static final long BYTES_KB = 1024L;
    public static final long BYTES_100KB = 102400L;
    public static final long BYTES_MB = 1048576L;
    public static final long BYTES_10MB = 10485760L;
    public static final long BYTES_GB = 1073741824L;

    public FileUtil() {
    }

    private static String _size(String formatter, double bytes, String unit) {
        return (new DecimalFormat(formatter)).format(bytes) + unit;
    }

    private static String _size(long bytes, String unit) {
        return bytes + unit;
    }

    public static String size(long bytes) {
        return bytes > 1073741824L ? _size("#.##", (double) bytes * 1.0D / 1.073741824E9D, "GB") : (bytes > 10485760L ? _size("#.#", (double) bytes * 1.0D / 1048576.0D, "MB") : (bytes > 1048576L ? _size("#.##", (double) bytes * 1.0D / 1048576.0D, "MB") : (bytes > 102400L ? bytes / 1024L + "KB" : (bytes > 1024L ? _size("#.#", (double) bytes * 1.0D / 1024.0D, "KB") : bytes + "B"))));
    }

    public static long calcDirectorySize(File dir) {
        long size = 0L;

        try {
            File[] e = dir.listFiles();
            int var4 = e.length;

            for (int var5 = 0; var5 < var4; ++var5) {
                File item = e[var5];
                if (item.isDirectory()) {
                    size += calcDirectorySize(item);
                } else {
                    size += item.length();
                }
            }
        } catch (Exception var7) {
            var7.printStackTrace();
        }

        return size;
    }

    public static void ensureDir(String filename) {
        File file = new File(filename);
        if (!file.exists() || !file.isDirectory()) {
            file.mkdirs();
        }

    }

    public static boolean isExist(String filename) {
        return !TextUtils.isEmpty(filename) && (new File(filename)).exists();
    }

    public static boolean isFile(String filename) {
        if (TextUtils.isEmpty(filename)) {
            return false;
        } else {
            File file = new File(filename);
            return file.exists() && file.isFile();
        }
    }

    public static boolean isDirectory(String filename) {
        if (TextUtils.isEmpty(filename)) {
            return false;
        } else {
            File file = new File(filename);
            return file.exists() && file.isDirectory();
        }
    }

    public static boolean delete(String filename) {
        return !TextUtils.isEmpty(filename) && delete(new File(filename));
    }

    public static boolean delete(File file) {
        boolean result = true;
        if (!file.exists()) {
            return false;
        } else if (file.isFile()) {
            return file.delete();
        } else if (!file.isDirectory()) {
            return false;
        } else {
            File[] var2 = file.listFiles();
            int var3 = var2.length;

            for (int var4 = 0; var4 < var3; ++var4) {
                File child = var2[var4];
                result &= delete(child);
            }

            result &= file.delete();
            return result;
        }
    }

    public static void clean(File dir, boolean onlyFile) {
        if (dir.exists() && dir.isDirectory()) {
            File[] var2 = dir.listFiles();
            int var3 = var2.length;

            for (int var4 = 0; var4 < var3; ++var4) {
                File child = var2[var4];
                if (!onlyFile) {
                    delete(child);
                } else if (!dir.isDirectory()) {
                    child.delete();
                }
            }

        }
    }
}

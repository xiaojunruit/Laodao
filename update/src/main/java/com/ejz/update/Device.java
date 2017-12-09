package com.ejz.update;

import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * Created by WORK on 2017/3/20.
 */

public class Device {
    public static final boolean isEmulator;
    public static final DisplayMetrics dm;
    public static String telecomId;
    public Device() {
    }

    public static int dp2px(float dp) {
        return (int) TypedValue.applyDimension(1, dp, dm);
    }

    public static int px2dp(float px) {
        return (int)(px / dm.density + 0.5F);
    }

    public static int sp2px(float sp) {
        return (int)TypedValue.applyDimension(2, sp, dm);
    }

    public static int px2sp(float px) {
        return (int)(px / dm.scaledDensity + 0.5F);
    }


    static {
        isEmulator = Build.MODEL.equals("sdk") || Build.MODEL.equals("google_sdk");
        dm = Resources.getSystem().getDisplayMetrics();
        telecomId = "tel";
    }
}

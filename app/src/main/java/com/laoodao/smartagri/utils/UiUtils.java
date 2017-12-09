package com.laoodao.smartagri.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.AnyRes;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.laoodao.smartagri.LDApplication;
import com.laoodao.smartagri.base.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.laoodao.smartagri.base.AppManager.APP_EXIT;
import static com.laoodao.smartagri.base.AppManager.KILL_ALL;

/**
 * Created by 欧源 on 2017/4/13.
 */

public class UiUtils {

    static public Toast mToast;
    public static final int START_ACTIVITY = 0;
    private static long _lastClickTime;


    /**
     * dip转pix
     *
     * @param dpValue
     * @return
     */
    public static int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 获得资源
     */
    public static Resources getResources() {
        return LDApplication.getInstance().getResources();
    }


    /**
     * 得到字符数组
     */
    public static String[] getStringArray(int id) {
        return getResources().getStringArray(id);
    }

    /**
     * pix转dip
     */
    public static int pix2dip(int pix) {
        final float densityDpi = getResources().getDisplayMetrics().density;
        return (int) (pix / densityDpi + 0.5f);
    }

    public static AssetManager getAssets() {
        return getResources().getAssets();
    }

    /**
     * 获得上下文
     *
     * @return
     */
    public static Context getContext() {
        return LDApplication.getInstance();
    }


    /**
     * 从dimens中获得尺寸
     *
     * @param homePicHeight
     * @return
     */

    public static int getDimens(int homePicHeight) {
        return (int) getResources().getDimension(homePicHeight);
    }

    /**
     * 从dimens中获得尺寸
     *
     * @param
     * @return
     */

    public static float getDimens(String dimenNmae) {
        return getResources().getDimension(getResources().getIdentifier(dimenNmae, "dimen", getContext().getPackageName()));
    }

    /**
     * 从String 中获得字符
     *
     * @return
     */

    public static String getString(int stringID) {
        return getResources().getString(stringID);
    }


    public static String getString(int stringID, Object... args) {
        return getResources().getString(stringID, args);
    }


    /**
     * 从String 中获得字符
     *
     * @return
     */

    public static String getString(String strName) {
        return getString(getResources().getIdentifier(strName, "string", getContext().getPackageName()));
    }

    /**
     * findview
     *
     * @param view
     * @param viewName
     * @param <T>
     * @return
     */
    public static <T extends View> T findViewByName(View view, String viewName) {
        int id = getResources().getIdentifier(viewName, "id", getContext().getPackageName());
        T v = (T) view.findViewById(id);
        return v;
    }


    /**
     * findview
     *
     * @param activity
     * @param viewName
     * @param <T>
     * @return
     */
    public static <T extends View> T findViewByName(Activity activity, String viewName) {
        int id = getResources().getIdentifier(viewName, "id", getContext().getPackageName());
        T v = (T) activity.findViewById(id);
        return v;
    }

    /**
     * 根据lauout名字获得id
     *
     * @param layoutName
     * @return
     */
    public static int findLayout(String layoutName) {
        int id = getResources().getIdentifier(layoutName, "layout", getContext().getPackageName());
        return id;
    }

    /**
     * 填充view
     *
     * @param detailScreen
     * @return
     */
    public static View inflate(int detailScreen) {
        return View.inflate(getContext(), detailScreen, null);
    }

    /**
     * 单列toast
     *
     * @param string
     */

    public static void makeText(String string) {
        if (mToast == null) {
            mToast = Toast.makeText(getContext(), string, Toast.LENGTH_SHORT);

        }
        mToast.setText(string);
        mToast.show();
    }

    /**
     * 通过资源id获得drawable
     *
     * @param rID
     * @return
     */
    public static Drawable getDrawablebyResource(int rID) {
        return getResources().getDrawable(rID);
    }

    /**
     * 跳转界面
     *
     * @param activity
     * @param homeActivityClass
     */
    public static void startActivity(Activity activity, Class homeActivityClass) {
        Intent intent = new Intent(getContext(), homeActivityClass);
        activity.startActivity(intent);
    }

    /**
     * 跳转界面3
     *
     * @param
     * @param homeActivityClass
     */
    public static void startActivity(Class homeActivityClass) {
        MessageEvent event = new MessageEvent();
        event.code = START_ACTIVITY;
        event.data = homeActivityClass;
        EventBus.getDefault().post(event);
    }

    /**
     * 跳转界面5
     *
     * @param
     */
    public static void startActivity(Context context, Class clazz, Bundle params) {
        Intent intent = new Intent(context, clazz);
        intent.putExtras(params);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, Class clazz, Bundle params, int flags) {
        Intent intent = new Intent(context, clazz);
        intent.putExtras(params);
        intent.setFlags(flags);
        context.startActivity(intent);
    }


    /**
     * 跳转界面4
     *
     * @param
     */
    public static void startActivity(Activity activity, Intent intent) {
        activity.startActivity(intent);
    }

    public static int getLayoutId(String layoutName) {
        return getResources().getIdentifier(layoutName, "layout", getContext().getPackageName());
    }

    public static void killAll(){
        MessageEvent event = new MessageEvent();
        event.code = KILL_ALL;
        EventBus.getDefault().post(event);
    }

    public static void exitApp(){
        MessageEvent event = new MessageEvent();
        event.code = APP_EXIT;
        EventBus.getDefault().post(event);
    }



    /**
     * 获得屏幕的宽度
     *
     * @return
     */
    public static int getScreenWidth() {
        return getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获得屏幕的高度
     *
     * @return
     */
    public static int getScreenHeidth() {
        return getResources().getDisplayMetrics().heightPixels;
    }


    /**
     * 获得颜色
     */
    public static int getColor(int rid) {
        return getResources().getColor(rid);
    }

    /**
     * 获得颜色
     */
    public static int getColor(String colorName) {
        return getColor(getResources().getIdentifier(colorName, "color", getContext().getPackageName()));
    }

    /**
     * 移除孩子
     *
     * @param view
     */
    public static void removeChild(View view) {
        ViewParent parent = view.getParent();
        if (parent instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) parent;
            group.removeView(view);
        }
    }

    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        return false;
    }


    /**
     * MD5
     *
     * @param string
     * @return
     * @throws Exception
     */
    public static String MD5encode(String string) {
        byte[] hash = new byte[0];
        try {
            hash = MessageDigest.getInstance("MD5").digest(
                    string.getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) {
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }


    /**
     * 全屏，并且沉侵式状态栏
     *
     * @param activity
     */
    public static void statuInScreen(Activity activity) {
        WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
        attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
        activity.getWindow().setAttributes(attrs);
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     */
    public static int px2sp(float pxValue) {
        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public static int sp2px(float spValue) {
        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static void hideOrShowPassword(ImageView imgPwd, EditText editText) {
        boolean isHide = imgPwd.isSelected();
        imgPwd.setSelected(!isHide);
        if (!isHide) {
            editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        editText.setSelection(editText.getText().toString().length());
    }

    //glide加载图片
    public static void glideLoadImg(Context context, ImageView imageView, String url) {
        Glide.with(context)
                .load(url)
                .into(imageView);
    }

    public static final Uri toUri(@AnyRes int resId) {
        Resources res = getResources();
        Uri resUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + res.getResourcePackageName(resId) + '/' + res.getResourceTypeName(resId) + '/' + res
                .getResourceEntryName(resId));
        return resUri;
    }

    public static void loadImage(ImageView imageView, String url) {
        if (TextUtils.isEmpty(url)) {
            imageView.setVisibility(View.GONE);
        } else {
            imageView.setVisibility(View.VISIBLE);
            Glide.with(getContext())
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .dontAnimate()
                    .dontTransform()
                    .centerCrop()
                    .into(imageView);
        }

    }

    public static boolean isFastClick(long limit) {
        long now = System.currentTimeMillis();
        long diff = now - _lastClickTime;
        LogUtil.e("lastClickTime = " + _lastClickTime);
        if (0L < diff && diff < limit) {
            return true;
        } else {
            _lastClickTime = now;
            return false;
        }
    }


}

package com.laoodao.smartagri.utils;

import android.content.res.Resources;

import com.laoodao.smartagri.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.internal.Utils;

/**
 * Created by 欧源 on 2017/5/2.
 */

public class DateUtils {
    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }

    public static String longToString(long currentTime, String formatType)
            throws ParseException {
        Date date = longToDate(currentTime, formatType); // long类型转成Date类型
        String strTime = dateToString(date, formatType); // date类型转成String
        return strTime;
    }

    public static Date stringToDate(String strTime, String formatType)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }

    public static Date longToDate(long currentTime, String formatType)
            throws ParseException {
        Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
        Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
        return date;
    }

    public static long stringToLong(String strTime, String formatType)
            throws ParseException {
        Date date = stringToDate(strTime, formatType); // String类型转成date类型
        if (date == null) {
            return 0;
        } else {
            long currentTime = dateToLong(date); // date类型转成long类型
            return currentTime;
        }
    }

    public static long dateToLong(Date date) {
        return date.getTime();
    }

    public static  String formatDate(String date) {
        Calendar mCalendar = Calendar.getInstance();
        long refreshTime = mCalendar.getTimeInMillis();
        long lastRefreshTime = 0L;
        lastRefreshTime =  com.laoodao.smartagri.view.wheelPicker.utils.DateUtils.parseDate(date,"yyyy-MM-dd HH:mm").getTime();
        long howLong = refreshTime - lastRefreshTime;
        int minutes = (int) (howLong / 1000 / 60);
        String refreshTimeText = null;

        mCalendar.setTimeInMillis(lastRefreshTime);
        int hour = mCalendar.get(Calendar.HOUR);
        int minute = mCalendar.get(Calendar.MINUTE);
        if (minutes < 60 * 24) {
            refreshTimeText = "今天";
        } else if (minutes > 60 * 24 && minutes < 60 * 24 * 2) {
            refreshTimeText = "昨天";
        } else {
            return date;
        }


        return refreshTimeText + hour + ":" + minute;
    }
}

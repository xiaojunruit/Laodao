package com.laoodao.smartagri.utils;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.SystemClock;
import android.support.v7.app.NotificationCompat;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by WORK on 2017/4/12.
 */
@SuppressLint({"NewApi"})
public class NotifyUtil {
    private static final int FLAG = 4;
    int requestCode = (int) SystemClock.uptimeMillis();
    private int NOTIFICATION_ID;
    private NotificationManager nm;
    private Notification notification;
    private NotificationCompat.Builder cBuilder;
    private Notification.Builder nBuilder;
    private Context mContext;

    public NotifyUtil(Context context, int ID) {
        this.NOTIFICATION_ID = ID;
        this.mContext = context;
        this.nm = (NotificationManager)this.mContext.getSystemService("notification");
        this.cBuilder = new NotificationCompat.Builder(this.mContext);
    }

    private void setCompatBuilder(PendingIntent pendingIntent, int smallIcon, String ticker, String title, String content, boolean sound, boolean vibrate, boolean lights) {
        this.cBuilder.setContentIntent(pendingIntent);
        this.cBuilder.setSmallIcon(smallIcon);
        this.cBuilder.setTicker(ticker);
        this.cBuilder.setContentTitle(title);
        this.cBuilder.setContentText(content);
        this.cBuilder.setWhen(System.currentTimeMillis());
        this.cBuilder.setAutoCancel(true);
        this.cBuilder.setPriority(2);
        int defaults = 0;
        if(sound) {
            defaults |= 1;
        }

        if(vibrate) {
            defaults |= 2;
        }

        if(lights) {
            defaults |= 4;
        }

        this.cBuilder.setDefaults(defaults);
    }

    private void setBuilder(PendingIntent pendingIntent, int smallIcon, String ticker, boolean sound, boolean vibrate, boolean lights) {
        this.nBuilder = new Notification.Builder(this.mContext);
        this.nBuilder.setContentIntent(pendingIntent);
        this.nBuilder.setSmallIcon(smallIcon);
        this.nBuilder.setTicker(ticker);
        this.nBuilder.setWhen(System.currentTimeMillis());
        this.nBuilder.setPriority(2);
        int defaults = 0;
        if(sound) {
            defaults |= 1;
        }

        if(vibrate) {
            defaults |= 2;
        }

        if(lights) {
            defaults |= 4;
        }

        this.nBuilder.setDefaults(defaults);
    }

    public void notify_normal_singline(PendingIntent pendingIntent, int smallIcon, String ticker, String title, String content, boolean sound, boolean vibrate, boolean lights) {
        this.setCompatBuilder(pendingIntent, smallIcon, ticker, title, content, sound, vibrate, lights);
        this.sent();
    }

    public void notify_mailbox(PendingIntent pendingIntent, int smallIcon, int largeIcon, ArrayList<String> messageList, String ticker, String title, String content, boolean sound, boolean vibrate, boolean lights) {
        this.setCompatBuilder(pendingIntent, smallIcon, ticker, title, content, sound, vibrate, lights);
        Bitmap bitmap = BitmapFactory.decodeResource(this.mContext.getResources(), largeIcon);
        this.cBuilder.setLargeIcon(bitmap);
        this.cBuilder.setDefaults(-1);
        this.cBuilder.setAutoCancel(true);
        android.support.v4.app.NotificationCompat.InboxStyle inboxStyle = new android.support.v4.app.NotificationCompat.InboxStyle();
        Iterator var13 = messageList.iterator();

        while(var13.hasNext()) {
            String msg = (String)var13.next();
            inboxStyle.addLine(msg);
        }

        inboxStyle.setSummaryText("[" + messageList.size() + "条]" + title);
        this.cBuilder.setStyle(inboxStyle);
        this.sent();
    }

    public void notify_customview(RemoteViews remoteViews, PendingIntent pendingIntent, int smallIcon, String ticker, boolean sound, boolean vibrate, boolean lights) {
        this.setCompatBuilder(pendingIntent, smallIcon, ticker, (String)null, (String)null, sound, vibrate, lights);
        this.notification = this.cBuilder.build();
        this.notification.contentView = remoteViews;
        this.nm.notify(this.NOTIFICATION_ID, this.notification);
    }

    public void notify_normail_moreline(PendingIntent pendingIntent, int smallIcon, String ticker, String title, String content, boolean sound, boolean vibrate, boolean lights) {
        int sdk = Build.VERSION.SDK_INT;
        if(sdk < 16) {
            this.notify_normal_singline(pendingIntent, smallIcon, ticker, title, content, sound, vibrate, lights);
            Toast.makeText(this.mContext, "您的手机低于Android 4.1.2，不支持多行通知显示！！", 0).show();
        } else {
            this.setBuilder(pendingIntent, smallIcon, ticker, true, true, false);
            this.nBuilder.setContentTitle(title);
            this.nBuilder.setContentText(content);
            this.nBuilder.setPriority(1);
            this.notification = (new Notification.BigTextStyle(this.nBuilder)).bigText(content).build();
            this.nm.notify(this.NOTIFICATION_ID, this.notification);
        }

    }

    public void notify_progress(PendingIntent pendingIntent, int smallIcon, String ticker, String title, String content, boolean sound, boolean vibrate, boolean lights) {
        this.setCompatBuilder(pendingIntent, smallIcon, ticker, title, content, sound, vibrate, lights);
        (new Thread(new Runnable() {
            public void run() {
                for(int incr = 0; incr <= 100; incr += 10) {
                    NotifyUtil.this.cBuilder.setProgress(100, incr, false);
                    NotifyUtil.this.sent();

                    try {
                        Thread.sleep(500L);
                    } catch (InterruptedException var3) {
                        var3.printStackTrace();
                    }
                }

                NotifyUtil.this.cBuilder.setContentText("下载完成").setProgress(0, 0, false);
                NotifyUtil.this.sent();
            }
        })).start();
    }

    public void notify_bigPic(PendingIntent pendingIntent, int smallIcon, String ticker, String title, String content, int bigPic, boolean sound, boolean vibrate, boolean lights) {
        this.setCompatBuilder(pendingIntent, smallIcon, ticker, title, (String)null, sound, vibrate, lights);
        android.support.v4.app.NotificationCompat.BigPictureStyle picStyle = new android.support.v4.app.NotificationCompat.BigPictureStyle();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = true;
        options.inSampleSize = 2;
        Bitmap bitmap = BitmapFactory.decodeResource(this.mContext.getResources(), bigPic, options);
        picStyle.bigPicture(bitmap);
        picStyle.bigLargeIcon(bitmap);
        this.cBuilder.setContentText(content);
        this.cBuilder.setStyle(picStyle);
        this.sent();
    }

    public void notify_button(int smallIcon, int leftbtnicon, String lefttext, PendingIntent leftPendIntent, int rightbtnicon, String righttext, PendingIntent rightPendIntent, String ticker, String title, String content, boolean sound, boolean vibrate, boolean lights) {
        this.requestCode = (int)SystemClock.uptimeMillis();
        this.setCompatBuilder(rightPendIntent, smallIcon, ticker, title, content, sound, vibrate, lights);
        this.cBuilder.addAction(leftbtnicon, lefttext, leftPendIntent);
        this.cBuilder.addAction(rightbtnicon, righttext, rightPendIntent);
        this.sent();
    }

    public void notify_HeadUp(PendingIntent pendingIntent, int smallIcon, int largeIcon, String ticker, String title, String content, int leftbtnicon, String lefttext, PendingIntent leftPendingIntent, int rightbtnicon, String righttext, PendingIntent rightPendingIntent, boolean sound, boolean vibrate, boolean lights) {
        this.setCompatBuilder(pendingIntent, smallIcon, ticker, title, content, sound, vibrate, lights);
        this.cBuilder.setLargeIcon(BitmapFactory.decodeResource(this.mContext.getResources(), largeIcon));
        if(Build.VERSION.SDK_INT >= 21) {
            this.cBuilder.addAction(leftbtnicon, lefttext, leftPendingIntent);
            this.cBuilder.addAction(rightbtnicon, righttext, rightPendingIntent);
        } else {
            Toast.makeText(this.mContext, "版本低于Andriod5.0，无法体验HeadUp样式通知", 0).show();
        }

        this.sent();
    }

    private void sent() {
        this.notification = this.cBuilder.build();
        this.nm.notify(this.NOTIFICATION_ID, this.notification);
    }

    public void clear() {
        this.nm.cancelAll();
    }
}

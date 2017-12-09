package com.ejz.update;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;

/**
 * Created by WORK on 2017/3/20.
 */

public class UpdateAgent {
    public static final String PREFS = "ezy.update";
    private static UpdateListener updateListener = new UpdateListener() {
        public void onUpdateReturned(int status, UpdateInfo info) {
        }
    };
    private static DownLoadListener downloadListener = new DownLoadListener() {
        public void onDownloadStart() {
        }

        public void onDownloadUpdate(int progress) {
        }

        public void onDownloadEnd(int status, String file) {
        }
    };
    private static Context gContext;
    private static String channel = "";
    static String updateUrl;
    static String userAgent;

    public UpdateAgent() {
    }

    public static void setContext(Context value) {
        gContext = value;
    }

    public static void setChannel(String value) {
        channel = value;
    }

    public static void setUpdateUrl(String url) {
        updateUrl = url;
    }

    public static void setUserAgent(String value) {
        userAgent = value;
    }

    public static void setUpdateListener(UpdateListener listener) {
        updateListener = listener;
    }

    public static void setDownloadListener(DownLoadListener listener) {
        downloadListener = listener;
    }

    public static void update(Context context) {
        if(!checkNetwork(context)) {
            updateListener.onUpdateReturned(3, new UpdateInfo());
        } else {
            (new UpdateAgent.UpdateChecker()).execute(new Void[0]);
        }
    }

    public static void download(Context context, UpdateInfo info) {
        (new UpdateDownloader(context, info.url, info.md5, downloadListener)).execute(new Void[0]);
    }

    public static void install(Context context, File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        intent.addFlags(268435456);
        context.startActivity(intent);
    }

    public static void ignore(Context context, UpdateInfo info) {
        ignore(context, info.md5);
    }

    public static void ignore(Context context, String md5) {
        context.getApplicationContext().getSharedPreferences("ezy.update", 0).edit().putString("ignore", md5).apply();
    }

    public static String getIgnoreMd5(Context context) {
        String md5 = context.getApplicationContext().getSharedPreferences("ezy.update", 0).getString("ignore", "");
        return "".equals(md5)?null:md5;
    }

    public static File getDownloadedFile(Context context, UpdateInfo info) {
        String filename = info.md5 + ".apk";

        try {
            File e = new File(context.getExternalCacheDir(), filename);
            return e.exists() && info.md5.equalsIgnoreCase(md5(e))?e:null;
        } catch (Exception var4) {
            var4.printStackTrace();
            return null;
        }
    }

    static boolean checkNetwork(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager)context.getSystemService("connectivity");
        if(connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if(info == null) {
                return false;
            } else {
                for(int i = 0; i < info.length; ++i) {
                    if(info[i].getState() == NetworkInfo.State.CONNECTED || info[i].getState() == NetworkInfo.State.CONNECTING) {
                        return true;
                    }
                }

                return false;
            }
        }
    }

    static String md5(File file) {
        MessageDigest digest = null;
        FileInputStream fis = null;
        byte[] buffer = new byte[1024];

        try {
            if(!file.isFile()) {
                return "";
            }

            digest = MessageDigest.getInstance("MD5");
            fis = new FileInputStream(file);

            int var5;
            while((var5 = fis.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, var5);
            }

            fis.close();
        } catch (Exception var51) {
            var51.printStackTrace();
            return null;
        }

        BigInteger var52 = new BigInteger(1, digest.digest());
        return String.format("%1$032x", new Object[]{var52});
    }

    static String makeUpdateUrl() {
        StringBuilder builder = new StringBuilder();
        builder.append(updateUrl);
        builder.append(updateUrl.indexOf("?") < 0?"?":"&");
        builder.append("package=");
        builder.append(gContext.getPackageName());
        builder.append("&version=");
        builder.append(getVersionCode(gContext));
        builder.append("&channel=");
        builder.append(channel);
        return builder.toString();
    }

    static int getVersionCode(Context context) {
        try {
            PackageInfo e = context.getPackageManager().getPackageInfo(context.getPackageName(), 128);
            return e.versionCode;
        } catch (PackageManager.NameNotFoundException var2) {
            return 0;
        }
    }

    static class UpdateChecker extends AsyncTask<Void, Integer, UpdateInfo> {
        UpdateChecker() {
        }

        protected UpdateInfo doInBackground(Void... params) {
            HttpURLConnection connection = null;

            UpdateInfo var5;
            try {
                URL e = new URL(UpdateAgent.makeUpdateUrl());
                connection = (HttpURLConnection)e.openConnection();
                connection.setRequestMethod("GET");
                if(!TextUtils.isEmpty(UpdateAgent.userAgent)) {
                    connection.setRequestProperty("User-Agent", UpdateAgent.userAgent);
                }

                connection.setRequestProperty("Accept", "application/json");
                int statusCode = connection.getResponseCode();
                if(statusCode != 200) {
                    var5 = null;
                    return var5;
                }

                var5 = UpdateInfo.parse(IoUtil.readString(connection.getInputStream()));
            } catch (Exception var9) {
                var9.printStackTrace();
                return null;
            } finally {
                if(connection != null) {
                    connection.disconnect();
                }

            }

            return var5;
        }

        protected void onPostExecute(UpdateInfo info) {
            if(info == null) {
                UpdateAgent.updateListener.onUpdateReturned(4, new UpdateInfo());
            } else {
                String ignore = UpdateAgent.getIgnoreMd5(UpdateAgent.gContext);
                if(ignore != null && ignore.equals(info.md5)) {
                    UpdateAgent.updateListener.onUpdateReturned(1, new UpdateInfo());
                } else {
                    UpdateAgent.updateListener.onUpdateReturned(0, info);
                }

            }
        }
    }
}

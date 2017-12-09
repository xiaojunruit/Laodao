package com.ejz.update;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by WORK on 2017/3/20.
 */

class UpdateDownloader extends AsyncTask<Void, Integer, Long> {
    private static final int TIME_OUT = 30000;
    private static final int BUFFER_SIZE = 102400;
    private static final int EVENT_START = 1;
    private static final int EVENT_PROGRESS = 2;
    private static final int EVENT_COMPLETE = 3;
    private String _url;
    private File _temp;
    private Context _context;
    private SystemException _error = null;
    private long _bytesLoaded = 0L;
    private long _bytesTotal = 0L;
    private long _bytesTemp = 0L;
    private long _timeBegin = 0L;
    private long _timeUsed = 1L;
    private long _timeLast = 0L;
    private long _speed = 0L;
    private boolean _isCancelled = false;
    private DownLoadListener _listener;
    private HttpURLConnection connection;
    public static final boolean DEBUG = true;

    public UpdateDownloader(Context context, String url, String md5, DownLoadListener listener) {
        this._context = context;
        this._url = url;
        this._listener = listener;
        this._temp = new File(context.getExternalCacheDir() + File.separator + md5);
        if(this._temp.exists()) {
            this._bytesTemp = this._temp.length();
        }

    }

    public long getBytesLoaded() {
        return this._bytesLoaded + this._bytesTemp;
    }

    public void stop(boolean clear) {
        this.cancel(true);
        this._isCancelled = true;
        if(clear && this._temp.exists()) {
            this._temp.delete();
        }

    }

    protected void onPreExecute() {
        this._timeBegin = System.currentTimeMillis();
    }

    protected Long doInBackground(Void... params) {
        long result = -1L;

        try {
            result = this.download();
        } catch (SystemException var9) {
            this._error = var9;
        } catch (IOException var10) {
            this._error = SystemException.wrap(var10, ErrorCode.NETWORK_IO);
        } finally {
            if(this.connection != null) {
                this.connection.disconnect();
            }

        }

        return Long.valueOf(result);
    }

    protected void onProgressUpdate(Integer... progress) {
        switch(progress[0].intValue()) {
            case 1:
                this._listener.onDownloadStart();
                break;
            case 2:
                long now = System.currentTimeMillis();
                if(now - this._timeLast >= 900L) {
                    this._timeLast = now;
                    this._timeUsed = now - this._timeBegin;
                    this._speed = this._bytesLoaded * 1000L / this._timeUsed;
                    this._listener.onDownloadUpdate((int)(this.getBytesLoaded() * 100L / this._bytesTotal));
                }
        }

    }

    protected void onPostExecute(Long result) {
        if(result.longValue() != -1L && this._error == null && !this._isCancelled) {
            String md5 = UpdateAgent.md5(this._temp);
            if(!md5.equals(this._temp.getName())) {
                this._listener.onDownloadEnd(0, (String)null);
            } else {
                this._listener.onDownloadEnd(1, this._temp.toString());
            }
        } else {
            this._listener.onDownloadEnd(0, (String)null);
        }
    }

    boolean isRedirect(int statusCode) {
        switch(statusCode) {
            case 301:
            case 302:
            case 303:
            case 307:
                return true;
            case 304:
            case 305:
            case 306:
            default:
                return false;
        }
    }

    void checkRedirect() throws IOException, SystemException {
        int deep = 0;

        while(true) {
            int statusCode = this.connection.getResponseCode();
            if(!this.isRedirect(statusCode)) {
                return;
            }

            if(deep >= 3) {
                throw (new SystemException("redirect too deep", ErrorCode.HTTP_STATUS)).set("statusCode", Integer.valueOf(statusCode));
            }

            String location = this.connection.getHeaderField("Location");
            if(!location.startsWith("http://") && !location.startsWith("https://")) {
                location = "http://" + location;
            }

            try {
                this.connection.disconnect();
                this.connection = this.create(new URL(location));
                this.connection.connect();
            } catch (Exception var5) {
                throw (new SystemException("invalid location", ErrorCode.HTTP_STATUS)).set("statusCode", Integer.valueOf(statusCode)).set("location", location);
            }

            ++deep;
        }
    }

    void checkNetwork() throws SystemException {
        if(!UpdateAgent.checkNetwork(this._context)) {
            throw new SystemException(ErrorCode.NETWORK_BLOCKED);
        }
    }

    void checkStatus() throws IOException, SystemException {
        int statusCode = this.connection.getResponseCode();
        if(statusCode != 200 && statusCode != 206) {
            throw (new SystemException(ErrorCode.HTTP_STATUS)).set("statusCode", Integer.valueOf(statusCode));
        }
    }

    void checkSpace(long loaded, long total) throws SystemException {
        long storage = getAvailableStorage();
        log("[need:" + total + " - " + loaded + " = " + (total - loaded) + "][space:" + storage + "]");
        if(total - loaded > storage) {
            throw new SystemException(ErrorCode.SDCARD_NO_SPACE);
        }
    }

    private HttpURLConnection create(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("GET");
        if(!TextUtils.isEmpty(UpdateAgent.userAgent)) {
            connection.setRequestProperty("User-Agent", UpdateAgent.userAgent);
        }

        connection.addRequestProperty("Accept", "application/*");
        connection.setConnectTimeout(10000);
        return connection;
    }

    private long download() throws IOException, SystemException {
        this.checkNetwork();
        this.connection = this.create(new URL(this._url));
        this.connection.connect();
        this.checkRedirect();
        this.checkStatus();
        this._bytesTotal = (long)this.connection.getContentLength();
        this.checkSpace(this._bytesTemp, this._bytesTotal);
        if(this._bytesTemp == this._bytesTotal) {
            this.publishProgress(new Integer[]{Integer.valueOf(1)});
            return 0L;
        } else {
            if(this._bytesTemp > 0L) {
                this.connection.disconnect();
                this.connection = this.create(this.connection.getURL());
                this.connection.addRequestProperty("Range", "bytes=" + this._bytesTemp + "-");
                this.connection.connect();
                this.checkStatus();
            }

            this.publishProgress(new Integer[]{Integer.valueOf(1)});
            UpdateDownloader.LoadingRandomAccessFile raf = new UpdateDownloader.LoadingRandomAccessFile(this._temp);
            int bytesCopied = this.copy(this.connection.getInputStream(), raf);
            if(this._isCancelled) {
                log("-> cancelled][" + this._url + "]");
            } else if(this._bytesTemp + (long)bytesCopied != this._bytesTotal && this._bytesTotal != -1L) {
                throw new SystemException(this._bytesTemp + " + " + bytesCopied + " != " + this._bytesTotal, ErrorCode.DOWNLOAD_INCOMPLETE);
            }

            return (long)bytesCopied;
        }
    }

    private int copy(InputStream in, RandomAccessFile out) throws IOException, SystemException {
        if(in != null && out != null) {
            byte[] buffer = new byte[102400];
            BufferedInputStream bis = new BufferedInputStream(in, 102400);

            int n;
            try {
                out.seek(out.length());
                int bytes = 0;
                long previousBlockTime = -1L;

                while(!this._isCancelled) {
                    n = bis.read(buffer, 0, 102400);
                    if(n == -1) {
                        break;
                    }

                    out.write(buffer, 0, n);
                    bytes += n;
                    this.checkNetwork();
                    if(this._speed != 0L) {
                        previousBlockTime = -1L;
                    } else if(previousBlockTime == -1L) {
                        previousBlockTime = System.currentTimeMillis();
                    } else if(System.currentTimeMillis() - previousBlockTime > 30000L) {
                        throw new SystemException(ErrorCode.NETWORK_TIMEOUT);
                    }
                }

                n = bytes;
            } finally {
                out.close();
                bis.close();
                in.close();
            }

            return n;
        } else {
            return -1;
        }
    }

    public static void log(String content) {
        Log.i("ezy-updater", content);
    }

    public static long getAvailableStorage() {
        try {
            StatFs ex = new StatFs(Environment.getExternalStorageDirectory().toString());
            return (long)ex.getAvailableBlocks() * (long)ex.getBlockSize();
        } catch (RuntimeException var1) {
            return 0L;
        }
    }

    private final class LoadingRandomAccessFile extends RandomAccessFile {
        public LoadingRandomAccessFile(File file) throws FileNotFoundException {
            super(file, "rw");
        }

        public void write(byte[] buffer, int offset, int count) throws IOException {
            super.write(buffer, offset, count);
            UpdateDownloader.this._bytesLoaded = UpdateDownloader.this._bytesLoaded + (long)count;
            UpdateDownloader.this.publishProgress(new Integer[]{Integer.valueOf(2)});
        }
    }
}

package com.ejz.update;

/**
 * Created by WORK on 2017/3/20.
 */

public interface DownLoadListener {
    void onDownloadStart();

    void onDownloadUpdate(int var1);

    void onDownloadEnd(int var1, String var2);
}

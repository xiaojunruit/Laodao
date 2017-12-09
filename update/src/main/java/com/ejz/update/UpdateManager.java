package com.ejz.update;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.format.Formatter;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Toast;

import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;

import java.io.File;
import java.lang.ref.WeakReference;

/**
 * Created by WORK on 2017/3/20.
 */

public class UpdateManager implements UpdateListener, DownLoadListener {
    WeakReference<Context> mRefContext;
    UpdateListener mListener;
    ProgressDialog dialog;

    public static void update(Context context) {
        update(context, (UpdateListener)null);
    }

    public static void update(Context context, UpdateListener listener) {
        UpdateManager manager = new UpdateManager(context, listener);
        UpdateAgent.setUpdateListener(manager);
        UpdateAgent.setDownloadListener(manager);
        UpdateAgent.setContext(context.getApplicationContext());
        UpdateAgent.update(context);
    }

    private UpdateManager(Context context, UpdateListener listener) {
        this.mRefContext = new WeakReference(context);
        this.mListener = listener;
    }

    public void onUpdateReturned(int status, UpdateInfo info) {
        if(info.hasUpdate) {
            this.onNewVersion(info);
        } else {
            if(this.mListener != null) {
                this.mListener.onUpdateReturned(status, info);
            }

        }
    }

    void onNewVersion(final UpdateInfo info) {
        final Context context = (Context)this.mRefContext.get();
        if(context != null) {
            String size = Formatter.formatShortFileSize(context, info.size);
            String content = String.format("最新版本：%1$s\n新版本大小：%2$s\n\n更新内容\n%3$s", new Object[]{info.versionName, size, info.updateContent});
            final NormalDialog dialog = new NormalDialog(context) {
                public View onCreateView() {
                    View root = super.onCreateView();
                    this.mTvContent.setMovementMethod(new ScrollingMovementMethod());
                    this.mTvContent.setVerticalScrollBarEnabled(true);
                    this.mTvContent.setMaxHeight(Device.dp2px(250.0F));
                    return root;
                }
            };
            ((NormalDialog)((NormalDialog)((NormalDialog)dialog.style(0).widthScale(0.8F)).contentTextSize(14.0F)).title("应用更新")).cornerRadius(5.0F);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            if(info.isForce) {
                content = "您需要更新应用才能继续使用\n\n" + content;
                ((NormalDialog)((NormalDialog)((NormalDialog)dialog.content(content)).btnNum(1)).btnText(new String[]{"确定"})).show();
                dialog.setOnBtnClickL(new OnBtnClickL[]{new OnBtnClickL() {
                    public void onBtnClick() {
                        UpdateManager.this.doUpdate(5, info);
                        dialog.dismiss();
                    }
                }});
            } else {
                ((NormalDialog)((NormalDialog)((NormalDialog)dialog.content(content)).btnNum(3)).btnText(new String[]{"以后再说", "立即更新", "忽略该版"})).show();
                dialog.setOnBtnClickL(new OnBtnClickL[]{new OnBtnClickL() {
                    public void onBtnClick() {
                        UpdateManager.this.doUpdate(6, info);
                        dialog.dismiss();
                    }
                }, new OnBtnClickL() {
                    public void onBtnClick() {
                        UpdateManager.this.doUpdate(5, info);
                        dialog.dismiss();
                    }
                }, new OnBtnClickL() {
                    public void onBtnClick() {
                        UpdateManager.this.doUpdate(7, info);
                        dialog.dismiss();
                    }
                }});
            }

        }
    }

    public void onDownloadStart() {
        Context context = (Context)this.mRefContext.get();
        if(context != null) {
            this.dialog = new ProgressDialog(context);
            this.dialog.setProgressStyle(1);
            this.dialog.setMessage("下载中...");
            this.dialog.setIndeterminate(false);
            this.dialog.setCancelable(false);
            this.dialog.show();
        }
    }

    public void onDownloadUpdate(int i) {
        if(this.dialog != null) {
            this.dialog.setProgress(i);
        }

    }

    public void onDownloadEnd(int status, String file) {
        if(this.dialog != null) {
            this.dialog.dismiss();
            this.dialog = null;
        }

        Context context = (Context)this.mRefContext.get();
        if(context != null) {
            switch(status) {
                case 0:
                default:
                    Toast.makeText(context, "下载失败", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    UpdateAgent.install(context, new File(file));
            }
        }

    }

    void doUpdate(int status, UpdateInfo info) {
        Context context = (Context)this.mRefContext.get();
        if(context != null) {
            switch(status) {
                case 5:
                    File file = UpdateAgent.getDownloadedFile(context, info);
                    if(file != null) {
                        UpdateAgent.install(context, file);
                    } else {
                        UpdateAgent.download(context, info);
                    }
                case 6:
                default:
                    break;
                case 7:
                    UpdateAgent.ignore(context, info);
            }

        }
    }

//    @Override
//    public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
//        Context context = (Context)this.mRefContext.get();
//        if(context != null) {
//            this.dialog = new ProgressDialog(context);
//            this.dialog.setProgressStyle(1);
//            this.dialog.setMessage("下载中...");
//            this.dialog.setIndeterminate(false);
//            this.dialog.setCancelable(false);
//            this.dialog.show();
//        }
//    }
}


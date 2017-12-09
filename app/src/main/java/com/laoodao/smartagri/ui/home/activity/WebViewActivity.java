package com.laoodao.smartagri.ui.home.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.Text;
import com.laoodao.smartagri.BuildConfig;
import com.laoodao.smartagri.Global;
import com.laoodao.smartagri.LDApplication;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.bean.User;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.common.Route;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerMainComponent;
import com.laoodao.smartagri.event.LoginWebEvent;
import com.laoodao.smartagri.event.ShareEvent;
import com.laoodao.smartagri.location.LocationSubscriber;
import com.laoodao.smartagri.location.RxLocation;
import com.laoodao.smartagri.ui.discovery.contract.WebViewContract;
import com.laoodao.smartagri.ui.discovery.presenter.WebViewPresenter;
import com.laoodao.smartagri.ui.user.activity.IntegralTaskActivity;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.NetworkUtils;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.wxapi.QQSdk;
import com.laoodao.smartagri.wxapi.WechatSdk;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import top.zibin.luban.Luban;

/**
 * Created by WORK on 2017/5/3.
 */

public class WebViewActivity extends BaseActivity<WebViewPresenter> implements WebViewContract.WebView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.webview)
    WebView mWebView;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout mSwipeLayout;
    private String mUrl = "";
    // 微信SDK
    private WechatSdk mWechatSdk;
    //QQSDK
    private QQSdk mQQSdk;
    private String mLatitude = "";
    private String mLongitude = "";
    private String mCity = "";
    private Uri imageUri;
    private final static int FILECHOOSER_RESULTCODE = 1;
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private ValueCallback<Uri> mUploadMessage;// 表单的数据信息

    public static void start(Context context, String url) {
        Bundle bundle = new Bundle();
        bundle.putString("URL", url);
        UiUtils.startActivity(context, WebViewActivity.class, bundle);
    }

    public void initSwipe() {
        mSwipeLayout.setSize(SwipeRefreshLayout.DEFAULT);
        mSwipeLayout.setBackgroundColor(0xffffffff);
        mSwipeLayout.setColorSchemeColors(0xfffa193e);
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setDistanceToTriggerSync(200);
        mSwipeLayout.setProgressViewEndTarget(false, 450);
    }


    private void take() {
        File imageStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyApp");
        // Create the storage directory if it does not exist
        if (!imageStorageDir.exists()) {
            imageStorageDir.mkdirs();
        }

        File file = new File(imageStorageDir + File.separator + "IMG_" + String.valueOf(System.currentTimeMillis()) + ".jpg");
        imageUri = Uri.fromFile(file);
        final List<Intent> cameraIntents = new ArrayList<Intent>();
        final Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent i = new Intent(captureIntent);
            i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            i.setPackage(packageName);
            i.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            cameraIntents.add(i);

        }
        Intent i = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        Intent chooserIntent = Intent.createChooser(i, "选择应用");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[]{}));
        this.startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE);
    }


    private void initWebView() {
        String url = getIntent().getExtras().getString("URL");
        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public boolean onShowFileChooser(WebView webView,
                                             ValueCallback<Uri[]> filePathCallback,
                                             FileChooserParams fileChooserParams) {
                mUploadCallbackAboveL = filePathCallback;
                take();
                return true;
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                mUploadMessage = uploadMsg;
                take();
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                mUploadMessage = uploadMsg;
                take();
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                mUploadMessage = uploadMsg;
                take();
            }
        });
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("laoodao://")) {
                    Route.go(view.getContext(), url);
                    return true;
                }

                mWebView.loadUrl(url);
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (url.contains(Constant.SCRATCH_CARD)) {
                    isRefresh(0);
                } else {
                    isRefresh(1);
                }
                if (mSwipeLayout != null) {
                    mSwipeLayout.setRefreshing(false);
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mUrl = url;

            }
        });

        WebSettings settings = mWebView.getSettings();
        settings.setAllowFileAccess(true);
        settings.setJavaScriptEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setSaveFormData(true);
        settings.setNeedInitialFocus(true);
        settings.setBlockNetworkImage(false);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setDatabasePath(getDir("database", MODE_PRIVATE).getPath());
        settings.setGeolocationEnabled(true);
        settings.setGeolocationDatabasePath(getFilesDir().getPath());
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLoadsImagesAutomatically(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)

        {
            CookieManager.getInstance().setAcceptThirdPartyCookies(mWebView, true);
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mWebView.setWebContentsDebuggingEnabled(true);
        }
        mWebView.requestFocusFromTouch();
        mWebView.requestFocus();
        mWebView.addJavascriptInterface(new JSInterface(this), "ldAPP");
        mWebView.loadUrl(url);

    }

    @Subscribe
    public void onEventMainThread(LoginWebEvent event) {
        if (Global.getInstance().isLoggedIn()) {
            onRefresh();
        } else {
            back();
        }
    }

    private void loadUrl() {
        if (!"".equals(mUrl)) {
            mWebView.loadUrl(mUrl);
        }
    }

    @Override
    public void onRefresh() {
        loadUrl();
    }

    @Override
    public void complete() {

    }

    @Override
    public void shareBackSuccess() {
        loadUrl();
    }

    public class JSInterface {

        private Context mContext;

        public JSInterface(Context context) {
            this.mContext = context;
        }

        @JavascriptInterface
        public String latitude() {
            if (!TextUtils.isEmpty(mLatitude)) {
                LogUtil.e("))))))))mLatitude))"+mLatitude);
                return mLatitude;
            }
            return "";
        }

        @JavascriptInterface
        public String longitude() {
            if (!TextUtils.isEmpty(mLongitude)) {
                LogUtil.e("))))))))mLongitude))"+mLongitude);
                return mLongitude;
            }
            return "";
        }

        @JavascriptInterface
        public String loginRoute() {
            return "laoodao://user/login";
        }

        @JavascriptInterface
        public String getToken() {
            String token = Global.getInstance().getToken();
            if (!TextUtils.isEmpty(token)) {
                return token;
            }
            return null;
        }

        @JavascriptInterface
        public String getM() {
            User user = Global.getInstance().getUserInfo();
            if (!UiUtils.isEmpty(user)) {
                return user.uid;
            }
            return null;
        }

        @JavascriptInterface
        public void goBack() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    back();
                }
            });
        }

        @JavascriptInterface
        public void refreshUrl(int isRefresh) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    isRefresh(isRefresh);
                }
            });

        }

        @JavascriptInterface
        public void getPhoto() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    take();
                }
            });
        }

        @JavascriptInterface
        public void openIntegralTask() {
            UiUtils.startActivity(IntegralTaskActivity.class);
        }

        @JavascriptInterface
        public void getLoadUrl() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loadUrl();
                }
            });
        }

        @JavascriptInterface
        public void share(int type, String title, String content, String img, String url) {
            switch (type) {
                case 1:
                    int scene = SendMessageToWX.Req.WXSceneSession;
                    if (mWechatSdk == null) {
                        mWechatSdk = new WechatSdk(mContext, BuildConfig.APP_ID_WECHAT);
                    }
                    mWechatSdk.share(title, content, img, url, scene);
                    break;
                case 2:
                    int scenea = SendMessageToWX.Req.WXSceneTimeline;
                    if (mWechatSdk == null) {
                        mWechatSdk = new WechatSdk(mContext, BuildConfig.APP_ID_WECHAT);
                    }
                    mWechatSdk.share(title, content, img, url, scenea);
                    break;
                case 3:
                    if (mQQSdk == null) {
                        mQQSdk = new QQSdk((WebViewActivity) mContext, BuildConfig.APP_ID_QQ);
                    }
                    mQQSdk.shareToQQ(title, content, img, url, true);
                    break;
                case 4:
                    if (mQQSdk == null) {
                        mQQSdk = new QQSdk((WebViewActivity) mContext, BuildConfig.APP_ID_QQ);
                    }
                    mQQSdk.shareToQQ(title, content, img, url, false);
                    break;
            }

        }

    }

    private void isRefresh(int refresh) {
        if (mSwipeLayout != null) {
            if (refresh == 0) {
                mSwipeLayout.setEnabled(false);
            } else {
                mSwipeLayout.setEnabled(true);
            }
        }
    }

    public void back() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            finish();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerMainComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    protected void configViews() {
        initSwipe();
        initWebView();
        RxLocation.get().locate(this)
                .subscribeOn(Schedulers.io())
                .compose(bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new LocationSubscriber() {
                    @Override
                    public void onLocatedSuccess(@NonNull BDLocation location) {

                        mLatitude = String.valueOf(location.getLatitude());
                        mLongitude = String.valueOf(location.getLongitude());
                        mCity = location.getCity();
//                        if (mTvLongitude == null) {
//                            return;
//                        }
//                        mTvLongitude.setText(location.getCity());
                    }

                    @Override
                    public void onLocatedFail(BDLocation bdLocation) {
//                        if (mTvLongitude == null) {
//                            return;
//                        }
//                        mTvLongitude.setText("定位失败");
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage && null == mUploadCallbackAboveL) return;
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (mUploadCallbackAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, data);
            } else if (mUploadMessage != null) {
                Log.e("result", result + "");
                if (result == null) {
                    mUploadMessage.onReceiveValue(imageUri);
                    mUploadMessage = null;
                } else {
                    mUploadMessage.onReceiveValue(result);
                    mUploadMessage = null;
                }


            }
        } else {
            mQQSdk.onActivityResult(requestCode, resultCode, data);
        }
    }

    private File uri2File(Uri uri) {
        String img_path;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor actualimagecursor = managedQuery(uri, proj, null,
                null, null);
        if (actualimagecursor == null) {
            img_path = uri.getPath();
        } else {
            int actual_image_column_index = actualimagecursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            actualimagecursor.moveToFirst();
            img_path = actualimagecursor
                    .getString(actual_image_column_index);
        }
        File file = new File(img_path);
        return file;
    }

    @SuppressWarnings("null")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent data) {
        if (requestCode != FILECHOOSER_RESULTCODE
                || mUploadCallbackAboveL == null) {
            return;
        }

        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (data == null) {
                results = new Uri[]{imageUri};
            } else {
                String dataString = data.getDataString();
                ClipData clipData = data.getClipData();

                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }

                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
        if (results != null) {
//            for (int i = 0; i < results.length; i++) {
//                Luban.get(LDApplication.getInstance())
//                        .load(uri2File(results[i]))
//                        .putGear(Luban.THIRD_GEAR)
//                        .asObservable()
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(file1 -> {
//                            imageUri=Uri.fromFile(file1);
//                            Uri[] results1 = new Uri[]{imageUri};
//                            mUploadCallbackAboveL.onReceiveValue(results1);
//                            mUploadCallbackAboveL = null;
//                        });
            mUploadCallbackAboveL.onReceiveValue(results);
            mUploadCallbackAboveL = null;
//        }

        } else

        {
//            Luban.get(LDApplication.getInstance())
//                    .load(uri2File(imageUri))
//                    .putGear(Luban.THIRD_GEAR)
//                    .asObservable()
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(file1 -> {
//                        imageUri=Uri.fromFile(file1);
//                        Uri[] results2 = new Uri[]{imageUri};
//                        mUploadCallbackAboveL.onReceiveValue(results2);
//                        mUploadCallbackAboveL = null;
//                    });
            results = new Uri[]{imageUri};
            mUploadCallbackAboveL.onReceiveValue(results);
            mUploadCallbackAboveL = null;
        }

        return;
    }

    @Override
    protected boolean enableEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void ShareChange(ShareEvent event) {
        if (mUrl.contains(Constant.SCRATCH_CARD)) {
            mPresenter.shareBack("ggl", 0);
        }
    }

}

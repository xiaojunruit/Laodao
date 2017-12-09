package com.laoodao.smartagri.view;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.laoodao.smartagri.utils.IoUtil;
import com.laoodao.smartagri.utils.LogUtil;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * Created by WORK on 2017/6/29.
 */

public class CatchWebView extends WebView {
    Activity mActivity;


    @SuppressLint("JavascriptInterface")
    public CatchWebView(Activity context, String url) {
        super(context);
        mActivity = context;

        WebSettings settings = getSettings();
//        settings.setAllowFileAccess(true);
        settings.setJavaScriptEnabled(true);
//        settings.setAppCacheEnabled(true);
//        settings.setBuiltInZoomControls(true);
//        settings.setDisplayZoomControls(false);
//        settings.setDomStorageEnabled(true);
//        settings.setDatabaseEnabled(true);
//        settings.setSaveFormData(true);
//        settings.setNeedInitialFocus(true);
//        settings.setBlockNetworkImage(false);
//        settings.setJavaScriptCanOpenWindowsAutomatically(true);
//        settings.setGeolocationEnabled(true);
//        settings.setUseWideViewPort(true);
//        settings.setLoadWithOverviewMode(true);
//        settings.setLoadsImagesAutomatically(true);
//        setWebViewClient(mWebClient);
//        setWebChromeClient(mChromeClient);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            CookieManager.getInstance().setAcceptThirdPartyCookies(this, true);
//            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            setWebContentsDebuggingEnabled(true);
//        }
        mActivity.addContentView(this, new ViewGroup.LayoutParams(500, 500));
        loadUrl(url);
    }

    WebViewClient mWebClient = new WebViewClient() {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }


        @SuppressLint("NewApi")
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            try {
                URLConnection connection = new URL(request.getUrl().toString()).openConnection();
                return new WebResourceResponse(connection.getContentType(), connection.getHeaderField("encoding"), connection.getInputStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {

                e.printStackTrace();
            }

            return super.shouldInterceptRequest(view, request);
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            try {
                URLConnection connection = new URL(url).openConnection();
                return new WebResourceResponse(connection.getContentType(), connection.getHeaderField("encoding"), connection.getInputStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return super.shouldInterceptRequest(view, url);
        }


        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        URLConnection request(WebResourceRequest request) throws IOException {

            URLConnection connection = new URL(request.getUrl().toString()).openConnection();
            for (Map.Entry<String, String> entry : request.getRequestHeaders().entrySet()) {
                connection.addRequestProperty(entry.getKey(), entry.getValue());
            }
            connection.connect();
            return connection;
        }

        String read(URLConnection connection) throws IOException {
            String encoding = connection.getContentEncoding();
            if (!TextUtils.isEmpty(encoding) && encoding.contains("gzip")) {
                return IoUtil.readString(new GZIPInputStream(connection.getInputStream()));
            } else {
                return IoUtil.readString(connection.getInputStream());
            }
        }

    };


    WebChromeClient mChromeClient = new WebChromeClient() {
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            result.cancel();
            return true;
        }

        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
            result.cancel();
            return true;
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onPermissionRequest(final PermissionRequest request) {
            post(() -> request.grant(request.getResources()));
        }

    };


    public void runJs(String js) {
        loadUrl("javascript:(function () { try {" + js + "}catch(e){console.log(e)}})()");
    }
}

package com.laoodao.smartagri.ui.discovery.fragment;

import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.roundview.RoundFrameLayout;
import com.flyco.roundview.RoundTextView;
import com.google.gson.Gson;
import com.laoodao.smartagri.Global;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.LazyFragment;
import com.laoodao.smartagri.bean.CottonBill;
import com.laoodao.smartagri.bean.CottonCookie;
import com.laoodao.smartagri.bean.cotton.Cotton;
import com.laoodao.smartagri.bean.cotton.CottonJsxxList;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerDiscoveryComponent;
import com.laoodao.smartagri.event.CottonBillSlideEvent;
import com.laoodao.smartagri.event.CottonRefreshCode;
import com.laoodao.smartagri.ui.discovery.contract.RecordBillContract;
import com.laoodao.smartagri.ui.discovery.dialog.CottonDialog;
import com.laoodao.smartagri.ui.discovery.presenter.RecordBillPresenter;
import com.laoodao.smartagri.utils.IDCardUtil;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.NetworkUtils;
import com.laoodao.smartagri.utils.SharedPreferencesUtil;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.LineProgressBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by WORK on 2017/7/21.
 */

public class RecordBillFragment extends LazyFragment<RecordBillPresenter> implements RecordBillContract.RecordBillView {
    @BindView(R.id.et_id_number)
    EditText mEtIdNumber;
    @BindView(R.id.tv_start)
    RoundTextView mTvStart;
    @BindView(R.id.id_num)
    RoundFrameLayout mIdNum;
    @BindView(R.id.tv_mark)
    TextView mTvMark;
    @BindView(R.id.et_code)
    EditText mEtCode;
    @BindView(R.id.code)
    LinearLayout mCode;
    @BindView(R.id.line_progresbar)
    LineProgressBar mLineProgresbar;
    @BindView(R.id.webview_code)
    WebView mWebviewCode;
    @BindView(R.id.tv_webview)
    TextView mTvWebview;
    private int count;
    public Gson mGson;
    private CottonBill mCotton;
    private CottonDialog onEmptyDialog;
    private CottonDialog codeErrorDialog;

    @Override
    public void complete() {

    }

    @Override
    protected void lazyFetchData() {
        listener();
    }

    public boolean isError = true;

    private void listener() {
        mWebviewCode.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                isError = true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (isError && mWebviewCode != null) {
                    mWebviewCode.setVisibility(View.VISIBLE);
                }
                CookieSyncManager.createInstance(view.getContext());
                CookieManager cookieManager = CookieManager.getInstance();
                cookieManager.setAcceptCookie(true);
                String coookie = cookieManager.getCookie(url);
                CottonCookie cottonCookie = new CottonCookie();
                cottonCookie.cookidValue = coookie;
                Global.getInstance().setCottonCookie(cottonCookie);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                isError = false;
                if (mWebviewCode != null && mTvWebview != null) {
                    mWebviewCode.setVisibility(View.GONE);
                    mTvWebview.setText("点击重试");
                }
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                isError = false;
                if (mWebviewCode != null && mTvWebview != null) {
                    mWebviewCode.setVisibility(View.GONE);
                    mTvWebview.setText("点击重试");
                }
            }
        });
    }

    public void updateCode() {
        if (NetworkUtils.isAvailable(getContext())) {
            mWebviewCode.loadUrl(Constant.COTTON_VERIFICATION_CODE + "?" + System.currentTimeMillis());
            mTvWebview.setText("");
        } else {
            mTvWebview.setText("点击刷新");
        }

    }


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_record_bill;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerDiscoveryComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void configViews() {

    }

    @OnClick({R.id.tv_start, R.id.tv_webview})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_start:
                generateClick();
                break;
            case R.id.tv_webview:
                updateCode();
                break;
        }

    }

    private void generateClick() {
        switch (generate) {
            case 0:
                if (TextUtils.isEmpty(inspectNum())) {
                    return;
                }
                count = 0;
                progress();
                mTvMark.setText(UiUtils.getString(R.string.record_bill_loading));
                mLineProgresbar.setMaxProgress(100);
                mLineProgresbar.setCurProgress(99, 1980);
                mPresenter.requestCottonBill(inspectNum(), "");
                break;
            case 1:
                SharedPreferencesUtil.getInstance().putObject(Constant.COTTON_BILL, mCotton);
                EventBus.getDefault().post(new CottonBillSlideEvent(2));
                break;
            case 2:
                if (TextUtils.isEmpty(inspectNum())) {
                    return;
                }
                if (TextUtils.isEmpty(inspectCode())) {
                    return;
                }
                count = 0;
                progress();
                mTvMark.setText(UiUtils.getString(R.string.record_bill_loading));
                mLineProgresbar.setMaxProgress(100);
                mLineProgresbar.setCurProgress(99, 1980);
                commit();
                break;
        }
    }

    private String inspectNum() {
        String num = mEtIdNumber.getText().toString().toUpperCase();
        if (TextUtils.isEmpty(num)) {
            UiUtils.makeText("请输入身份证号");
            return null;
        }
        if (!"YES".equals(IDCardUtil.IDCardValidate(num))) {
            UiUtils.makeText("身份证号不正确");
            return null;
        }
        return num;
    }

    private String inspectCode() {
        String code = mEtCode.getText().toString().trim();
        if (TextUtils.isEmpty(code)) {
            UiUtils.makeText("请填写验证码");
            return null;
        }
        return code;
    }


    private void progress() {
        mIdNum.setVisibility(View.GONE);
        mTvStart.setVisibility(View.GONE);
        mCode.setVisibility(View.GONE);
        mLineProgresbar.setVisibility(View.VISIBLE);
        mLineProgresbar.setProgress(0);
        showLine();
    }

    private int generate; //0后台返回接口查询 1查询成功 2查询失败，执行验证码操作

    //直线形状
    private void showLine() {

        mLineProgresbar.setOnFinishedListener(new LineProgressBar.OnFinishedListener() {
            @Override
            public void onFinish() {
                if (count == 0) {
                    if (mCotton.has_data) {
                        generate = 1;
                        mTvMark.setText(R.string.record_bill_success);
                        mIdNum.setVisibility(View.GONE);
                        mTvStart.setVisibility(View.VISIBLE);
                        mCode.setVisibility(View.GONE);
                        mLineProgresbar.setVisibility(View.GONE);
                    } else {
                        updateCode();
                        generate = 2;
                        mTvMark.setText(R.string.record_bill_fail);
                        mIdNum.setVisibility(View.VISIBLE);
                        mTvStart.setVisibility(View.VISIBLE);
                        mCode.setVisibility(View.VISIBLE);
                        mLineProgresbar.setVisibility(View.GONE);
                    }

                }
                count++;
            }

        });

        mLineProgresbar.setProgressColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
    }

    private void commit() {
        if (TextUtils.isEmpty(inspectNum())) {
            return;
        }
        if (TextUtils.isEmpty(inspectCode())) {
            return;
        }
        mPresenter.cottonSelect(getActivity(), Constant.COMMIT_COTTON, inspectNum(), inspectCode(), mCotton.year);
    }


    @Override
    public void setCottonBill(CottonBill cotton) {
        this.mCotton = cotton;
        int num = ((mLineProgresbar.getMax() - mLineProgresbar.getProgress()) * 20);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mLineProgresbar.setCurProgress(100, 20);
            }
        }, num);
    }

    @Override
    public void cottonSusscess(Cotton cotton) {
        if ("no".equals(cotton.validate)) {
            int num = ((mLineProgresbar.getMax() - mLineProgresbar.getProgress()) * 20);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mLineProgresbar.setCurProgress(100, 20);
                    if (codeErrorDialog == null) {
                        codeErrorDialog = new CottonDialog(getContext(), "您输入的验证码错误!", "请重新输入");
                    }
                    codeErrorDialog.show();
                    updateCode();
                }
            }, num);

            return;
        }

        if (TextUtils.isEmpty(cotton.jsInfo)) {
            int num = ((mLineProgresbar.getMax() - mLineProgresbar.getProgress()) * 20);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mLineProgresbar.setCurProgress(100, 20);
                    if (onEmptyDialog == null) {
                        onEmptyDialog = new CottonDialog(getContext(), "未找到符合条件的数据", "");
                    }
                    mWebviewCode.loadUrl(Constant.COTTON_VERIFICATION_CODE + "?" + System.currentTimeMillis());
                    onEmptyDialog.show();
                }
            }, num);
            return;
        }

        if (TextUtils.isEmpty(inspectNum())) {
            return;
        }
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                mPresenter.requestCottonBill(inspectNum(), gsonString(cotton.jsxxList));

            }
        });
    }

    public String gsonString(List<CottonJsxxList> jsxxLists) {
        mGson = new Gson();
        String gsonString = null;
        if (mGson != null) {
            gsonString = mGson.toJson(jsxxLists);
        }
        return gsonString;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && mWebviewCode != null) {
            updateCode();
        }
    }


    @Override
    protected boolean enableEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void RefreshCodeEvent(CottonRefreshCode code) {
        updateCode();
        mEtCode.setText("");
    }

}

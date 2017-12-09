package com.laoodao.smartagri.ui.discovery.fragment;

import android.graphics.Bitmap;
import android.os.Handler;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.dialog.widget.NormalListDialog;
import com.flyco.roundview.RoundTextView;
import com.laoodao.smartagri.Global;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.LazyFragment;
import com.laoodao.smartagri.bean.CottonCookie;
import com.laoodao.smartagri.bean.cotton.Cotton;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerDiscoveryComponent;
import com.laoodao.smartagri.event.CottonRefreshCode;
import com.laoodao.smartagri.ui.discovery.activity.CottonCultivationActivity;
import com.laoodao.smartagri.ui.discovery.contract.ReportInformationContract;
import com.laoodao.smartagri.ui.discovery.dialog.CottonDialog;
import com.laoodao.smartagri.ui.discovery.presenter.ReportInformationPresenter;
import com.laoodao.smartagri.utils.IDCardUtil;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.NetworkUtils;
import com.laoodao.smartagri.utils.UiUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by WORK on 2017/4/18.
 */

public class ReportInformationFragment extends LazyFragment<ReportInformationPresenter> implements ReportInformationContract.ReportInformationView {
    @BindView(R.id.btn_commit)
    RoundTextView mBtnCommit;
    @BindView(R.id.tv_year)
    TextView mTvYear;
    @BindView(R.id.et_id_number)
    EditText mEtIdNumber;
    @BindView(R.id.et_verification_code)
    EditText mEtVerificationCode;
    @BindView(R.id.webview_code)
    WebView mWebViewCode;
    @BindView(R.id.update_code)
    FrameLayout mUpdateCode;
    @BindView(R.id.tv_webview)
    TextView mTvWebView;
    @BindView(R.id.tv_code)
    TextView mTvCode;
    @BindView(R.id.ll_year)
    LinearLayout llYear;
    @BindView(R.id.tv_id_number)
    TextView mTvIdNumber;
    private NormalListDialog mYearSelectDialog;
    private CottonDialog codeErrorDialog;
    private CottonDialog onEmptyDialog;
    private CottonDialog onEmptyCodeDialog;
    public boolean isError = true;

    @Override
    public void complete() {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_report_information;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerDiscoveryComponent
                .builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }


    @Override
    public void configViews() {
        mTvCode.setText(Html.fromHtml(UiUtils.getString(R.string.verification_code, "验证码")));
        mTvIdNumber.setText(Html.fromHtml(UiUtils.getString(R.string.id_number, "身份证号码")));
    }


    public void commit() {

        String year = mTvYear.getText().toString();
        String IDNumber = mEtIdNumber.getText().toString().toUpperCase();
        String txtVerificationCode = mEtVerificationCode.getText().toString();
        mPresenter.cottonSelect(getActivity(), Constant.COMMIT_COTTON, IDNumber, txtVerificationCode, year);

    }

    public void inspectCode() {
        if (NetworkUtils.isAvailable(getContext())) {
            mWebViewCode.loadUrl(Constant.COTTON_VERIFICATION_CODE + "?" + System.currentTimeMillis());
            mTvWebView.setText("");
        } else {
            mTvWebView.setText("点击刷新");
        }

    }


    @OnClick({R.id.btn_commit, R.id.tv_webview, R.id.ll_year})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_webview:
                inspectCode();
                break;
            case R.id.btn_commit:
                String IDNumber = mEtIdNumber.getText().toString().toUpperCase();
                String txtVerificationCode = mEtVerificationCode.getText().toString();
                if (TextUtils.isEmpty(IDNumber)) {
                    UiUtils.makeText("请输入身份证号");
                    return;
                }
                if (!"YES".equals(IDCardUtil.IDCardValidate(IDNumber))) {
                    UiUtils.makeText("身份证号不正确");
                    return;
                }
                if (txtVerificationCode.length() != 4) {
                    if (onEmptyCodeDialog == null) {
                        onEmptyCodeDialog = new CottonDialog(getContext(), "请输入正确的验证码", "");
                    }
                    onEmptyCodeDialog.show();
                    return;
                }
                commit();
                break;
            case R.id.ll_year:
                showYearDialog();
                break;
        }
    }

    public void showYearDialog() {
        String[] timeList = UiUtils.getStringArray(R.array.time_dialog);
        if (mYearSelectDialog == null) {
            mYearSelectDialog = new NormalListDialog(getContext(), timeList);
        }
        mYearSelectDialog.dividerColor(R.color.common_divider_narrow)
                .titleBgColor(R.color.colorAccent)
                .isTitleShow(false)
                .layoutAnimation(null)
                .setOnOperItemClickL(((parent, view, position, id) -> {
                    mTvYear.setText(timeList[position]);
                    mYearSelectDialog.dismiss();
                }));
        mYearSelectDialog.show();
    }


    @Override
    public void cottonSusscess(Cotton cotton) {
        if ("no".equals(cotton.validate)) {
            if (codeErrorDialog == null) {
                codeErrorDialog = new CottonDialog(getContext(), "您输入的验证码错误!", "请重新输入");
            }
            codeErrorDialog.show();
            inspectCode();
            return;
        }
        if (cotton.data.size() != 0) {
            CottonCultivationActivity.start(getContext(), cotton);
        } else {
            if (onEmptyDialog == null) {
                onEmptyDialog = new CottonDialog(getContext(), "未找到符合条件的数据", "");
            }
            mWebViewCode.loadUrl(Constant.COTTON_VERIFICATION_CODE + "?" + System.currentTimeMillis());
            onEmptyDialog.show();
        }
    }


    @Override
    protected void lazyFetchData() {
        inspectCode();
        mWebViewCode.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                isError = true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (isError) {
                    mWebViewCode.setVisibility(View.VISIBLE);
                }
                CookieSyncManager.createInstance(getContext());
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
                UiUtils.makeText("网络异常，请检查您的网络");
                mWebViewCode.setVisibility(View.GONE);
                mTvWebView.setText("点击重试");
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                isError = false;
                UiUtils.makeText("网络异常，请检查您的网络");
                mWebViewCode.setVisibility(View.GONE);
                mTvWebView.setText("点击重试");
            }
        });
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && mWebViewCode != null) {
            inspectCode();
        }
    }


    @Override
    protected boolean enableEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void RefreshCodeEvent(CottonRefreshCode code) {
        inspectCode();
        mEtVerificationCode.setText("");
    }
}
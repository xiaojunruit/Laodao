package com.laoodao.smartagri.ui.discovery.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.NormalListDialog;
import com.flyco.roundview.RoundTextView;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.LazyFragment;
import com.laoodao.smartagri.bean.Enterprise.Enterprise;
import com.laoodao.smartagri.bean.Enterprise.EnterpriseAddress;
import com.laoodao.smartagri.bean.Enterprise.EnterpriseAddressChild;
import com.laoodao.smartagri.bean.cotton.Cotton;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerDiscoveryComponent;
import com.laoodao.smartagri.event.EnterpriseRefreshCode;
import com.laoodao.smartagri.ui.discovery.activity.CottonQueryResultActivity;
import com.laoodao.smartagri.ui.discovery.adapter.EnterpriseAddressChildTagInfoAdapter;
import com.laoodao.smartagri.ui.discovery.adapter.EnterpriseAddressTagInfoAdapter;
import com.laoodao.smartagri.ui.discovery.contract.EnterpriseInformationContract;
import com.laoodao.smartagri.ui.discovery.dialog.CottonDialog;
import com.laoodao.smartagri.ui.discovery.presenter.EnterpriseInformationPresenter;
import com.laoodao.smartagri.utils.NetworkUtils;
import com.laoodao.smartagri.utils.UiUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by WORK on 2017/4/18.
 */

public class EnterpriseInformationFragment extends LazyFragment<EnterpriseInformationPresenter> implements EnterpriseInformationContract.EnterpriseInformationView {
    @BindView(R.id.tv_ascriptiona)
    TextView mTvAscriptiona;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.tv_year)
    TextView mTvYear;
    @BindView(R.id.et_enterprisecode)
    EditText mEtEnterprisecode;
    @BindView(R.id.et_enterprisename)
    EditText mEtEnterprisename;
    @BindView(R.id.et_code)
    EditText mEtCode;
    @BindView(R.id.webview_code)
    WebView mWebviewCode;
    @BindView(R.id.tv_webview)
    TextView mTvWebView;
    @BindView(R.id.update_code)
    FrameLayout mUpdateCode;
    @BindView(R.id.btn_commit)
    RoundTextView mBtnCommit;
    @BindView(R.id.tv_code)
    TextView mTvCode;
    private EnterpriseAddressTagInfoAdapter tagInfoAdapter;
    private EnterpriseAddressChildTagInfoAdapter childTagInfoAdapter;
    private List<EnterpriseAddress> address = new ArrayList<>();
    private List<EnterpriseAddressChild> addressChild = new ArrayList<>();
    private NormalListDialog mAddressSelectDialog;
    private NormalListDialog mYearSelectDialog;
    private String mAreaValue = "";
    private String mCityValue = "";
    private int mPosition;
    private CottonDialog onEmptyDialog;
    private CottonDialog codeErrorDialog;
    private CottonDialog onEmptyCodeDialog;
    public boolean isError = true;

    @Override
    public void complete() {

    }


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_enterprise_information;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerDiscoveryComponent
                .builder()
                .appComponent(appComponent)
                .build()
                .inject(this);

    }

    public void inspectCode() {
        if (NetworkUtils.isAvailable(getContext())) {
            mWebviewCode.loadUrl(Constant.COTTON_VERIFICATION_CODE + "?" + System.currentTimeMillis());
            mTvWebView.setText("");
        } else {
            mTvWebView.setText("点击刷新");
        }
    }


    @Override
    public void configViews() {
        mTvCode.setText(Html.fromHtml(UiUtils.getString(R.string.verification_code, "验证码")));
        tagInfoAdapter = new EnterpriseAddressTagInfoAdapter(getContext());
        childTagInfoAdapter = new EnterpriseAddressChildTagInfoAdapter(getContext());
        mPresenter.getCottonArea();
        tagInfoAdapter.setOnItemClick((view, position, tag) -> {
            mAreaValue = tag.areaValue;
            mTvAscriptiona.setText(tag.areaName);
            this.mPosition = position;
            mTvAddress.setText("");
            mTvAddress.setHint("请输入选择所属市县");
            mCityValue = "";
            mAddressSelectDialog.dismiss();
        });
        childTagInfoAdapter.setOnItemClick((view, tag) -> {
            mCityValue = tag.areaValue;
            mTvAddress.setText(tag.areaName);
            mAddressSelectDialog.dismiss();
        });
        mWebviewCode.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mWebviewCode.setVisibility(View.VISIBLE);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                mTvWebView.setText("点击刷新");
                mWebviewCode.setVisibility(View.GONE);
                UiUtils.makeText("网络异常点击刷新试试");
            }
        });
    }

    @OnClick({R.id.btn_commit, R.id.tv_webview, R.id.tv_ascriptiona, R.id.tv_address, R.id.tv_year})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_year:
                showYearDialog();
                break;
            case R.id.tv_address:
                if (mAreaValue == null) {
                    UiUtils.makeText("请选择地州");
                    return;
                }
                childTagInfoAdapter.addAll(address.get(mPosition).child);
                showAddressDialog(view.getId());
                break;
            case R.id.tv_ascriptiona:
                tagInfoAdapter.addAll(address);
                showAddressDialog(view.getId());
                break;
            case R.id.btn_commit:
                commit();
                break;
            case R.id.tv_webview:
                inspectCode();
                break;
        }
    }

    public void commit() {
        String year = mTvYear.getText().toString();
        String enterprisecode = mEtEnterprisecode.getText().toString();
        String enterprisename = mEtEnterprisename.getText().toString();
        String code = mEtCode.getText().toString();
        if (TextUtils.isEmpty(code)) {
            if (onEmptyCodeDialog == null) {
                onEmptyCodeDialog = new CottonDialog(getContext(), "请输入验证码", "");
            }
            // UiUtils.makeText("请输入验证码");
            onEmptyCodeDialog.show();
            return;
        }
        mPresenter.enterpriseSelect(getActivity(), Constant.ENTERPRISE, enterprisecode, code, enterprisename, mAreaValue, mCityValue, year);
    }

    @Override
    protected void lazyFetchData() {
        inspectCode();
        mWebviewCode.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                isError = true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (isError) {
                    mWebviewCode.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                isError = false;
                UiUtils.makeText("网络异常，请检查您的网络");
                mWebviewCode.setVisibility(View.GONE);
                mTvWebView.setText("点击重试");
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                isError = false;
                UiUtils.makeText("网络异常，请检查您的网络");
                mWebviewCode.setVisibility(View.GONE);
                mTvWebView.setText("点击重试");
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && mWebviewCode != null) {
            inspectCode();
        }
    }

    @Override
    public void setEnterprise(Enterprise enterprise) {
        if ("no".equals(enterprise.validate)) {
            if (codeErrorDialog == null) {
                codeErrorDialog = new CottonDialog(getContext(), "您输入的验证码错误!", "请重新输入");
            }
            codeErrorDialog.show();
            inspectCode();
            return;
        }
        if (enterprise == null) {
            return;
        }
        if (enterprise.data.size() != 0) {
            CottonQueryResultActivity.start(getContext(), enterprise);
        } else {
            if (onEmptyDialog == null) {
                onEmptyDialog = new CottonDialog(getContext(), "未找到符合条件的数据", "");
            }
            mWebviewCode.loadUrl(Constant.COTTON_VERIFICATION_CODE + "?" + System.currentTimeMillis());
            onEmptyDialog.show();
        }

    }


    @Override
    public void setEnterpriseAddress(List<EnterpriseAddress> address) {
        this.address = address;
    }

    public void showAddressDialog(int viewId) {
        mAddressSelectDialog = new NormalListDialog(getContext(), viewId == R.id.tv_ascriptiona ? tagInfoAdapter : childTagInfoAdapter)
                .dividerColor(UiUtils.getColor(R.color.common_divider_narrow))
                .titleBgColor(UiUtils.getColor(R.color.colorAccent))
                .isTitleShow(false)
                .layoutAnimation(null);
        mAddressSelectDialog.show();
    }

    public void showYearDialog() {
        if (mYearSelectDialog == null) {
            String[] timeList = UiUtils.getStringArray(R.array.time_dialog);
            mYearSelectDialog = new NormalListDialog(getContext(), timeList)
                    .dividerColor(UiUtils.getColor(R.color.common_divider_narrow))
                    .titleBgColor(UiUtils.getColor(R.color.colorAccent))
                    .isTitleShow(false)
                    .layoutAnimation(null);
            mYearSelectDialog.setOnOperItemClickL(new OnOperItemClickL() {
                @Override
                public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mTvYear.setText(timeList[position]);
                    mYearSelectDialog.dismiss();
                }
            });
        }
        mYearSelectDialog.show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void RefreshCodeEvent(EnterpriseRefreshCode code) {
        mEtCode.setText("");
        inspectCode();
    }


    @Override
    protected boolean enableEventBus() {
        return true;
    }

}
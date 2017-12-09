package com.laoodao.smartagri.ui.discovery.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerDiscoveryComponent;
import com.laoodao.smartagri.ui.discovery.contract.MapFeedbackContract;
import com.laoodao.smartagri.ui.discovery.presenter.MapFeedbackPresenter;
import com.laoodao.smartagri.utils.UiUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by WORK on 2017/8/9.
 */

public class MapFeedbackActivity extends BaseActivity<MapFeedbackPresenter> implements MapFeedbackContract.MapFeedbackView {
    @BindView(R.id.tv_problem)
    TextView mTvProblem;
    @BindView(R.id.tv_contact_information)
    TextView mTvContactInformation;
    @BindView(R.id.et_mark)
    EditText mEtMark;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    private int type;
    private int id;

    public static void start(Context context, int type, int id) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putInt("id", id);
        UiUtils.startActivity(context, MapFeedbackActivity.class, bundle);
    }

    @Override
    public void complete() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_map_feedback;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerDiscoveryComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    protected void configViews() {
        type = getIntent().getIntExtra("type", 0);
        id = getIntent().getIntExtra("id", 0);
        if (type == 3) {
            setTitle("地点报错");
        } else {
            setTitle("其他报错");
        }
        mTvProblem.setText(Html.fromHtml(UiUtils.getString(R.string.problem)));
        mTvContactInformation.setText(Html.fromHtml(UiUtils.getString(R.string.notes_contact_information)));

    }

    @Override
    public void success() {
        finish();
    }

    @OnClick(R.id.tv_commit)
    public void onClick() {
        String mark = mEtMark.getText().toString().trim();
        String phone = mEtPhone.getText().toString().trim();
        if (TextUtils.isEmpty(mark)) {
            UiUtils.makeText("内农不能为空");
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            UiUtils.makeText("联系方式不能为空");
            return;
        }
        if (mark.length() < 5) {
            UiUtils.makeText("请输入至少5个字以上");
            return;
        }
        mPresenter.addSuggest(mark, type, id, phone);
    }
}

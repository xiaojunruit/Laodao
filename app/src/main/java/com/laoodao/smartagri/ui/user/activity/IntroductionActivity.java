package com.laoodao.smartagri.ui.user.activity;

import android.app.Activity;
import android.content.Intent;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.laoodao.smartagri.Global;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.bean.User;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerUserComponent;
import com.laoodao.smartagri.ui.user.contract.IntroductionContract;
import com.laoodao.smartagri.ui.user.dialog.AvatarSelectDialog;
import com.laoodao.smartagri.ui.user.presenter.IntroductionPresenter;
import com.laoodao.smartagri.utils.ProgressOperator;
import com.laoodao.smartagri.utils.UiUtils;

import org.greenrobot.eventbus.Subscribe;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

public class IntroductionActivity extends BaseActivity<IntroductionPresenter> implements IntroductionContract.IntroductionView {


    @BindView(R.id.et_content)
    EditText mEtContent;
    private AvatarSelectDialog dialog;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerUserComponent
                .builder()
                .appComponent(appComponent)
                .build()
                .inject(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_introduction;
    }

    @Override
    protected void configViews() {
        User user = Global.getInstance().getUserInfo();
        mEtContent.setText(user.signature);
        mEtContent.setOnEditorActionListener((v, actionId, event) -> {
            return (event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
        });
        initDialog();
    }

    private void initDialog() {
        dialog = new AvatarSelectDialog(this, true);
        dialog.setOnAvatarListener((view1) -> {
            dialog.dismiss();
            switch (view1.getId()) {
                case R.id.btn_camera:
                    mPresenter.updateSignature(mEtContent.getText().toString());
                    break;
                case R.id.btn_photo:
                    Observable.timer(500, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                            .compose(bindToLifecycle())
                            .subscribe(aLong -> {
                                if (!dialog.isShowing())
                                    finish();
                            });
                    break;
            }
        });
    }

    @Override
    public void complete() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.complete, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_complete) {
            mPresenter.updateSignature(mEtContent.getText().toString());
        } else if (item.getItemId() == android.R.id.home) {
            User user = Global.getInstance().getUserInfo();
            String cotent = mEtContent.getText().toString() + "1";
            if (!cotent.equals(user.signature + "1")) {
                dialog.show();
            } else {
                finish();
            }
        }
        return true;
    }

    @Override
    public void updateSignatureSuccess(String signature) {
        User user = Global.getInstance().getUserInfo();
        user.signature = signature;
        Global.getInstance().setUserInfo(user);
        finish();

    }

    @Override
    public void onBackPressed() {
        User user = Global.getInstance().getUserInfo();
        String cotent = mEtContent.getText().toString() + "1";
        if (!cotent.equals(user.signature + "1")) {
            dialog.show();
            return;
        }
        super.onBackPressed();
    }
}
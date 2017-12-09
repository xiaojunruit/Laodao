package com.laoodao.smartagri.ui.user.activity;

import android.content.Context;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.laoodao.smartagri.Global;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.bean.User;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerUserComponent;
import com.laoodao.smartagri.ui.user.contract.UpdateNickNameContract;
import com.laoodao.smartagri.ui.user.presenter.UpdateNickNamePresenter;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.utils.validator.DefaultValidator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;

import butterknife.BindView;

/**
 * Created by WORK on 2017/4/15
 * 修改昵称
 */

public class UpdateNickNameActivity extends BaseActivity<UpdateNickNamePresenter> implements UpdateNickNameContract.BasicInfoView {
    @Order(1)
    @NotEmpty(messageResId = R.string.please_input_nickname)
    @Length(min = 4, max = 20, messageResId = R.string.please_input_true_nickname)
    @BindView(R.id.et_content)
    EditText mEtContent;
    @BindView(R.id.tv_clues)
    TextView mTvClues;
    private String nickName = "";
    private DefaultValidator mValidator;

    @Override
    public void complete() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_basic_info;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerUserComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    protected void configViews() {
        mValidator = new DefaultValidator(this);
        mTvClues.setText(R.string.update_nick_name);
        User user = Global.getInstance().getUserInfo();
        mEtContent.setText(user.nickname);
        mValidator.succeeded(() -> {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mEtContent.getWindowToken(), 0);
            postData();
        });

        mEtContent.setOnEditorActionListener((v, actionId, event) -> {
            return (event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            mValidator.validate();

        }
        return super.onOptionsItemSelected(item);
    }

    private void postData() {
        nickName = mEtContent.getText().toString();
        mPresenter.updateNickName(nickName);
    }

    @Override
    public void updateSuccess() {
        User user = Global.getInstance().getUserInfo();
        user.nickname = nickName;
        Global.getInstance().setUserInfo(user);
        Global.getInstance().markUserInfoChange();
        finish();
    }
}

package com.laoodao.smartagri.ui.user.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.flyco.roundview.RoundTextView;
import com.flyco.tablayout.utils.FragmentChangeManager;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerUserComponent;
import com.laoodao.smartagri.ui.user.contract.ForgetPwdContract;
import com.laoodao.smartagri.ui.user.fragment.SettingPwdFragment;
import com.laoodao.smartagri.ui.user.fragment.ValidatePhoneFragment;
import com.laoodao.smartagri.ui.user.presenter.ForgetPwdPresenter;
import com.laoodao.smartagri.utils.CodeCountDown;
import com.laoodao.smartagri.utils.validator.DefaultValidator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by WORK on 2017/4/15.
 */

public class ForgetPwdActivity extends BaseActivity<ForgetPwdPresenter> implements ForgetPwdContract.ForgetPwdView {

    private ArrayList<Fragment> fragments = new ArrayList<>();

    private FragmentChangeManager mFragmentChangeManager;

    @Override
    public void complete() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget_pwd;
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
        fragments.add(new ValidatePhoneFragment());
        fragments.add(new SettingPwdFragment());
        mFragmentChangeManager = new FragmentChangeManager(getSupportFragmentManager(), R.id.content, fragments);
        mFragmentChangeManager.setFragments(0);


    }

    public void nextStep(String token) {
        mFragmentChangeManager.setFragments(1);
        Fragment currentFragment = mFragmentChangeManager.getCurrentFragment();
        ((SettingPwdFragment) currentFragment).setToken(token);
    }
}

package com.laoodao.smartagri.ui.user.activity;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.roundview.RoundTextView;
import com.laoodao.smartagri.Global;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.bean.Calendar;
import com.laoodao.smartagri.bean.SignIn;
import com.laoodao.smartagri.bean.User;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerUserComponent;
import com.laoodao.smartagri.ui.user.adapter.CalendarAdapter;
import com.laoodao.smartagri.ui.user.contract.SignInContract;
import com.laoodao.smartagri.ui.user.presenter.SignInPresenter;
import com.laoodao.smartagri.utils.SpannableStringUtils;
import com.laoodao.smartagri.utils.UiUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SignInActivity extends BaseActivity<SignInPresenter> implements SignInContract.SignInView {


    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.tv_integral_record)
    TextView mTvIntegralRecord;
    @BindView(R.id.tv_use_record)
    TextView mTvUseRecord;
    @BindView(R.id.tv_date)
    TextView mTvDate;
    @BindView(R.id.tv_previous)
    TextView mTvPrevious;
    @BindView(R.id.tv_next)
    TextView mTvNext;
    @BindView(R.id.btn_sign_in)
    FrameLayout mBtnSignIn;
    @BindView(R.id.tv_sign_in)
    TextView mTvSignIn;
    @BindView(R.id.tv_points)
    TextView mTvPoints;
    @BindView(R.id.tv_welfare)
    RoundTextView mTvWelfare;
    @BindView(R.id.tv_sign_in_days)
    TextView mTvSignInDays;
    @BindView(R.id.integral)
    LinearLayout mIntegral;

    private CalendarAdapter mAdapter;

    private SignIn mSignIn;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerUserComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sign_in;
    }

    @Override
    protected void configViews() {
        mTvIntegralRecord.setText(SpannableStringUtils.getBuilder(this, getString(R.string.integral_record)).setUnderline().create());
        mTvUseRecord.setText(SpannableStringUtils.getBuilder(this, getString(R.string.use_record)).setUnderline().create());
        mAdapter = new CalendarAdapter(this);
        mRecyclerview.setAdapter(mAdapter);
        mPresenter.getCalender();
        mPresenter.requestPoint();
    }


    @Override
    public void complete() {

    }


    @Override
    public void showCalender(List<Calendar> calendarList) {
        mAdapter.addAll(calendarList, true);
    }

    @Override
    public void showDate(String date) {
        mTvDate.setText(date);
    }

    @Override
    public void signInSuccess() {
        mPresenter.getCalender();
    }

    @Override
    public void showSignIn(SignIn result) {
        this.mSignIn = result;
        mTvPoints.setText(String.valueOf(result.points));
        mTvSignInDays.setText(UiUtils.getString(R.string.sign_in_days, result.signInDays));
        mTvSignIn.setText(result.isSignIn ? R.string.signed : R.string.sign_in);
        mAdapter.setSignedData(result.calendar);
    }

    @Override
    public void pointSuccess(String point) {
        if (!TextUtils.isEmpty(point)) {
            User user = Global.getInstance().getUserInfo();
            user.points = point;
            mTvPoints.setText(point);
            Global.getInstance().setUserInfo(user);
        }

    }

    @OnClick({R.id.tv_integral_record, R.id.tv_use_record, R.id.tv_previous, R.id.tv_next, R.id.btn_sign_in, R.id.integral})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.integral:
                IntegralDetailActivity.start(this, String.valueOf(mSignIn.points));
                break;
            case R.id.tv_integral_record:
                break;
            case R.id.tv_use_record:
                break;
            case R.id.tv_previous:
                mPresenter.previousMonth();
                break;
            case R.id.tv_next:
                mPresenter.nextMonth();
                break;
            case R.id.btn_sign_in:

                if (this.mSignIn != null && mSignIn.isSignIn) {
                    UiUtils.startActivity(IntegralTaskActivity.class);
                } else {
                    mPresenter.signIn();
                }
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_rule) {
            UiUtils.startActivity(IntegralRuleActivity.class);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.rule, menu);
        return true;
    }
}
package com.laoodao.smartagri.ui.qa.fragment;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.laoodao.smartagri.Global;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseFragment;
import com.laoodao.smartagri.base.LazyFragment;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerQAComponent;
import com.laoodao.smartagri.event.QAEvent;
import com.laoodao.smartagri.event.ReleaseQuestionEvent;
import com.laoodao.smartagri.ui.qa.activity.AskActivity;
import com.laoodao.smartagri.ui.qa.activity.QuestionSearchActivity;
import com.laoodao.smartagri.ui.qa.contract.QAContract;
import com.laoodao.smartagri.ui.qa.dialog.QuestionSuccessDialog;
import com.laoodao.smartagri.ui.qa.presenter.QAPresenter;
import com.laoodao.smartagri.ui.user.activity.LoginActivity;
import com.laoodao.smartagri.ui.user.adapter.TabsAdapter;
import com.laoodao.smartagri.utils.UiUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 欧源 on 2017/4/13.
 * 问答
 */

public class QAFragment extends LazyFragment<QAPresenter> implements QAContract.QAView {
    @BindView(R.id.tabLayout)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    @BindView(R.id.iv_search)
    ImageView mIvSearch;
    private TabsAdapter mTabAdapter;
    @BindView(R.id.tv_ask)
    TextView mTvAsk;

    BaseFragment[] mFragments = {new NewestQuestionFragment(), new FollowContentFragment(), new MyQATabLayoutFragment()};
    private QuestionSuccessDialog dialog;


    @Override
    protected boolean enableEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void qaEvent(QAEvent event) {
        mViewpager.setCurrentItem(event.id);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_qa;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerQAComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    private void initTabLayout() {
        String[] tabTitle = getResources().getStringArray(R.array.qa_array);

        mTabAdapter = new TabsAdapter(getChildFragmentManager(), mFragments);
        mViewpager.setAdapter(mTabAdapter);
        mTabLayout.setViewPager(mViewpager, tabTitle);
    }

    @Override
    public void configViews() {

    }

    @Override
    public void complete() {

    }

    @OnClick({R.id.tv_ask, R.id.iv_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_ask:
                if (!Global.getInstance().isLoggedIn()) {
                    UiUtils.startActivity(LoginActivity.class);
                    return;
                }
                AskActivity.start(getContext(), "main");
                break;
            case R.id.iv_search:
                UiUtils.startActivity(mActivity, QuestionSearchActivity.class);
                break;
        }
    }


    @Override
    protected void lazyFetchData() {
        initTabLayout();
        dialog = new QuestionSuccessDialog(getContext());
    }




}

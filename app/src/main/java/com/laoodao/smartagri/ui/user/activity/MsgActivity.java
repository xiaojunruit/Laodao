package com.laoodao.smartagri.ui.user.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.flyco.tablayout.utils.UnreadMsgUtils;
import com.flyco.tablayout.widget.MsgView;
import com.laoodao.smartagri.Global;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.bean.NewMessage;
import com.laoodao.smartagri.bean.User;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerUserComponent;
import com.laoodao.smartagri.event.ClearMsgEvent;
import com.laoodao.smartagri.event.CloseNoticeEvent;
import com.laoodao.smartagri.event.NewMessageEvent;
import com.laoodao.smartagri.event.RefreshMsgEvent;
import com.laoodao.smartagri.ui.user.adapter.TabsAdapter;
import com.laoodao.smartagri.ui.user.contract.MsgContract;
import com.laoodao.smartagri.ui.user.fragment.DynamicFragment;
import com.laoodao.smartagri.ui.user.fragment.NoticeFragment;
import com.laoodao.smartagri.ui.user.fragment.ReplyFragment;
import com.laoodao.smartagri.ui.user.presenter.MsgPresenter;
import com.laoodao.smartagri.utils.UiUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * Created by WORK on 2017/5/4.
 */

public class MsgActivity extends BaseActivity<MsgPresenter> implements MsgContract.MsgView {
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    @BindView(R.id.tabLayout)
    SegmentTabLayout mTabLayout;
    private int tab;

    @Override
    public void complete() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_msg;
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
        User user = Global.getInstance().getUserInfo();
        String[] tabTitle = getResources().getStringArray(R.array.user_message);
        Fragment[] fragments = {
                new NoticeFragment(),
                new ReplyFragment(),
                new DynamicFragment()
        };
        mViewpager.setAdapter(new TabsAdapter(getSupportFragmentManager(), fragments));
        mTabLayout.setTabData(tabTitle);
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewpager.setCurrentItem(position);

            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout.setCurrentTab(position);
                if (position != 0) {
                    mTabLayout.hideMsg(position);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.requestMessage();
    }

    @Override
    public void isNewMessage(NewMessage message) {
        // EventBus.getDefault().post(new NewMessageEvent(message.notice, message.dynamic, message.answer));
        refreshMsg(message);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshMsgChange(RefreshMsgEvent event) {
        refreshMsg(event.newMessage);
    }

    public void refreshMsg(NewMessage message) {
        if (message.notice) {
            mTabLayout.showDot(0);
        } else {
            mTabLayout.hideMsg(0);
        }
        if (message.answer) {
            mTabLayout.showDot(1);
        } else {
            mTabLayout.hideMsg(1);
        }
        if (message.dynamic) {
            mTabLayout.showDot(2);
        } else {
            mTabLayout.hideMsg(2);
        }
    }


    @Override
    public void clearMsgSuccess() {
        EventBus.getDefault().post(new ClearMsgEvent(tab));
    }


    public void showDot(int position) {
        mTabLayout.showDot(position);
        MsgView msgView = mTabLayout.getMsgView(position);
        if (msgView != null) {
            UnreadMsgUtils.setSize(msgView, UiUtils.dip2px(7.5f));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.clear, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_clear) {
            tab = mTabLayout.getCurrentTab();
            mPresenter.clearMsg((mTabLayout.getCurrentTab() + 1));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected boolean enableEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageChange(CloseNoticeEvent event) {
        mTabLayout.hideMsg(0);
    }
}

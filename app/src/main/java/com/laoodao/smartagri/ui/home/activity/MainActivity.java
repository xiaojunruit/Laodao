package com.laoodao.smartagri.ui.home.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.ejz.update.UpdateAgent;
import com.ejz.update.UpdateManager;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.flyco.tablayout.utils.UnreadMsgUtils;
import com.flyco.tablayout.widget.MsgView;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.model.EaseNotifier;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.laoodao.smartagri.BuildConfig;
import com.laoodao.smartagri.Global;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.bean.NewMessage;
import com.laoodao.smartagri.bean.TabEntity;
import com.laoodao.smartagri.bean.User;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerMainComponent;
import com.laoodao.smartagri.event.NewMessageEvent;
import com.laoodao.smartagri.event.ReleaseQuestionEvent;
import com.laoodao.smartagri.event.ShareEvent;
import com.laoodao.smartagri.event.ShareIdEvent;
import com.laoodao.smartagri.event.UserInfoChangedEvent;
import com.laoodao.smartagri.ui.discovery.fragment.DiscoverFragment;
import com.laoodao.smartagri.ui.home.contract.MainContract;
import com.laoodao.smartagri.ui.home.fragment.HomeFragment;
import com.laoodao.smartagri.ui.home.presenter.MainPresenter;
import com.laoodao.smartagri.ui.market.dialog.ShareDialog;
import com.laoodao.smartagri.ui.market.fragment.MarketFragment;
import com.laoodao.smartagri.ui.qa.dialog.QuestionSuccessDialog;
import com.laoodao.smartagri.ui.qa.fragment.QAFragment;
import com.laoodao.smartagri.ui.user.activity.ChatActivity;
import com.laoodao.smartagri.ui.user.adapter.TabsAdapter;
import com.laoodao.smartagri.ui.user.fragment.UserFragmnet;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.Packer;
import com.laoodao.smartagri.utils.PermissionUtil;
import com.laoodao.smartagri.utils.SharedPreferencesUtil;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.NoScrollViewPager;
import com.laoodao.smartagri.wxapi.QQSdk;
import com.tbruyelle.rxpermissions.RxPermissions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.MainView {


    @BindView(R.id.tabLayout)
    CommonTabLayout mTabLayout;
    @BindView(R.id.viewpager)
    NoScrollViewPager mViewPager;
    private long _lastClickTime = 0L;
    private ShareDialog shareDialog;
    private QuestionSuccessDialog dialog;
    private boolean shareDialogShow, layoutVisible;
    private int QuestionId;
    private String askFrom;


    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerMainComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    public static void start(Context context, int tab) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.TAB, tab);
        UiUtils.startActivity(context, MainActivity.class, bundle, Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
    }


    public static void start(Context context, Bundle bundle) {
        UiUtils.startActivity(context, MainActivity.class, bundle, Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        updateTab(intent);
    }

    public void updateTab(Intent intent) {
        int tab = intent.getIntExtra(Constant.TAB, getLayoutId() > 0 ? mTabLayout.getCurrentTab() : 0);
        mViewPager.setCurrentItem(tab, false);
    }

    private EMMessageListener msgListener;

    @Override
    protected void onDestroy() {
        EMClient.getInstance().chatManager().removeMessageListener(msgListener);
        super.onDestroy();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    protected void configViews() {
        initTabLayout();
        requestPermission();
        checkUpdate();
        isNewMessage();
        if (Global.getInstance().isLoggedIn()) {
            mPresenter.requsetMessage();
        }
        mPresenter.requestInitMenu();

        setEaseUIProviders();
        msgListener = new EMMessageListener() {

            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                for (EMMessage message : messages) {
//                    DemoHelper.getInstance().getNotifier().onNewMsg(message);
                    EaseUI.getInstance().getNotifier().onNewMsg(message);
                }

            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {
                //收到透传消息
            }

            @Override
            public void onMessageRead(List<EMMessage> messages) {
                //收到已读回执
            }

            @Override
            public void onMessageDelivered(List<EMMessage> message) {
                //收到已送达回执
            }

            @Override
            public void onMessageRecalled(List<EMMessage> messages) {
                //消息被撤回
            }

            @Override
            public void onMessageChanged(EMMessage message, Object change) {
                //消息状态变动
            }
        };
        EMClient.getInstance().chatManager().addMessageListener(msgListener);
//        EMMessage message = EMMessage.createTxtSendMessage("32131321", "android2");
//        EMClient.getInstance().chatManager().sendMessage(message);

    }

    private void setEaseUIProviders() {
        if (EaseUI.getInstance().getNotifier() == null) {
            return;
        }
        EaseUI.getInstance().getNotifier().setNotificationInfoProvider(new EaseNotifier.EaseNotificationInfoProvider() {

            @Override
            public String getTitle(EMMessage message) {
                //you can update title here
                return null;
            }

            @Override
            public int getSmallIcon(EMMessage message) {
                //you can update icon here
                return 0;
            }

            @Override
            public String getDisplayedText(EMMessage message) {
                // be used on notification bar, different text according the message type.
                String ticker = EaseCommonUtils.getMessageDigest(message, MainActivity.this);
                if (message.getType() == EMMessage.Type.TXT) {
                    ticker = ticker.replaceAll("\\[.{2,3}\\]", "[表情]");
                }
                return message.getFrom() + ": " + ticker;
            }

            @Override
            public String getLatestText(EMMessage message, int fromUsersNum, int messageNum) {
                // here you can customize the text.
                // return fromUsersNum + "contacts send " + messageNum + "messages to you";
                return null;
            }

            @Override
            public Intent getLaunchIntent(EMMessage message) {
                User user = Global.getInstance().getUserInfo();
                Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                intent.putExtra("userId", message.getFrom());
                intent.putExtra("chatType", 1);
                intent.putExtra("avatar", "");
                intent.putExtra("userAvatar", user.avatar);
                return intent;
            }
        });
    }


    /**
     * 检查更新
     */
    private void checkUpdate() {
        UpdateAgent.setChannel(Packer.getMarket(this, BuildConfig.FLAVOR));
        UpdateAgent.setUpdateUrl(BuildConfig.APP_BASE_URL + "ld/checkUpdate");
        UpdateManager.update(this, (var1, var2) -> {
//            UiUtils.makeText("已是最新版本");
        });
    }

    private void requestPermission() {
        PermissionUtil.externalStorage(new PermissionUtil.RequestPermissionListener() {
            @Override
            public void success() {

            }

            @Override
            public void failure() {
                Toast.makeText(mApplication, "请求权限失败,请前往设置开启权限！", Toast.LENGTH_SHORT).show();
            }
        }, new RxPermissions(this));
    }

    private void initTabLayout() {
        String[] tabTitle = getResources().getStringArray(R.array.homt_tab);
        Fragment[] fragments = {new HomeFragment(), new QAFragment(), new DiscoverFragment(), new MarketFragment(), new UserFragmnet()};
        TypedArray selectedIcon = getResources().obtainTypedArray(R.array.homt_tab_selected_icon);
        TypedArray unselectedIcon = getResources().obtainTypedArray(R.array.homt_tab_unselected_icon);

        ArrayList<CustomTabEntity> tabEntity = new ArrayList<>();
        for (int i = 0; i < tabTitle.length; i++) {
            tabEntity.add(new TabEntity(tabTitle[i], selectedIcon.getResourceId(i, 0), unselectedIcon.getResourceId(i, 0)));
        }
        mTabLayout.setTabData(tabEntity);
        mViewPager.setAdapter(new TabsAdapter(getSupportFragmentManager(), fragments));
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position, false);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (shareDialogShow) {
            shareDialog.onActivityResult(requestCode, resultCode, data);
            shareDialogShow = false;
        } else if (dialog.dialogIsShow) {
            dialog.onActivityResult(requestCode, resultCode, data);
            dialog.dialogIsShow = false;
        }


    }


    @Subscribe
    public void shareQuestion(ShareIdEvent event) {
        QuestionId = event.id;
        if (shareDialog == null) {
            shareDialog = event.shareDialog;
        }
        shareDialogShow = true;
    }

    @Subscribe
    public void releaseQuestion(ReleaseQuestionEvent event) {
        QuestionId = event.askSuccess.askId;
        askFrom = event.askFrom;
        if (dialog == null) {
            dialog = new QuestionSuccessDialog(this);
        }
        dialog.setData(event.askSuccess);
        dialog.dialogIsShow = true;
    }

    @Subscribe
    public void shareSuccessEvent(ShareEvent event) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (layoutVisible) {
                    mPresenter.share("ask", QuestionId);
                }
            }
        }, 500);
    }

    @Override
    public void onPause() {
        super.onPause();
        layoutVisible = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        layoutVisible = true;
        if ("main".equals(askFrom) && dialog != null) {
            dialog.show();
            askFrom = "";
        }
    }

    @Override
    public void complete() {

    }

    public void onBackPressed() {
        if (this.isFastClick()) {
            UiUtils.killAll();
        } else {
            UiUtils.makeText("再按一次，退出应用！");
        }
    }

    private boolean isFastClick() {
        long now = System.currentTimeMillis();
        if (now - this._lastClickTime < 2000L) {
            return true;
        } else {
            this._lastClickTime = now;
            return false;
        }
    }

    private void isNewMessage() {
        Observable.interval(30, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .compose(bindToLifecycle())
                .subscribe(aLong -> {
                    if (!Global.getInstance().isLoggedIn()) return;
                    mPresenter.requsetMessage();
                });
    }

    @Override
    public void showMessage(NewMessage message) {
        if (message.dynamic || message.answer || message.notice) {
            showDot();
            EventBus.getDefault().post(new NewMessageEvent(message.notice, message.dynamic, message.answer));
        } else {
            mTabLayout.hideMsg(4);
        }
    }

    @Override
    public void loginHuanXin() {
        if (!EMClient.getInstance().isLoggedInBefore()) {
            String pwd = SharedPreferencesUtil.getInstance().getString(Constant.HUAN_XIN_PWD);
            User user = Global.getInstance().getUserInfo();
            if (!TextUtils.isEmpty(pwd)) {
                EMClient.getInstance().login(user.uid, pwd, new EMCallBack() {//回调
                    @Override
                    public void onSuccess() {
                        EMClient.getInstance().groupManager().loadAllGroups();
                        EMClient.getInstance().chatManager().loadAllConversations();
                        Log.e("main", "登录聊天服务器成功！");
                        startActivity(new Intent(MainActivity.this, MainActivity.class));
                    }

                    @Override
                    public void onProgress(int progress, String status) {

                    }

                    @Override
                    public void onError(int code, String message) {
                        Log.e("main", "登录聊天服务器失败！code=" + code + "----" + message);
                    }
                });
            }
        }
    }


    /**
     * 显示消息小红蒂娜
     */
    public void showDot() {
        mTabLayout.showDot(4);
        MsgView msgView = mTabLayout.getMsgView(4);
        if (msgView != null) {
            UnreadMsgUtils.setSize(msgView, UiUtils.dip2px(7.5f));
            mTabLayout.setMsgMargin(4, -3, 5);
        }
    }


    @Override
    protected boolean enableEventBus() {
        return true;
    }

    @Subscribe
    public void messageChange(NewMessageEvent event) {
        if (event != null) {
            if (event.notice || event.dynamic || event.answer) {
                showDot();
            } else {
                mTabLayout.hideMsg(4);
            }
        }
    }

    public void isLoginEvent(UserInfoChangedEvent event) {
        isNewMessage();
    }
}

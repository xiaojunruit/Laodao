package com.laoodao.smartagri.ui.user.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.flyco.tablayout.widget.MsgView;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.laoodao.smartagri.Global;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseFragment;
import com.laoodao.smartagri.bean.NewMessage;
import com.laoodao.smartagri.bean.User;
import com.laoodao.smartagri.bean.UserMenu;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.common.Route;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerUserComponent;
import com.laoodao.smartagri.event.CloseNoticeEvent;
import com.laoodao.smartagri.event.DynamicEvent;
import com.laoodao.smartagri.event.ReplyEvent;
import com.laoodao.smartagri.event.UserInfoChangedEvent;
import com.laoodao.smartagri.ui.home.activity.MainActivity;
import com.laoodao.smartagri.ui.user.activity.ApplyCertificationActivity;
import com.laoodao.smartagri.ui.user.activity.IntegralDetailActivity;
import com.laoodao.smartagri.ui.user.activity.LoginActivity;
import com.laoodao.smartagri.ui.user.activity.MsgActivity;
import com.laoodao.smartagri.ui.user.activity.MyLoanActivity;
import com.laoodao.smartagri.ui.user.activity.UserHomePageActivity;
import com.laoodao.smartagri.ui.user.activity.UserInfoActivity;
import com.laoodao.smartagri.ui.user.adapter.UserMenuAdapter;
import com.laoodao.smartagri.ui.user.contract.UserContract;
import com.laoodao.smartagri.ui.user.presenter.UserPresenter;
import com.laoodao.smartagri.utils.PermissionUtil;
import com.laoodao.smartagri.utils.SharedPreferencesUtil;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.recyclerview.decoration.DividerDecoration;
import com.tbruyelle.rxpermissions.RxPermissions;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 欧源 on 2017/4/13.
 */

public class UserFragmnet extends BaseFragment<UserPresenter> implements UserContract.UserView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.message)
    RelativeLayout mMessage;
    @BindView(R.id.msg_dot)
    MsgView mMsgDot;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout mSwipeLayout;
    @BindView(R.id.my_loan)
    LinearLayout mMyLoan;
    @BindView(R.id.integral)
    LinearLayout mIntegral;
    @BindView(R.id.userinfo)
    LinearLayout mUserinfo;
    @BindView(R.id.img_avatar)
    CircleImageView mImgAvatar;
    @BindView(R.id.tv_username)
    TextView mTvUsername;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.tv_loan)
    TextView mTvLoan;
    @BindView(R.id.tv_integral)
    TextView mTvIntegral;
    @BindView(R.id.tv_perfect)
    TextView mTvPerfect;
    @BindView(R.id.fl_perfect)
    FrameLayout mFlPerfect;
    @BindView(R.id.tv_authentication)
    TextView mTvAuthentication;
    @BindView(R.id.tv_signature)
    TextView mTvSignature;
    private UserMenuAdapter mAdapter;
    private boolean notice;
    private boolean dynamic;
    private boolean answer;
    private String id;
    private String imPwd = "";

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_user;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerUserComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }


    @Override
    public void onRefresh() {
        mPresenter.getMenuList();
        initUserInfo();
        if (Global.getInstance().isLoggedIn()) {
            mPresenter.requestPoint();
        }
    }

    private void isLogin(View view) {
        boolean isLogin = Global.getInstance().isLoggedIn();
        if (!isLogin) {
            UiUtils.startActivity(LoginActivity.class);
            return;
        }
        switch (view.getId()) {
            case R.id.tv_open_information:
                UiUtils.startActivity(UserInfoActivity.class);
                break;
            case R.id.my_loan:
                UiUtils.startActivity(MyLoanActivity.class);
                break;
            case R.id.integral:
                IntegralDetailActivity.start(getContext(), mTvIntegral.getText().toString());
                break;
            case R.id.userinfo:
                UserHomePageActivity.start(getContext(), Integer.parseInt(id));
                break;
            case R.id.message:
                UiUtils.startActivity(MsgActivity.class);
                break;
            case R.id.tv_authentication:
                User user = Global.getInstance().getUserInfo();
                ApplyCertificationActivity.start(getContext(), Integer.parseInt(user.memberType));
                break;
        }
    }

    @OnClick({R.id.message, R.id.my_loan, R.id.integral, R.id.userinfo, R.id.tv_open_information, R.id.tv_authentication})
    public void onClick(View view) {
        isLogin(view);
    }


    @Override
    public void configViews() {
        mAdapter = new UserMenuAdapter(getContext());
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeColors(0xff0081cc);
        mAdapter.setOnItemClickListener(position -> {
            if (!Global.getInstance().isLoggedIn()) {
                UiUtils.startActivity(LoginActivity.class);
                return;
            }
            if (mAdapter.getItem(position).url.contains("emchat_list")) {
                requestPermission(position);
            } else {
                Route.go(mActivity, mAdapter.getItem(position).url);
            }
        });
        mRecyclerview.setAdapter(mAdapter);
        mRecyclerview.addItemDecoration(new DividerDecoration(ContextCompat.getColor(getContext(), R.color.common_divider_narrow), 1));
        onRefresh();
    }

    private void requestPermission(int position) {
        PermissionUtil.recordAudio(new PermissionUtil.RequestPermissionListener() {
            @Override
            public void success() {
                loginHuanXin(getContext(), mAdapter.getItem(position));
            }

            @Override
            public void failure() {
                Toast.makeText(getContext(), "请求权限失败,请前往设置开启权限！", Toast.LENGTH_SHORT).show();
            }
        }, new RxPermissions(getActivity()));
    }

    private void loginHuanXin(Context context, UserMenu item) {
        if (!EMClient.getInstance().isLoggedInBefore()) {
            Toast.makeText(context, "正在连接...", Toast.LENGTH_SHORT).show();
            User info = Global.getInstance().getUserInfo();
            if (!TextUtils.isEmpty(info.emcode)) {
                imPwd = info.emcode;
            } else {
                String pwd = SharedPreferencesUtil.getInstance().getString(Constant.HUAN_XIN_PWD, "");
                if (!TextUtils.isEmpty(pwd)) {
                    imPwd = pwd;
                }
            }
            if (TextUtils.isEmpty(imPwd)) {
                Toast.makeText(context, "密码获取失败，请重新获取密码", Toast.LENGTH_SHORT).show();
                return;
            }
            EMClient.getInstance().login(info.uid, imPwd, new EMCallBack() {//回调
                @Override
                public void onSuccess() {
                    EMClient.getInstance().groupManager().loadAllGroups();
                    EMClient.getInstance().chatManager().loadAllConversations();
                    Route.go(context, item.url);
                    Log.e("main", "登录聊天服务器成功！");
                }

                @Override
                public void onProgress(int progress, String status) {

                }

                @Override
                public void onError(int code, String message) {
                    Log.e("main", "登录聊天服务器失败！code=" + code + "----" + message);
                }
            });
        } else {
            Route.go(context, item.url);
        }
    }


    @Override
    public void showMenuList(List<UserMenu> userMenuList) {
        mAdapter.clear();
        mAdapter.addAll(userMenuList);
    }

    @Override
    public void showUserInfo(User user) {
        Global.getInstance().setUserInfo(user);
        id = user.cid;
        mTvUsername.setText(user.nickname);
        mTvPhone.setText(UiUtils.getString(R.string.mobile_phone, user.mobile));
        mTvLoan.setText(user.loan);
        mTvIntegral.setText(user.points);
        Glide.with(mApplication)
                .load(user.avatar)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(mImgAvatar);
        mFlPerfect.setVisibility(user.isShowIntegrity ? View.VISIBLE : View.GONE);
        mTvPerfect.setText(Html.fromHtml(UiUtils.getString(R.string.integrity, user.integrity)));
        if (Global.getInstance().isLoggedIn()) {
            mTvAuthentication.setVisibility(TextUtils.isEmpty(user.memberTypeName) ? View.INVISIBLE : View.VISIBLE);
        }
        mTvAuthentication.setText(user.memberTypeName);
        mTvSignature.setText(user.signature);
    }

    @Override
    public void isNewMessage(NewMessage message) {
        notice = message.notice;
        dynamic = message.dynamic;
        answer = message.answer;
        if (message.notice || message.dynamic || message.answer) {
            mMsgDot.setVisibility(View.VISIBLE);
        } else {
            mMsgDot.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void pointSuccess(String point) {
        if (!TextUtils.isEmpty(point)) {
            User user = Global.getInstance().getUserInfo();
            user.points = point;
            mTvIntegral.setText(point);
            Global.getInstance().setUserInfo(user);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (Global.getInstance().isLoggedIn()) {
            onRefresh();
            //mPresenter.requestPoint();
            //mPresenter.getUserInfo();
            if (notice || dynamic || answer) {
                mMsgDot.setVisibility(View.VISIBLE);
            } else {
                mMsgDot.setVisibility(View.INVISIBLE);
            }
        }
    }


    /**
     * 接受eventbus传递过来的消息，监听用户数据是否改变
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receive(UserInfoChangedEvent event) {
        initUserInfo();
        if (Global.getInstance().isLoggedIn()) {
            loginHuanXin();
        }
    }

    public void loginHuanXin() {
        if (!EMClient.getInstance().isLoggedInBefore()) {
            User user = Global.getInstance().getUserInfo();
            if (!TextUtils.isEmpty(user.emcode)) {
                EMClient.getInstance().login(user.uid, user.emcode, new EMCallBack() {//回调
                    @Override
                    public void onSuccess() {
                        EMClient.getInstance().groupManager().loadAllGroups();
                        EMClient.getInstance().chatManager().loadAllConversations();
                        Log.e("main", "登录聊天服务器成功！");
                        startActivity(new Intent(getActivity(), MainActivity.class));
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


    @Override
    protected boolean enableEventBus() {
        return true;
    }

    /**
     * 初始化用户数据
     */
    private void initUserInfo() {
        boolean loggedIn = Global.getInstance().isLoggedIn();
        if (!loggedIn) {
            mImgAvatar.setImageResource(R.mipmap.ic_default_avatar);
            mTvIntegral.setText("－－");
            mTvLoan.setText("－－");
            mTvPhone.setText(null);
            mTvUsername.setText(R.string.not_logged_in);
            mTvAuthentication.setVisibility(View.GONE);
            mMsgDot.setVisibility(View.INVISIBLE);
            mTvSignature.setVisibility(View.GONE);
            mFlPerfect.setVisibility(View.GONE);
        } else {
            mPresenter.getUserInfo();
            mPresenter.requestMessage();
        }
    }

    @Override
    public void complete() {
        mSwipeLayout.setRefreshing(false);
    }


    @Subscribe
    public void ReplyChange(ReplyEvent event) {
        answer = false;
    }

    @Subscribe
    public void DynamicChange(DynamicEvent event) {
        dynamic = false;
    }

    @Subscribe
    public void NoticeChange(CloseNoticeEvent event) {
        notice = false;
    }


}

package com.laoodao.smartagri.ui.user.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ejz.multistateview.MultiStateView;
import com.flyco.roundview.RoundTextView;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.laoodao.smartagri.Global;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseHeaderView;
import com.laoodao.smartagri.base.BaseXRVActivity;
import com.laoodao.smartagri.bean.ShareInfo;
import com.laoodao.smartagri.bean.User;
import com.laoodao.smartagri.bean.UserHomePageData;
import com.laoodao.smartagri.bean.UserHomePageHead;
import com.laoodao.smartagri.bean.UserMenu;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.common.Route;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerUserComponent;
import com.laoodao.smartagri.event.ReleaseQuestionEvent;
import com.laoodao.smartagri.event.ShareEvent;
import com.laoodao.smartagri.ui.discovery.activity.MyAttentionPriceActivity;
import com.laoodao.smartagri.ui.home.activity.MainActivity;
import com.laoodao.smartagri.ui.market.activity.ReleaseBuyingActivity;
import com.laoodao.smartagri.ui.market.activity.ReleaseSupplyingActivity;
import com.laoodao.smartagri.ui.market.activity.SupplyDetailsActivity;
import com.laoodao.smartagri.ui.market.dialog.ShareDialog;
import com.laoodao.smartagri.ui.qa.activity.AskActivity;
import com.laoodao.smartagri.ui.qa.dialog.QuestionSuccessDialog;
import com.laoodao.smartagri.ui.user.adapter.UserHomePageAdapter;
import com.laoodao.smartagri.ui.user.contract.UserHomePageContract;
import com.laoodao.smartagri.ui.user.presenter.UserHomePagePresenter;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.PermissionUtil;
import com.laoodao.smartagri.utils.SharedPreferencesUtil;
import com.laoodao.smartagri.utils.UiUtils;
import com.tbruyelle.rxpermissions.RxPermissions;

import org.greenrobot.eventbus.Subscribe;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by WORK on 2017/8/14.
 */

public class UserHomePageActivity extends BaseXRVActivity<UserHomePagePresenter> implements UserHomePageContract.UserHomePageView {


    @BindView(R.id.fl_toolbar)
    FrameLayout mFlToolbar;
    @BindView(R.id.fl_all_dynamic)
    FrameLayout mFlAllDynamic;
    UserHomePageHeader header;
    @BindView(R.id.tv_chat)
    TextView mTvChat;
    @BindView(R.id.tv_add)
    TextView mTvAdd;
    @BindView(R.id.tv_dynamic)
    TextView mTvDynamic;
    private UserHomePageHead userheader;
    private int id;
    private int type;
    private ShareDialog shareDialog;
    private QuestionSuccessDialog dialog;
    private int QuestionId;
    private String askFrom = "";
    private boolean layoutVisible;
    private String imPwd = "";


    public static void start(Context context, int id) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        UiUtils.startActivity(context, UserHomePageActivity.class, bundle);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_home_page;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerUserComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    int mDistanceY;

    @Override
    protected void configViews() {
        id = getIntent().getIntExtra("id", 0);
        mHeader = new UserHomePageHeader(this);
        header = (UserHomePageHeader) mHeader;
        initAdapter(UserHomePageAdapter.class);
        mRecyclerView.setStateTextColor(R.color.white);
        mRecyclerView.setRefreshBackroundRes(R.color.colorAccent);
        mRecyclerView.setProgressBar();
        mRecyclerView.setLoadingMoreEnabled(false);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //滑动的距离
                mDistanceY += dy;
                //高度
                int toolbarHeight = UiUtils.dip2px(50);
                //当滑动的距离 <= toolbar高度的时候，改变Toolbar背景色的透明度，达到渐变的效果
                if (mDistanceY <= toolbarHeight) {
                    float scale = (float) mDistanceY / toolbarHeight;
                    float alpha = scale * 255;
                    mFlToolbar.setBackgroundColor(Color.argb((int) alpha, 0, 129, 204));
                } else {
                    //上述虽然判断了滑动距离与toolbar高度相等的情况，但是实际测试时发现，标题栏的背景色
                    //很少能达到完全不透明的情况，所以这里又判断了滑动距离大于toolbar高度的情况，
                    //将标题栏的颜色设置为完全不透明状态
                    mFlToolbar.setBackgroundResource(R.color.colorAccent);
                }
            }
        });
        ((UserHomePageAdapter) mAdapter).setOnFollowListener(new UserHomePageAdapter.OnFollowListener() {
            @Override
            public void setOnFollowListener(boolean isCheck, int position, int objId) {
                if (isCheck) {
                    mPresenter.unfollow(objId, position);
                } else {
                    mPresenter.follow(objId, position);
                }
            }
        });
        dialog = new QuestionSuccessDialog(this);
    }

    private void loginHuanXin() {
        if (!EMClient.getInstance().isLoggedInBefore()) {
            Toast.makeText(this, "正在连接...", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(this, "密码获取失败，请重新获取密码", Toast.LENGTH_SHORT).show();
                return;
            }
            EMClient.getInstance().login(info.uid, imPwd, new EMCallBack() {//回调
                @Override
                public void onSuccess() {
                    EMClient.getInstance().groupManager().loadAllGroups();
                    EMClient.getInstance().chatManager().loadAllConversations();
                    startChat();
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
            startChat();
        }
    }

    private void startChat() {
        if (userheader.isSelf == 1) {
            UiUtils.startActivity(ContactsListActivity.class);
        } else {
            User user = Global.getInstance().getUserInfo();
            ChatActivity.start(UserHomePageActivity.this, userheader.uid, userheader.memberAvatar, user.avatar, userheader.mobile, userheader.nick_name);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Global.getInstance().isLoggedIn()) {
            layoutVisible = true;
            if ("userHome".equals(askFrom) && dialog != null) {
                dialog.show();
                askFrom = "";
            }
            mPresenter.rquestHeaderData(id);
        } else {
            finish();
            MainActivity.start(this, 4);
        }
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        mPresenter.userHomePageData(page, id, type);
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        mPresenter.userHomePageData(page, id, type);
    }


    @Override
    public void setUserHomePageHeader(UserHomePageHead user) {
        if (user.isSelf == 1) {
            header.mTvAuthentication.setVisibility(View.GONE);
        }
        userheader = user;
        Glide.with(this).load(user.memberAvatar).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(header.mImgAvatar);
        header.mTvName.setText(user.nick_name);
        header.mIvSex.setVisibility(user.sex == 0 ? View.GONE : View.VISIBLE);
        header.mIvSex.setImageResource(user.sex == 1 ? R.mipmap.ic_sex_man : R.mipmap.ic_sex_girl);
//        header.mTvAutograph.setVisibility(TextUtils.isEmpty(user.signature) ? View.GONE : View.VISIBLE);
        header.mTvAutograph.setText(user.signature);
        header.mTvAddress.setVisibility(TextUtils.isEmpty(user.areaInfo) ? View.GONE : View.VISIBLE);
        header.mTvAddress.setText(user.areaInfo);

        header.mTvMutualConcern.setText(UiUtils.getString(R.string.mutual_concern, user.wonderTotal));
        header.mTvFans.setText(UiUtils.getString(R.string.fans, user.fansTotal));
        header.mTvShopNum.setText(user.wonderShop);
        header.mTvPriceQuotation.setText(user.wonderPrice);
        header.mTvAuthentication.setVisibility(TextUtils.isEmpty(user.memberTypeName) ? View.GONE : View.VISIBLE);
        header.mTvAuthentication.setText(user.memberTypeName);
        header.mTvCollection.setText(user.collectTotal);
        if (user.isSelf == 1) {
            mTvAdd.setText("我的关注");
        } else {
            if (user.isWonder == 0) {
                Drawable drawable = ContextCompat.getDrawable(this, R.mipmap.ic_add_back);
                drawable.setBounds(0, 0, UiUtils.dip2px(17), UiUtils.dip2px(17));
                mTvAdd.setCompoundDrawables(drawable, null, null, null);
                mTvAdd.setText("关注");
            } else {
                mTvAdd.setCompoundDrawables(null, null, null, null);
                mTvAdd.setText("取消关注");
            }
        }
        mTvChat.setText(user.isSelf == 1 ? "聊天列表" : "聊天");
        header.mTvEditData.setVisibility(user.isSelf == 1 ? View.VISIBLE : View.GONE);
        onRefresh();

    }


    @Override
    public void addWonderSuccess() {

    }


    @Override
    public void setListSize(int size) {

        if (size < 1) {
            switch (type) {
                case 1:
                    header.mRtvNoData.setText("去提问");
                    break;
                case 2:
                    header.mRtvNoData.setText("去评论/回复");
                    break;
                case 3:
                    header.mRtvNoData.setText("去发布求购");
                    break;
                case 4:
                    header.mRtvNoData.setText("去发布供销");
                    break;
            }
            if (type == 0 && userheader.isSelf == 1) {
                header.mLlNoDynamic.setVisibility(View.VISIBLE);
                header.mLlEmptyUser.setVisibility(View.GONE);
            } else if (type != 0 && userheader.isSelf == 1) {
                header.mLlNoDynamic.setVisibility(View.GONE);
                header.mLlEmptyUser.setVisibility(View.VISIBLE);
            } else {
                header.mLlNoDynamic.setVisibility(View.GONE);
                header.mLlEmptyUser.setVisibility(View.GONE);
            }
            header.mFlEmpty.setVisibility(View.VISIBLE);
            header.mLlEmptyTa.setVisibility(userheader.isSelf == 1 ? View.GONE : View.VISIBLE);
        } else {
            header.mFlEmpty.setVisibility(View.GONE);
            header.mFlEmpty.setVisibility(View.GONE);
            header.mLlEmptyUser.setVisibility(View.GONE);
            header.mLlEmptyTa.setVisibility(View.GONE);
        }
    }

    @Override
    public void followSuccess(int dataPosition, Map<String, String> data) {
        UserHomePageData userhome = ((UserHomePageAdapter) mAdapter).getItem(dataPosition);
        userhome.isWonder = "1";
        userhome.knowTotal = data.get("member_names_total");
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void unFollowSuccess(int dataPosition, Map<String, String> data) {
        UserHomePageData userhome = ((UserHomePageAdapter) mAdapter).getItem(dataPosition);
        userhome.isWonder = "0";
        userhome.knowTotal = data.get("member_names_total");
        mAdapter.notifyDataSetChanged();
    }


    private void showPopup() {
        View popupView = getLayoutInflater().inflate(R.layout.popup_user_home_page_menu, null);
        PopupWindow window = new PopupWindow(popupView, UiUtils.dip2px(100), UiUtils.dip2px(200));
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
        window.setFocusable(true);
        window.setOutsideTouchable(true);
        window.update();
        window.showAsDropDown(mFlAllDynamic, 30, -20);
        popupView.findViewById(R.id.tv_all).setOnClickListener(v -> {
            type = 0;
            mTvDynamic.setText("全部动态");
            window.dismiss();
            onRefresh();
        });
        popupView.findViewById(R.id.tv_release_problem).setOnClickListener(v -> {
            type = 1;
            mTvDynamic.setText("发布问题");
            window.dismiss();
            onRefresh();
        });
        popupView.findViewById(R.id.tv_comment).setOnClickListener(v -> {
            type = 2;
            mTvDynamic.setText("评论/回复");
            window.dismiss();
            onRefresh();
        });
        popupView.findViewById(R.id.tv_want_buy).setOnClickListener(v -> {
            type = 3;
            mTvDynamic.setText("求购");
            window.dismiss();
            onRefresh();
        });
        popupView.findViewById(R.id.tv_supply).setOnClickListener(v -> {
            type = 4;
            mTvDynamic.setText("供销");
            window.dismiss();
            onRefresh();
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (dialog.dialogIsShow) {
            dialog.onActivityResult(requestCode, resultCode, data);
            dialog.dialogIsShow = false;
        } else {
            shareDialog.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void requestPermission() {
        PermissionUtil.recordAudio(new PermissionUtil.RequestPermissionListener() {
            @Override
            public void success() {
                loginHuanXin();
            }

            @Override
            public void failure() {
                Toast.makeText(UserHomePageActivity.this, "请求权限失败,请前往设置开启权限！", Toast.LENGTH_SHORT).show();
            }
        }, new RxPermissions(this));
    }

    @OnClick({R.id.iv_back, R.id.fl_chat, R.id.fl_add, R.id.fl_all_dynamic, R.id.iv_share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_share:
                if (shareDialog == null) {
                    shareDialog = new ShareDialog(this);
                }
                shareDialog.setShareInfo(userheader.share);
                shareDialog.show();
                break;
            case R.id.fl_all_dynamic:
                showPopup();
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.fl_chat:
                if (userheader==null){
                    return;
                }
                requestPermission();
                break;
            case R.id.fl_add:
                if (userheader==null){
                    return;
                }
                if (userheader.isSelf == 1) {
                    FansActivity.start(this, id, 1);
                } else {
                    mPresenter.addWonder(id);
                    if (userheader.isWonder == 0) {
                        userheader.isWonder = 1;
                        mTvAdd.setText("取消关注");
                        mTvAdd.setCompoundDrawables(null, null, null, null);
                    } else {
                        userheader.isWonder = 0;
                        Drawable drawable = ContextCompat.getDrawable(this, R.mipmap.ic_add_back);
                        drawable.setBounds(0, 0, UiUtils.dip2px(17), UiUtils.dip2px(17));
                        mTvAdd.setCompoundDrawables(drawable, null, null, null);
                        mTvAdd.setText("关注");
                    }
                }
                break;
        }
    }

    class UserHomePageHeader extends BaseHeaderView {
        @BindView(R.id.img_avatar)
        CircleImageView mImgAvatar;
        @BindView(R.id.fl_empty)
        FrameLayout mFlEmpty;
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.iv_sex)
        ImageView mIvSex;
        @BindView(R.id.tv_address)
        TextView mTvAddress;
        @BindView(R.id.tv_mutual_concern)
        TextView mTvMutualConcern;
        @BindView(R.id.tv_autograph)
        TextView mTvAutograph;
        @BindView(R.id.tv_shop_num)
        TextView mTvShopNum;
        @BindView(R.id.tv_price_quotation)
        TextView mTvPriceQuotation;
        @BindView(R.id.tv_collection)
        TextView mTvCollection;
        @BindView(R.id.ll_empty_user)
        LinearLayout mLlEmptyUser;
        @BindView(R.id.ll_empty_ta)
        LinearLayout mLlEmptyTa;
        @BindView(R.id.tv_edit_data)
        RoundTextView mTvEditData;
        @BindView(R.id.tv_authentication)
        TextView mTvAuthentication;
        @BindView(R.id.tv_fans)
        TextView mTvFans;
        @BindView(R.id.fl_shop_num)
        FrameLayout mFlShopNum;
        @BindView(R.id.rtv_no_data)
        RoundTextView mRtvNoData;
        @BindView(R.id.fl_price_quotation)
        FrameLayout mFlPriceQuotation;
        @BindView(R.id.tv_prompt)
        TextView mTvPrompt;
        @BindView(R.id.ll_no_dynamic)
        LinearLayout mLlNoDynamic;
        private Context mContext;


        public UserHomePageHeader(Context context) {
            super(context);
            mContext = context;
        }

        @Override
        protected int getLayoutHeaderViewId() {
            return R.layout.header_user_home_page;
        }

        @OnClick({R.id.tv_edit_data, R.id.fl_shop_num, R.id.fl_price_quotation, R.id.fl_collection, R.id.tv_mutual_concern, R.id.tv_fans, R.id.tv_authentication, R.id.ll_empty_user})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_edit_data:
                    UiUtils.startActivity(UserInfoActivity.class);
                    break;
                case R.id.fl_shop_num:
                    FollowFarmShopActivity.start(mContext, id + "");
                    break;
                case R.id.fl_price_quotation:
                    MyAttentionPriceActivity.start(view.getContext(), 1, id);
                    break;
                case R.id.fl_collection:
                    if (userheader==null){
                        return;
                    }
                    MyCollectionActivity.start(mContext, id,userheader.isSelf);
                    break;
                case R.id.tv_mutual_concern:
                    FansActivity.start(mContext, id, 1);
                    break;
                case R.id.tv_fans:
                    FansActivity.start(mContext, id, 2);
                    break;
                case R.id.tv_authentication:
                    if (userheader.isSelf == 1) {
                        User user = Global.getInstance().getUserInfo();
                        ApplyCertificationActivity.start(mContext, Integer.parseInt(user.memberType));
                    }
                    break;
                case R.id.ll_empty_user:
                    if (userheader.isSelf == 1) {
                        switch (type) {
                            case 0:
                                break;
                            case 1:
                                AskActivity.start(mContext, "userHome");
                                break;
                            case 2:
                                MainActivity.start(view.getContext(), 1);
                                break;
                            case 3:
                                ReleaseBuyingActivity.start(mContext, "发布求购", -1);
                                break;
                            case 4:
                                ReleaseSupplyingActivity.start(mContext, "发布供销", -1);
                                break;
                        }
                    }
                    break;
            }
        }
    }


    @Subscribe
    public void releaseQuestion(ReleaseQuestionEvent event) {
        QuestionId = event.askSuccess.askId;
        askFrom = event.askFrom;
        dialog.setData(event.askSuccess);
        dialog.dialogIsShow = true;
    }

    @Override
    protected boolean enableEventBus() {
        return true;
    }

    @Subscribe
    public void shareSuccessEvent(ShareEvent event) {
        if (dialog.dialogIsShow) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (layoutVisible) {
                        mPresenter.share("ask", QuestionId);
                    }
                }
            }, 500);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        layoutVisible = false;
    }


}

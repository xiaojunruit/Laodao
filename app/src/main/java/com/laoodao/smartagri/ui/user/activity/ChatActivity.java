package com.laoodao.smartagri.ui.user.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.bean.ChatAvatar;
import com.laoodao.smartagri.bean.ChatGoodsInit;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerUserComponent;
import com.laoodao.smartagri.ui.user.contract.ChatContract;
import com.laoodao.smartagri.ui.user.fragment.ChatFragment;
import com.laoodao.smartagri.ui.user.presenter.ChatPresenter;
import com.laoodao.smartagri.utils.DeviceUtils;
import com.laoodao.smartagri.utils.UiUtils;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.File;
import java.nio.channels.NonReadableChannelException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by WORK on 2017/8/16.
 */

public class ChatActivity extends BaseActivity<ChatPresenter> implements ChatContract.ChatView {

    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.iv_close)
    ImageView mIvClose;
    @BindView(R.id.ll_goods)
    LinearLayout mLlGoods;
    @BindView(R.id.iv_goods)
    RoundedImageView mIvGoods;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.tv_top_title)
    TextView mTvTopTitle;
    @BindView(R.id.container)
    FrameLayout mContainer;
    private EaseChatFragment chatFragment;
    private String toChatUsername;
    private String avatar = "";
    private String userAvatar = "";
    private String mType = "";
    private ChatGoodsInit mData;
    private String mPhone;
    private String nameText = "";

    public static void start(Context context, String userId) {
        Bundle bundle = new Bundle();
        bundle.putString("userId", userId);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        intent.setClass(context, ChatActivity.class);
        context.startActivity(intent);
    }

    public static void start(Context context, String userId, String avatar, String userAvatar, String phone, String nameText) {
        start(context, userId, avatar, userAvatar, "", null, phone, nameText);
    }

    public static void start(Context context, String userId, String avatar, String userAvatar, String type, ChatGoodsInit data, String phone, String nameText) {
        Bundle bundle = new Bundle();
        bundle.putString("userId", userId);
        bundle.putString("avatar", avatar);
        bundle.putString("userAvatar", userAvatar);
        bundle.putString("type", type);
        bundle.putSerializable("ChatGoodsInit", data);
        bundle.putString("phone", phone);
        bundle.putString("nameText", nameText);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        intent.setClass(context, ChatActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void complete() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_chat;
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
        toChatUsername = getIntent().getExtras().getString("userId");
        avatar = getIntent().getStringExtra("avatar");
        userAvatar = getIntent().getStringExtra("userAvatar");
        mType = getIntent().getStringExtra("type");
        mData = (ChatGoodsInit) getIntent().getSerializableExtra("ChatGoodsInit");
        mPhone = getIntent().getStringExtra("phone");
        nameText = getIntent().getStringExtra("nameText");
        chatFragment = new ChatFragment();
        mTvName.setText(mData == null ? nameText : mData.contactor);
        mLlGoods.setVisibility(TextUtils.isEmpty(mType) ? View.GONE : View.VISIBLE);
        if (mData != null) {
            mIvGoods.setVisibility(TextUtils.isEmpty(mData.image) ? View.GONE : View.VISIBLE);
            Glide.with(this).load(mData.image).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(mIvGoods);
            mTvTopTitle.setText(mData.toptitle);
            mTvTitle.setText(mData.content);
            mTvPhone.setText(UiUtils.getString(R.string.contacts, mData.contactor));
            mTvPrice.setText(UiUtils.getString(R.string.price_no_symbol, mData.price));
        }
        if (TextUtils.isEmpty(avatar)) {
            mPresenter.getAvatar(toChatUsername);
            return;
        }
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();

    }

    @Override
    public void setAvatar(List<ChatAvatar> list) {
        if (list.size() >= 1) {
            getIntent().putExtra("avatar", list.get(0).avatar);
            chatFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // make sure only one chat activity is opened
        String username = intent.getStringExtra("userId");
        if (toChatUsername.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
        }
    }


    public String getToChatUsername() {
        return toChatUsername;
    }

    public void requestSendText(String content) {
        mPresenter.setSendContent("", 1, toChatUsername, content, new File(""), new File(""));
    }

    public void requestSendVoice(File content) {
        mPresenter.setSendContent("", 3, toChatUsername, "", new File(""), content);
    }

    public void requestSendImage(File content) {
        mPresenter.setSendContent("", 2, toChatUsername, "", content, new File(""));
    }

    @OnClick({R.id.iv_close, R.id.iv_back, R.id.ll_call_phone})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_call_phone:
                if (TextUtils.isEmpty(mPhone)) {
                    return;
                }
                DeviceUtils.openDial(getApplication().getApplicationContext(), mPhone);
                break;
            case R.id.iv_close:
                mLlGoods.setVisibility(View.GONE);
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }


}

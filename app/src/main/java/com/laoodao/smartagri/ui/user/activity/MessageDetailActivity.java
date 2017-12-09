package com.laoodao.smartagri.ui.user.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.bean.MsgDetail;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerUserComponent;
import com.laoodao.smartagri.ui.user.contract.MessageDetailContract;
import com.laoodao.smartagri.ui.user.presenter.MessageDetailPresenter;
import com.laoodao.smartagri.utils.UiUtils;

import butterknife.BindView;

/**
 * Created by WORK on 2017/6/22.
 */

public class MessageDetailActivity extends BaseActivity<MessageDetailPresenter> implements MessageDetailContract.MessageDetailView {

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.iv_img)
    ImageView mIvImg;
    private int id;
    private String objType;

    public static void start(Context context, int id, String objType) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putString("objType", objType);
        UiUtils.startActivity(context, MessageDetailActivity.class, bundle);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_detail;
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
        id = getIntent().getIntExtra("id", 0);
        objType = getIntent().getStringExtra("objType");
        mPresenter.getMsgDetail(id, objType);
    }

    @Override
    public void complete() {

    }

    @Override
    public void setMsgDetail(MsgDetail msgDetail) {
        mTvTitle.setText(msgDetail.title);
        mTvTime.setText(msgDetail.addTime);
        if (!TextUtils.isEmpty(msgDetail.img)) {
            mIvImg.setVisibility(View.VISIBLE);
            Glide.with(this).load(msgDetail.img).diskCacheStrategy(DiskCacheStrategy.RESULT).into(mIvImg);
        }
        mTvContent.setText(msgDetail.content);
        // RichText.from(msgDetail.content).into(mTvContent);
    }
}

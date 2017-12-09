package com.laoodao.smartagri.ui.qa.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.flyco.roundview.RoundTextView;
import com.laoodao.smartagri.Global;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.Answer;
import com.laoodao.smartagri.event.CommentEvent;
import com.laoodao.smartagri.ui.qa.dialog.MenuPopup;
import com.laoodao.smartagri.ui.user.activity.LoginActivity;
import com.laoodao.smartagri.ui.user.activity.UserHomePageActivity;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.comment.CommentAdapter;
import com.laoodao.smartagri.view.comment.CommentListView;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by WORK on 2017/4/17.
 */

public class QuestionDetailAdapter extends BaseAdapter<Answer> {

    private View.OnClickListener mOnClickListener;
    private int mCid;

    public QuestionDetailAdapter(Context context, View.OnClickListener onClickListener) {
        super(context);
        this.mOnClickListener = onClickListener;
        mCid = 0;
        if (Global.getInstance().isLoggedIn()) {
            mCid = Integer.parseInt(Global.getInstance().getUserInfo().cid);
        }
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new QuestionDetailHolder(parent);
    }

    class QuestionDetailHolder extends BaseViewHolder<Answer> implements View.OnClickListener {

        @BindView(R.id.civ_avatar)
        CircleImageView mCivAvatar;
        @BindView(R.id.tv_answer_time)
        TextView mTvAnswerTime;
        @BindView(R.id.tv_address)
        TextView mTvAddress;
        @BindView(R.id.tv_content)
        TextView mTvContent;
        @BindView(R.id.tv_names)
        TextView mTvNames;
        @BindView(R.id.tv_names_total)
        TextView mTvNamesTotal;
        @BindView(R.id.tv_comment_count)
        TextView mTvCommentCount;
        @BindView(R.id.commentView)
        CommentListView mCommentView;
        @BindView(R.id.iv_more)
        ImageView mIvMore;
        CommentAdapter mAdapter;

        MenuPopup mMenuPopup;
        @BindView(R.id.ll_praise)
        LinearLayout mLlPraise;
        @BindView(R.id.tv_nickname)
        TextView mTvNickname;
        @BindView(R.id.rtv_member_type_name)
        RoundTextView mRtvMemberTypeName;
        @BindView(R.id.rtv_follow)
        RoundTextView mRtvFollow;
        @BindView(R.id.fl_follow)
        FrameLayout mFlFollow;


        public QuestionDetailHolder(ViewGroup parent) {
            super(parent, R.layout.item_qa_detail);
            mAdapter = new CommentAdapter(getContext());
        }

        @Override
        public void setData(Answer data) {
            super.setData(data);
            mLlPraise.setVisibility(data.praiseNum <= 0 ? View.GONE : View.VISIBLE);
            mTvNames.setText(data.praiseName);
            mTvNamesTotal.setText(UiUtils.getString(R.string.praised_count, data.praiseNum));
            mTvAnswerTime.setText(data.time);
            mTvContent.setText(data.content);
            mRtvMemberTypeName.setText(data.memberTypeName);
            mRtvMemberTypeName.setVisibility(TextUtils.isEmpty(data.memberTypeName) ? View.GONE : View.VISIBLE);
            if (mCid == data.cid) {
                mFlFollow.setVisibility(View.GONE);
            } else {
                mFlFollow.setVisibility(View.VISIBLE);
                if (data.isUserWonder == 0) {
                    Drawable followDrawable = ContextCompat.getDrawable(getContext(), R.mipmap.ic_add_blue);
                    mRtvFollow.getDelegate().setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                    mRtvFollow.getDelegate().setStrokeColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
                    mRtvFollow.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
                    followDrawable.setBounds(0, 0, UiUtils.dip2px(10), UiUtils.dip2px(10));
                    mRtvFollow.setCompoundDrawables(followDrawable, null, null, null);
                    mRtvFollow.setText("关注");
                } else {
                    mRtvFollow.getDelegate().setBackgroundColor(ContextCompat.getColor(getContext(), R.color.common_h5));
                    mRtvFollow.getDelegate().setStrokeColor(ContextCompat.getColor(getContext(), R.color.white));
                    mRtvFollow.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                    mRtvFollow.setCompoundDrawables(null, null, null, null);
                    mRtvFollow.setText("已关注");
                }
            }
            if (!TextUtils.isEmpty(data.address)) {
                mTvAddress.setText(data.address);
                mTvAddress.setVisibility(View.VISIBLE);
            } else {
                mTvAddress.setVisibility(View.GONE);
            }
//            mTvCommentCount.setText(UiUtils.getString(R.string.comment_count, String.valueOf(data.commentCount)));
            mCommentView.setAdapter(mAdapter);
            mAdapter.setDatas(data.commentList);
            mTvNickname.setText(data.nickname);
            mAdapter.notifyDataSetChanged();
            mAdapter.setOnItemClickListener((position, comment) -> {
                CommentEvent event = new CommentEvent();
                event.id = comment.id;
                event.position = getCurrentPosition();
                EventBus.getDefault().post(event);
            });

            Glide.with(getContext().getApplicationContext())
                    .load(data.avator)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .centerCrop()
                    .dontAnimate()
                    .into(mCivAvatar);

        }

        @OnClick({R.id.iv_more, R.id.fl_follow, R.id.civ_avatar})
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_more:
                    if (mMenuPopup == null) {
                        mMenuPopup = new MenuPopup(getContext(), this);
                        mMenuPopup.setRightText(UiUtils.getString(R.string.comment_txt));
                        mMenuPopup.offset(-45, 35);
                        mMenuPopup.setCenterVisible(false);
                    }
                    Answer item = getItem(getCurrentPosition());
                    mMenuPopup.setLeftText(item.isPraise ? UiUtils.getString(R.string.praised) : UiUtils.getString(R.string.praise));
                    mMenuPopup.anchorView(mIvMore);
                    mMenuPopup.show();
                    break;
                case R.id.tv_left:
                case R.id.tv_right:
                case R.id.fl_follow:
                    v.setTag(getCurrentPosition());
                    if (mOnClickListener != null)
                        mOnClickListener.onClick(v);
                    break;
                case R.id.civ_avatar:
                    if (mCid == 0) {
                        return;
                    }
                    if (!Global.getInstance().isLoggedIn()) {
                        UiUtils.startActivity(LoginActivity.class);
                        return;
                    }
                    UserHomePageActivity.start(getContext(), getItem(getCurrentPosition()).cid);
                    break;
            }

        }


    }


}

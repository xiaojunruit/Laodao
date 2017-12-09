package com.laoodao.smartagri.ui.discovery.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.CottonfieldComment;
import com.laoodao.smartagri.view.comment.CommentListView;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/8/2 0002.
 */

public class PriceDetailAdapter extends BaseAdapter<CottonfieldComment> {
    public PriceDetailAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new PriceDetailHolder(parent);
    }

    class PriceDetailHolder extends BaseViewHolder<CottonfieldComment> {
        @BindView(R.id.civ_avatar)
        CircleImageView mCivAvatar;
        @BindView(R.id.tv_nickname)
        TextView mTvNickname;
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
        @BindView(R.id.ll_praise)
        LinearLayout mLlPraise;
        @BindView(R.id.tv_comment_count)
        TextView mTvCommentCount;
        @BindView(R.id.iv_more)
        ImageView mIvMore;
        @BindView(R.id.fl_comment)
        FrameLayout mFlComment;
        @BindView(R.id.commentView)
        CommentListView mCommentView;

        public PriceDetailHolder(ViewGroup parent) {
            super(parent, R.layout.item_qa_detail);
        }

        @Override
        public void setData(CottonfieldComment data) {
            super.setData(data);
            mFlComment.setVisibility(View.GONE);
            mCommentView.setVisibility(View.GONE);

            mTvNickname.setText(data.nickName);
            mTvAnswerTime.setText(data.addTime);
            mTvAddress.setText(data.areaName);
            mTvContent.setText(data.content);
            Glide.with(getContext()).load(data.avatar).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(mCivAvatar);
        }
    }
}

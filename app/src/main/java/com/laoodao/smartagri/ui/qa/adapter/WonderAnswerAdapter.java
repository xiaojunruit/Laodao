package com.laoodao.smartagri.ui.qa.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.WonderAnswer;
import com.laoodao.smartagri.ui.qa.activity.QuestionDetailActivity;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;

/**
 * Created by 欧源 on 2017/5/2.
 */

public class WonderAnswerAdapter extends BaseAdapter<WonderAnswer> {
    public WonderAnswerAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new WonderAnswerHolder(parent);
    }


    class WonderAnswerHolder extends BaseViewHolder<WonderAnswer> {

        @BindView(R.id.tv_time)
        TextView mTvTime;
        @BindView(R.id.iv_image)
        RoundedImageView mIvImage;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_content)
        TextView mTvContent;
        @BindView(R.id.tv_answer)
        TextView mTvAnswer;
        @BindView(R.id.tv_comment)
        TextView mTvComment;
        @BindView(R.id.tv_wonder_answer)
        TextView mTvWonderAnswer;
        @BindView(R.id.tv_browse)
        TextView mTvBrowse;

        public WonderAnswerHolder(ViewGroup itemView) {
            super(itemView, R.layout.item_my_wonder_answer);
        }

        @Override
        public void setData(int position, WonderAnswer data) {
            mTvTitle.setText(data.title);
            mTvContent.setText(data.content);
            mTvComment.setText(UiUtils.getString(R.string.comment_total, data.commentTotal));
            mTvWonderAnswer.setText(UiUtils.getString(R.string.wonder_answer_num, data.knowTotal));
            mTvBrowse.setText(UiUtils.getString(R.string.browse, data.browse));
            mTvAnswer.setText(UiUtils.getString(R.string.answer_count, data.answerCount));
            mTvTime.setText(UiUtils.getString(R.string.add_at_time, data.time));
            UiUtils.loadImage(mIvImage, data.thumbUrl);
            itemView.setOnClickListener(v -> {
                QuestionDetailActivity.start(getContext(), getItem(position).id);
            });
        }
    }
}

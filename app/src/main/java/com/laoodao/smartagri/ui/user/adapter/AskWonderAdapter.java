package com.laoodao.smartagri.ui.user.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.AskWonderList;
import com.laoodao.smartagri.ui.qa.activity.QuestionDetailActivity;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/8/19 0019.
 */

public class AskWonderAdapter extends BaseAdapter<AskWonderList> {
    private OnFollowClickListener mOnFollowClickListener;

    public AskWonderAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new AskWonderHolder(parent);
    }

    public void setOnFollowClickListener(OnFollowClickListener listener) {
        mOnFollowClickListener = listener;
    }

    class AskWonderHolder extends BaseViewHolder<AskWonderList> {


        @BindView(R.id.iv_image)
        RoundedImageView mIvImage;
        @BindView(R.id.tv_content)
        TextView mTvContent;
        @BindView(R.id.tv_answer)
        TextView mTvAnswer;

        @BindView(R.id.tv_browse)
        TextView mTvBrowse;
        @BindView(R.id.cb_follow)
        TextView mCbFollow;
        private int mPosition;
        private int mid;
        private Drawable drawable;

        public AskWonderHolder(ViewGroup parent) {
            super(parent, R.layout.item_ask_wonder);
            drawable = ContextCompat.getDrawable(getContext(), R.mipmap.ic_preview);
            drawable.setBounds(0, 0, UiUtils.dip2px(16), UiUtils.dip2px(16));
        }

        @OnClick(R.id.cb_follow)
        public void onOnClick() {
            mOnFollowClickListener.setOnFollowClick(mCbFollow.isSelected(), mPosition, mid);
        }

        @Override
        public void setData(int position, AskWonderList data) {
            super.setData(position, data);
            mPosition = position;
            mTvBrowse.setCompoundDrawables(drawable, null, null, null);
            mid = data.id;
            if (TextUtils.isEmpty(data.thumbImg)) {
                mIvImage.setVisibility(View.GONE);
            } else {
                mIvImage.setVisibility(View.VISIBLE);
                Glide.with(getContext()).load(data.thumbImg).diskCacheStrategy(DiskCacheStrategy.SOURCE).error(R.mipmap.bg_seize_seat).placeholder(R.mipmap.bg_seize_seat).into(mIvImage);
            }
            mTvContent.setText(data.content);
            mTvAnswer.setText(UiUtils.getString(R.string.answer_count, data.answerTotal));
            mTvBrowse.setText(UiUtils.getString(R.string.browse_count, data.viewTotal));
            mCbFollow.setText(UiUtils.getString(R.string.follow, data.knowTotal));
            mCbFollow.setSelected(data.isWonder == 1);
            itemView.setOnClickListener(v -> {
                QuestionDetailActivity.start(getContext(), data.id);
            });
        }

    }

    public interface OnFollowClickListener {
        void setOnFollowClick(boolean isCheck, int position, int id);
    }
}

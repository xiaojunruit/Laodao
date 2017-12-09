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
import com.laoodao.smartagri.bean.UserHomePageData;
import com.laoodao.smartagri.ui.market.activity.BuyDetailsActivity;
import com.laoodao.smartagri.ui.market.activity.SupplyDetailsActivity;
import com.laoodao.smartagri.ui.qa.activity.QuestionDetailActivity;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by WORK on 2017/8/14.
 */

public class UserHomePageAdapter extends BaseAdapter<UserHomePageData> {
    public OnFollowListener mOnFollowListener;
    private Drawable drawable;

    public UserHomePageAdapter(Context context) {
        super(context);
        drawable = ContextCompat.getDrawable(getContext(), R.mipmap.ic_preview);
        drawable.setBounds(0, 0, UiUtils.dip2px(16), UiUtils.dip2px(16));
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new UserHomePageHolder(parent);
    }

    public void setOnFollowListener(OnFollowListener onFollowListener) {
        this.mOnFollowListener = onFollowListener;
    }

    public interface OnFollowListener {
        void setOnFollowListener(boolean isCheck, int position, int objId);
    }

    class UserHomePageHolder extends BaseViewHolder<UserHomePageData> {

        @BindView(R.id.tv_name_reply)
        TextView mTvNameReply;
        @BindView(R.id.iv_image)
        RoundedImageView mIvImage;
        @BindView(R.id.tv_content)
        TextView mTvContent;
        @BindView(R.id.tv_answer)
        TextView mTvAnswer;
        @BindView(R.id.tv_browse)
        TextView mTvBrowse;
        @BindView(R.id.tv_follow)
        TextView mTvFollow;
        @BindView(R.id.tv_reply_time)
        TextView mTvReplyTime;
        @BindView(R.id.tv_reply_count)
        TextView mTvReplyCount;

        private int mPosition;
        UserHomePageData mData;

        public UserHomePageHolder(ViewGroup parent) {
            super(parent, R.layout.item_user_home_page);
        }


        @OnClick(R.id.tv_follow)
        public void onClick() {
            mOnFollowListener.setOnFollowListener(mTvFollow.isSelected(), mPosition, mData.objId);
//            if ("1".equals(mData.isWonder)){
//                mData.isWonder="0";
//            }else{
//                mData.isWonder="1";
//            }
        }

        @Override
        public void setData(int position, UserHomePageData data) {
            super.setData(position, data);
            mData = data;
            mTvReplyCount.setText(data.content);
            mTvReplyCount.setVisibility(TextUtils.isEmpty(data.content) ? View.GONE : View.VISIBLE);
            mTvReplyTime.setText(data.addTime);
            mTvBrowse.setCompoundDrawables(drawable, null, null, null);
            mTvNameReply.setVisibility(TextUtils.isEmpty(data.desc) ? View.GONE : View.VISIBLE);
            //mTvNameReply.setText(Html.fromHtml(UiUtils.getString(R.string.reply_problem, data.dealName)));
            mTvNameReply.setText(data.desc);
            mIvImage.setVisibility(TextUtils.isEmpty(data.img) ? View.GONE : View.VISIBLE);
            Glide.with(getContext()).load(data.img).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(mIvImage);
            mTvContent.setText(data.sourceContent);
            mPosition = position;
            mTvAnswer.setText(UiUtils.getString(R.string.answer_count, data.answerTotal));
            mTvBrowse.setText(UiUtils.getString(R.string.browse_count, data.viewTotal));
            mTvFollow.setSelected("1".equals(data.isWonder));
            mTvFollow.setText(UiUtils.getString(R.string.follow, data.knowTotal));
            if (data.objType == 4 || data.objType == 5) {
                mTvAnswer.setVisibility(View.GONE);
            } else {
                mTvAnswer.setVisibility(View.VISIBLE);
            }
            itemView.setOnClickListener(v -> {
                switch (data.objType) {
                    case 1:
                        QuestionDetailActivity.start(getContext(), data.objId);
                        break;
                    case 2:
                        QuestionDetailActivity.start(getContext(), data.objId);
                        break;
                    case 3:
                        QuestionDetailActivity.start(getContext(), data.objId);
                        break;
                    case 4:
                        BuyDetailsActivity.start(getContext(), "我的求购详情", data.objId);
                        break;
                    case 5:
                        SupplyDetailsActivity.start(getContext(), "我的供销详情", data.objId);
                        break;

                }
            });
        }
    }


}

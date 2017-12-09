package com.laoodao.smartagri.ui.user.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.flyco.roundview.RoundFrameLayout;
import com.flyco.roundview.RoundTextView;
import com.laoodao.smartagri.Global;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.User;
import com.laoodao.smartagri.bean.WonderUser;
import com.laoodao.smartagri.ui.user.activity.UserHomePageActivity;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/8/14 0014.
 */

public class FansAdapter extends BaseAdapter<WonderUser> {
    public interface onClickListener {
        void setOnClickListener(int positoin, int isWonder);
    }

    public onClickListener mOnClickListener;

    public FansAdapter(Context context, onClickListener onClickListener) {
        super(context);
        mOnClickListener = onClickListener;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new FansHolder(parent);
    }

    class FansHolder extends BaseViewHolder<WonderUser> {

        @BindView(R.id.tv_nick_name)
        TextView mTvNickName;
        @BindView(R.id.rtv_type)
        RoundTextView mRtvType;
        @BindView(R.id.tv_fans_total)
        TextView mTvFansTotal;
        @BindView(R.id.tv_wonder_total)
        TextView mTvWonderTotal;
        @BindView(R.id.tv_signature)
        TextView mTvSignature;
        @BindView(R.id.tv_follow)
        TextView mTvFollow;
        @BindView(R.id.rfl_follow)
        RoundFrameLayout mRflFollow;
        @BindView(R.id.riv_img)
        CircleImageView mRivImg;

        public FansHolder(ViewGroup parent) {
            super(parent, R.layout.item_follow);
        }

        @Override
        public void setData(WonderUser data) {
            super.setData(data);
            itemView.setOnClickListener(v -> {
                if (Global.getInstance().isLoggedIn() && Integer.parseInt(Global.getInstance().getUserInfo().cid) != data.memberId) {
                    UserHomePageActivity.start(getContext(), data.memberId);
                }

            });
            if (Global.getInstance().isLoggedIn()) {
                User user = Global.getInstance().getUserInfo();
                mRflFollow.setVisibility(Integer.parseInt(user.cid) == data.memberId ? View.GONE : View.VISIBLE);
            }
            Glide.with(getContext()).load(data.avatar).dontAnimate().diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(R.mipmap.bg_seize_seat).error(R.mipmap.bg_seize_seat).into(mRivImg);
            mTvNickName.setText(data.membeNick);
            switch (data.memberType) {
                case 0:
                    mRtvType.setText("未认证");
                    break;
                case 1:
                    mRtvType.setText("农民");
                    break;
                case 2:
                    mRtvType.setText("商家");
                    break;
                case 3:
                    mRtvType.setText("专家");
                    break;
                default:
                    mRtvType.setVisibility(View.GONE);
                    break;
            }
            mTvFansTotal.setText("粉丝" + data.fansTotal);
            mTvWonderTotal.setText("关注" + data.wonderTotal);
            mTvSignature.setText(data.signature);

            if (data.isTogether == 0 && data.isWonder == 1) {
                mRflFollow.getDelegate().setBackgroundColor(ContextCompat.getColor(getContext(), R.color.common_h5));
                mTvFollow.setCompoundDrawables(null, null, null, null);
                mTvFollow.setText("已关注");
            } else if (data.isTogether == 1 && data.isWonder == 1) {
                mRflFollow.getDelegate().setBackgroundColor(ContextCompat.getColor(getContext(), R.color.common_h5));
                mTvFollow.setCompoundDrawables(null, null, null, null);
                mTvFollow.setText("互相关注");
            } else {
                Drawable drawable = ContextCompat.getDrawable(getContext(), R.mipmap.ic_add);
                drawable.setBounds(0, 0, UiUtils.dip2px(10), UiUtils.dip2px(10));
                mRflFollow.getDelegate().setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
                mTvFollow.setCompoundDrawables(drawable, null, null, null);
                mTvFollow.setText("关注");
            }
        }

        @OnClick(R.id.rfl_follow)
        public void onClick() {
            mOnClickListener.setOnClickListener(getCurrentPosition(), getItem(getCurrentPosition()).isWonder);
        }
    }
}

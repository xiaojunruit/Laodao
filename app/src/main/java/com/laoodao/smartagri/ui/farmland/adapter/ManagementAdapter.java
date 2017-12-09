package com.laoodao.smartagri.ui.farmland.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.AccountList;
import com.laoodao.smartagri.ui.farmland.activity.AccountDetailActivity;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;
import com.laoodao.smartagri.view.recyclerview.adapter.RecyclerArrayAdapter;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/4/25.
 */

public class ManagementAdapter extends BaseAdapter<AccountList> {
    public ManagementAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(parent, R.layout.item_farmland_management);
    }

    class MyHolder extends BaseViewHolder<AccountList> {

        @BindView(R.id.tv_type)
        TextView mTvType;
        @BindView(R.id.tv_money)
        TextView mTvMoney;
        @BindView(R.id.tv_date)
        TextView mTvDate;
        @BindView(R.id.iv_type)
        ImageView mIvType;

        public MyHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);
        }

        @Override
        public void setData(AccountList data) {
            super.setData(data);
            mTvType.setText(data.operateName);
            mTvMoney.setText(data.tag + data.account);
            mTvDate.setText(data.date);
            Glide.with(getContext())
                    .load(data.operateIcon)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .dontAnimate()
                    .centerCrop()
                    .into(mIvType);
            itemView.setOnClickListener(v -> {
                AccountDetailActivity.start(getContext(), data.id,getCurrentPosition());
            });

        }
    }
}

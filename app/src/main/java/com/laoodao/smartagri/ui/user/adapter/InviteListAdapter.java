package com.laoodao.smartagri.ui.user.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.flyco.roundview.RoundTextView;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.IntegralDetail;
import com.laoodao.smartagri.bean.InviteList;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;
import com.laoodao.smartagri.view.recyclerview.adapter.RecyclerArrayAdapter;

import butterknife.BindView;

/**
 * Created by Long-PC on 2017/4/14.
 */

public class InviteListAdapter extends BaseAdapter<InviteList> {
    public InviteListAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new InviteListHolder(parent, R.layout.item_invite_list);
    }

    class InviteListHolder extends BaseViewHolder<InviteList> {
        @BindView(R.id.tv_username)
        RoundTextView mTvUsername;
        @BindView(R.id.tv_time)
        RoundTextView mTvTime;

        public InviteListHolder(ViewGroup parent, @LayoutRes int viewType) {
            super(parent, viewType);
        }

        @Override
        public void setData(InviteList data) {
            mTvUsername.setText(data.name);
            mTvTime.setText(data.addTime);
            mTvTime.getDelegate().setBackgroundColor(getCurrentPosition() % 2 == 0 ? 0xfff7f7f7 : 0xffffffff);
            mTvUsername.getDelegate().setBackgroundColor(getCurrentPosition() % 2 == 0 ? 0xfff7f7f7 : 0xffffffff);
        }
    }

}

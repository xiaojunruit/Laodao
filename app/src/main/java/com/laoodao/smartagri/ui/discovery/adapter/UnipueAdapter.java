package com.laoodao.smartagri.ui.discovery.adapter;

import android.content.Context;
import android.text.Html;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.Unipue;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by WORK on 2017/5/18.
 */

public class UnipueAdapter extends BaseAdapter<Unipue> {

    public UnipueAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new UnipueHolder(parent, R.layout.item_unique);
    }

    class UnipueHolder extends BaseViewHolder<Unipue> {
        @BindView(R.id.avatar)
        CircleImageView mAvatar;
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_address)
        TextView mTvAddress;
        @BindView(R.id.tv_time)
        TextView mTvTime;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_content)
        TextView mTvContent;

        public UnipueHolder(ViewGroup parent, int viewType) {
            super(parent, viewType);
        }

        @Override
        public void setData(int position, Unipue data) {
            Glide.with(getContext()).load(data.memberAvatar).into(mAvatar);
            mTvName.setText(data.memberNick);
            mTvAddress.setText(data.loca);
            mTvTime.setText(data.addTime);
            mTvTitle.setText(data.title);
            mTvContent.setText(Html.fromHtml(UiUtils.getString(R.string.prevention_method, data.content)));
        }
    }
}

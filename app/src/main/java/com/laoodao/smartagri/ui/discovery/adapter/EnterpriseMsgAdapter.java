package com.laoodao.smartagri.ui.discovery.adapter;

import android.content.Context;
import android.text.Html;
import android.view.ViewGroup;
import android.widget.TextView;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.Enterprise.EnterpriseData;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import butterknife.BindView;

/**
 * Created by WORK on 2017/7/1.
 */

public class EnterpriseMsgAdapter extends BaseAdapter<EnterpriseData> {
    public EnterpriseMsgAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new EnterpriseMsgHolder(parent);
    }

    class EnterpriseMsgHolder extends BaseViewHolder<EnterpriseData> {

        @BindView(R.id.tv_enterprisename)
        TextView mTvEnterprisename;
        @BindView(R.id.tv_enterprisecode)
        TextView mTvEnterprisecode;
        @BindView(R.id.tv_address)
        TextView mTvAddress;
        @BindView(R.id.tv_contactperson)
        TextView mTvContactperson;
        @BindView(R.id.tv_phone)
        TextView mTvPhone;

        public EnterpriseMsgHolder(ViewGroup parent) {
            super(parent, R.layout.item_enterprise_msg);
        }

        @Override
        public void setData(EnterpriseData data) {
            super.setData(data);
            mTvEnterprisename.setText(data.enterprisename);
            mTvEnterprisecode.setText(data.enterprisecode);
            mTvAddress.setText(data.ascriptiona + "  " + data.address);
            mTvContactperson.setText(Html.fromHtml(UiUtils.getString(R.string.contactperson, data.contactperson)));
            mTvPhone.setText(Html.fromHtml(UiUtils.getString(R.string.html_phone, data.phone)));
        }
    }
}

package com.laoodao.smartagri.ui.user.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.CerifyMenu;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/8/16 0016.
 */

public class CompanyKindAdapter extends BaseAdapter<CerifyMenu.CompanyKindBean> {
    public interface OnClickListener {
        void setOnClickListener(int poistoin);
    }

    public OnClickListener mOnClickListener;

    public CompanyKindAdapter(Context context, OnClickListener onClickListener) {
        super(context);
        mOnClickListener = onClickListener;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new CompanyKindHolder(parent);
    }

    class CompanyKindHolder extends BaseViewHolder<CerifyMenu.CompanyKindBean> {
        @BindView(R.id.rb_name)
        RadioButton mRbName;

        public CompanyKindHolder(ViewGroup parent) {
            super(parent, R.layout.item_field);
        }

        @Override
        public void setData(CerifyMenu.CompanyKindBean data) {
            super.setData(data);
            mRbName.setPadding(0, UiUtils.dip2px(15),0,UiUtils.dip2px(15));
            mRbName.setText(data.name);
            if (data.isSelect) {
                mRbName.setChecked(true);
                mRbName.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
            }else {
                mRbName.setChecked(false);
                mRbName.setTextColor(ContextCompat.getColor(getContext(), R.color.common_h1));
            }
            mRbName.setOnClickListener(v -> {
                mOnClickListener.setOnClickListener(getCurrentPosition());
            });
        }
    }
}

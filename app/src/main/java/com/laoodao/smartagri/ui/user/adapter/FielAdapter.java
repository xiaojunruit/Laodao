package com.laoodao.smartagri.ui.user.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.CerifyMenu;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/8/16 0016.
 */

public class FielAdapter extends BaseAdapter<CerifyMenu.SpecialFieldBean> {


    public interface onClickListener {
        void setOnClickListener(int position);
    }

    public onClickListener mOnClickListener;

    public FielAdapter(Context context, onClickListener onClickListener) {
        super(context);
        mOnClickListener = onClickListener;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new FielHolder(parent);
    }

    class FielHolder extends BaseViewHolder<CerifyMenu.SpecialFieldBean> {
        @BindView(R.id.rb_name)
        RadioButton mRbName;

        public FielHolder(ViewGroup parent) {
            super(parent, R.layout.item_field);
        }

        @Override
        public void setData(CerifyMenu.SpecialFieldBean data) {
            super.setData(data);
            mRbName.setText(data.name);
            if (data.isSelect) {
                mRbName.setChecked(true);
            } else {
                mRbName.setChecked(false);
            }
            mRbName.setOnClickListener(v -> {
                mOnClickListener.setOnClickListener(getCurrentPosition());
            });
        }
    }
}

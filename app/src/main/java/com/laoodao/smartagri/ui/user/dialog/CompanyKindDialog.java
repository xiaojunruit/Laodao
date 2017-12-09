package com.laoodao.smartagri.ui.user.dialog;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.flyco.dialog.widget.base.BaseDialog;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.bean.CerifyMenu;
import com.laoodao.smartagri.ui.user.adapter.CompanyKindAdapter;
import com.laoodao.smartagri.ui.user.adapter.FielAdapter;
import com.laoodao.smartagri.utils.KnifeUtil;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.recyclerview.decoration.DividerDecoration;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/8/16 0016.
 */

public class CompanyKindDialog extends BaseDialog {
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    private Context mContext;
    private CompanyKindAdapter mAdapter;


    public CompanyKindDialog(Context context, CompanyKindAdapter adapter) {
        super(context);
        mContext = context;
        mAdapter = adapter;
    }

    @Override
    public View onCreateView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_company_kind,null,false);
        KnifeUtil.bindTarget(this, view);
        widthScale(0.8f);
        showAnim(null);
        dismissAnim(null);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerview.addItemDecoration(new DividerDecoration(ContextCompat.getColor(mContext, R.color.common_divider_narrow), UiUtils.dip2px(1), 0, 0));
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void setUiBeforShow() {

    }


}

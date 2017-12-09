package com.laoodao.smartagri.ui.discovery.fragment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseFragment;
import com.laoodao.smartagri.base.LazyFragment;
import com.laoodao.smartagri.bean.Enterprise.Enterprise;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerDiscoveryComponent;
import com.laoodao.smartagri.ui.discovery.adapter.EnterpriseMsgAdapter;
import com.laoodao.smartagri.ui.discovery.contract.NormalEnterpriseContract;
import com.laoodao.smartagri.ui.discovery.presenter.NormalEnterprisePresenter;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.view.recyclerview.decoration.DividerDecoration;

import butterknife.BindView;

/**
 * Created by WORK on 2017/4/18.
 */

public class NormalEnterpriseFragment extends BaseFragment<NormalEnterprisePresenter> implements NormalEnterpriseContract.NormalEnterpriseView {

    @BindView(R.id.recyclerview_list)
    RecyclerView mRecyclerviewList;
    @BindView(R.id.iv_prompt)
    ImageView mIvPrompt;
    @BindView(R.id.rl_prompt)
    RelativeLayout mRlPrompt;
    @BindView(R.id.tv_prompt)
    TextView mTvPrompt;
    private Enterprise enterprise;
    private EnterpriseMsgAdapter mAdapter;
    private int type;

    public static NormalEnterpriseFragment newInstance(int type, Enterprise enterprise) {
        Bundle args = new Bundle();
        args.putSerializable("enterprise", enterprise);
        args.putInt("type", type);
        NormalEnterpriseFragment fragment = new NormalEnterpriseFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_normal_enterprise;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerDiscoveryComponent
                .builder()
                .appComponent(appComponent)
                .build()
                .inject(this);

    }

    @Override
    public void configViews() {
        enterprise = (Enterprise) getArguments().getSerializable("enterprise");
        type = getArguments().getInt("type");
        mAdapter = new EnterpriseMsgAdapter(getContext());
        mRecyclerviewList.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerviewList.setHasFixedSize(true);
        mRecyclerviewList.setAdapter(mAdapter);
        mRecyclerviewList.addItemDecoration(new DividerDecoration(ContextCompat.getColor(getContext(), R.color.common_divider_narrow), 1, 45, 0));
        if (type == 1) {
            if (enterprise.normalData() != null && enterprise.normalData().size() >= 1) {
                mRlPrompt.setVisibility(View.GONE);
                mAdapter.addAll(enterprise.normalData());
            } else {
                mRlPrompt.setVisibility(View.VISIBLE);
            }
            mTvPrompt.setText("暂无正常企业");
        } else {
            if (enterprise.blacklistData() != null && enterprise.blacklistData().size() >= 1) {
                mRlPrompt.setVisibility(View.GONE);
                mAdapter.addAll(enterprise.blacklistData());
            } else {
                mRlPrompt.setVisibility(View.VISIBLE);
            }
            mTvPrompt.setText("暂无黑名单企业");
        }
    }


    @Override
    public void complete() {

    }
}
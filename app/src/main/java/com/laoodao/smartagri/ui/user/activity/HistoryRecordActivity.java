package com.laoodao.smartagri.ui.user.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseHeaderView;
import com.laoodao.smartagri.base.BaseXRVActivity;
import com.laoodao.smartagri.bean.HistoryRecord;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerUserComponent;
import com.laoodao.smartagri.ui.user.adapter.HistoryRecordAdapter;
import com.laoodao.smartagri.ui.user.contract.HistoryRecordContract;
import com.laoodao.smartagri.ui.user.presenter.HistoryRecordPresenter;
import com.laoodao.smartagri.utils.UiUtils;

import butterknife.BindView;

/**
 * Created by WORK on 2017/5/6.
 */

public class HistoryRecordActivity extends BaseXRVActivity<HistoryRecordPresenter> implements HistoryRecordContract.HistoryRecordView {

    private int id;

    public static void start(Context context, int id) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        UiUtils.startActivity(context, HistoryRecordActivity.class, bundle);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_history_record;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerUserComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void historyRecordSuccess(HistoryRecord record) {
        mAdapter.clear();
        if (record.records != null) {
            mAdapter.addAll(record.records);
        }
        HistoryRecordHeader header = ((HistoryRecordHeader) mHeader);
        switch (record.state) {
            case 1://欠款
                header.mTvState.setText(Html.fromHtml(UiUtils.getString(R.string.order_state,"欠款")));
                break;
            case 2://还款中
                header.mTvState.setText(Html.fromHtml(UiUtils.getString(R.string.order_state,"还款中")));
                break;
            case 3://已完成
                header.mTvState.setText(Html.fromHtml(UiUtils.getString(R.string.order_state,"已完成")));
                break;
            case 4://未付
                header.mTvState.setText(Html.fromHtml(UiUtils.getString(R.string.order_state,"未付")));
                break;
            case 5://已付
                header.mTvState.setText(Html.fromHtml(UiUtils.getString(R.string.order_state,"已付")));
                break;
        }

        header.mTvOrderNum.setText(UiUtils.getString(R.string.order_num, record.orderSn));
        header.mTvTime.setText(UiUtils.getString(R.string.update_time, record.updateTime));

    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.requestHistoryRecord(id);
    }

    class HistoryRecordHeader extends BaseHeaderView {

        @BindView(R.id.tv_state)
        TextView mTvState;
        @BindView(R.id.tv_order_num)
        TextView mTvOrderNum;
        @BindView(R.id.tv_time)
        TextView mTvTime;

        public HistoryRecordHeader(Context context) {
            super(context);
        }

        @Override
        protected int getLayoutHeaderViewId() {
            return R.layout.header_view_history_record;
        }
    }

    @Override
    protected void configViews() {
        id = getIntent().getIntExtra("id", id);
        mHeader = new HistoryRecordHeader(this);
        initAdapter(HistoryRecordAdapter.class);
        mRecyclerView.setLoadingMoreEnabled(false);
        onRefresh();
    }
}

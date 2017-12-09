package com.laoodao.smartagri.ui.user.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.HistoryRecord;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import java.util.List;

import butterknife.BindView;

/**
 * Created by WORK on 2017/5/6.
 */

public class HistoryRecordAdapter extends BaseAdapter<HistoryRecord.RepayList> {


    public HistoryRecordAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new HistoryRecordHolder(parent, R.layout.item_history_record);
    }

    class HistoryRecordHolder extends BaseViewHolder<HistoryRecord.RepayList> {
        @BindView(R.id.view_left)
        View mViewLeft;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_time)
        TextView mTvTime;

        public HistoryRecordHolder(ViewGroup parent, int viewType) {
            super(parent, viewType);
        }

        @Override
        public void setData(HistoryRecord.RepayList data) {
            mTvTitle.setText(data.desc);
            mTvTime.setText(data.time);
        }
    }

}

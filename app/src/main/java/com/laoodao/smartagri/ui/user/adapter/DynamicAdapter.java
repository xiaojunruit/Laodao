package com.laoodao.smartagri.ui.user.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.Notice;
import com.laoodao.smartagri.common.Route;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;

/**
 * Created by WORK on 2017/5/4.
 */

public class DynamicAdapter extends BaseAdapter<Notice> {
    public DynamicAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new DynamicHolder(parent, R.layout.item_dynamic);
    }

    class DynamicHolder extends BaseViewHolder<Notice> {
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_time)
        TextView mTvTime;
        @BindView(R.id.tv_source_title)
        TextView mTvSourceTitle;
        @BindView(R.id.tv_source_content)
        TextView mTvSourceContent;
        @BindView(R.id.tv_message)
        TextView mTvMessage;

        public DynamicHolder(ViewGroup parent, int viewType) {
            super(parent, viewType);
        }

        @Override
        public void setData(Notice data) {

            super.setData(data);
            mTvTime.setText(data.time);
            mTvTitle.setText(data.title);
            //mTvSourceTitle.setText(data.sourceTitle);
            mTvSourceContent.setText(data.sourceContent);
            mTvMessage.setText(data.message);
            mTvMessage.setVisibility(data.message.isEmpty() ? View.GONE : View.VISIBLE);
            itemView.setOnClickListener(v -> {
                Route.go(getContext(), data.url);
            });
        }

    }

}

package com.laoodao.smartagri.ui.user.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.flyco.tablayout.widget.MsgView;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.Notice;
import com.laoodao.smartagri.common.Route;
import com.laoodao.smartagri.event.ReadMsgEvent;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

/**
 * Created by WORK on 2017/5/4.
 */

public class NoticeAdapter extends BaseAdapter<Notice> {


    public NoticeAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new NoticeHolder(parent, R.layout.item_notice);
    }

    class NoticeHolder extends BaseViewHolder<Notice> {
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_content)
        TextView mTvContent;
        @BindView(R.id.msg_dot)
        MsgView mMsgDot;


        public NoticeHolder(ViewGroup parent, int viewType) {
            super(parent, viewType);
        }

        @Override
        public void setData(Notice data) {
            super.setData(data);
            mTvTitle.setText(data.title);
            mTvContent.setText(data.message);
            mMsgDot.setVisibility(data.state == 0 ? View.VISIBLE : View.INVISIBLE);
            itemView.setOnClickListener(v -> {
                mMsgDot.setVisibility(View.INVISIBLE);
                Route.go(getContext(), data.url);
                EventBus.getDefault().post(new ReadMsgEvent(data.messageId));
            });
        }

    }


}

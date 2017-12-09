package com.laoodao.smartagri.ui.qa.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.Keyword;
import com.laoodao.smartagri.event.DeleteHistoryEvent;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 欧源 on 2017/5/2.
 */

public class QuestionSearchAdapter extends BaseAdapter<Keyword> {
    public QuestionSearchAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new HistoryHolder(parent);
    }


    class HistoryHolder extends BaseViewHolder<Keyword> {

        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.iv_delete)
        ImageView mIvDelete;

        public HistoryHolder(ViewGroup itemView) {
            super(itemView, R.layout.item_question_search);
        }

        @Override
        public void setData(Keyword data) {
            mTvName.setText(data.kw);
        }

        @OnClick(R.id.iv_delete)
        public void onClick() {
            DeleteHistoryEvent event = new DeleteHistoryEvent();
            event.id = getItem(getCurrentPosition()).id;
            EventBus.getDefault().post(event);
        }
    }
}

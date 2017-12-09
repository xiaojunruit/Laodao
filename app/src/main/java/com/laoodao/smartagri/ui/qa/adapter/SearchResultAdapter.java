package com.laoodao.smartagri.ui.qa.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.Question;
import com.laoodao.smartagri.ui.qa.activity.QuestionDetailActivity;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import butterknife.BindView;

/**
 * Created by 欧源 on 2017/5/2.
 */

public class SearchResultAdapter extends BaseAdapter<Question> {
    public SearchResultAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchResultHolder(parent);
    }


    class SearchResultHolder extends BaseViewHolder<Question> {

        //        @BindView(R.id.tv_title)
//        TextView mTvTitle;
        @BindView(R.id.tv_content)
        TextView mTvContent;

        public SearchResultHolder(ViewGroup itemView) {
            super(itemView, R.layout.item_search_question_result);
        }

        @Override
        public void setData(Question data) {

            mTvContent.setText(data.content);
            //mTvTitle.setText(data.title);
            itemView.setOnClickListener(v -> {
                QuestionDetailActivity.start(getContext(), data.id);
            });
        }
    }
}

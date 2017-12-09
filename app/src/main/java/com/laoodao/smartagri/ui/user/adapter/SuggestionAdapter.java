package com.laoodao.smartagri.ui.user.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.flyco.roundview.RoundTextView;
import com.flyco.roundview.RoundViewDelegate;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.bean.Suggestion;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;
import com.laoodao.smartagri.view.recyclerview.adapter.RecyclerArrayAdapter;

import butterknife.BindView;

/**
 * Created by 欧源 on 2017/4/22.
 */

public class SuggestionAdapter extends RecyclerArrayAdapter<Suggestion> {

    public SuggestionAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new SuggestionHolder(parent);
    }

    static class SuggestionHolder extends BaseViewHolder<Suggestion> {

        @BindView(R.id.tv_suggestion)
        RoundTextView mTvSuggestion;

        public SuggestionHolder(ViewGroup itemView) {
            super(itemView, R.layout.item_suggestion);
        }

        @Override
        public void setData(Suggestion suggestion) {
            mTvSuggestion.setText(suggestion.name);
            boolean isSelected = suggestion.isSelected;
            RoundViewDelegate delegate = mTvSuggestion.getDelegate();
            if (isSelected) {
                delegate.setBackgroundColor(UiUtils.getColor(R.color.colorAccent));
                delegate.setStrokeColor(UiUtils.getColor(R.color.transparent));
                mTvSuggestion.setTextColor(UiUtils.getColor(R.color.white));
            } else {
                delegate.setBackgroundColor(UiUtils.getColor(R.color.transparent));
                delegate.setStrokeColor(UiUtils.getColor(R.color.common_divider_narrow));
                mTvSuggestion.setTextColor(UiUtils.getColor(R.color.common_h3));
            }

        }
    }

    public Suggestion getSelectedItem() {

        for (Suggestion suggestion : getAllData()) {
            if (suggestion.isSelected) {
                return suggestion;
            }
        }
        return null;
    }
}

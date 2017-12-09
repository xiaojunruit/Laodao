package com.laoodao.smartagri.ui.discovery.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.view.ViewGroup;

import com.flyco.roundview.RoundTextView;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.MechanicalType;
import com.laoodao.smartagri.ui.discovery.interfaces.OnMoveAndSwipedListener;
import com.laoodao.smartagri.utils.DeviceUtils;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

/**
 * Created by WORK on 2017/5/18.
 */

public class SelectNewsMenuAdapter extends BaseAdapter<MechanicalType> implements OnMoveAndSwipedListener {
    List<MechanicalType> data = new ArrayList<>();

    public SelectNewsMenuAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new SelectNewsMenuHolder(parent, R.layout.item_select_news_menu);
    }

    class SelectNewsMenuHolder extends BaseViewHolder<MechanicalType> {
        @BindView(R.id.tv_tab)
        RoundTextView mTvTab;

        public SelectNewsMenuHolder(ViewGroup parent, int viewType) {
            super(parent, viewType);

        }

        @Override
        public void setData(int position, MechanicalType data) {
            mTvTab.setText(data.name);
            GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) itemView.getLayoutParams();
            params.width = (int) (DeviceUtils.getScreenWidth(getContext()) / 4) - UiUtils.dip2px(20);
            itemView.setLayoutParams(params);
        }
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(getAllData(), fromPosition, toPosition);
        data = getAllData();
        MechanicalType mechanicalType = data.get(fromPosition);
        data.remove(fromPosition);
        data.add(toPosition, mechanicalType);
        setNotifyOnChange(false);
        addAll(data, true);
        setNotifyOnChange(true);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

}

package com.laoodao.smartagri.ui.discovery.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.BaikeMenu;
import com.laoodao.smartagri.ui.discovery.interfaces.OnMoveAndSwipedListener;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/5/19.
 */

public class FindCropAdapter extends BaseAdapter<BaikeMenu.ItemsBean> implements OnMoveAndSwipedListener {
    List<BaikeMenu.ItemsBean> itemsBeen = new ArrayList<>();

    public FindCropAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new FindCropHolder(parent, R.layout.item_find_crop);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(getAllData(), fromPosition, toPosition);
        itemsBeen = getAllData();
        BaikeMenu.ItemsBean cropListBean = itemsBeen.get(fromPosition);
        itemsBeen.remove(fromPosition);
        itemsBeen.add(toPosition, cropListBean);
        setNotifyOnChange(false);
        addAll(itemsBeen, true);
        setNotifyOnChange(true);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    class FindCropHolder extends BaseViewHolder<BaikeMenu.ItemsBean> {
        @BindView(R.id.tv_type)
        TextView mTvType;

        public FindCropHolder(ViewGroup parent, int viewType) {
            super(parent, viewType);
        }

        @Override
        public void setData(BaikeMenu.ItemsBean data) {
            super.setData(data);
            mTvType.setText(data.name);
        }
    }
}

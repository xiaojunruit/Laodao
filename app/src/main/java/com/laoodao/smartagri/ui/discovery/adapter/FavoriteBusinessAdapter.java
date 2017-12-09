package com.laoodao.smartagri.ui.discovery.adapter;

import android.content.Context;
import android.text.Html;
import android.view.ViewGroup;
import android.widget.TextView;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.CottonBill;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import butterknife.BindView;

/**
 * Created by WORK on 2017/7/22.
 */

public class FavoriteBusinessAdapter extends BaseAdapter<CottonBill.ByCompanyBean> {

    private final int TYPE_FIRIST = 2;
    private final int TYPE_OTHER = 1;

    public FavoriteBusinessAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_FIRIST;
        }
        return TYPE_OTHER;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FIRIST) {
            return new FavoriteBusinessFiristHolder(parent);
        }
        return new FavoriteBusinessHolder(parent);
    }

    class FavoriteBusinessHolder extends BaseViewHolder<CottonBill.ByCompanyBean> {

        @BindView(R.id.tv_number)
        TextView tvNumber;
        @BindView(R.id.tv_producer_name)
        TextView tvProducerName;
        @BindView(R.id.tv_com_net_weigh)
        TextView tvComNetWeigh;

        public FavoriteBusinessHolder(ViewGroup parent) {
            super(parent, R.layout.item_favorite_business);
        }

        @Override
        public void setData(CottonBill.ByCompanyBean data) {
            super.setData(data);
            tvNumber.setText("NO." + (getCurrentPosition() + 1));
            if (data.producer_name.length() >= 12) {
                String str = data.producer_name.substring(0, 11);
                str += "...";
                tvProducerName.setText(str);
            } else {
                tvProducerName.setText(data.producer_name);
            }
            tvComNetWeigh.setText(Html.fromHtml(UiUtils.getResources().getString(R.string.com_net_weigh, data.sell_count, "&#160;&#160;&#160;&#160;&#160;&#160;&#160;", data.com_net_weigh)));
        }
    }

    class FavoriteBusinessFiristHolder extends BaseViewHolder<CottonBill.ByCompanyBean> {
        @BindView(R.id.tv_producer_name)
        TextView tvProducerName;
        @BindView(R.id.tv_com_net_weigh)
        TextView tvComNetWeigh;

        public FavoriteBusinessFiristHolder(ViewGroup parent) {
            super(parent, R.layout.item_favorite_business_firist);
        }

        @Override
        public void setData(CottonBill.ByCompanyBean data) {
            super.setData(data);
            if (data.producer_name.length() >= 12) {
                String str = data.producer_name.substring(0, 11);
                str += "...";
                tvProducerName.setText(str);
            } else {
                tvProducerName.setText(data.producer_name);
            }
            tvComNetWeigh.setText(Html.fromHtml(UiUtils.getResources().getString(R.string.com_net_weigh, data.sell_count, ",", data.com_net_weigh)));
        }
    }
}

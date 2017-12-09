package com.laoodao.smartagri.ui.discovery.adapter;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.cotton.CottonData;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import butterknife.BindView;

/**
 * Created by WORK on 2017/6/30.
 */

public class CottonCultivationAdapter extends BaseAdapter<CottonData> {
    public CottonCultivationAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new CottonCultivationHolder(parent);
    }

    class CottonCultivationHolder extends BaseViewHolder<CottonData> {
        @BindView(R.id.tv_address)
        TextView mTvAddress;
        @BindView(R.id.tv_plant_place)
        TextView mTvPlantPlace;
        @BindView(R.id.tv_crop_plant_area)
        TextView mTvCropPlantArea;
        @BindView(R.id.tv_cotton_plant_area)
        TextView mTvCottonPlantArea;
        @BindView(R.id.tv_cotton_avg)
        TextView mTvCottonAvg;
        @BindView(R.id.tv_specotton_plant_area)
        TextView mTvSpecottonPlantArea;
        @BindView(R.id.tv_specotton_avg)
        TextView mTvSpecottonAvg;
        @BindView(R.id.tv_report_date)
        TextView mTvReportDate;

        public CottonCultivationHolder(ViewGroup parent) {
            super(parent, R.layout.item_cotton_cultivation);
        }

        int i = 0;

        @Override
        public void setData(CottonData data) {
            super.setData(data);
            i++;
            mTvAddress.setText(data.dzname + "  " + data.xsname + "  " + data.xzname + " " + data.cmname);
            mTvPlantPlace.setText(Html.fromHtml(UiUtils.getString(R.string.plant_place, data.plantPlace)));
            mTvCropPlantArea.setText(Html.fromHtml(UiUtils.getString(R.string.crop_plant_area, data.cropPlantArea)));
            mTvCottonPlantArea.setText(Html.fromHtml(UiUtils.getString(R.string.cotton_plant_area, data.cottonPlantArea)));
            mTvCottonAvg.setText(Html.fromHtml(UiUtils.getString(R.string.cotton_avg, data.cottonAvg)));
            mTvSpecottonPlantArea.setText(Html.fromHtml(UiUtils.getString(R.string.specotton_plant_area, data.specottonPlantArea)));
            mTvSpecottonAvg.setText(Html.fromHtml(UiUtils.getString(R.string.specotton_avg, data.specottonAvg)));
            mTvReportDate.setText(data.reportDate);

        }
    }
}

package com.laoodao.smartagri.ui.home.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.weather.Future;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.WeatherUtils;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import butterknife.BindView;

/**
 * Created by WORK on 2017/5/2.
 */

public class Day7WeatherAdapter extends BaseAdapter<Future> {


    public Day7WeatherAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new DayWeatherHolder(parent, R.layout.item_day7weather);
    }

    class DayWeatherHolder extends BaseViewHolder<Future> {

        @BindView(R.id.tv_date)
        TextView mTvDate;
        @BindView(R.id.tv_temperature)
        TextView mTvTemperature;
        @BindView(R.id.iv_weather_img)
        ImageView mIvWeatherImg;
        @BindView(R.id.tv_weather)
        TextView mTvWeather;

        public DayWeatherHolder(ViewGroup parent, int viewType) {
            super(parent, viewType);
            int width = UiUtils.getScreenWidth() / 4;
            itemView.setLayoutParams(new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT));
        }

        @Override
        public void setData(Future data) {

            mTvDate.setText(data.date);
            mTvTemperature.setText(data.temperature);
            mTvWeather.setText(data.weather);
            if (!TextUtils.isEmpty(data.weatherIdList.fa)) {
                int resId = WeatherUtils.getWeatherResId(data.weatherIdList.fa);
                if (resId != -1) {
                    mIvWeatherImg.setImageResource(resId);
                }
            }
        }
    }
}

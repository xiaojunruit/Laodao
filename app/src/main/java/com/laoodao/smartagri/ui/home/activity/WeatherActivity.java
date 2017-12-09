package com.laoodao.smartagri.ui.home.activity;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.bean.weather.SK;
import com.laoodao.smartagri.bean.weather.Weather;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerMainComponent;
import com.laoodao.smartagri.ui.home.adapter.Day7WeatherAdapter;
import com.laoodao.smartagri.ui.home.contract.WeatherContract;
import com.laoodao.smartagri.ui.home.presenter.WeatherPresenter;
import com.laoodao.smartagri.utils.DeviceUtils;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.GradeProgressView;
import com.laoodao.smartagri.view.IndexHorizontalScrollView;
import com.laoodao.smartagri.view.Today24HourView;
import com.laoodao.smartagri.view.WeatherUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by WORK on 2017/5/2.
 */

public class WeatherActivity extends BaseActivity<WeatherPresenter> implements WeatherContract.WeatherView {

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.txt_title)
    TextView mTxtTitle;
    @BindView(R.id.tv_time_release)
    TextView mTvTimeRelease;
    @BindView(R.id.title)
    FrameLayout mTitle;
    @BindView(R.id.minus)
    ImageView mMinus;
    @BindView(R.id.temperature1)
    ImageView mTemperature1;
    @BindView(R.id.temperature2)
    ImageView mTemperature2;
    @BindView(R.id.iv_weather_img)
    ImageView mIvWeatherImg;
    @BindView(R.id.tv_text_mark)
    TextView mTvTextMark;
    @BindView(R.id.tv_num_mark)
    TextView mTvNumMark;
    @BindView(R.id.tv_humidity)
    TextView mTvHumidity;
    @BindView(R.id.tv_wind_power)
    TextView mTvWindPower;
    @BindView(R.id.day7_weather_list)
    RecyclerView mDay7WeatherList;
    @BindView(R.id.content_top)
    RelativeLayout mContentTop;
    @BindView(R.id.tv_max_temperature)
    TextView mTvMaxTemperature;
    @BindView(R.id.tv_min_temperature)
    TextView mTvMinTemperature;
    @BindView(R.id.day24_hour)
    Today24HourView mDay24Hour;
    @BindView(R.id.horizontal)
    IndexHorizontalScrollView mHorizontal;
    @BindView(R.id.gpv)
    GradeProgressView mGpv;
    @BindView(R.id.big_fan)
    ImageView mBigFan;
    @BindView(R.id.small_fan)
    ImageView mSmallFan;
    @BindView(R.id.content)
    LinearLayout mContent;
    @BindView(R.id.tv_wind_direction)
    TextView mTvWindDirection;
    @BindView(R.id.tv_wind_strength)
    TextView mTvWindStrength;
    private Drawable mDrawable;
    private String mAreaId, mLon, mLat;
    private Day7WeatherAdapter mAdapter = new Day7WeatherAdapter(this);

    public static void start(Context context, String areaId, String lon, String lat) {
        Bundle bundle = new Bundle();
        bundle.putString("areaId", areaId);
        bundle.putString("lon", lon);
        bundle.putString("lat", lat);
        UiUtils.startActivity(context, WeatherActivity.class, bundle);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_weather;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerMainComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    protected void configViews() {
        mAreaId = getIntent().getStringExtra("areaId");
        mLon = getIntent().getStringExtra("lon");
        mLat = getIntent().getStringExtra("lat");
        StatusBarUtil.setTranslucent(this, 0);
        initContentTopHeight();
        initDay7WeatherList();
        mPresenter.requestWeather(mAreaId, mLon, mLat);
        startFanAnim();
        mHorizontal.setToday24HourView(mDay24Hour);
    }


    private void startFanAnim() {
        // 加载动画
        AnimationDrawable bitFanAnim = (AnimationDrawable) mBigFan.getDrawable();
        // 默认进入页面就开启动画
        if (!bitFanAnim.isRunning()) {
            bitFanAnim.start();
        }

        // 加载动画
        AnimationDrawable samllFanAnim = (AnimationDrawable) mSmallFan.getDrawable();
        // 默认进入页面就开启动画
        if (!samllFanAnim.isRunning()) {
            samllFanAnim.start();
        }
    }


    @Override
    public void weatherSuccess(Weather weather) {
        mAdapter.clear();
        mAdapter.addAll(weather.future);

        mTxtTitle.setText(weather.today.city);
        mTvTimeRelease.setText(weather.sk.time + "发布");
        mTvTimeRelease.setVisibility(TextUtils.isEmpty(weather.sk.time) ? View.GONE : View.VISIBLE);
        if (!TextUtils.isEmpty(weather.today.weatherId.fa)) {
            int resId = WeatherUtils.getWeatherResId(weather.today.weatherId.fa);
            if (resId != -1) {
                mIvWeatherImg.setImageResource(resId);
            }
        }
        mTvTextMark.setText(weather.today.weather);
        mTvNumMark.setText(weather.today.temperature);
        mTvHumidity.setText(weather.sk.humidity);
        mTvWindPower.setText(weather.sk.windStrength);
        mTvMaxTemperature.setText(Html.fromHtml(weather.today.maxTemperature));
        mTvMinTemperature.setText(Html.fromHtml(weather.today.minTemperature));
        mTvWindDirection.setText(weather.sk.windDirection);
        mTvWindStrength.setText(weather.sk.windStrength);


        setTemperature(weather.sk);
        mGpv.setProgress(20);
        //湿度
        String humidity = weather.sk.humidity;
        mGpv.setProgress(Integer.parseInt(humidity.replace("%", "")));
        String maxTem = weather.today.maxTemperature.replace("℃", "");
        String minTem = weather.today.minTemperature.replace("℃", "");
        mDay24Hour.setData(Integer.parseInt(maxTem), Integer.parseInt(minTem), weather.hours);
    }

    private void initDay7WeatherList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mDay7WeatherList.setLayoutManager(layoutManager);
        mDay7WeatherList.setAdapter(mAdapter);
    }

    private void initContentTopHeight() {
        //获取toolbar坐标

        ViewTreeObserver vto = mTitle.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(() -> {
            final Rect rect = new Rect();
            mTitle.getWindowVisibleDisplayFrame(rect);

            int[] location = new int[2];
            mTitle.getLocationInWindow(location);

            int y = mTitle.getHeight() + location[1];
            //获取屏幕高度
            int heightPixels = (int) DeviceUtils.getScreenHeight(this);
            mContentTop.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, heightPixels - y));
        });
    }

    /**
     * 设置温度（图片代替数字）
     */
    private void setTemperature(SK sk) {
        String temp = sk.temp;
        if (!TextUtils.isEmpty(temp)) {

            //两位数
            if (temp.length() == 2 && !temp.contains("-")) {        //2位正数

                int temp1 = Integer.parseInt(temp.substring(0, 1));
                int temp2 = Integer.parseInt(temp.substring(1));
                mTemperature1.setImageResource(getMipmapResId(temp1));
                mTemperature2.setImageResource(getMipmapResId(temp2));
                mTemperature1.setVisibility(View.VISIBLE);
                mTemperature2.setVisibility(View.VISIBLE);
            } else if (temp.length() == 1 && !temp.contains("-")) {     //一位正数
                int temp1 = Integer.parseInt(temp);
                mTemperature1.setImageResource(getMipmapResId(temp1));
                mTemperature2.setVisibility(View.GONE);
            } else if (temp.length() == 2 && temp.contains("-")) {  // 负数 1wei
                mMinus.setVisibility(View.VISIBLE);
                int temp1 = Integer.parseInt(temp.substring(1));
                mTemperature2.setVisibility(View.GONE);
                mTemperature1.setImageResource(getMipmapResId(temp1));
            } else if (temp.length() == 3 && temp.contains("-")) {
                mMinus.setVisibility(View.VISIBLE);
                int temp1 = Integer.parseInt(temp.substring(1, 2));
                int temp2 = Integer.parseInt(temp.substring(2));
                mTemperature1.setImageResource(getMipmapResId(temp1));
                mTemperature2.setImageResource(getMipmapResId(temp2));
                mTemperature1.setVisibility(View.VISIBLE);
                mTemperature2.setVisibility(View.VISIBLE);
            }
        }
    }

    private int getMipmapResId(int temp) {
        return getResources().getIdentifier("number_" + temp, "mipmap", getPackageName());
    }


    @Override
    public void complete() {

    }

    @OnClick(R.id.iv_back)
    public void onClick() {
        finish();
    }


}

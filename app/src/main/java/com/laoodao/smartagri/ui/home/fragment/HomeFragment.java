package com.laoodao.smartagri.ui.home.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.ejz.imageSelector.adapter.GridSpacingItemDecoration;
import com.ejz.multistateview.MultiStateView;
import com.ejz.xrecyclerview.XRecyclerView;
import com.laoodao.smartagri.Global;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseHeaderView;
import com.laoodao.smartagri.base.BaseXRVFragment;
import com.laoodao.smartagri.bean.Home;
import com.laoodao.smartagri.bean.News;
import com.laoodao.smartagri.bean.SelectLocation;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerMainComponent;
import com.laoodao.smartagri.event.SelectAreaChangeEvent;
import com.laoodao.smartagri.location.LocationSubscriber;
import com.laoodao.smartagri.location.RxLocation;
import com.laoodao.smartagri.ui.discovery.activity.NewsDetailActivity;
import com.laoodao.smartagri.ui.home.activity.WeatherActivity;
import com.laoodao.smartagri.ui.home.adapter.HomeAdapter;
import com.laoodao.smartagri.ui.home.adapter.HomeMenuGridListAdapter;
import com.laoodao.smartagri.ui.home.adapter.HomeMenuListAdapter;
import com.laoodao.smartagri.ui.home.contract.HomeContract;
import com.laoodao.smartagri.ui.home.presenter.HomePresenter;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.WeatherUtils;
import com.laoodao.smartagri.view.cityselector.activity.CitySelectorActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 欧源 on 2017/4/13.
 * 首页
 */

public class HomeFragment extends BaseXRVFragment<HomePresenter> implements HomeContract.HomeView {

    @BindView(R.id.tv_location)
    TextView mTvLocation;
    //用户手动选择的地区Id
    private String mAreaId = "650100";

    //经纬度
    private String mLatitude;
    private String mLongitude;


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerMainComponent.builder().
                appComponent(appComponent).
                build().
                inject(this);
    }


    @Override
    public void configViews() {
        mHeader = new HomeHeader(getContext());
        initAdapter(HomeAdapter.class);
        XRecyclerView.DividerItemDecoration decoration = mRecyclerView.new DividerItemDecoration(ContextCompat.getColor(mActivity, R.color.common_divider_narrow), 1, UiUtils.dip2px(15), UiUtils.dip2px(15));
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setStateTextColor(R.color.white);
        mRecyclerView.setRefreshBackroundRes(R.color.colorAccent);
        mRecyclerView.setProgressBar();
        mRecyclerView.setArrowImageView(R.mipmap.ic_pulltorefresh_arrow_white);
        mAdapter.setOnItemClickListener(position -> {
            News news = (News) mAdapter.getItem(position);
            NewsDetailActivity.start(getContext(), news.id);
        });

    }

    /**
     * 选择地区改变  事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void selectAreaChange(SelectAreaChangeEvent event) {
        //手动选择的定位
        SelectLocation selectLocation = Global.getInstance().getSelectLocation();
        if (selectLocation != null) {
            mAreaId = selectLocation.areaId;
            mLatitude = selectLocation.latitude;
            mLongitude = selectLocation.longitude;
            mTvLocation.setText(selectLocation.cityName);
            onRefresh();
        }
    }

    @Override
    protected void lazyFetchData() {
        //已经手动选择过
        SelectLocation selectLocation = Global.getInstance().getSelectLocation();
        if (selectLocation == null) {
            selectLocation = Global.getInstance().getCurrentLocation();
        }
        if (selectLocation != null) {
            mAreaId = selectLocation.areaId;
            mLatitude = selectLocation.latitude;
            mLongitude = selectLocation.longitude;
            mTvLocation.setText(selectLocation.cityName);
            onRefresh();
        } else {
            requestLocation();
        }
    }

    /**
     * 请求定位
     */
    private void requestLocation() {
        RxLocation.get().locate(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindToLifecycle())
                .subscribe(new LocationSubscriber() {
                    @Override
                    public void onLocatedSuccess(@NonNull BDLocation bdLocation) {
                        Global.getInstance().setCurrentLocation(bdLocation);
                        mAreaId = null;
                        mLatitude = String.valueOf(bdLocation.getLatitude());
                        mLongitude = String.valueOf(bdLocation.getLongitude());
                        mTvLocation.setText(bdLocation.getCity());
                        onRefresh();
                    }

                    @Override
                    public void onLocatedFail(BDLocation bdLocation) {
                        mTvLocation.setText("乌鲁木齐");
                        SelectLocation selectLocation = new SelectLocation();
                        selectLocation.areaId = "650100";
                        selectLocation.cityName = "乌鲁木齐";
                        Global.getInstance().setSelectLocation(selectLocation);
                        onRefresh();
                    }
                });
    }

    @Override
    protected boolean enableEventBus() {
        return true;
    }

    @Override
    public void initHome(Home home) {
        HomeHeader header = ((HomeHeader) mHeader);
        header.mListAdapter.addAll(home.tMenu, true);
        header.mGridListAdapter.addAll(home.sMenu, true);
        if (!TextUtils.isEmpty(home.today.weatherId.fa)) {
            int resId = WeatherUtils.getWeatherResId(home.today.weatherId.fa);
            if (resId != -1) {
                header.mIvTodayImg.setImageResource(resId);
            }
        }
        if (!TextUtils.isEmpty(home.tomorrow.weatherId.fa)) {
            int resId = WeatherUtils.getWeatherResId(home.tomorrow.weatherId.fa);
            if (resId != -1) {
                header.mIvTomorrowImg.setImageResource(resId);
            }
        }

        header.mTvTodayMark.setText(home.today.weather);
        header.mTvTodayTemperature.setText(UiUtils.getString(R.string.temperature, home.today.temperature));
        header.mTvTodayTemperatureUp.setText(UiUtils.getString(R.string.temperature, home.today.temperatureMin));
        header.mTvTodayTemperatureUn.setText(UiUtils.getString(R.string.temperature, home.today.temperatureMax));

        header.mTvTomorrowMark.setText(home.tomorrow.weather);
        header.mTvTomorrowTemperatureUp.setText(home.tomorrow.temperatureMax);
        header.mTvTomorrowTemperatureUn.setText(home.tomorrow.temperatureMin);
        header.mTvTodayDate.setText(home.date);
    }

    @Override
    public void initHomeNews(List<News> homeNewsList, boolean isRefresh) {
        if (isRefresh) {
            mAdapter.clear();
        }
        mAdapter.addAll(homeNewsList);
    }


    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.requestHome(mAreaId, mLongitude + "," + mLatitude);
        mPresenter.requestHomeNews(page);
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();

        mPresenter.requestHomeNews(page);
    }

    @OnClick(R.id.tv_location)
    public void onClick() {
        CitySelectorActivity.start(getContext(), mTvLocation.getText().toString());
    }

    class HomeHeader extends BaseHeaderView {


        @BindView(R.id.menu_list)
        RecyclerView mMenuList;
        @BindView(R.id.menu_gridlist)
        RecyclerView mMenuGridlist;
        HomeMenuGridListAdapter mGridListAdapter;
        HomeMenuListAdapter mListAdapter;


        @BindView(R.id.iv_today_img)
        ImageView mIvTodayImg;
        @BindView(R.id.tv_today_mark)
        TextView mTvTodayMark;
        @BindView(R.id.tv_today_temperature)
        TextView mTvTodayTemperature;
        @BindView(R.id.tv_today_temperature_up)
        TextView mTvTodayTemperatureUp;
        @BindView(R.id.tv_today_temperature_un)
        TextView mTvTodayTemperatureUn;

        @BindView(R.id.iv_tomorrow_img)
        ImageView mIvTomorrowImg;
        @BindView(R.id.tv_tomorrow_mark)
        TextView mTvTomorrowMark;
        @BindView(R.id.tv_tomorrow_temperature_up)
        TextView mTvTomorrowTemperatureUp;
        @BindView(R.id.tv_tomorrow_temperature_un)
        TextView mTvTomorrowTemperatureUn;
        @BindView(R.id.tv_today_date)
        TextView mTvTodayDate;
        @BindView(R.id.weather_bottom)
        FrameLayout mWeatherBottom;
        @BindView(R.id.weather)
        FrameLayout mWeather;

        public HomeHeader(Context context) {
            super(context);
            mGridListAdapter = new HomeMenuGridListAdapter(context);
            mListAdapter = new HomeMenuListAdapter(context);
            mMenuList.setLayoutManager(new GridLayoutManager(context, 3) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });
            mMenuGridlist.setLayoutManager(new GridLayoutManager(context, 2) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });
            mMenuGridlist.setHasFixedSize(true);
            mMenuList.setHasFixedSize(true);
            mMenuList.setAdapter(mListAdapter);
            mMenuGridlist.setAdapter(mGridListAdapter);
            mMenuGridlist.addItemDecoration(new GridSpacingItemDecoration(2, 1, false));
            mMenuList.addItemDecoration(new GridSpacingItemDecoration(3, 1, false));

        }

        @Override
        protected int getLayoutHeaderViewId() {
            return R.layout.item_home_header;
        }

        @OnClick({R.id.weather, R.id.weather_bottom})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.weather:
                case R.id.weather_bottom:
                    WeatherActivity.start(getContext(), mAreaId, mLongitude, mLatitude);
                    break;
            }
        }
    }
}


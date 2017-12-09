package com.laoodao.smartagri.ui.home.presenter;

import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.base.RxPresenter;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.bean.weather.Weather;
import com.laoodao.smartagri.ui.home.contract.WeatherContract;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by WORK on 2017/5/2.
 */

public class WeatherPresenter extends RxPresenter<WeatherContract.WeatherView> implements WeatherContract.Presenter<WeatherContract.WeatherView> {
    ServiceManager mServiceManager;

    @Inject
    public WeatherPresenter(ServiceManager serviceManager) {
        this.mServiceManager = serviceManager;
    }

    @Override
    public void requestWeather(String city, String lon, String lat) {
        mServiceManager.getHomeService()
                .getWeather(city, lon, lat)
                .compose(Api.checkOn(mView))
                .subscribe(new Subscriber<Result<Weather>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Result<Weather> weatherResult) {
                        mView.weatherSuccess(weatherResult.data);
                    }
                });
    }
}

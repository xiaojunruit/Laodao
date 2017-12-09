package com.laoodao.smartagri.ui.home.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.bean.weather.Weather;

/**
 * Created by WORK on 2017/5/2.
 */

public interface WeatherContract {
    interface WeatherView extends BaseView {
        void weatherSuccess(Weather weather);
    }

    interface Presenter<T> extends BasePresenter<T> {
        void requestWeather(String city,String lon,String lat);
    }
}

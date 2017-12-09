package com.laoodao.smartagri.bean;

import com.google.gson.annotations.SerializedName;
import com.laoodao.smartagri.bean.weather.Weather;
import com.laoodao.smartagri.bean.weather.WeatherId;

import java.util.List;

/**
 * Created by WORK on 2017/4/28.
 */

public class Home {
    @SerializedName("t_menu")
    public List<Menu> tMenu;
    @SerializedName("s_menu")
    public List<Menu> sMenu;
    public String date;
    public HomeWeather today;
    public HomeWeather tomorrow;

    public class HomeWeather {
        public String humidity;
        public String local;
        public String temperature;
        @SerializedName("temperature_max")
        public String temperatureMax;
        @SerializedName("temperature_min")
        public String temperatureMin;
        public String weather;
        @SerializedName("weather_id")
        public WeatherId weatherId;
    }

    @Override
    public String toString() {
        return "Home{" +
                "tMenu=" + tMenu +
                ", sMenu=" + sMenu +
                ", date='" + date + '\'' +
                ", today=" + today +
                ", tomorrow=" + tomorrow +
                '}';
    }
}

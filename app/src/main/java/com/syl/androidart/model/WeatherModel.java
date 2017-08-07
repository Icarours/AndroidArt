package com.syl.androidart.model;

/**
 * Created by Bright on 2017/8/8.
 *
 * @Describe mvc中 model,请求网络数据接口
 * @Called
 */

public interface WeatherModel {
    void getWeather(String cityId, OnWeatherListener listener);
}

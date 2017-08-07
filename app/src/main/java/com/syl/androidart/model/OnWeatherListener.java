package com.syl.androidart.model;

import com.syl.androidart.bean.WeatherInfo;

/**
 * Created by Bright on 2017/8/8.
 *
 * @Describe mvc中 model
 * 得到网络数据结果接口,这个不需要用自己的,只要用okHttp3就行了
 * @Called
 */

public interface OnWeatherListener {
    void onSuccess(WeatherInfo weatherInfo);

    void onError();
}

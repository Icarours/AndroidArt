package com.syl.androidart.model;

import com.google.gson.Gson;
import com.syl.androidart.bean.WeatherInfo;
import com.syl.androidart.utils.LogUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Bright on 2017/8/8.
 *
 * @Describe mvc中 model,业务逻辑
 * @Called
 */

public class WeatherModelImpl implements WeatherModel {
    public static final String TAG = WeatherModelImpl.class.getSimpleName();

    @Override
    public void getWeather(String cityId, final OnWeatherListener listener) {
        OkHttpClient okHttpClient = new OkHttpClient();

        String url = "http://www.weather.com.cn/data/sk/" + cityId + ".html";
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.d(TAG, "WeatherModelImpl 网络请求失败");
                listener.onError();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Gson gson = new Gson();
                WeatherInfo mWeatherInfo = gson.fromJson(result, WeatherInfo.class);
                listener.onSuccess(mWeatherInfo);
            }
        });
    }
}

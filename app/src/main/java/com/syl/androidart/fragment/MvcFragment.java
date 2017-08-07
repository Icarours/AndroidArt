package com.syl.androidart.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.syl.androidart.R;
import com.syl.androidart.base.BaseFragment;
import com.syl.androidart.bean.WeatherInfo;
import com.syl.androidart.model.OnWeatherListener;
import com.syl.androidart.model.WeatherModelImpl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Bright on 2017/8/8.
 *
 * @Describe mvc中view
 * @Called
 */

public class MvcFragment extends BaseFragment implements View.OnClickListener, OnWeatherListener {

    @BindView(R.id.tv_city)
    TextView mTvCity;
    @BindView(R.id.tv_cityid)
    TextView mTvCityid;
    @BindView(R.id.tv_temp)
    TextView mTvTemp;
    @BindView(R.id.tv_WD)
    TextView mTvWD;
    @BindView(R.id.tv_WS)
    TextView mTvWS;
    @BindView(R.id.tv_SD)
    TextView mTvSD;
    @BindView(R.id.tv_WSE)
    TextView mTvWSE;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_isRadar)
    TextView mTvIsRadar;
    @BindView(R.id.tv_Radar)
    TextView mTvRadar;
    @BindView(R.id.tv_njd)
    TextView mTvNjd;
    @BindView(R.id.tv_qy)
    TextView mTvQy;
    @BindView(R.id.tv_rain)
    TextView mTvRain;
    Unbinder unbinder;
    @BindView(R.id.et_city_id)
    EditText mEtCityId;
    @BindView(R.id.btn_query)
    Button mBtnQuery;
    private View mRootView;
    private String mCityId;
    private WeatherModelImpl mWeatherModel;
    private ProgressDialog mProgressDialog;

    @Override
    public View initView() {
        return null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {//缓存mRootView,如果mRootView不为null,直接返回
            mRootView = View.inflate(getActivity(), R.layout.fragment_mvc, null);
        }
        unbinder = ButterKnife.bind(this, mRootView);

        mCityId = mEtCityId.getText().toString();
        mWeatherModel = new WeatherModelImpl();
        return mRootView;
    }

    /**
     * 刷新UI
     *
     * @param weatherinfo
     */
    private void displayResult(final WeatherInfo.WeatherinfoBean weatherinfo) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTvCity.setText("City==" + weatherinfo.getCity());
                mTvCityid.setText("Cityid==" + weatherinfo.getCityid());
                mTvIsRadar.setText("IsRadar==" + weatherinfo.getIsRadar());
                mTvNjd.setText("Njd==" + weatherinfo.getNjd());
                mTvQy.setText("Qy==" + weatherinfo.getQy());
                mTvRadar.setText("Radar==" + weatherinfo.getRadar());
                mTvRain.setText("Rain==" + weatherinfo.getRain());
                mTvSD.setText("SD==" + weatherinfo.getSD());
                mTvTemp.setText("Temp==" + weatherinfo.getTemp());
                mTvTime.setText("Time==" + weatherinfo.getTime());
                mTvWD.setText("WD==" + weatherinfo.getWD());
                mTvWS.setText("WS==" + weatherinfo.getWS());
                mTvWSE.setText("WSE==" + weatherinfo.getWSE());

                mProgressDialog.dismiss();
            }
        });
    }

    @Override
    public void initListener() {
        mBtnQuery.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_query:
                mProgressDialog = new ProgressDialog(getActivity());
                mProgressDialog.setMessage("正在加载,请稍后..");
                mProgressDialog.setTitle("加载中..");
                mProgressDialog.show();
                mWeatherModel.getWeather(mCityId, this);
                break;
            default:
                break;
        }
    }

    @Override
    public void onSuccess(WeatherInfo weatherInfo) {
        WeatherInfo.WeatherinfoBean weatherinfo = weatherInfo.getWeatherinfo();
        displayResult(weatherinfo);
    }

    @Override
    public void onError() {

    }
}

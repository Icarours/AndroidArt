package com.syl.androidart.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Bright on 2017/8/8.
 *
 * @Describe model中的bean
 * @Called
 */

public class WeatherInfo implements Parcelable {

    /**
     * weatherinfo : {"city":"深圳","cityid":"101280601","temp":"21","WD":"南风","WS":"1级","SD":"31%","WSE":"1","time":"17:05","isRadar":"1","Radar":"JC_RADAR_AZ9755_JB","njd":"暂无实况","qy":"1014","rain":"0"}
     */
    public WeatherInfo() {
    }

    private WeatherinfoBean weatherinfo;

    protected WeatherInfo(Parcel in) {
        weatherinfo = in.readParcelable(WeatherinfoBean.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(weatherinfo, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<WeatherInfo> CREATOR = new Creator<WeatherInfo>() {
        @Override
        public WeatherInfo createFromParcel(Parcel in) {
            return new WeatherInfo(in);
        }

        @Override
        public WeatherInfo[] newArray(int size) {
            return new WeatherInfo[size];
        }
    };

    public WeatherinfoBean getWeatherinfo() {
        return weatherinfo;
    }

    public void setWeatherinfo(WeatherinfoBean weatherinfo) {
        this.weatherinfo = weatherinfo;
    }

    public static class WeatherinfoBean implements Parcelable {
        /**
         * city : 深圳
         * cityid : 101280601
         * temp : 21
         * WD : 南风
         * WS : 1级
         * SD : 31%
         * WSE : 1
         * time : 17:05
         * isRadar : 1
         * Radar : JC_RADAR_AZ9755_JB
         * njd : 暂无实况
         * qy : 1014
         * rain : 0
         */

        private String city;
        private String cityid;
        private String temp;
        private String WD;
        private String WS;
        private String SD;
        private String WSE;
        private String time;
        private String isRadar;
        private String Radar;
        private String njd;
        private String qy;
        private String rain;

        protected WeatherinfoBean(Parcel in) {
            city = in.readString();
            cityid = in.readString();
            temp = in.readString();
            WD = in.readString();
            WS = in.readString();
            SD = in.readString();
            WSE = in.readString();
            time = in.readString();
            isRadar = in.readString();
            Radar = in.readString();
            njd = in.readString();
            qy = in.readString();
            rain = in.readString();
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCityid() {
            return cityid;
        }

        public void setCityid(String cityid) {
            this.cityid = cityid;
        }

        public String getTemp() {
            return temp;
        }

        public void setTemp(String temp) {
            this.temp = temp;
        }

        public String getWD() {
            return WD;
        }

        public void setWD(String WD) {
            this.WD = WD;
        }

        public String getWS() {
            return WS;
        }

        public void setWS(String WS) {
            this.WS = WS;
        }

        public String getSD() {
            return SD;
        }

        public void setSD(String SD) {
            this.SD = SD;
        }

        public String getWSE() {
            return WSE;
        }

        public void setWSE(String WSE) {
            this.WSE = WSE;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getIsRadar() {
            return isRadar;
        }

        public void setIsRadar(String isRadar) {
            this.isRadar = isRadar;
        }

        public String getRadar() {
            return Radar;
        }

        public void setRadar(String Radar) {
            this.Radar = Radar;
        }

        public String getNjd() {
            return njd;
        }

        public void setNjd(String njd) {
            this.njd = njd;
        }

        public String getQy() {
            return qy;
        }

        public void setQy(String qy) {
            this.qy = qy;
        }

        public String getRain() {
            return rain;
        }

        public void setRain(String rain) {
            this.rain = rain;
        }

        public static final Creator<WeatherinfoBean> CREATOR = new Creator<WeatherinfoBean>() {
            @Override
            public WeatherinfoBean createFromParcel(Parcel in) {
                return new WeatherinfoBean(in);
            }

            @Override
            public WeatherinfoBean[] newArray(int size) {
                return new WeatherinfoBean[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(city);
            dest.writeString(cityid);
            dest.writeString(temp);
            dest.writeString(WD);
            dest.writeString(WS);
            dest.writeString(SD);
            dest.writeString(WSE);
            dest.writeString(time);
            dest.writeString(isRadar);
            dest.writeString(Radar);
            dest.writeString(njd);
            dest.writeString(qy);
            dest.writeString(rain);
        }
    }
}

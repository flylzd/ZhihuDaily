package com.lizeda.daily;

import java.io.Serializable;

/**
 * 作者：lemon
 * 日期：2015-09-08
 */
public class WeatherInfo implements Serializable {

    public String city;
    public String cityid;
    public String temp;
    public String WD;
    public String WS;
    public String SD;
    public String WSE;
    public String time;
    public String isRadar;
    public String Radar;


    @Override
    public String toString() {
        return "WeatherInfo{" +
                "city='" + city + '\'' +
                ", cityid='" + cityid + '\'' +
                ", temp='" + temp + '\'' +
                ", WD='" + WD + '\'' +
                ", WS='" + WS + '\'' +
                ", SD='" + SD + '\'' +
                ", WSE='" + WSE + '\'' +
                ", time='" + time + '\'' +
                ", isRadar='" + isRadar + '\'' +
                ", Radar='" + Radar + '\'' +
                '}';
    }


}

package com.lizeda.daily;

import java.io.Serializable;

/**
 * 作者：lemon
 * 日期：2015-09-08
 */
public class Weather implements Serializable {

    public WeatherInfo weatherinfo;

    @Override
    public String toString() {
        return "Weather{" +
                "weatherinfo='" + weatherinfo.toString() +
                '}';
    }
}

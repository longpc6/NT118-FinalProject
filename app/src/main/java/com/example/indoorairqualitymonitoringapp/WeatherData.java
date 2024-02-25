package com.example.indoorairqualitymonitoringapp;

import com.google.gson.annotations.SerializedName;

public class WeatherData {
    @SerializedName("main")
    private MainInfo mainInfo;
    @SerializedName("dt")
    private long dateTime;  // Adding dt attribute



    public double getTemperature() {
        return mainInfo != null ? mainInfo.getTemperature() : 0.0;
    }

    public int getHumidity() {
        return mainInfo != null ? mainInfo.getHumidity() : 0;
    }
    public long getDateTime() {
        return dateTime;
    }


}
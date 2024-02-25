package com.example.indoorairqualitymonitoringapp;

import com.google.gson.annotations.SerializedName;

public class MainInfo {

    @SerializedName("temp")
    private double temperature;

    @SerializedName("humidity")
    private int humidity;


    public double getTemperature() {
        return temperature;
    }

    public int getHumidity() {
        return humidity;
    }
}

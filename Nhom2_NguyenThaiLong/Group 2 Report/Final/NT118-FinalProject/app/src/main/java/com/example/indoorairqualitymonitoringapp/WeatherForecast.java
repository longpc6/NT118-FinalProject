package com.example.indoorairqualitymonitoringapp;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherForecast {
    @SerializedName("list")
    private List<WeatherData> weatherDataList;

    public List<WeatherData> getWeatherDataList() {
        return weatherDataList;
    }
}
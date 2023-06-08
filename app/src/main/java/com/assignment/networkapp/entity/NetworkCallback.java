package com.assignment.networkapp.entity;

import com.assignment.networkapp.entity.WeatherForecast;

public interface NetworkCallback {
    void onDataLoaded(WeatherForecast data);

    void onDataLoadFailed();
}


package com.assignment.networkapp.entity;

import android.os.AsyncTask;
import android.util.Log;

import com.assignment.networkapp.service.WeatherService;

import java.io.IOException;

import okhttp3.Response;

public class NetworkTask extends AsyncTask<Void, Void, WeatherForecast> {
    private static final String TAG = NetworkTask.class.getSimpleName();

    private final WeatherService weatherService;
    private final NetworkCallback callback;
    private final double latitude;
    private final double longitude;

    public NetworkTask(NetworkCallback callback, double latitude, double longitude) {
        weatherService = new WeatherService();
        this.callback = callback;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    protected WeatherForecast doInBackground(Void... voids) {
        try {
            Response response = weatherService.fetchWeatherForecast(latitude, longitude);
            if (response.isSuccessful()) {
                String responseData = response.body().string();
                WeatherForecast forecast = weatherService.parseWeatherForecast(responseData);
                return forecast;
            } else {
                Log.e(TAG, "Unsuccessful response: " + response.code());
            }
        } catch (IOException e) {
            Log.e(TAG, "Network failure: " + e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(WeatherForecast forecast) {
        if (forecast != null) {
            callback.onDataLoaded(forecast);
        } else {
            callback.onDataLoadFailed();
        }
    }
}

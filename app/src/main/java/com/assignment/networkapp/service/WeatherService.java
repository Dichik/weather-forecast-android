package com.assignment.networkapp.service;

import com.assignment.networkapp.entity.WeatherForecast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherService {
    private static final String API_KEY = "68b90f1a5d1ab11e8815b02923f7c5dc";
    private final OkHttpClient httpClient;

    public WeatherService() {
        httpClient = new OkHttpClient();
    }

    public Response fetchWeatherForecast(double latitude, double longitude) throws IOException {
        String apiUrl = buildApiUrl(latitude, longitude);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(apiUrl)
                .build();

        return client.newCall(request).execute();
    }

    private String buildApiUrl(double latitude, double longitude) {
        return "https://api.openweathermap.org/data/2.5/weather?" +
                "lat=" + latitude +
                "&lon=" + longitude +
                "&appid=" + API_KEY +
                "&units=metric";
    }

    public WeatherForecast parseWeatherForecast(String responseData) {
        try {
            JSONObject responseJson = new JSONObject(responseData);

            String cityName = responseJson.getString("name");

            JSONObject mainJson = responseJson.getJSONObject("main");
            double temperature = mainJson.getDouble("temp");

            return new WeatherForecast(cityName, temperature);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}

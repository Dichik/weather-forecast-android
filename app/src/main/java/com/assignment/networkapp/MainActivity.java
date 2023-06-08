package com.assignment.networkapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.assignment.networkapp.entity.NetworkCallback;
import com.assignment.networkapp.entity.NetworkTask;
import com.assignment.networkapp.entity.WeatherForecast;
import com.assignment.networkapp.utils.NetworkUtils;

// lat=50.450001&lon=30.523333

public class MainActivity extends AppCompatActivity implements NetworkCallback {

    private EditText editLatitude;
    private EditText editLongitude;
    private Button btnGetForecast;
    private TextView textWeatherForecast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editLatitude = findViewById(R.id.edit_latitude);
        editLongitude = findViewById(R.id.edit_longitude);
        btnGetForecast = findViewById(R.id.btn_get_forecast);
        textWeatherForecast = findViewById(R.id.text_weather_forecast);

        btnGetForecast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double latitude = Double.parseDouble(editLatitude.getText().toString());
                double longitude = Double.parseDouble(editLongitude.getText().toString());

                if (NetworkUtils.isNetworkAvailable(MainActivity.this)) {
                    NetworkTask networkTask = new NetworkTask(MainActivity.this, latitude, longitude);
                    networkTask.execute();
                } else {
                    // Network is not available, display an error message
                    Toast.makeText(MainActivity.this, "No network connection available", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onDataLoaded(WeatherForecast data) {
        String forecastText = "City: " + data.getCityName() + "\nTemperature: " + data.getTemperature() + "°C";
        textWeatherForecast.setText(forecastText);
    }

    @Override
    public void onDataLoadFailed() {
        textWeatherForecast.setText("Failed to load weather forecast.");
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openweatherdesktop;

//package com.example.weatherapp;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class WeatherController {

//    private static final String API_KEY = "YOUR_API_KEY";
    private static final String API_KEY = "edefc762f4743a1c4daff794753cf258";
    private static final String API_URL = "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric";

    @FXML
    private TextField cityField;

    @FXML
    private Label tempLabel;

    @FXML
    private Label descLabel;

    @FXML
    private Label errorLabel;

    @FXML
    private void handleFetchWeather() {
        String cityName = cityField.getText();

        try {
            String apiUrl = String.format(API_URL, cityName, API_KEY);
            URL url = new URL(apiUrl);
            URLConnection conn = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            JSONObject json = new JSONObject(response.toString());
            JSONObject main = json.getJSONObject("main");
            double temp = main.getDouble("temp");
            String desc = json.getJSONArray("weather").getJSONObject(0).getString("description");

            tempLabel.setText(String.format("%.1f\u00B0C", temp));
            descLabel.setText(desc);
            errorLabel.setText("");
        } catch (Exception e) {
            tempLabel.setText("");
            descLabel.setText("");
            errorLabel.setText("Error fetching weather data");
            e.printStackTrace();
        }
    }

}



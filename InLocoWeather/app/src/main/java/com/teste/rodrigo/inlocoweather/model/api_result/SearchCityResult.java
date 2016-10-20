package com.teste.rodrigo.inlocoweather.model.api_result;

import com.teste.rodrigo.inlocoweather.model.CityWeather;

import java.util.ArrayList;
import java.util.List;

public class SearchCityResult {

    private String message;
    private String cod;
    private List<CityWeather> cityWeatherList;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public void addCity(CityWeather cityWeather) {
        if (cityWeatherList == null)
            cityWeatherList = new ArrayList<>();

        cityWeatherList.add(cityWeather);
    }

    public List<CityWeather> getCityWeatherList() {
        return cityWeatherList;
    }
}

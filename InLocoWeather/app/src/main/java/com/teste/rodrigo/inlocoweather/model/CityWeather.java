package com.teste.rodrigo.inlocoweather.model;

public class CityWeather {

    private String cityName;
    private double minimumTemperature, maximumTemperature;
    private String weatherDescription;

    public CityWeather(String cityName, double tempMin, double tempMax, String weatherDescription) {
        this.cityName = cityName;
        this.minimumTemperature = tempMin;
        this.maximumTemperature = tempMax;
        this.weatherDescription = weatherDescription;
    }

    public String getCityName() {
        return cityName;
    }

    public double getMaximumTemperature() {
        return maximumTemperature;
    }

    public double getMinimumTemperature() {
        return minimumTemperature;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }
}

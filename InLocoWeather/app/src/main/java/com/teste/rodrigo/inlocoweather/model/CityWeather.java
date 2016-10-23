package com.teste.rodrigo.inlocoweather.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CityWeather implements Parcelable {

    private String cityName;
    private double minimumTemperature, maximumTemperature;
    private String weatherDescription;

    public CityWeather(String cityName, double tempMin, double tempMax, String weatherDescription) {
        this.cityName = cityName;
        this.minimumTemperature = tempMin;
        this.maximumTemperature = tempMax;
        this.weatherDescription = weatherDescription;
    }

    @Override
    public String toString() {
        return getCityName();
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

    ///////////////////////PARCELABLE CONTENT///////////////////////////

    public CityWeather(Parcel in){
        this.cityName = in.readString();
        this.minimumTemperature = in.readDouble();
        this.maximumTemperature = in.readDouble();
        this.weatherDescription = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.cityName);
        dest.writeDouble(this.minimumTemperature);
        dest.writeDouble(this.maximumTemperature);
        dest.writeString(this.weatherDescription);
    }

    public static final Parcelable.Creator<CityWeather> CREATOR = new Parcelable.Creator<CityWeather>() {
        @Override
        public CityWeather createFromParcel(Parcel in) {
            return new CityWeather(in);
        }
        @Override
        public CityWeather[] newArray(int size) {
            return new CityWeather[size];
        }
    };
}

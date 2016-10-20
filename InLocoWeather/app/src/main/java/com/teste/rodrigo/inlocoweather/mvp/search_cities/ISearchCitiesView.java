package com.teste.rodrigo.inlocoweather.mvp.search_cities;

import com.teste.rodrigo.inlocoweather.model.CityWeather;

import java.util.List;

public interface ISearchCitiesView {
    void onCitiesLoaded(List<CityWeather> cities);
    void onCitiesError();
}

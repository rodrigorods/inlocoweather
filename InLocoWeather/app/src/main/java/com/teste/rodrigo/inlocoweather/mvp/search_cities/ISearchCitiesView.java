package com.teste.rodrigo.inlocoweather.mvp.search_cities;

import com.teste.rodrigo.inlocoweather.model.CityWeather;
import com.teste.rodrigo.inlocoweather.model.GeocodedAddress;

import java.util.List;

public interface ISearchCitiesView {
    void onCitiesLoadedByTerm(List<GeocodedAddress> addresses);
    void onCitiesLoaded(List<CityWeather> cities);
    void onCitiesLoadError();
}

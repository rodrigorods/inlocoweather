package com.teste.rodrigo.inlocoweather.mvp.search_cities;

import com.google.android.gms.maps.model.LatLng;

public interface ISearchCitiesPresenter {
    void searchSurroundingCities(LatLng coords);
    void searchCityByTerm(String query);
}

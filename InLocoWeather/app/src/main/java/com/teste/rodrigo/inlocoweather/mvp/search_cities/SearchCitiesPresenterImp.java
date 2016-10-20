package com.teste.rodrigo.inlocoweather.mvp.search_cities;

import android.content.res.Resources;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.teste.rodrigo.inlocoweather.R;
import com.teste.rodrigo.inlocoweather.model.api_result.SearchCityResult;
import com.teste.rodrigo.inlocoweather.network.WeatherApiBuilder;
import com.teste.rodrigo.inlocoweather.network.api.SearchCitiesApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchCitiesPresenterImp implements ISearchCitiesPresenter{

    private static final int CITIES_COUNT = 15;

    private SearchCitiesApi mSearchApi;
    private ISearchCitiesView mView;
    private Resources mResources;

    public SearchCitiesPresenterImp(ISearchCitiesView mView, Resources res){
        this.mView = mView;
        this.mResources = res;
        this.mSearchApi = WeatherApiBuilder.build(  SearchCitiesApi.class,
                                                    WeatherApiBuilder.GSON_SEARCH_CITY_CONVERTER,
                                                    res);
    }

    @Override
    public void searchSurroundingCities(LatLng coords) {
        mSearchApi.searchCitiesForLocation( coords.latitude,
                                            coords.longitude,
                                            CITIES_COUNT,
                                            mResources.getString(R.string.weather_api_key))
                .enqueue(new Callback<SearchCityResult>() {
                    @Override
                    public void onResponse(Call<SearchCityResult> call, Response<SearchCityResult> response) {
                        if (response.isSuccessful()) {
                            mView.onCitiesLoaded(response.body().getCityWeatherList());
                        } else {
                            mView.onCitiesError();
                        }
                    }

                    @Override
                    public void onFailure(Call<SearchCityResult> call, Throwable t) {
                        mView.onCitiesError();
                    }
                });
    }
}

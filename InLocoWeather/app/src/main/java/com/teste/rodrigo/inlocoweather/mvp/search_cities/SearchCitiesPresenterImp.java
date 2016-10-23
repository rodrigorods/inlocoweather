package com.teste.rodrigo.inlocoweather.mvp.search_cities;

import android.content.Context;
import android.content.res.Resources;

import com.google.android.gms.maps.model.LatLng;
import com.teste.rodrigo.inlocoweather.R;
import com.teste.rodrigo.inlocoweather.model.CityWeather;
import com.teste.rodrigo.inlocoweather.model.GeocodedAddress;
import com.teste.rodrigo.inlocoweather.model.api_result.ApiListResult;
import com.teste.rodrigo.inlocoweather.network.RetrofitApiBuilder;
import com.teste.rodrigo.inlocoweather.network.api.GeocodingApi;
import com.teste.rodrigo.inlocoweather.network.api.SearchCitiesApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchCitiesPresenterImp implements ISearchCitiesPresenter{

    private static final int CITIES_COUNT = 15;
    private static final String CELSIUS_METRIC = "metric";

    private SearchCitiesApi mSearchApi;
    private GeocodingApi mGeocodingApi;
    private ISearchCitiesView mView;
    private Resources mResources;

    public SearchCitiesPresenterImp(ISearchCitiesView mView, Context ctx){
        this.mView = mView;
        this.mResources = ctx.getResources();
        this.mSearchApi = RetrofitApiBuilder.build(  SearchCitiesApi.class,
                                                    RetrofitApiBuilder.GSON_SEARCH_CITY_CONVERTER,
                                                    ctx.getResources().getString(R.string.weather_base_url) );

        this.mGeocodingApi = RetrofitApiBuilder.build(   GeocodingApi.class,
                                                        RetrofitApiBuilder.GSON_GEOCODING_CONVERTER,
                                                        ctx.getResources().getString(R.string.geocoding_base_url) );

    }

    @Override
    public void searchSurroundingCities(LatLng coords) {
        mSearchApi.searchCitiesForLocation( coords.latitude,
                                            coords.longitude,
                                            CITIES_COUNT,
                                            CELSIUS_METRIC,
                                            mResources.getString(R.string.weather_api_key))
                .enqueue(new Callback<ApiListResult<CityWeather>>() {
                    @Override
                    public void onResponse(Call<ApiListResult<CityWeather>> call, Response<ApiListResult<CityWeather>> response) {
                        if (response.isSuccessful()) {
                            mView.onCitiesLoaded(response.body().getListResponse());
                        } else {
                            mView.onCitiesLoadError();
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiListResult<CityWeather>> call, Throwable t) {
                        mView.onCitiesLoadError();
                    }
                });
    }

    @Override
    public void searchCityByTerm(String query) {
        mGeocodingApi.searchCitiesForAddress(query, mResources.getString(R.string.google_geocoding_key)).enqueue(new Callback<ApiListResult<GeocodedAddress>>() {
            @Override
            public void onResponse(Call<ApiListResult<GeocodedAddress>> call, Response<ApiListResult<GeocodedAddress>> response) {
                if (response.isSuccessful()) {
                    mView.onCitiesLoadedByTerm(response.body().getListResponse());
                } else {
                    mView.onCitiesLoadError();
                }
            }

            @Override
            public void onFailure(Call<ApiListResult<GeocodedAddress>> call, Throwable t) {
                mView.onCitiesLoadError();
            }
        });
    }
}

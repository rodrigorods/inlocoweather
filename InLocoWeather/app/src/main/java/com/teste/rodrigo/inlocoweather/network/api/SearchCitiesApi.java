package com.teste.rodrigo.inlocoweather.network.api;

import com.teste.rodrigo.inlocoweather.model.api_result.SearchCityResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SearchCitiesApi {
    @GET("/data/2.5/find")
    Call<SearchCityResult> searchCitiesForLocation(@Query("lat") double latitude,
                                                   @Query("lon") double longitude,
                                                   @Query("cnt") int count,
                                                   @Query("APPID") String apiKey);
}

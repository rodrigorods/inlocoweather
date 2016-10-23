package com.teste.rodrigo.inlocoweather.network.api;

import com.teste.rodrigo.inlocoweather.model.GeocodedAddress;
import com.teste.rodrigo.inlocoweather.model.api_result.ApiListResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GeocodingApi {
    @GET("/maps/api/geocode/json")
    Call<ApiListResult<GeocodedAddress>> searchCitiesForAddress(@Query("address") String address,
                                                                @Query("key") String appKey);
}

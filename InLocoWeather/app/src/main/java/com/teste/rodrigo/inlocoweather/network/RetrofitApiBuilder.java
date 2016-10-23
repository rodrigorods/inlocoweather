package com.teste.rodrigo.inlocoweather.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.teste.rodrigo.inlocoweather.BuildConfig;
import com.teste.rodrigo.inlocoweather.model.api_result.ApiListResult;
import com.teste.rodrigo.inlocoweather.network.deserializer.GeocodingDeserializer;
import com.teste.rodrigo.inlocoweather.network.deserializer.SearchCityDeserializer;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitApiBuilder {

    public static final int GSON_GEOCODING_CONVERTER = 2;
    public static final int GSON_SEARCH_CITY_CONVERTER = 1;
    public static final int GSON_DEFAULT_CONVERTER = 0;

    public static <T> T build(Class<T> instanceName, String baseUrl) {
        return build(instanceName, GSON_DEFAULT_CONVERTER, baseUrl);
    }

    public static <T> T build(Class<T> instanceName, int gsonConverterType, String baseUrl) {
        Retrofit.Builder builder = new Retrofit.Builder();

        builder.baseUrl(baseUrl);
        builder.client(getLogLevelClient());

        builder.addConverterFactory(getGsonConverterFactory(gsonConverterType));

        return builder.build().create(instanceName);
    }

    private static OkHttpClient getLogLevelClient() {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            clientBuilder.addInterceptor(interceptor);
        }

        return clientBuilder.build();
    }

    private static Converter.Factory getGsonConverterFactory(int converterType){
        if (converterType == GSON_SEARCH_CITY_CONVERTER) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(ApiListResult.class,
                            new SearchCityDeserializer()).create();

            return GsonConverterFactory.create(gson);
        } else if (converterType == GSON_GEOCODING_CONVERTER){
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(ApiListResult.class,
                            new GeocodingDeserializer()).create();

            return GsonConverterFactory.create(gson);
        } else {
            return GsonConverterFactory.create();
        }
    }
}

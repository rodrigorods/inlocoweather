package com.teste.rodrigo.inlocoweather.network.deserializer;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.teste.rodrigo.inlocoweather.model.CityWeather;
import com.teste.rodrigo.inlocoweather.model.api_result.ApiListResult;

import java.lang.reflect.Type;

public class SearchCityDeserializer implements JsonDeserializer<ApiListResult>
{
    @Override
    public ApiListResult deserialize(JsonElement je, Type type, JsonDeserializationContext jdc)
            throws JsonParseException
    {
        ApiListResult<CityWeather> result = new ApiListResult<>();

        JsonArray jsonArray = je.getAsJsonObject().getAsJsonArray("list");

        for (int i = 0; i<jsonArray.size(); i++){
            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();

            String cityName = jsonObject.get("name").getAsString();

            JsonObject jsonTemperatureObj = jsonObject.get("main").getAsJsonObject();
            double tempMin = jsonTemperatureObj.get("temp_min").getAsDouble();
            double tempMax = jsonTemperatureObj.get("temp_max").getAsDouble();

            JsonArray jsonWeatherArray = jsonObject.get("weather").getAsJsonArray();
            String description = assembleWeatherDescription(jsonWeatherArray);

            result.addModel(new CityWeather(cityName, tempMin, tempMax, description));
        }

        return result;
    }

    private String assembleWeatherDescription(JsonArray jsonWeatherArray){
        StringBuilder descriptionBuilder = new StringBuilder();
        for (int i = 0; i<jsonWeatherArray.size(); i++){
            JsonObject weatherRootObj = jsonWeatherArray.get(i).getAsJsonObject();
            String weatherDescription = weatherRootObj.get("description").getAsString();

            if (!descriptionBuilder.toString().isEmpty())
                descriptionBuilder.append(",");

            descriptionBuilder.append(weatherDescription);
        }

        return descriptionBuilder.toString();
    }
}
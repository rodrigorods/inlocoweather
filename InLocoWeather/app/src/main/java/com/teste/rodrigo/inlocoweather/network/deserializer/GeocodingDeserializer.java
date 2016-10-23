package com.teste.rodrigo.inlocoweather.network.deserializer;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.teste.rodrigo.inlocoweather.model.GeocodedAddress;
import com.teste.rodrigo.inlocoweather.model.api_result.ApiListResult;

import java.lang.reflect.Type;

public class GeocodingDeserializer implements JsonDeserializer<ApiListResult>
{
    @Override
    public ApiListResult deserialize(JsonElement je, Type type, JsonDeserializationContext jdc)
            throws JsonParseException
    {
        ApiListResult<GeocodedAddress> result = new ApiListResult<>();

        JsonArray jsonArray = je.getAsJsonObject().getAsJsonArray("results");

        for (int i = 0; i<jsonArray.size(); i++){
            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();

            String formattedAddress = jsonObject.get("formatted_address").getAsString();

            JsonObject jsonTemperatureObj = jsonObject.get("geometry").getAsJsonObject().get("location").getAsJsonObject();
            double lat = jsonTemperatureObj.get("lat").getAsDouble();
            double lng = jsonTemperatureObj.get("lng").getAsDouble();

            result.addModel(new GeocodedAddress(formattedAddress, lat, lng));
        }

        return result;
    }

}
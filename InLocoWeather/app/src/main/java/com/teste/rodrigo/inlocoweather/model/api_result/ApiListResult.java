package com.teste.rodrigo.inlocoweather.model.api_result;

import java.util.ArrayList;
import java.util.List;

public class ApiListResult<T> {

    private List<T> apiList;

    public void addModel(T cityWeather) {
        if (apiList == null)
            apiList = new ArrayList<>();

        apiList.add(cityWeather);
    }

    public List<T> getListResponse() {
        return apiList;
    }
}

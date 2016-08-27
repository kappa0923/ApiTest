package com.kappa0923.android.app.apitest.network;

import com.kappa0923.android.app.apitest.entities.WeatherEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * お天気APIへのRESTインタフェース
 *
 */
public interface WeatherApi {
    @GET("/forecast/webservice/json/v1")
    public Call<WeatherEntity> getWeather(@Query("city") final String city);
}

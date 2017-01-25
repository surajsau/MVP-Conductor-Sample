package com.halfplatepoha.mvpconductor.data.network;

import com.halfplatepoha.mvpconductor.data.CityWeather;
import com.halfplatepoha.mvpconductor.data.DailyForecast;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by surajkumarsau on 26/01/17.
 */

public interface WeatherClient {

    @GET("weather")
    Observable<CityWeather> getCityWeather(@Query("id") int cityId);

    @GET("forecast/daily")
    Observable<DailyForecast> getCityDailyForecast(@Query("id") int cityId, @Query("cnt") int numberOfDays);

}

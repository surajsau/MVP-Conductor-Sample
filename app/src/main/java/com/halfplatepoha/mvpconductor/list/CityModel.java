package com.halfplatepoha.mvpconductor.list;

/**
 * Created by surajkumarsau on 26/01/17.
 */

public class CityModel {
    private final int cityId;
    private final String cityName;
    private final float temp;
    private final String weather;
    private final String weatherDescription;

    public CityModel(int cityId, String cityName, float temp, String weather, String weatherDescription ) {
        this.cityId = cityId;
        this.cityName = cityName;
        this.temp = temp;
        this.weather = weather;
        this.weatherDescription = weatherDescription;
    }

    public int getCityId() {
        return cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public float getTemp() {
        return temp;
    }

    public String getWeather() {
        return weather;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }
}

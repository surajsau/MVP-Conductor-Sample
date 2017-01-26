package com.halfplatepoha.mvpconductor.city;

/**
 * Created by surajkumarsau on 26/01/17.
 */

public class WeatherModel {

    private final String mainWeather;
    private final String weatherDescription;
    private final float temp;
    private final float minTemp;
    private final float maxTemp;
    private final int humidity;

    public WeatherModel(String mainWeather, String weatherDescription, float temp, float minTemp, float maxTemp, int humidity) {
        this.mainWeather = mainWeather;
        this.weatherDescription = weatherDescription;
        this.temp = temp;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.humidity = humidity;
    }

    public String getMainWeather() {
        return mainWeather;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public float getTemp() {
        return temp;
    }

    public float getMinTemp() {
        return minTemp;
    }

    public float getMaxTemp() {
        return maxTemp;
    }

    public int getHumidity() {
        return humidity;
    }
}

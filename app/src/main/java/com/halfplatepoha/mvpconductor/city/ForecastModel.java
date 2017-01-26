package com.halfplatepoha.mvpconductor.city;

/**
 * Created by surajkumarsau on 26/01/17.
 */

public class ForecastModel {
    private final long date;
    private final float dayTemp;
    private final float minTemp;
    private final float maxTemp;
    private final int humidity;
    private final String weatherDescription;
    private final String mainWeather;

    public ForecastModel(long date, float dayTemp, float minTemp, float maxTemp, int humidity, String weatherDescription, String mainWeather) {
        this.date = date;
        this.dayTemp = dayTemp;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.humidity = humidity;
        this.weatherDescription = weatherDescription;
        this.mainWeather = mainWeather;
    }

    public long getDate() {
        return date;
    }

    public float getDayTemp() {
        return dayTemp;
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

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public String getMainWeather() {
        return mainWeather;
    }
}

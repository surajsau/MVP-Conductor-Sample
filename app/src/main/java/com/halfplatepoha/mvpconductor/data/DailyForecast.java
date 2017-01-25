package com.halfplatepoha.mvpconductor.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by surajkumarsau on 26/01/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class DailyForecast implements Serializable {

    private int cnt;
    private ArrayList<Daily> list;
    private City city;

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public ArrayList<Daily> getList() {
        return list;
    }

    public void setList(ArrayList<Daily> list) {
        this.list = list;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class City implements Serializable {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Daily implements Serializable{
        private long dt;
        private Temp temp;
        private int humidity;
        private ArrayList<Weather> weather;

        public long getDt() {
            return dt;
        }

        public void setDt(long dt) {
            this.dt = dt;
        }

        public Temp getTemp() {
            return temp;
        }

        public void setTemp(Temp temp) {
            this.temp = temp;
        }

        public int getHumidity() {
            return humidity;
        }

        public void setHumidity(int humidity) {
            this.humidity = humidity;
        }

        public ArrayList<Weather> getWeather() {
            return weather;
        }

        public void setWeather(ArrayList<Weather> weather) {
            this.weather = weather;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Temp implements Serializable {
            private float day;
            private float min;
            private float max;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Weather implements Serializable {
            private String main;
            private String description;

            public String getMain() {
                return main;
            }

            public void setMain(String main) {
                this.main = main;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }
        }
    }
}

package com.halfplatepoha.mvpconductor.search;

/**
 * Created by surajkumarsau on 26/01/17.
 */

public class SearchModel {
    private final int cityId;
    private final String cityName;

    public SearchModel(int cityId, String cityName) {
        this.cityId = cityId;
        this.cityName = cityName;
    }

    public int getCityId() {
        return cityId;
    }

    public String getCityName() {
        return cityName;
    }
}

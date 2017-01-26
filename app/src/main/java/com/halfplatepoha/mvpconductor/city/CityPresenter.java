package com.halfplatepoha.mvpconductor.city;

import com.halfplatepoha.mvpconductor.BasePresenter;

/**
 * Created by surajkumarsau on 26/01/17.
 */

public interface CityPresenter extends BasePresenter {
    void getForecast();
    void getWeather();
    void setNumberOfDays(int numberOfDays);

    void onRefresh();
}

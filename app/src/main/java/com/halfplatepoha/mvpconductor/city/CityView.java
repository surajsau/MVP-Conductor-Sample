package com.halfplatepoha.mvpconductor.city;

import com.halfplatepoha.mvpconductor.BaseView;

/**
 * Created by surajkumarsau on 25/01/17.
 */

public interface CityView extends BaseView<CityPresenterImpl> {
    void updateWeatherPredictionsList(ForecastModel model);
    void showCurrentWeather(WeatherModel model);

    void clearForecastList();

    void showRefreshLayout();
    void hideRefreshLayout();

    void stopRefreshingAnimation();

    void hideFilterMenuHelpText();
    void makeRefreshButtonToRetryButton();

    void hideButtonLayout();
    void showButtonLayout();
}

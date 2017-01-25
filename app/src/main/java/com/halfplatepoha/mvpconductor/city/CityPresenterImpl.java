package com.halfplatepoha.mvpconductor.city;

import com.halfplatepoha.mvpconductor.BasePresenter;
import com.halfplatepoha.mvpconductor.data.network.ServiceGenerator;
import com.halfplatepoha.mvpconductor.data.network.WeatherClient;

/**
 * Created by surajkumarsau on 25/01/17.
 */

public class CityPresenterImpl implements BasePresenter {

    private WeatherClient mClient;

    @Override
    public void start() {
        mClient = ServiceGenerator.createService(WeatherClient.class);
    }



}

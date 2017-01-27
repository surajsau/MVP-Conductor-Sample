package com.halfplatepoha.mvpconductor.list;

import com.halfplatepoha.mvpconductor.BasePresenter;

/**
 * Created by surajkumarsau on 26/01/17.
 */

public interface CityListPresenter extends BasePresenter {
    void onRefresh();

    void getWeathersOfCity(int cityId);
}

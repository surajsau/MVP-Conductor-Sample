package com.halfplatepoha.mvpconductor.list;

/**
 * Created by surajkumarsau on 26/01/17.
 */

public interface CityListView {
    void hideRefreshLayout();
    void showRefreshLayout();

    void addCity(CityModel city);

    void hideHelpText();

    boolean isActive();
}

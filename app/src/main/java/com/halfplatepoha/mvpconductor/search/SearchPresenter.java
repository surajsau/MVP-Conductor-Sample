package com.halfplatepoha.mvpconductor.search;

import com.halfplatepoha.mvpconductor.BasePresenter;

/**
 * Created by surajkumarsau on 26/01/17.
 */

public interface SearchPresenter extends BasePresenter {
    void search(String searchQuery);

    void updateCityAsSelected(int cityId);
}

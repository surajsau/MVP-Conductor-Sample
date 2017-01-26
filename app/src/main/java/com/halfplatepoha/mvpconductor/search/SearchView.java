package com.halfplatepoha.mvpconductor.search;

import java.util.ArrayList;

/**
 * Created by surajkumarsau on 26/01/17.
 */

public interface SearchView {
    void refreshList(ArrayList<SearchModel> searchResults);

    void setResultAndGoBack(int cityId);
}

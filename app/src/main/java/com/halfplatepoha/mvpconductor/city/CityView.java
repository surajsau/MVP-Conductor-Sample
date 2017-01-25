package com.halfplatepoha.mvpconductor.city;

import com.halfplatepoha.mvpconductor.BaseView;

/**
 * Created by surajkumarsau on 25/01/17.
 */

public interface CityView extends BaseView<CityPresenterImpl> {
    void showLoadingIndicator();
    void hideLoadingIndicator();

    void showTasks();
    void showNoTasks();
    void showActiveTasks();
    void showCompletedTasks();
    void showNoActiveTasks();
    void showNoCompletedTasks();

    void showAddTask();

    void showTaskDetails(String taskId);

    void showFilter();
    void markActiveFilter();
    void markCompletedFilter();
    void markAllFilter();

    void showSuccessfullySavedMessage();
    void showLoadingError();
}

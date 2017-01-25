package com.halfplatepoha.mvpconductor.city;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.halfplatepoha.mvpconductor.BaseController;

/**
 * Created by surajkumarsau on 25/01/17.
 */

public class CityController extends BaseController implements CityView {

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return null;
    }

    @Override
    public void showLoadingIndicator() {

    }

    @Override
    public void hideLoadingIndicator() {

    }

    @Override
    public void showTasks() {

    }

    @Override
    public void showAddTask() {

    }

    @Override
    public void showTaskDetails(String taskId) {

    }

    @Override
    public void showFilter() {

    }

    @Override
    public void showNoTasks() {

    }

    @Override
    public void showActiveTasks() {

    }

    @Override
    public void showCompletedTasks() {

    }

    @Override
    public void markActiveFilter() {

    }

    @Override
    public void markCompletedFilter() {

    }

    @Override
    public void markAllFilter() {

    }

    @Override
    public void showNoActiveTasks() {

    }

    @Override
    public void showNoCompletedTasks() {

    }

    @Override
    public void showSuccessfullySavedMessage() {

    }

    @Override
    public void showLoadingError() {

    }
}

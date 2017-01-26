package com.halfplatepoha.mvpconductor.city;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.halfplatepoha.mvpconductor.BaseController;
import com.halfplatepoha.mvpconductor.IConstants;
import com.halfplatepoha.mvpconductor.R;

/**
 * Created by surajkumarsau on 25/01/17.
 */

public class CityController extends BaseController implements CityView, SwipeRefreshLayout.OnRefreshListener,
        View.OnClickListener{

    private CityPresenter presenter;

    private RecyclerView rlForecasts;
    private SwipeRefreshLayout refreshLayout;
    private ForecastAdapter mAdapter;

    private ButtonState buttonState;
    private Button btnRefresh;
    private TextView tvFitlerMenuHelpText;

    private View llRefresh;

    private int cityId;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRefresh:
                presenter.getForecast();
        }
    }

    public CityController(Bundle args) {
        super(args);
        getDataFromBundle();
    }

    private enum ButtonState {
        BUTTON_REFRESH, BUTTON_RETRY
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        View view = inflater.inflate(R.layout.layout_city, container, false);
        return view;
    }

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);
        presenter = new CityPresenterImpl(this, cityId);

        getDataFromBundle();

        buttonState = ButtonState.BUTTON_REFRESH;

        rlForecasts = (RecyclerView) view.findViewById(R.id.rlForecasts);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        btnRefresh = (Button) view.findViewById(R.id.btnRefresh);
        tvFitlerMenuHelpText = (TextView) view.findViewById(R.id.tvChooseFilterHelpText);
        llRefresh =  view.findViewById(R.id.llRefresh);

        refreshLayout.setOnRefreshListener(this);
        btnRefresh.setOnClickListener(this);

        mAdapter = new ForecastAdapter(getActivity());

        rlForecasts.setLayoutManager(new LinearLayoutManager(getActivity()));
        rlForecasts.setAdapter(mAdapter);

        presenter.start();
    }

    private void getDataFromBundle() {
        if(getArgs() != null) {
            cityId = getArgs().getInt(IConstants.CITY_ID, 0);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_city_detail, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.days_1:
                presenter.setNumberOfDays(1);
                presenter.getForecast();
                break;

            case R.id.days_7:
                presenter.setNumberOfDays(7);
                presenter.getForecast();
                break;

            case R.id.days_15:
                presenter.setNumberOfDays(15);
                presenter.getForecast();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void updateWeatherPredictionsList(ForecastModel model) {
        mAdapter.addForecast(model);
    }

    @Override
    public void showCurrentWeather(WeatherModel model) {

    }

    @Override
    public void clearForecastList() {
        mAdapter.clear();
    }

    @Override
    public void showRefreshLayout() {
        refreshLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRefreshLayout() {
        refreshLayout.setVisibility(View.GONE);
    }

    @Override
    public void stopRefreshingAnimation() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void hideFilterMenuHelpText() {
        tvFitlerMenuHelpText.setVisibility(View.GONE);
    }

    @Override
    public void makeRefreshButtonToRetryButton() {
        buttonState = ButtonState.BUTTON_RETRY;
        btnRefresh.setText("Retry");
    }

    @Override
    public void hideButtonLayout() {
        llRefresh.setVisibility(View.GONE);
    }

    @Override
    public void showButtonLayout() {
        llRefresh.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRefresh() {
        presenter.onRefresh();
    }
}

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

    private TextView tvTemp;
    private TextView tvMinTemp;
    private TextView tvMaxTemp;

    private View llRefresh;

    private int cityId;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRefresh:
                presenter.onRefresh();
        }
    }

    public CityController() {}

    @Override
    public String getTitle() {
        return "City Details";
    }

    public CityController(int cityId) {
        this.cityId = cityId;
    }

    private enum ButtonState {
        BUTTON_REFRESH, BUTTON_RETRY
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        View view = inflater.inflate(R.layout.layout_city, container, false);
        setHasOptionsMenu(true);

        presenter = new CityPresenterImpl(this, cityId);
        presenter.start();

        buttonState = ButtonState.BUTTON_REFRESH;

        rlForecasts = (RecyclerView) view.findViewById(R.id.rlForecasts);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        btnRefresh = (Button) view.findViewById(R.id.btnRefresh);
        tvFitlerMenuHelpText = (TextView) view.findViewById(R.id.tvChooseFilterHelpText);
        llRefresh =  view.findViewById(R.id.llRefresh);

        tvTemp = (TextView) view.findViewById(R.id.tvTemp);
        tvMinTemp = (TextView) view.findViewById(R.id.tvMinTemp);
        tvMaxTemp = (TextView) view.findViewById(R.id.tvTMaxemp);

        refreshLayout.setOnRefreshListener(this);
        btnRefresh.setOnClickListener(this);

        mAdapter = new ForecastAdapter(getActivity());

        rlForecasts.setLayoutManager(new LinearLayoutManager(getActivity()));
        rlForecasts.setAdapter(mAdapter);
        return view;
    }

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_city_detail, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getRouter().popController(this);
                break;
            case R.id.days_1:
                presenter.setNumberOfDays(1);
                presenter.onRefresh();
                break;

            case R.id.days_7:
                presenter.setNumberOfDays(7);
                presenter.onRefresh();
                break;

            case R.id.days_15:
                presenter.setNumberOfDays(15);
                presenter.onRefresh();
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
        tvTemp.setText(Float.toString(model.getTemp()));
        tvMinTemp.setText(Float.toString(model.getMinTemp()));
        tvMaxTemp.setText(Float.toString(model.getMaxTemp()));
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

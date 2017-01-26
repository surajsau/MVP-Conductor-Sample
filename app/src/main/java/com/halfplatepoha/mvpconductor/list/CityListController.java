package com.halfplatepoha.mvpconductor.list;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler;
import com.bluelinelabs.conductor.changehandler.VerticalChangeHandler;
import com.halfplatepoha.mvpconductor.BaseController;
import com.halfplatepoha.mvpconductor.IConstants;
import com.halfplatepoha.mvpconductor.R;
import com.halfplatepoha.mvpconductor.city.CityController;
import com.halfplatepoha.mvpconductor.search.SearchController;

/**
 * Created by surajkumarsau on 26/01/17.
 */

public class CityListController extends BaseController implements CityListView,
        CityListAdapter.OnRowClickListener, View.OnClickListener{

    private static final String TAG = CityListController.class.getSimpleName();

    private FloatingActionButton btnAddCity;
    private TextView tvHelpText;
    private RecyclerView rlCity;

    private CityListAdapter mAdapter;

    private CityListPresenter presenter;

    private boolean isFromResult;

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        View view = inflater.inflate(R.layout.layout_city_list, container, false);
        setHasOptionsMenu(true);

        presenter = new CityListPresenterImpl(this);
        presenter.start();

        btnAddCity = (FloatingActionButton) view.findViewById(R.id.btnAddCity);
        tvHelpText = (TextView) view.findViewById(R.id.tvHelpText);
        rlCity = (RecyclerView) view.findViewById(R.id.rlCity);

        mAdapter = new CityListAdapter(getActivity());
        mAdapter.setOnRowClickListener(this);

        rlCity.setLayoutManager(new LinearLayoutManager(getActivity()));
        rlCity.setAdapter(mAdapter);

        btnAddCity.setOnClickListener(this);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_city_list, menu);
    }

    @Override
    public String getTitle() {
        return "MVP Conductor";
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                presenter.onRefresh();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRowClick(int cityId) {
        getRouter().pushController(RouterTransaction.with(new CityController(cityId))
                                    .pushChangeHandler(new HorizontalChangeHandler())
                                    .popChangeHandler(new HorizontalChangeHandler()));
    }

    @Override
    public void hideRefreshLayout() {
        rlCity.setVisibility(View.GONE);
    }

    @Override
    public void showRefreshLayout() {
        rlCity.setVisibility(View.VISIBLE);
    }

    @Override
    public void addCity(CityModel city) {
        mAdapter.add(city);
    }

    @Override
    public void hideHelpText() {
        tvHelpText.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddCity: {
                getRouter().pushController(RouterTransaction.with(new SearchController())
                                            .pushChangeHandler(new HorizontalChangeHandler())
                                            .popChangeHandler(new HorizontalChangeHandler()));
            }
            break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case IConstants.REQUEST_SEARCH_RESULT:{
                if(resultCode == Activity.RESULT_OK && data != null) {
                    Log.e(TAG, "onActivityResult");
                    isFromResult = true;
                    presenter.getWeathersOfCity(data.getIntExtra(IConstants.SEARCH_CITY_ID, 0));
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

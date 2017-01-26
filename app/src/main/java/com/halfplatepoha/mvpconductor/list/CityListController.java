package com.halfplatepoha.mvpconductor.list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler;
import com.bluelinelabs.conductor.changehandler.VerticalChangeHandler;
import com.halfplatepoha.mvpconductor.BaseController;
import com.halfplatepoha.mvpconductor.IConstants;
import com.halfplatepoha.mvpconductor.R;
import com.halfplatepoha.mvpconductor.city.CityController;

/**
 * Created by surajkumarsau on 26/01/17.
 */

public class CityListController extends BaseController implements CityListView, SwipeRefreshLayout.OnRefreshListener,
        CityListAdapter.OnRowClickListener, View.OnClickListener{

    private FloatingActionButton btnAddCity;
    private TextView tvHelpText;
    private RecyclerView rlCity;
    private SwipeRefreshLayout refreshLayout;

    private CityListAdapter mAdapter;

    private CityListPresenter presenter;

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.layout_city_list, container, false);
    }

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);

        presenter = new CityListPresenterImpl(this);

        btnAddCity = (FloatingActionButton) view.findViewById(R.id.btnAddCity);
        tvHelpText = (TextView) view.findViewById(R.id.tvHelpText);
        rlCity = (RecyclerView) view.findViewById(R.id.rlCity);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);

        mAdapter = new CityListAdapter(getActivity());
        mAdapter.setOnRowClickListener(this);

        refreshLayout.setOnRefreshListener(this);
        btnAddCity.setOnClickListener(this);
    }

    @Override
    public void onRefresh() {
        presenter.onRefresh();
    }

    @Override
    public void onRowClick(int cityId) {
        Bundle args = new Bundle();
        args.putInt(IConstants.CITY_ID, cityId);

        getRouter().pushController(RouterTransaction.with(new CityController(args))
                                    .pushChangeHandler(new HorizontalChangeHandler())
                                    .popChangeHandler(new HorizontalChangeHandler()));
    }

    @Override
    public void hideRefreshLayout() {
        refreshLayout.setVisibility(View.GONE);
    }

    @Override
    public void showRefreshLayout() {
        refreshLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void clearList() {
        mAdapter.clear();
    }

    @Override
    public void addCity(CityModel city) {
        mAdapter.add(city);
    }

    @Override
    public void stopRefreshAnimation() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void hideHelpText() {
        tvHelpText.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddCity:
                break;
        }
    }
}

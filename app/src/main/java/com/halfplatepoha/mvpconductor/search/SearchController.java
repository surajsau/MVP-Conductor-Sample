package com.halfplatepoha.mvpconductor.search;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.halfplatepoha.mvpconductor.BaseController;
import com.halfplatepoha.mvpconductor.IConstants;
import com.halfplatepoha.mvpconductor.R;

import java.util.ArrayList;

/**
 * Created by surajkumarsau on 26/01/17.
 */

public class SearchController extends BaseController implements SearchView,
        SearchAdapter.OnRowClickListener, View.OnClickListener{

    private SearchPresenter presenter;

    private SearchAdapter mAdapter;

    private EditText etSearch;
    private RecyclerView rlSearch;
    private Button btnSearch;

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        View view = inflater.inflate(R.layout.layout_search, container, false);
        setHasOptionsMenu(true);

        presenter = new SearchPresenterImpl(this);
        presenter.start();

        etSearch = (EditText) view.findViewById(R.id.etSearch);
        rlSearch = (RecyclerView) view.findViewById(R.id.rlSearch);
        btnSearch = (Button) view.findViewById(R.id.btnSearch);

        mAdapter = new SearchAdapter(getActivity());
        mAdapter.setOnRowClickListener(this);

        btnSearch.setOnClickListener(this);

        rlSearch.setLayoutManager(new LinearLayoutManager(getActivity()));
        rlSearch.setAdapter(mAdapter);
        return inflater.inflate(R.layout.layout_search, container, false);
    }

    @Override
    public String getTitle() {
        return "Add City";
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getRouter().popController(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void refreshList(ArrayList<SearchModel> searchResults) {
        mAdapter.refreshSearchResult(searchResults);
    }

    @Override
    public void setResultAndGoBack(int cityId) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(IConstants.SEARCH_CITY_ID, cityId);
        getRouter().onActivityResult(IConstants.REQUEST_SEARCH_RESULT, Activity.RESULT_OK, resultIntent);
        getRouter().popController(this);
    }

    @Override
    public void onRowClick(int cityId) {
        presenter.updateCityAsSelected(cityId);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSearch:{
                presenter.search(etSearch.getText().toString());
            }
            break;
        }
    }
}

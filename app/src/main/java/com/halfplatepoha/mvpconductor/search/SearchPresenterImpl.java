package com.halfplatepoha.mvpconductor.search;

import android.content.Intent;
import android.os.AsyncTask;

import com.halfplatepoha.mvpconductor.IConstants;
import com.halfplatepoha.mvpconductor.data.db.CityDbModel;
import com.halfplatepoha.mvpconductor.data.db.CityDbModelDao;
import com.halfplatepoha.mvpconductor.data.db.DaoSession;
import com.halfplatepoha.mvpconductor.data.db.DbUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by surajkumarsau on 26/01/17.
 */

public class SearchPresenterImpl implements SearchPresenter {

    private SearchView view;

    public SearchPresenterImpl(SearchView view) {
        this.view = view;
    }

    @Override
    public void start() {
        new SearchTask().execute("");
    }

    @Override
    public void search(String searchQuery) {
        new SearchTask().execute(searchQuery);
    }

    @Override
    public void updateCityAsSelected(int cityId) {
        new UpdateStatusOfCityTask(cityId).execute();
    }

    private class SearchTask extends AsyncTask<String, Void, List<CityDbModel>> {

        private DaoSession daoSession;

        @Override
        protected List<CityDbModel> doInBackground(String... params) {
            daoSession = DbUtils.getInstance().getDaoSession();
            return daoSession.getCityDbModelDao().queryBuilder()
                    .where(CityDbModelDao.Properties.CityName.like("%" + params[0] + "%"),
                            CityDbModelDao.Properties.Status.eq(IConstants.STATUS_UNSELECTED))
                    .orderAsc(CityDbModelDao.Properties.CityName)
                    .list();
        }

        @Override
        protected void onPostExecute(List<CityDbModel> cityDbModels) {
            super.onPostExecute(cityDbModels);
            daoSession.clear();
            Observable.just(cityDbModels)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .filter(new Func1<List<CityDbModel>, Boolean>() {
                        @Override
                        public Boolean call(List<CityDbModel> cityDbModels) {
                            return cityDbModels != null && !cityDbModels.isEmpty();
                        }
                    })
                    .flatMap(new Func1<List<CityDbModel>, Observable<CityDbModel>>() {
                        @Override
                        public Observable<CityDbModel> call(List<CityDbModel> cityDbModels) {
                            return Observable.from(cityDbModels);
                        }
                    })
                    .map(new Func1<CityDbModel, SearchModel>() {
                        @Override
                        public SearchModel call(CityDbModel cityDbModel) {
                            return new SearchModel(cityDbModel.getCityId(), cityDbModel.getCityName());
                        }
                    })
                    .subscribe(new Subscriber<SearchModel>() {
                        ArrayList<SearchModel> searchResults = new ArrayList<>();

                        @Override
                        public void onCompleted() {
                            view.refreshList(searchResults);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(SearchModel searchModel) {
                            searchResults.add(searchModel);
                        }
                    });
        }
    }

    private class UpdateStatusOfCityTask extends AsyncTask<Void, Void, Void> {

        private int cityId;
        private DaoSession daoSession;

        private UpdateStatusOfCityTask(int cityId) {
            this.cityId = cityId;
        }

        @Override
        protected Void doInBackground(Void... params) {
            daoSession = DbUtils.getInstance().getDaoSession();
            CityDbModel city = daoSession.getCityDbModelDao().queryBuilder()
                    .where(CityDbModelDao.Properties.CityId.eq(cityId))
                    .unique();
            city.setStatus(IConstants.STATUS_SELECTED);
            daoSession.getCityDbModelDao().update(city);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            daoSession.clear();
            view.setResultAndGoBack(cityId);
        }
    }
}

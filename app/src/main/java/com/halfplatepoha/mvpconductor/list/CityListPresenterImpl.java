package com.halfplatepoha.mvpconductor.list;

import android.os.AsyncTask;

import com.halfplatepoha.mvpconductor.data.CityWeather;
import com.halfplatepoha.mvpconductor.data.db.CityDbModel;
import com.halfplatepoha.mvpconductor.data.db.CityDbModelDao;
import com.halfplatepoha.mvpconductor.data.db.DaoSession;
import com.halfplatepoha.mvpconductor.data.db.DbUtils;
import com.halfplatepoha.mvpconductor.data.network.ServiceGenerator;
import com.halfplatepoha.mvpconductor.data.network.WeatherClient;

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

public class CityListPresenterImpl implements CityListPresenter {

    private CityListView view;
    private WeatherClient client;

    public CityListPresenterImpl(CityListView view) {
        this.view = view;
    }
    @Override
    public void onRefresh() {

    }

    @Override
    public void getWeathersOfCities() {

    }

    @Override
    public void start() {
        client = ServiceGenerator.createService(WeatherClient.class);
    }

    private class GetSelectedCitiesTask extends AsyncTask<Void, Void, List<CityDbModel>> {

        private DaoSession session;

        @Override
        protected List<CityDbModel> doInBackground(Void... params) {
            session = DbUtils.getInstance().getDaoSession();
            return session.getCityDbModelDao().queryBuilder()
                    .where(CityDbModelDao.Properties.Selected.eq(true))
                    .list();
        }

        @Override
        protected void onPostExecute(List<CityDbModel> ids) {
            Observable.just(ids)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(Schedulers.newThread())
                    .filter(new Func1<List<CityDbModel>, Boolean>() {
                        @Override
                        public Boolean call(List<CityDbModel> integers) {
                            return integers != null && !integers.isEmpty();
                        }
                    })
                    .flatMap(new Func1<List<CityDbModel>, Observable<CityDbModel>>() {
                        @Override
                        public Observable<CityDbModel> call(List<CityDbModel> cityDbModels) {
                            return Observable.from(cityDbModels);
                        }
                    })
                    .map(new Func1<CityDbModel, Integer>() {
                        @Override
                        public Integer call(CityDbModel cityDbModel) {
                            return cityDbModel.getCityId();
                        }
                    })
                    .subscribe(new Subscriber<Integer>() {
                        @Override
                        public void onCompleted() {
                            view.stopRefreshAnimation();
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(Integer integer) {
                            client.getCityWeather(integer)
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribeOn(Schedulers.newThread())
                                    .filter(new Func1<CityWeather, Boolean>() {
                                        @Override
                                        public Boolean call(CityWeather cityWeather) {
                                            return cityWeather != null;
                                        }
                                    })
                                    .map(new Func1<CityWeather, CityModel>() {
                                        @Override
                                        public CityModel call(CityWeather cityWeather) {
                                            return new CityModel(cityWeather.getId(),
                                                    cityWeather.getName(),
                                                    cityWeather.getMain().getTemp(),
                                                    cityWeather.getWeather().get(0).getMain(),
                                                    cityWeather.getWeather().get(0).getDescription());
                                        }
                                    })
                                    .subscribe(new Subscriber<CityModel>() {
                                        @Override
                                        public void onCompleted() {}

                                        @Override
                                        public void onError(Throwable e) {}

                                        @Override
                                        public void onNext(CityModel cityModel) {
                                            view.addCity(cityModel);
                                        }
                                    });
                        }
                    });
        }
    }
}

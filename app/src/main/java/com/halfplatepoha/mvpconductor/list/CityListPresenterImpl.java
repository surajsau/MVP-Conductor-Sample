package com.halfplatepoha.mvpconductor.list;

import android.os.AsyncTask;
import android.util.Log;

import com.halfplatepoha.mvpconductor.IConstants;
import com.halfplatepoha.mvpconductor.data.CityWeather;
import com.halfplatepoha.mvpconductor.data.db.CityDbModel;
import com.halfplatepoha.mvpconductor.data.db.CityDbModelDao;
import com.halfplatepoha.mvpconductor.data.db.DaoSession;
import com.halfplatepoha.mvpconductor.data.db.DbUtils;
import com.halfplatepoha.mvpconductor.data.network.ServiceGenerator;
import com.halfplatepoha.mvpconductor.data.network.WeatherClient;

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

    private static final String TAG = CityListPresenterImpl.class.getSimpleName();

    private CityListView view;
    private WeatherClient client;

    public CityListPresenterImpl(CityListView view) {
        this.view = view;
        initClient();
    }

    public void initClient() {
        client = ServiceGenerator.createService(WeatherClient.class);
    }

    @Override
    public void onRefresh() {
        new GetSelectedCitiesTask().execute();
    }

    @Override
    public void getWeathersOfCity(int cityId) {
        callWeatherApi(cityId);
    }

    @Override
    public void start() {
        new GetSelectedCitiesTask().execute();
    }

    private class GetSelectedCitiesTask extends AsyncTask<Void, Void, List<CityDbModel>> {

        private DaoSession session;

        @Override
        protected List<CityDbModel> doInBackground(Void... params) {
            session = DbUtils.getInstance().getDaoSession();
            return session.getCityDbModelDao().queryBuilder()
                    .where(CityDbModelDao.Properties.Status.eq(IConstants.STATUS_SELECTED))
                    .list();
        }

        @Override
        protected void onPostExecute(List<CityDbModel> ids) {
            session.clear();
            Observable.just(ids)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
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
                    .distinct(new Func1<CityDbModel, Integer>() {
                        @Override
                        public Integer call(CityDbModel cityDbModel) {
                            return cityDbModel.getCityId();
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

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(Integer integer) {
                            callWeatherApi(integer);
                        }
                    });
        }
    }

    private void callWeatherApi(int cityid) {
        client.getCityWeather(cityid)
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
}

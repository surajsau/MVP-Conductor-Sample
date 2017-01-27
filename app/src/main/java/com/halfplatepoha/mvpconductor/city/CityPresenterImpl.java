package com.halfplatepoha.mvpconductor.city;

import com.halfplatepoha.mvpconductor.BasePresenter;
import com.halfplatepoha.mvpconductor.data.CityWeather;
import com.halfplatepoha.mvpconductor.data.DailyForecast;
import com.halfplatepoha.mvpconductor.data.network.ServiceGenerator;
import com.halfplatepoha.mvpconductor.data.network.WeatherClient;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by surajkumarsau on 25/01/17.
 */

public class CityPresenterImpl implements CityPresenter {

    private WeatherClient mClient;
    private int cityId;

    private int numberOfDays;

    private CityView view;

    @Override
    public void start() {
        getWeather();
    }

    public CityPresenterImpl(CityView view, int cityId) {
        this.view = view;
        this.cityId = cityId;

        initClient();
    }

    public void initClient() {
        mClient = ServiceGenerator.createService(WeatherClient.class);
    }

    private void getForecast() {
        mClient.getCityDailyForecast(cityId, numberOfDays)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<DailyForecast, Observable<DailyForecast.Daily>>() {
                    @Override
                    public Observable<DailyForecast.Daily> call(DailyForecast dailyForecast) {
                        return Observable.from(dailyForecast.getList());
                    }
                })
                .filter(new Func1<DailyForecast.Daily, Boolean>() {
                    @Override
                    public Boolean call(DailyForecast.Daily daily) {
                        return (daily != null);
                    }
                })
                .flatMap(new Func1<DailyForecast.Daily, Observable<ForecastModel>>() {
                    @Override
                    public Observable<ForecastModel> call(DailyForecast.Daily daily) {
                        return Observable.just(new ForecastModel(daily.getDt(), daily.getTemp().getDay(),
                                daily.getTemp().getMin(), daily.getTemp().getMax(), daily.getHumidity(),
                                daily.getWeather().get(0).getDescription(),
                                daily.getWeather().get(0).getMain()));
                    }
                })
                .subscribe(new Subscriber<ForecastModel>() {
                    @Override
                    public void onCompleted() {
                        view.stopRefreshingAnimation();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.stopRefreshingAnimation();
                        view.hideRefreshLayout();
                        view.makeRefreshButtonToRetryButton();
                        view.showButtonLayout();
                    }

                    @Override
                    public void onNext(ForecastModel forecastModel) {
                        view.updateWeatherPredictionsList(forecastModel);
                    }
                });

    }

    @Override
    public void getWeather() {
        mClient.getCityWeather(cityId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Func1<CityWeather, Boolean>() {
                    @Override
                    public Boolean call(CityWeather cityWeather) {
                        return (cityWeather != null);
                    }
                })
                .flatMap(new Func1<CityWeather, Observable<WeatherModel>>() {
                    @Override
                    public Observable<WeatherModel> call(CityWeather cityWeather) {
                        return Observable.just(new WeatherModel(cityWeather.getWeather().get(0).getMain(),
                                cityWeather.getWeather().get(0).getDescription(),
                                cityWeather.getMain().getTemp(),
                                cityWeather.getMain().getTemp_min(),
                                cityWeather.getMain().getTemp_max(),
                                cityWeather.getMain().getHumidity()));
                    }
                })
                .subscribe(new Subscriber<WeatherModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(WeatherModel model) {
                        view.showCurrentWeather(model);
                    }
                });
    }

    @Override
    public void setNumberOfDays(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    @Override
    public void onRefresh() {
        view.showRefreshLayout();
        view.hideButtonLayout();

        view.clearForecastList();

        getForecast();
    }
}

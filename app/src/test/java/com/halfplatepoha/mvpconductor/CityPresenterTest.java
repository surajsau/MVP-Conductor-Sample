package com.halfplatepoha.mvpconductor;

import com.halfplatepoha.mvpconductor.city.CityPresenter;
import com.halfplatepoha.mvpconductor.city.CityPresenterImpl;
import com.halfplatepoha.mvpconductor.city.CityView;
import com.halfplatepoha.mvpconductor.city.WeatherModel;
import com.halfplatepoha.mvpconductor.data.CityWeather;
import com.halfplatepoha.mvpconductor.data.network.WeatherClient;
import com.halfplatepoha.mvpconductor.list.CityModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import rx.Observable;
import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.schedulers.Schedulers;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by surajkumarsau on 26/01/17.
 */

public class CityPresenterTest {

    @Mock
    private CityView cityView;

    @Mock
    private WeatherClient client;

    @Mock
    private CityModel city;

    @Mock
    private CityWeather cityWeatherResponse;

    @Mock
    private WeatherModel weatherModel;

    private CityPresenter presenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(cityView.isActive()).thenReturn(true);
        RxAndroidPlugins.getInstance().registerSchedulersHook(new RxAndroidSchedulersHook(){
            @Override
            public Scheduler getMainThreadScheduler() {
                return Schedulers.immediate();
            }
        });
    }

    @Test
    public void testUIChangesWhenRefreshed() {
        presenter = new CityPresenterImpl(cityView, city.getCityId());
        presenter.onRefresh();
        verify(cityView).hideButtonLayout();
        verify(cityView).showRefreshLayout();
        verify(cityView).clearForecastList();
    }

    @Test
    public void testApiClientIsNotNull() {
        presenter = new CityPresenterImpl(cityView, city.getCityId());
        assertNotNull(client);
    }

    @Test
    public void testShowFillAdapter() {
        when(client.getCityWeather(city.getCityId())).thenReturn(Observable.just(cityWeatherResponse));
    }

    @Test
    public void testGetWeatherApiIsCalledOnStart() {
        presenter = new CityPresenterImpl(cityView, city.getCityId());
        presenter.start();
    }

    @After
    public void tearDown() {
        RxAndroidPlugins.getInstance().reset();
    }

}

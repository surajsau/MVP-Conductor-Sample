package com.halfplatepoha.mvpconductor;

import com.halfplatepoha.mvpconductor.data.network.WeatherClient;
import com.halfplatepoha.mvpconductor.list.CityListPresenter;
import com.halfplatepoha.mvpconductor.list.CityListPresenterImpl;
import com.halfplatepoha.mvpconductor.list.CityListView;
import com.halfplatepoha.mvpconductor.list.CityModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.schedulers.Schedulers;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * Created by surajkumarsau on 26/01/17.
 */

public class CityListPresenterTest {

    @Mock
    private CityListView cityListView;

    @Mock
    private WeatherClient client;

    @Mock
    private CityModel city;

    private CityListPresenter presenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(cityListView.isActive()).thenReturn(true);
        RxAndroidPlugins.getInstance().registerSchedulersHook(new RxAndroidSchedulersHook(){
            @Override
            public Scheduler getMainThreadScheduler() {
                return Schedulers.immediate();
            }
        });
    }

    @Test
    public void testWeatherClientIsNotNull() {
        presenter = new CityListPresenterImpl(cityListView);
        assertNotNull(client);
    }

    @After
    public void tearDown() {
        RxAndroidPlugins.getInstance().reset();
    }
}

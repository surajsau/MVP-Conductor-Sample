package com.halfplatepoha.mvpconductor;

import com.halfplatepoha.mvpconductor.search.SearchView;

import org.junit.After;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.schedulers.Schedulers;

import static org.mockito.Mockito.when;

/**
 * Created by surajkumarsau on 26/01/17.
 */

public class SearchPresenterTest {

    @Mock
    private SearchView searchView;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(searchView.isActive()).thenReturn(true);
        RxAndroidPlugins.getInstance().registerSchedulersHook(new RxAndroidSchedulersHook(){
            @Override
            public Scheduler getMainThreadScheduler() {
                return Schedulers.immediate();
            }
        });
    }

    @After
    public void tearDown() {
        RxAndroidPlugins.getInstance().reset();
    }
}

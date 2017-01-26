package com.halfplatepoha.mvpconductor;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.halfplatepoha.mvpconductor.list.CityListController;

public class MainActivity extends AppCompatActivity {

    private Router mRouter;

    private ViewGroup container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        container = (ViewGroup) findViewById(R.id.container);

        mRouter = Conductor.attachRouter(this, container, savedInstanceState);
        if(!mRouter.hasRootController()) {
            CityListController listController = new CityListController();
            listController.registerForActivityResult(IConstants.REQUEST_SEARCH_RESULT);
            listController.setRetainViewMode(Controller.RetainViewMode.RETAIN_DETACH);
            mRouter.pushController(RouterTransaction.with(listController));
        }
    }
}

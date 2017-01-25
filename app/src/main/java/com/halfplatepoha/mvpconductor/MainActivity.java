package com.halfplatepoha.mvpconductor;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;

public class MainActivity extends AppCompatActivity implements DrawerLayoutProvider{

    private DrawerLayout mDrawerLayout;
    private Router mRouter;

    private ViewGroup container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        container = (ViewGroup) findViewById(R.id.container);

        mRouter = Conductor.attachRouter(this, container, savedInstanceState);
        if(!mRouter.hasRootController()) {

        }
    }

    @Override
    public DrawerLayout getDrawerLayout() {
        return mDrawerLayout;
    }
}

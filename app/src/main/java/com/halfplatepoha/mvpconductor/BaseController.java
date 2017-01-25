package com.halfplatepoha.mvpconductor;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Controller;

/**
 * Created by surajkumarsau on 25/01/17.
 */

public abstract class BaseController extends Controller {
    private boolean mActive = false;

    public BaseController(){}

    public BaseController(Bundle args){}

    protected DrawerLayout getDrawerLayout() {
        DrawerLayoutProvider provider = (DrawerLayoutProvider)getActivity();
        return provider.getDrawerLayout();
    }

    protected ActionBar getSupportActionBar() {
        ActionBarInterface actionBarInterface = (ActionBarInterface)getActivity();
        return actionBarInterface.getSupportActionBar();
    }

    protected void setSupportActionBar(Toolbar toolbar) {
        ActionBarInterface actionBarInterface = (ActionBarInterface)getActivity();
        actionBarInterface.setSupportActionBar(toolbar);
    }

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);
        DrawerLayout drawerLayout = getDrawerLayout();
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getRouter().handleBack();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroyView(@NonNull View view) {
        super.onDestroyView(view);
        setActive(false);
    }

    public boolean isActive() {
        return mActive;
    }

    protected void setActive(boolean active) {
        mActive = active;
    }
}

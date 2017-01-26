package com.halfplatepoha.mvpconductor;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Controller;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by surajkumarsau on 25/01/17.
 */

public abstract class BaseController extends Controller {
    private boolean mActive = false;

    public BaseController(){}

    public abstract String getTitle();

    protected ActionBar getActionBar() {
        return ((AppCompatActivity)getActivity()).getSupportActionBar();
    }

    protected void setActionBar(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getTitle());
    }

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);
        setActionBar(view);
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

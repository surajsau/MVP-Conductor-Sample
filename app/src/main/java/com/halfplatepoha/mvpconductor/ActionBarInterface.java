package com.halfplatepoha.mvpconductor;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

/**
 * Created by surajkumarsau on 25/01/17.
 */

public interface ActionBarInterface {

    ActionBar getSupportActionBar();
    void setSupportActionBar(Toolbar toolbar);
}

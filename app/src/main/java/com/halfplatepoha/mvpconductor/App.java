package com.halfplatepoha.mvpconductor;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import com.halfplatepoha.mvpconductor.data.db.CityDbModel;
import com.halfplatepoha.mvpconductor.data.db.DaoMaster;
import com.halfplatepoha.mvpconductor.data.db.DaoSession;
import com.halfplatepoha.mvpconductor.data.db.DbUtils;

import org.greenrobot.greendao.database.Database;

import java.util.ArrayList;

/**
 * Created by surajkumarsau on 25/01/17.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DbUtils.init(this);
        new UpdateCitiesDb(this).execute();
    }

    private class UpdateCitiesDb extends AsyncTask<Void, Void, Void> {

        private DaoSession daoSession;

        public UpdateCitiesDb(Context context) {
            daoSession = DbUtils.getInstance().getDaoSession();
        }

        @Override
        protected Void doInBackground(Void... params) {
            if(daoSession.getCityDbModelDao().count() == 0)
                daoSession.getCityDbModelDao().insertInTx(DbUtils.getInstance().getCities());
            return null;
        }
    }

}

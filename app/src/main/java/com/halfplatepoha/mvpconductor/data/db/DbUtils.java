package com.halfplatepoha.mvpconductor.data.db;

import android.content.Context;

import com.halfplatepoha.mvpconductor.data.City;

import org.greenrobot.greendao.database.Database;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by surajkumarsau on 26/01/17.
 */

public class DbUtils {

    private static DbUtils mInstance;
    private Context mContext;
    private DaoSession daoSession;

    private DbUtils(Context context) {
        mContext = context;
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(mContext, "cities-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public static DbUtils getInstance() {
        return mInstance;
    }

    public static void init(Context context) {
        if(mInstance == null) {
            mInstance = new DbUtils(context);
        }
    }

    private String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = mContext.getAssets().open("citylist.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }

    public ArrayList<CityDbModel> getCities() {
        ArrayList<CityDbModel> cities = new ArrayList<>();
        try {
            JSONArray objs = new JSONArray(loadJSONFromAsset());
            for(int i=0; i<objs.length(); i++) {
                cities.add(new CityDbModel(null,
                        objs.getJSONObject(i).getInt("_id"),
                        objs.getJSONObject(i).getString("name"),
                        false));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return cities;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }


}

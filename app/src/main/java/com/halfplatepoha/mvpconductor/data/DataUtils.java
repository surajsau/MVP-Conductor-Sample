package com.halfplatepoha.mvpconductor.data;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by surajkumarsau on 26/01/17.
 */

public class DataUtils {

    private static DataUtils mInstance;
    private Context mContext;

    private DataUtils(Context context) {
        mContext = context;
    }

    public static DataUtils getInstance(Context context) {
        if(mInstance == null)
            mInstance = new DataUtils(context);
        return mInstance;
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

    public ArrayList<City> getCities() {
        ArrayList<City> cities = new ArrayList<>();
        try {
            JSONArray objs = new JSONArray(loadJSONFromAsset());
            for(int i=0; i<objs.length(); i++) {
                cities.add(new City(objs.getJSONObject(i).getLong("_id"), objs.getJSONObject(i).getString("name")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return cities;
    }


}

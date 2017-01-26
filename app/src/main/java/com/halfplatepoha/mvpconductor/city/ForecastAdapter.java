package com.halfplatepoha.mvpconductor.city;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.halfplatepoha.mvpconductor.R;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by surajkumarsau on 26/01/17.
 */

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder> {

    private ArrayList<ForecastModel> forecasts;
    private Context mContext;

    public ForecastAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ForecastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(mContext).inflate(R.layout.forecast_layout, parent, false);
        return new ForecastViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(ForecastViewHolder holder, int position) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(forecasts.get(position).getDate());
        holder.tvDate.setText(calendar.getTime().toString());
        holder.tvTemp.setText(Float.toString(forecasts.get(position).getDayTemp()));
        holder.tvMinTemp.setText(Float.toString(forecasts.get(position).getMinTemp()));
        holder.tvMaxTemp.setText(Float.toString(forecasts.get(position).getMaxTemp()));
    }

    @Override
    public int getItemCount() {
        if(forecasts != null)
            forecasts.size();
        return 0;
    }

    public static class ForecastViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTemp;
        private TextView tvMinTemp;
        private TextView tvMaxTemp;
        private TextView tvDate;

        public ForecastViewHolder(View view) {
            super(view);
            tvTemp = (TextView) view.findViewById(R.id.tvTemp);
            tvMaxTemp = (TextView) view.findViewById(R.id.tvTMaxemp);
            tvMinTemp = (TextView) view.findViewById(R.id.tvMinTemp);
            tvDate = (TextView) view.findViewById(R.id.tvDate);
        }
    }

    public void clear() {
        forecasts.clear();
        notifyDataSetChanged();
    }

    public void addForecast(ForecastModel forecast) {
        if(forecasts == null)
            forecasts = new ArrayList<>();
        forecasts.add(forecast);
        notifyItemInserted(forecasts.size() - 1);
    }
}

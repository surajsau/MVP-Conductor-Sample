package com.halfplatepoha.mvpconductor.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.halfplatepoha.mvpconductor.R;

import java.util.ArrayList;

/**
 * Created by surajkumarsau on 26/01/17.
 */

public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.CityListViewHolder> {

    private OnRowClickListener mListener;
    private ArrayList<CityModel> cities;
    private Context mContext;

    public CityListAdapter(Context context) {
        mContext = context;
    }

    public void setOnRowClickListener(OnRowClickListener listener) {
        mListener = listener;
    }

    @Override
    public CityListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(mContext).inflate(R.layout.city_layout, parent, false);
        return new CityListViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(CityListViewHolder holder, int position) {
        holder.itemView.setTag(cities.get(position).getCityId());

        holder.tvName.setText(cities.get(position).getCityName());
        holder.tvTemp.setText(Float.toString(cities.get(position).getTemp()));
        holder.tvWeather.setText(cities.get(position).getWeather());
        holder.tvWeatherDescription.setText(cities.get(position).getWeatherDescription());
    }

    @Override
    public int getItemCount() {
        if(cities != null)
            return cities.size();
        return 0;
    }

    public class CityListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView tvName;
        private TextView tvTemp;
        private TextView tvWeather;
        private TextView tvWeatherDescription;

        public CityListViewHolder(View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvTemp = (TextView) itemView.findViewById(R.id.tvTemp);
            tvWeather = (TextView) itemView.findViewById(R.id.tvWeather);
            tvWeatherDescription = (TextView) itemView.findViewById(R.id.tvWeatherDescription);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onRowClick((Integer) v.getTag());
        }
    }

    public interface OnRowClickListener {
        void onRowClick(int cityId);
    }

    public void add(CityModel city) {
        if(cities == null)
            cities = new ArrayList<>();
        cities.add(city);
        notifyItemInserted(cities.size() - 1);
    }

    public void clear() {
        cities.clear();
        notifyDataSetChanged();
    }
}

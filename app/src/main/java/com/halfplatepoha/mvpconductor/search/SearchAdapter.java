package com.halfplatepoha.mvpconductor.search;

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

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private OnRowClickListener mListener;
    private Context mContext;

    private ArrayList<SearchModel> mSearchResults;

    public SearchAdapter(Context context) {
        mContext = context;
        mSearchResults = new ArrayList<>();
    }

    public void setOnRowClickListener(OnRowClickListener listener) {
        mListener = listener;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(mContext).inflate(R.layout.search_layout, parent, false);
        return new SearchViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        holder.itemView.setTag(mSearchResults.get(position).getCityId());
        holder.tvName.setText(mSearchResults.get(position).getCityName());
    }

    @Override
    public int getItemCount() {
        if(mSearchResults != null)
            return mSearchResults.size();
        return 0;
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvName;

        public SearchViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);

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

    public void refreshSearchResult(ArrayList<SearchModel> searchResults) {
        mSearchResults.clear();
        if(searchResults != null) {
            mSearchResults.addAll(searchResults);
        }

        notifyDataSetChanged();
    }
}

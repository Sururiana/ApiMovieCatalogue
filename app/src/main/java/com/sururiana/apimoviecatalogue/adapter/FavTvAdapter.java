package com.sururiana.apimoviecatalogue.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sururiana.apimoviecatalogue.R;
import com.sururiana.apimoviecatalogue.model.Tv;

import java.util.ArrayList;

public class FavTvAdapter extends RecyclerView.Adapter<FavTvAdapter.FavViewHolder> {
    public ArrayList<Tv> getTvList() {
        return tvList;
    }
    private Context mContext;

    private final ArrayList<Tv> tvList = new ArrayList<>();
    private final Activity activity;

    public FavTvAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setTvList(ArrayList<Tv> tvList) {

        if (tvList.size() > 0) {
            this.tvList.clear();
        }
        this.tvList.addAll(tvList);

        notifyDataSetChanged();
    }

    public void addItem(Tv tv) {
        this.tvList.add(tv);
        notifyItemInserted(tvList.size() - 1);
    }

    public void removeItem(int position) {
        this.tvList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, tvList.size());
    }

    @NonNull
    @Override
    public FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_favmovie, parent, false);
        return new FavViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavViewHolder holder, int position) {
        holder.title.setText(tvList.get(position).getName());
        holder.release.setText(tvList.get(position).getFirstAirDate());
        String vote = Double.toString(tvList.get(position).getVoteAverage());
        holder.userrating.setText(vote);
        Glide.with(activity).load(tvList.get(position).getPosterPathFav()).apply(new RequestOptions().placeholder(R.drawable.load).fitCenter()).into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return tvList.size();
    }

    class FavViewHolder extends RecyclerView.ViewHolder {
        public TextView title, userrating, release;
        public ImageView thumbnail;

        public FavViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            userrating = (TextView) itemView.findViewById(R.id.userrating);
            release = (TextView) itemView.findViewById(R.id.release);
            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
        }
    }
}

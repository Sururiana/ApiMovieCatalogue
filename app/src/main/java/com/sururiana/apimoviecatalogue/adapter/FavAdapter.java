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
import com.sururiana.apimoviecatalogue.model.Movie;

import java.util.ArrayList;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.FavViewHolder> {
    public ArrayList<Movie> getMovieList() {
        return movieList;
    }
    private Context mContext;

    private final ArrayList<Movie> movieList = new ArrayList<>();
    private final Activity activity;

    public FavAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setMovieList(ArrayList<Movie> movieList) {

        if (movieList.size() > 0) {
            this.movieList.clear();
        }
        this.movieList.addAll(movieList);

        notifyDataSetChanged();
    }

    public void addItem(Movie movie) {
        this.movieList.add(movie);
        notifyItemInserted(movieList.size() - 1);
    }

    public void removeItem(int position) {
        this.movieList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, movieList.size());
    }

    @NonNull
    @Override
    public FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_favmovie, parent, false);
        return new FavViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavViewHolder holder, int position) {
        holder.title.setText(movieList.get(position).getTitle());
        holder.release.setText(movieList.get(position).getReleaseDate());
        String vote = Double.toString(movieList.get(position).getVoteAverage());
        holder.userrating.setText(vote);
        Glide.with(activity).load(movieList.get(position).getPosterPathFav()).apply(new RequestOptions().placeholder(R.drawable.load).fitCenter()).into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
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

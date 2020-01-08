package com.sururiana.apimoviecatalogue.adapter;

import android.content.Context;
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

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<Movie> movieList;

    public MoviesAdapter(Context mContext){
        this.mContext = mContext;
        this.movieList = new ArrayList<>();
    }

    public void setMovies(ArrayList<Movie> movie) {
        movieList = movie;
        notifyDataSetChanged();
    }

    @Override
    public MoviesAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row_movie, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MoviesAdapter.MyViewHolder viewHolder, int i) {
        viewHolder.title.setText(movieList.get(i).getTitle());
        viewHolder.release.setText(movieList.get(i).getReleaseDate());
        String vote = Double.toString(movieList.get(i).getVoteAverage());
        viewHolder.userrating.setText(vote);

        Glide.with(mContext)
                .load(movieList.get(i).getPosterPath()).apply(new RequestOptions().placeholder(R.drawable.load).fitCenter()).into(viewHolder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, userrating, release;
        public ImageView thumbnail;
        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            userrating = (TextView) itemView.findViewById(R.id.userrating);
            release = (TextView) itemView.findViewById(R.id.release);
            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
        }
    }
}

package com.sururiana.apimoviecatalogue.adapter;

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

import java.util.List;

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.MyViewHolder> {

    public TvAdapter(Context mContext) {
        this.mContext = mContext;
    }

    private Context mContext;


    public void setTvList(List<Tv> tvList) {
        this.tvList = tvList;
    }

    private List<Tv> tvList;

    @NonNull
    @Override
    public TvAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tvshow, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvAdapter.MyViewHolder viewHolder, int i) {
        viewHolder.title.setText(tvList.get(i).getName());
        viewHolder.release.setText(tvList.get(i).getFirstAirDate());
        String vote = Double.toString(tvList.get(i).getVoteAverage());
        viewHolder.userrating.setText(vote);

        Glide.with(mContext)
                .load(tvList.get(i).getPosterPath()).apply(new RequestOptions().placeholder(R.drawable.load).fitCenter()).into(viewHolder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return tvList.size();
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

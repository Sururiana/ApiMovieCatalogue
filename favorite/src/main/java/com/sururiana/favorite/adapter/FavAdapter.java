package com.sururiana.favorite.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.sururiana.favorite.R;
import com.sururiana.favorite.model.Movie;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ViewHolder> {
    public FavAdapter(Context context){Context context1 = context;}
    private Cursor cursor;
    public void setMovie(Cursor listmovie){this.cursor = listmovie;}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_favmovie,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Movie result = getItem(position);
        holder.title.setText(result.getTitle());
        String vote = Double.toString(result.getVoteAverage());
        holder.userrating.setText(vote);
        holder.release.setText(result.getReleaseDate());
        String poster = result.getPosterPathFav();
        Picasso.get().load(poster).placeholder(R.drawable.load).into(holder.thumbnail);
    }

    private Movie getItem(int i){
        if (!cursor.moveToPosition(i)){
        }
        return new Movie(cursor);
    }

    @Override
    public int getItemCount() {
        if (cursor == null) return 0;
        return cursor.getCount();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, userrating, release;
        ImageView thumbnail;
        ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            userrating = (TextView) itemView.findViewById(R.id.userrating);
            release = (TextView) itemView.findViewById(R.id.release);
            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
        }
    }
}

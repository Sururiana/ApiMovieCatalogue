package com.sururiana.favorite.fragment;


import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sururiana.favorite.R;
import com.sururiana.favorite.adapter.FavAdapter;
import com.sururiana.favorite.data.MovieHelper;

import java.util.Objects;

import static com.sururiana.favorite.data.FavoriteContract.CONTENT_URI;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {
    RecyclerView recyclerView;
    FavAdapter favAdapter;
    private Cursor list;

    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        recyclerView = view.findViewById(R.id.rv_list);
        MovieHelper movieHelper = MovieHelper.getInstance(getContext());
        movieHelper.open();
        new loadMovie().execute();

        favAdapter = new FavAdapter(this.getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(favAdapter);
        favAdapter.setMovie(list);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private class loadMovie extends AsyncTask<Void, Void, Cursor>{

        @Override
        protected Cursor doInBackground(Void... voids) {
            return Objects.requireNonNull(getContext()).getContentResolver().query(CONTENT_URI,null,null,null,null);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(Cursor cursor_movie) {
            super.onPostExecute(cursor_movie);
            list = cursor_movie;
            favAdapter.setMovie(list);
            favAdapter.notifyDataSetChanged();
        }
    }

}

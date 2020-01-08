package com.sururiana.apimoviecatalogue.fragment.fav;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sururiana.apimoviecatalogue.DetailActivity;
import com.sururiana.apimoviecatalogue.R;
import com.sururiana.apimoviecatalogue.adapter.FavAdapter;
import com.sururiana.apimoviecatalogue.data.MovieHelper;
import com.sururiana.apimoviecatalogue.model.Movie;
import com.sururiana.apimoviecatalogue.utils.ItemClickSupport;
import com.sururiana.apimoviecatalogue.utils.LoadMovieCallback;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment implements LoadMovieCallback {
    private RecyclerView rvMovie;
    private FavAdapter favAdapter;
    private static final String EXTRA_STATE = "EXTRA_STATE";

    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvMovie = view.findViewById(R.id.rv_list);
        rvMovie.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMovie.setHasFixedSize(true);

        MovieHelper movieHelper = MovieHelper.getInstance(getActivity());
        movieHelper.open();

        favAdapter = new FavAdapter(getActivity());
        rvMovie.setAdapter(favAdapter);

        if (savedInstanceState == null) {
            new LoadMoviesAsync(movieHelper, (LoadMovieCallback) this).execute();
        } else {
            ArrayList<Movie> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                favAdapter.setMovieList(list);
            }
        }
    }

    @Override
    public void preExecute() {

    }

    @Override
    public void postExecute(final ArrayList<Movie> movies) {
        favAdapter.setMovieList(movies);
        rvMovie.setAdapter(favAdapter);
        ItemClickSupport.addTo(rvMovie).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) { Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra(DetailActivity.EXTRA_MOVIE, movies.get(position));
            startActivity(intent);
            }
        });
    }

    private static class LoadMoviesAsync extends AsyncTask<Void, Void, ArrayList<Movie>> {
        private final WeakReference<MovieHelper> weakMovieHelper;
        private final WeakReference<LoadMovieCallback> weakCallback;

        private LoadMoviesAsync(MovieHelper movieHelper, LoadMovieCallback callback) {
            weakMovieHelper = new WeakReference<>(movieHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<Movie> doInBackground(Void... voids) {
            return weakMovieHelper.get().getAllMovie();
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            super.onPostExecute(movies);
            weakCallback.get().postExecute(movies);
        }
    }
}

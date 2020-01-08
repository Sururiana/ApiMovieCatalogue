package com.sururiana.apimoviecatalogue.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.sururiana.apimoviecatalogue.DetailActivity;
import com.sururiana.apimoviecatalogue.search.SearchActivity;
import com.sururiana.apimoviecatalogue.utils.ItemClickSupport;
import com.sururiana.apimoviecatalogue.R;
import com.sururiana.apimoviecatalogue.adapter.MoviesAdapter;
import com.sururiana.apimoviecatalogue.api.MoviesRepository;
import com.sururiana.apimoviecatalogue.api.OnGetMoviesCallback;
import com.sururiana.apimoviecatalogue.model.Movie;

import java.util.ArrayList;

import static com.sururiana.apimoviecatalogue.search.SearchActivity.SEARCH;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {
    private RecyclerView recyclerView;
    private MoviesAdapter adapter;
    private MoviesRepository moviesRepository;
    public final static String LIST_STATE_KEY = "STATE";
    private ArrayList<Movie> movieList = new ArrayList<>();
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        recyclerView = view.findViewById(R.id.rv_list);
        progressBar = view.findViewById(R.id.progressBar);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        moviesRepository = MoviesRepository.getInstance();

        SearchView sv = view.findViewById(R.id.search_view);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra(SEARCH, query);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        if (savedInstanceState != null) {
            progressBar.setVisibility(View.INVISIBLE);
            final ArrayList<Movie> moviesState = savedInstanceState.getParcelableArrayList(LIST_STATE_KEY);
            assert moviesState != null;
            movieList.addAll(moviesState);
            adapter = new MoviesAdapter(getContext());
            adapter.setMovies(moviesState);
            recyclerView.setAdapter(adapter);
            ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    intent.putExtra(DetailActivity.EXTRA_MOVIE, moviesState.get(position));
                    startActivity(intent);
                }
            });
        } else {
            progressBar.setVisibility(View.VISIBLE);
            getData();
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(LIST_STATE_KEY, movieList); }

        public void getData() {
        moviesRepository.getMoviesPopular(new OnGetMoviesCallback() {
            @Override
            public void onSuccess(final ArrayList<Movie> movies) {
                adapter = new MoviesAdapter(getContext());
                adapter.setMovies(movies);
                recyclerView.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);
                movieList.addAll(movies);
                ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                        @Override
                        public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                            Intent intent = new Intent(getActivity(), DetailActivity.class);
                            intent.putExtra(DetailActivity.EXTRA_MOVIE, movies.get(position));
                            startActivity(intent); }
                    });
                }
                String error = getString(R.string.check_your_internet);
            @Override
            public void onError() {
                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}


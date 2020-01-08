package com.sururiana.apimoviecatalogue.search;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sururiana.apimoviecatalogue.DetailActivity;
import com.sururiana.apimoviecatalogue.R;
import com.sururiana.apimoviecatalogue.adapter.MoviesAdapter;
import com.sururiana.apimoviecatalogue.api.MoviesRepository;
import com.sururiana.apimoviecatalogue.api.OnGetSearch;
import com.sururiana.apimoviecatalogue.model.Movie;
import com.sururiana.apimoviecatalogue.utils.ItemClickSupport;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    public static String SEARCH = "query";
    RecyclerView recyclerView;
    MoviesAdapter adapter;
    MoviesRepository moviesRepository;
    ProgressBar progressBar;
    String query;
    ArrayList<Movie> movieList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        recyclerView = findViewById(R.id.rv_list);
        progressBar = findViewById(R.id.progressBar);
        moviesRepository = MoviesRepository.getInstance();

        Intent intent = getIntent();
        query = intent.getStringExtra(SEARCH);

        adapter = new MoviesAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        getSearch(query);
    }

    private void getSearch(String query) {
        moviesRepository.getSearch(query, new OnGetSearch() {
            @Override
            public void onSuccess(final ArrayList<Movie> movies) {
                progressBar.setVisibility(View.GONE);
                adapter.setMovies(movies);
                recyclerView.setAdapter(adapter);
                movieList.addAll(movies);
                ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Intent intent = new Intent(SearchActivity.this, DetailActivity.class);
                        intent.putExtra(DetailActivity.EXTRA_MOVIE, movies.get(position));
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onError() {
                String error = getString(R.string.check_your_internet);
                Toast.makeText(SearchActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

}

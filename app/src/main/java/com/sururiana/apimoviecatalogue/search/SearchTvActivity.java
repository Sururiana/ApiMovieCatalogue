package com.sururiana.apimoviecatalogue.search;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sururiana.apimoviecatalogue.DetailTvActivity;
import com.sururiana.apimoviecatalogue.R;
import com.sururiana.apimoviecatalogue.adapter.TvAdapter;
import com.sururiana.apimoviecatalogue.api.OnGetSearchTv;
import com.sururiana.apimoviecatalogue.api.TvRepository;
import com.sururiana.apimoviecatalogue.model.Tv;
import com.sururiana.apimoviecatalogue.utils.ItemClickSupport;

import java.util.ArrayList;

public class SearchTvActivity extends AppCompatActivity {
    public static String SEARCH_TV = "query";
    RecyclerView recyclerView;
    TvAdapter adapter;
    TvRepository tvRepository;
    ProgressBar progressBar;
    String query;
    ArrayList<Tv> tvList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_tv);
        recyclerView = findViewById(R.id.rv_list);
        progressBar = findViewById(R.id.progressBar);
        tvRepository = TvRepository.getInstance();

        Intent intent = getIntent();
        query = intent.getStringExtra(SEARCH_TV);

        adapter = new TvAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        getSearch(query);
    }

    private void getSearch(String query) {
        tvRepository.getSearch(query, new OnGetSearchTv() {
            @Override
            public void onSuccess(final ArrayList<Tv> tvs) {
                progressBar.setVisibility(View.GONE);
                adapter.setTvList(tvs);
                recyclerView.setAdapter(adapter);
                tvList.addAll(tvs);
                ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Intent intent = new Intent(SearchTvActivity.this, DetailTvActivity.class);
                        intent.putExtra(DetailTvActivity.EXTRA_TV, tvs.get(position));
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onError() {
                String error = getString(R.string.check_your_internet);
                Toast.makeText(SearchTvActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

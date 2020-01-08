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

import com.sururiana.apimoviecatalogue.DetailTvActivity;
import com.sururiana.apimoviecatalogue.search.SearchTvActivity;
import com.sururiana.apimoviecatalogue.utils.ItemClickSupport;
import com.sururiana.apimoviecatalogue.R;
import com.sururiana.apimoviecatalogue.adapter.TvAdapter;
import com.sururiana.apimoviecatalogue.api.OnGetTvCallback;
import com.sururiana.apimoviecatalogue.api.TvRepository;
import com.sururiana.apimoviecatalogue.model.Tv;

import java.util.ArrayList;

import static com.sururiana.apimoviecatalogue.search.SearchTvActivity.SEARCH_TV;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvshowFragment extends Fragment {
    private RecyclerView recyclerView;
    private TvAdapter adapter;
    private TvRepository tvRepository;
    public final static String LIST_STATE_KEY = "STATE";
    private ArrayList<Tv> tvList = new ArrayList<>();
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tvshow, container, false);
        recyclerView = view.findViewById(R.id.rv_list);
        progressBar = view.findViewById(R.id.progressBar);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        tvRepository = TvRepository.getInstance();

        SearchView sv = view.findViewById(R.id.search_view);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(getActivity(), SearchTvActivity.class);
                intent.putExtra(SEARCH_TV, query);
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
            final ArrayList<Tv> tvState = savedInstanceState.getParcelableArrayList(LIST_STATE_KEY);
            assert tvState != null;
            tvList.addAll(tvState);
            adapter = new TvAdapter(getContext());
            adapter.setTvList(tvState);
            recyclerView.setAdapter(adapter);
            ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    Intent intent = new Intent(getActivity(), DetailTvActivity.class);
                    intent.putExtra(DetailTvActivity.EXTRA_TV, tvState.get(position));
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
        outState.putParcelableArrayList(LIST_STATE_KEY, tvList); }

    public void getData() {
        tvRepository.getTvPopular(new OnGetTvCallback() {
            @Override
            public void onSuccess(final ArrayList<Tv> tvs) {
                adapter = new TvAdapter(getContext());
                adapter.setTvList(tvs);
                recyclerView.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);
                tvList.addAll(tvs);
                ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Intent intent = new Intent(getActivity(), DetailTvActivity.class);
                        intent.putExtra(DetailTvActivity.EXTRA_TV, tvs.get(position));
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

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

import com.sururiana.apimoviecatalogue.DetailTvActivity;
import com.sururiana.apimoviecatalogue.R;
import com.sururiana.apimoviecatalogue.adapter.FavTvAdapter;
import com.sururiana.apimoviecatalogue.data.TvHelper;
import com.sururiana.apimoviecatalogue.model.Tv;
import com.sururiana.apimoviecatalogue.utils.ItemClickSupport;
import com.sururiana.apimoviecatalogue.utils.LoadTvCallback;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteTvFragment extends Fragment implements LoadTvCallback{
    private RecyclerView rvTv;
    private FavTvAdapter favTvAdapter;
    private static final String EXTRA_STATE = "EXTRA_STATE";
    public FavoriteTvFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_tv, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvTv = view.findViewById(R.id.rv_list);
        rvTv.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTv.setHasFixedSize(true);

        TvHelper tvHelper = TvHelper.getInstance(getActivity());
        tvHelper.open();

        favTvAdapter = new FavTvAdapter(getActivity());
        rvTv.setAdapter(favTvAdapter);

        if (savedInstanceState == null) {
            new FavoriteTvFragment.LoadTvsAsync(tvHelper, (LoadTvCallback) this).execute();
        } else {
            ArrayList<Tv> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                favTvAdapter.setTvList(list);
            }
        }
    }

    @Override
    public void preExecute() {

    }

    @Override
    public void postExecute(final ArrayList<Tv> tvs) {
        favTvAdapter.setTvList(tvs);
        rvTv.setAdapter(favTvAdapter);
        ItemClickSupport.addTo(rvTv).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) { Intent intent = new Intent(getActivity(), DetailTvActivity.class);
                intent.putExtra(DetailTvActivity.EXTRA_TV, tvs.get(position));
                startActivity(intent);
            }
        });
    }

    private static class LoadTvsAsync extends AsyncTask<Void, Void, ArrayList<Tv>> {
        private final WeakReference<TvHelper> weakTvHelper;
        private final WeakReference<LoadTvCallback> weakCallback;

        private LoadTvsAsync(TvHelper tvHelper, LoadTvCallback callback) {
            weakTvHelper = new WeakReference<>(tvHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<Tv> doInBackground(Void... voids) {
            return weakTvHelper.get().getAllTv();
        }

        @Override
        protected void onPostExecute(ArrayList<Tv> tvs) {
            super.onPostExecute(tvs);
            weakCallback.get().postExecute(tvs);
        }
    }
}

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
import com.sururiana.favorite.adapter.FavTvAdapter;
import com.sururiana.favorite.data.TvHelper;

import java.util.Objects;

import static com.sururiana.favorite.data.FavoriteContract.CONTENT_URI_TV;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteTvFragment extends Fragment {
    RecyclerView recyclerView;
    FavTvAdapter favTvAdapter;
    private Cursor list;


    public FavoriteTvFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite_tv, container, false);
        recyclerView = view.findViewById(R.id.rv_list);
        TvHelper tvHelper = TvHelper.getInstance(getContext());
        tvHelper.open();
        new loadTv().execute();

        favTvAdapter = new FavTvAdapter(this.getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(favTvAdapter);
        favTvAdapter.setTv(list);
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
    }

    private class loadTv extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return Objects.requireNonNull(getContext()).getContentResolver().query(CONTENT_URI_TV,null,null,null,null);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(Cursor cursor_tv) {
            super.onPostExecute(cursor_tv);
            list = cursor_tv;
            favTvAdapter.setTv(list);
            favTvAdapter.notifyDataSetChanged();
        }
    }

}

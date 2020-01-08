package com.sururiana.apimoviecatalogue.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sururiana.apimoviecatalogue.R;
import com.sururiana.apimoviecatalogue.adapter.SectionPagerAdapter;
import com.sururiana.apimoviecatalogue.fragment.fav.FavoriteFragment;
import com.sururiana.apimoviecatalogue.fragment.fav.FavoriteTvFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavFragment extends Fragment {
    ViewPager viewPager;
    TabLayout tabLayout;


    public FavFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fav, container, false);
        viewPager = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.tabLayout);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setUpViewPager(ViewPager viewPager) {
        SectionPagerAdapter adapter = new SectionPagerAdapter(getChildFragmentManager());

        adapter.addFragment(new FavoriteFragment(),getString(R.string.movie));
        adapter.addFragment(new FavoriteTvFragment(),getString(R.string.tv_show));

        viewPager.setAdapter(adapter);
    }
}

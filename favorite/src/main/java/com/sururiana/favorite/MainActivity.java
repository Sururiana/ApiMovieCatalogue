package com.sururiana.favorite;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sururiana.favorite.adapter.SectionPagerAdapter;
import com.sururiana.favorite.fragment.FavoriteFragment;
import com.sururiana.favorite.fragment.FavoriteTvFragment;

public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setUpViewPager(ViewPager viewPager) {
        SectionPagerAdapter adapter = new SectionPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new FavoriteFragment(),"Movie");
        adapter.addFragment(new FavoriteTvFragment(),"Tv Show");
        viewPager.setAdapter(adapter);
    }
}

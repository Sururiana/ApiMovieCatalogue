package com.sururiana.apimoviecatalogue;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
//import com.squareup.picasso.Picasso;
import com.sururiana.apimoviecatalogue.api.OnGetDetailTv;
import com.sururiana.apimoviecatalogue.api.TvRepository;
import com.sururiana.apimoviecatalogue.data.TvHelper;
import com.sururiana.apimoviecatalogue.model.Tv;

public class DetailTvActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String EXTRA_TV = "extra_tv";
    public int tvId;
    ImageView thumnail;
    TextView txtTitle, txtDescription, txtRating, txt_release;
    String poster, rating;

    private TvHelper tvHelper;
    private TvRepository tvRepository;
    FloatingActionButton favoriteButton, deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Tv selectedTv = getIntent().getParcelableExtra(EXTRA_TV);
        String idTv = Integer.toString(selectedTv.getId());

        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        tvHelper = TvHelper.getInstance(getApplicationContext());
        tvHelper.open();

        favoriteButton = findViewById(R.id.btnFav);
        favoriteButton.setOnClickListener(this);

        deleteButton = findViewById(R.id.btnDel);
        deleteButton.setOnClickListener(this);

        if (tvHelper.checkMovie(idTv)) {
            favoriteButton.setVisibility(View.GONE);
            deleteButton.setVisibility(View.VISIBLE);
        }
        getTv();
    }

    private void getTv() {
        final ProgressBar progressBar = findViewById(R.id.progressBar);
        Tv selectedTv = getIntent().getParcelableExtra(EXTRA_TV);
        tvId = selectedTv.getId();
        tvRepository = TvRepository.getInstance();
        tvRepository.getTv(tvId, new OnGetDetailTv() {
            @Override
            public void onSuccess(Tv tv) {

                poster = tv.getPosterPath();
                thumnail = findViewById(R.id.img_object);

                txtTitle = findViewById(R.id.text_movie);
                txtTitle.setText(tv.getName());

                rating = Double.toString(tv.getVoteAverage());
                txtRating = findViewById(R.id.score_object);
                txtRating.setText(rating);

                txtDescription = findViewById(R.id.text_description);
                txtDescription.setText(tv.getOverview());

                txt_release = findViewById(R.id.text_release);
                txt_release.setText(tv.getFirstAirDate());

                if (!isFinishing()) {
                    Glide.with(getApplicationContext()).load(poster).
                            apply(new RequestOptions().placeholder(R.drawable.load).fitCenter()).into(thumnail);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError() {
                Toast.makeText(DetailTvActivity.this, getString(R.string.check_your_internet), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnFav) {
            Tv selectedTv = getIntent().getParcelableExtra(EXTRA_TV);
            String toastFav = getString(R.string.add);
            String toastFavFail = getString(R.string.fail);
            long result = tvHelper.insertTv(selectedTv);
            if (result > 0) {
                favoriteButton.setVisibility(View.GONE);
                deleteButton.setVisibility(View.VISIBLE);
                Toast.makeText(DetailTvActivity.this, toastFav, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(DetailTvActivity.this, toastFavFail, Toast.LENGTH_SHORT).show();
            }
        } else if (v.getId() == R.id.btnDel) {
            Tv selectedTv = getIntent().getParcelableExtra(EXTRA_TV);
            String toastDel = getString(R.string.remove);
            tvHelper.deleteTv(selectedTv.getId());
            Toast.makeText(DetailTvActivity.this, toastDel, Toast.LENGTH_SHORT).show();
            favoriteButton.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.GONE);
        }
    }
}

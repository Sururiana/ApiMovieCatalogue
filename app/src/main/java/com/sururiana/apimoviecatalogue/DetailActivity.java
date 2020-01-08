package com.sururiana.apimoviecatalogue;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sururiana.apimoviecatalogue.api.MoviesRepository;
import com.sururiana.apimoviecatalogue.api.OnGetDetailMovie;
import com.sururiana.apimoviecatalogue.data.MovieHelper;
import com.sururiana.apimoviecatalogue.model.Movie;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String EXTRA_MOVIE = "extra_movie";
    public int movieId;
    ImageView thumnail;
    TextView txtTitle, txtDescription, txtRating, txt_release;
    String poster, rating;

    private MovieHelper movieHelper;
    private MoviesRepository moviesRepository;
    FloatingActionButton favoriteButton, deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Movie selectedMovie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        String idMovie = Integer.toString(selectedMovie.getId());

        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        movieHelper = MovieHelper.getInstance(getApplicationContext());
        movieHelper.open();

        favoriteButton = findViewById(R.id.btnFav);
        favoriteButton.setOnClickListener(this);

        deleteButton = findViewById(R.id.btnDel);
        deleteButton.setOnClickListener(this);

        if (movieHelper.checkMovie(idMovie)) {
            favoriteButton.setVisibility(View.GONE);
            deleteButton.setVisibility(View.VISIBLE);
        }
        getMovie();
    }

    private void getMovie() {
        final ProgressBar progressBar = findViewById(R.id.progressBar);
        Movie selectedMovie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        movieId = selectedMovie.getId();
        moviesRepository = MoviesRepository.getInstance();
        moviesRepository.getMovie(movieId, new OnGetDetailMovie() {
            @Override
            public void onSuccess(Movie movie) {

                poster = movie.getPosterPath();
                thumnail = findViewById(R.id.img_object);

                txtTitle = findViewById(R.id.text_movie);
                txtTitle.setText(movie.getTitle());

                rating = Double.toString(movie.getVoteAverage());
                txtRating = findViewById(R.id.score_object);
                txtRating.setText(rating);

                txtDescription = findViewById(R.id.text_description);
                txtDescription.setText(movie.getOverview());

                txt_release = findViewById(R.id.text_release);
                txt_release.setText(movie.getReleaseDate());

                if (!isFinishing()) {
                    Glide.with(getApplicationContext()).load(poster).
                            apply(new RequestOptions().placeholder(R.drawable.load).fitCenter()).into(thumnail);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError() {
                Toast.makeText(DetailActivity.this, getString(R.string.check_your_internet), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnFav) {
            Movie selectedMovie = getIntent().getParcelableExtra(EXTRA_MOVIE);
            String toastFav = getString(R.string.add);
            String toastFavFail = getString(R.string.fail);
            long result = movieHelper.insertMovie(selectedMovie);
            if (result > 0) {
                favoriteButton.setVisibility(View.GONE);
                deleteButton.setVisibility(View.VISIBLE);
                Toast.makeText(DetailActivity.this, toastFav, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(DetailActivity.this, toastFavFail, Toast.LENGTH_SHORT).show();
            }
        } else if (v.getId() == R.id.btnDel) {
            Movie selectedMovie = getIntent().getParcelableExtra(EXTRA_MOVIE);
            String toastDel = getString(R.string.remove);
            movieHelper.deleteMovie(selectedMovie.getId());
            Toast.makeText(DetailActivity.this, toastDel, Toast.LENGTH_SHORT).show();
            favoriteButton.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.GONE);
        }
    }
}

package com.sururiana.apimoviecatalogue.api;

import android.support.annotation.NonNull;

import com.sururiana.apimoviecatalogue.BuildConfig;
import com.sururiana.apimoviecatalogue.model.Movie;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoviesRepository {

    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String LANGUAGE = "en-US";
    private static MoviesRepository repository;
    private DbApi api;

    private MoviesRepository(DbApi api) {
        this.api = api;
    }

    public static MoviesRepository getInstance() {
        if (repository == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            repository = new MoviesRepository(retrofit.create(DbApi.class));
        }

        return repository;
    }

    public void getMoviesPopular(final OnGetMoviesCallback callback) {
        String apiKey = BuildConfig.THE_MOVIE_DB_API_TOKEN;
        api.getPopularMovies(apiKey, LANGUAGE, 1)
                .enqueue(new Callback<MoviesResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                        if (response.isSuccessful()) {
                            MoviesResponse moviesResponse = response.body();
                            if (moviesResponse != null && moviesResponse.getMovies() != null) {
                                callback.onSuccess(moviesResponse.getMovies());
                            } else {
                                callback.onError();
                            }
                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable t) {
                        callback.onError();
                    }
                });
    }

    public void getMovie(int movieId, final OnGetDetailMovie callback) {
        api.getMovie(movieId, BuildConfig.THE_MOVIE_DB_API_TOKEN, LANGUAGE)
                .enqueue(new Callback<Movie>() {
                    @Override
                    public void onResponse(@NonNull Call<Movie> call,@NonNull Response<Movie> response) {
                        if (response.isSuccessful()) {
                            Movie movie = response.body();
                            if (movie != null) {
                                callback.onSuccess(movie);
                            } else {
                                callback.onError();
                            }
                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Movie> call, @NonNull Throwable t) {
                        callback.onError();
                    }
                });
    }

    public void getSearch(String query, final OnGetSearch callback) {
        String apiKey = BuildConfig.THE_MOVIE_DB_API_TOKEN;
        api.getSearch(query, apiKey)
                .enqueue(new Callback<MoviesResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                        if (response.isSuccessful()) {
                            MoviesResponse moviesResponse = response.body();
                            if (moviesResponse != null && moviesResponse.getMovies() != null) {
                                callback.onSuccess(moviesResponse.getMovies());
                            } else {
                                callback.onError();
                            }
                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable t) {
                        callback.onError();
                    }
                });
    }

    public void getRelease(String release, String released, final OnGetReleaseMovie callback) {
        String apiKey = BuildConfig.THE_MOVIE_DB_API_TOKEN;
        api.getRealiseMovies(apiKey, release, released)
                .enqueue(new Callback<MoviesResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                        if (response.isSuccessful()) {
                            MoviesResponse moviesResponse = response.body();
                            if (moviesResponse != null && moviesResponse.getMovies() != null) {
                                callback.onSuccess(moviesResponse.getMovies());
                            } else {
                                callback.onError();
                            }
                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable t) {
                        callback.onError();
                    }
                });
    }

}

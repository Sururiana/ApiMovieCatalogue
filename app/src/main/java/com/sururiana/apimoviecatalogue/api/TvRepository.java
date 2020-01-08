package com.sururiana.apimoviecatalogue.api;

import android.support.annotation.NonNull;

import com.sururiana.apimoviecatalogue.BuildConfig;
import com.sururiana.apimoviecatalogue.model.Tv;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TvRepository {
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String LANGUAGE = "en-US";
    private static TvRepository repository;

    private DbApi api;

    private TvRepository(DbApi api) {
        this.api = api;
    }

    public static TvRepository getInstance() {
        if (repository == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            repository = new TvRepository(retrofit.create(DbApi.class));
        }

        return repository;
    }

    public void getTvPopular(final OnGetTvCallback callback) {
        String apiKey = BuildConfig.THE_MOVIE_DB_API_TOKEN;
        api.getPopularTv(apiKey, LANGUAGE, 1)
                .enqueue(new Callback<TvResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<TvResponse> call, @NonNull Response<TvResponse> response) {
                        if (response.isSuccessful()) {
                            TvResponse TvResponse = response.body();
                            if (TvResponse != null && TvResponse.getTvs() != null) {
                                callback.onSuccess(TvResponse.getTvs());
                            } else {
                                callback.onError();
                            }
                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<TvResponse> call, @NonNull Throwable t) {
                        callback.onError();
                    }
                });
    }

    public void getTv(int tvId, final OnGetDetailTv callback) {
        api.getTv(tvId, BuildConfig.THE_MOVIE_DB_API_TOKEN, LANGUAGE)
                .enqueue(new Callback<Tv>() {
                    @Override
                    public void onResponse(@NonNull Call<Tv> call,@NonNull Response<Tv> response) {
                        if (response.isSuccessful()) {
                            Tv tv = response.body();
                            if (tv != null) {
                                callback.onSuccess(tv);
                            } else {
                                callback.onError();
                            }
                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Tv> call, @NonNull Throwable t) {
                        callback.onError();
                    }
                });
    }

    public void getSearch(String query, final OnGetSearchTv callback) {
        String apiKey = BuildConfig.THE_MOVIE_DB_API_TOKEN;
        api.getSearchTv(query, apiKey)
                .enqueue(new Callback<TvResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<TvResponse> call, @NonNull Response<TvResponse> response) {
                        if (response.isSuccessful()) {
                            TvResponse tvResponse = response.body();
                            if (tvResponse != null && tvResponse.getTvs() != null) {
                                callback.onSuccess(tvResponse.getTvs());
                            } else {
                                callback.onError();
                            }
                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<TvResponse> call, @NonNull Throwable t) {
                        callback.onError();
                    }
                });
    }

}

package com.sururiana.apimoviecatalogue.api;

import com.sururiana.apimoviecatalogue.model.Movie;
import com.sururiana.apimoviecatalogue.model.Tv;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DbApi {

    @GET("movie/popular")
    Call<MoviesResponse> getPopularMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );
    @GET("tv/popular")
    Call<TvResponse> getPopularTv(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );
    @GET("movie/{movie_id}")
    Call<Movie> getMovie(
            @Path("movie_id") int id,
            @Query("api_key") String apiKEy,
            @Query("language") String language
    );

    @GET("tv/{tv_id}")
    Call<Tv> getTv(
            @Path("tv_id") int id,
            @Query("api_key") String apiKEy,
            @Query("language") String language
    );

    @GET("search/movie")
    Call<MoviesResponse> getSearch(
            @Query("query") String query,
            @Query("api_key") String apiKey
    );

    @GET("search/tv")
    Call<TvResponse> getSearchTv(
            @Query("query") String query,
            @Query("api_key") String apiKey
    );

    @GET("discover/movie")
    Call<MoviesResponse> getRealiseMovies(
            @Query("api_key") String apiKey,
            @Query("primary_release_date.gte") String date_gte,
            @Query("primary_release_date.lte") String date_lte
    );

}
package com.sururiana.apimoviecatalogue.api;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sururiana.apimoviecatalogue.model.Movie;

import java.util.ArrayList;

class MoviesResponse {

    @SerializedName("results")
    @Expose
    private ArrayList<Movie> movies;

    ArrayList<Movie> getMovies() {
        return movies;
    }


}
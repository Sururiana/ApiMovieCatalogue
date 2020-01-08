package com.sururiana.apimoviecatalogue.utils;

import com.sururiana.apimoviecatalogue.model.Movie;

import java.util.ArrayList;

public interface LoadMovieCallback {
    void preExecute();

    void postExecute(ArrayList<Movie> movies);
}

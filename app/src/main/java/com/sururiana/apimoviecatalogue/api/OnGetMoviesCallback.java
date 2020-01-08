package com.sururiana.apimoviecatalogue.api;


import com.sururiana.apimoviecatalogue.model.Movie;

import java.util.ArrayList;

public interface OnGetMoviesCallback {
    void onSuccess(final ArrayList<Movie> movies);

    void onError();
}

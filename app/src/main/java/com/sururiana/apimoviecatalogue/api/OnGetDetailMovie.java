package com.sururiana.apimoviecatalogue.api;


import com.sururiana.apimoviecatalogue.model.Movie;

public interface OnGetDetailMovie {
    void onSuccess(Movie movie);

    void onError();
}

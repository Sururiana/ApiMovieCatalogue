package com.sururiana.apimoviecatalogue.api;


import com.sururiana.apimoviecatalogue.model.Movie;

import java.util.ArrayList;

public interface OnGetSearch {
    void onSuccess(ArrayList<Movie> movies);

    void onError();
}




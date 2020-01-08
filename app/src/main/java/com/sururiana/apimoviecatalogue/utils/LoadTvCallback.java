package com.sururiana.apimoviecatalogue.utils;

import com.sururiana.apimoviecatalogue.model.Movie;
import com.sururiana.apimoviecatalogue.model.Tv;

import java.util.ArrayList;

public interface LoadTvCallback {
    void preExecute();

    void postExecute(ArrayList<Tv> tvs);
}

package com.sururiana.apimoviecatalogue.api;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sururiana.apimoviecatalogue.model.Tv;

import java.util.ArrayList;

class TvResponse {
    @SerializedName("results")
    @Expose
    private ArrayList<Tv> tvs;

    ArrayList<Tv> getTvs() {
        return tvs;
    }
}



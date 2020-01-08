package com.sururiana.apimoviecatalogue.api;


import com.sururiana.apimoviecatalogue.model.Tv;

import java.util.ArrayList;

public interface OnGetSearchTv {
    void onSuccess(ArrayList<Tv> tvs);

    void onError();
}




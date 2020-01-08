package com.sururiana.apimoviecatalogue.api;


import com.sururiana.apimoviecatalogue.model.Tv;

import java.util.ArrayList;

public interface OnGetTvCallback {
    void onSuccess(ArrayList<Tv> tvs);

    void onError();
}

package com.sururiana.apimoviecatalogue.api;

import com.sururiana.apimoviecatalogue.model.Tv;

public interface OnGetDetailTv {
    void onSuccess(Tv tv);

    void onError();
}

package com.sururiana.apimoviecatalogue.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent i) {
        return new ViewsFactory(this.getApplicationContext(), i);
    }
}

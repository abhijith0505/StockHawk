package com.sam_chordas.android.stockhawk.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;


public class QuoteWidgetRemoteViewsService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        WidgetDataProvider dataProvider = new WidgetDataProvider(
                getApplicationContext(), intent);
        return dataProvider;
    }
}

package com.sam_chordas.android.stockhawk.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.sam_chordas.android.stockhawk.data.QuoteDatabase;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;
import com.sam_chordas.android.stockhawk.rest.CursorRecyclerViewAdapter;
import com.sam_chordas.android.stockhawk.rest.QuoteCursorAdapter;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("NewApi")
public class WidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {

    ArrayList<String> mCollections;

    Context mContext;

    public WidgetDataProvider(Context context, Intent intent) {
        mContext = context;
        mCollections = new ArrayList<>();
        initData();
    }
    @Override
    public void onCreate() {
        mCollections = new ArrayList<>();
        initData();
    }

    @Override
    public void onDataSetChanged() {
        initData();
    }

    @Override
    public void onDestroy() {
        initData();
    }



    @Override
    public int getCount() {
        return mCollections.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews mView = new RemoteViews(mContext.getPackageName(),
                android.R.layout.simple_list_item_1);
        if(mCollections.size()>0){
            mView.setTextViewText(android.R.id.text1, mCollections.get(position)+"");
            mView.setTextColor(android.R.id.text1, Color.WHITE);
        }

        return mView;
    }

    private void initData() {

        mCollections.clear();

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("widget", Context.MODE_PRIVATE);
        int c = sharedPreferences.getInt("size",0);

        for(int i=1;i<c;++i){
            mCollections.add(sharedPreferences.getString("item_"+i,null));
            Log.i("received",sharedPreferences.getString("item_"+i,null));
        }
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}

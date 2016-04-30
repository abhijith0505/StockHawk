package com.sam_chordas.android.stockhawk.ui;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.db.chart.Tools;
import com.db.chart.model.LineSet;
import com.db.chart.view.AxisController;
import com.db.chart.view.LineChartView;
import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;

import java.util.ArrayList;
import java.util.Collections;


public class GraphView extends AppCompatActivity {

    Cursor cursor;
    String item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_graph);

        item = getIntent().getExtras().getString("item");


        final ArrayList<Float> mValues = new ArrayList<>();

        LineSet dataset = new LineSet();

        LineChartView lineChartView = (LineChartView) findViewById(R.id.linechart);
        cursor = this.getContentResolver().query(QuoteProvider.Quotes.CONTENT_URI,
                new String[] { QuoteColumns.SYMBOL, QuoteColumns.BIDPRICE }, null,
                null, null);
        cursor.moveToFirst();
        mValues.clear();
        for(int i=0; i<cursor.getCount();++i){
            if(cursor.getString(cursor.getColumnIndex("symbol")).equals(item)){
                mValues.add(Float.parseFloat(cursor.getString(cursor.getColumnIndex("bid_price"))));
                dataset.addPoint(cursor.getString(cursor.getColumnIndex("symbol")),Float.parseFloat(cursor.getString(cursor.getColumnIndex("bid_price"))));
                Log.i("blah",cursor.getString(cursor.getColumnIndex("symbol"))+cursor.getString(cursor.getColumnIndex("bid_price")));
            }
            //Log.i("blah",cursor.getString(cursor.getColumnIndex("symbol"))+cursor.getString(cursor.getColumnIndex("bid_price")));
            cursor.moveToNext();
        }

        float min = mValues.get(0);
        float max = mValues.get(0);

        for(int i= 0; i<mValues.size();i++) {
            if(mValues.get(i) < min) min = mValues.get(i);
            if(mValues.get(i) > max) max = mValues.get(i);
        }

        dataset.setColor(Color.parseColor("#b3b5bb"))
                .setFill(Color.parseColor("#2d374c"))
                .setDotsColor(Color.parseColor("#ffc755"));
        lineChartView.addData(dataset);
        lineChartView.setBorderSpacing(Tools.fromDpToPx(15))
                .setLabelsColor(Color.WHITE)
                .setXLabels(AxisController.LabelPosition.NONE)
                .setXAxis(true)
                .setAxisBorderValues((int) Math.floor(min),(int)Math.ceil(max))
                .setYAxis(true);
        lineChartView.show();
    }

}

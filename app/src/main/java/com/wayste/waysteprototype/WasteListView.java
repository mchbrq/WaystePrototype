package com.wayste.waysteprototype;

import android.app.Activity;
import android.widget.ArrayAdapter;

/**
 * Created by michellebarraquio on 26/10/2017.
 */

public class WasteListView extends ArrayAdapter {

    private String[] names;
    private Integer[] imgIds;
    private Activity context;

    public WasteListView(Activity context, String[] names, Integer[] imgIds) {
        super(context, R.layout.activity_client, names);
        this.names = names;
        this.imgIds = imgIds;
        this.context = context;
    }
}

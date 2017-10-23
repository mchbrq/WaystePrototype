package com.wayste.waysteprototype;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;

public class RateCollectorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_collector);

        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        int stars = ratingBar.getNumStars();
        //TODO: save rating in db
    }

    public void submitRating(View view) {
        Intent intent = new Intent(this, ClientActivity.class);
        startActivity(intent);
    }
}

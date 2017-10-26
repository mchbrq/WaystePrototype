package com.wayste.waysteprototype;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;

public class RateCollectorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_collector);

        final AlertDialog alertDialog = new AlertDialog.Builder(RateCollectorActivity.this).create();
        alertDialog.setTitle("Thank you!");
        alertDialog.setMessage("You've earned 2 points and 10 XP!");
        alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();

        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        int stars = ratingBar.getNumStars();
        //TODO: save rating in db
    }

    public void submitRating(View view) {
        Intent intent = new Intent(this, ClientActivity.class);
        startActivity(intent);
    }
}

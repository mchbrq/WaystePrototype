package com.wayste.waysteprototype;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.logging.Logger;

public class ShowClientDetails extends AppCompatActivity {

    private static final Logger LOGGER = Logger.getLogger(ShowClientDetails.class.getName());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_client_details);

        Intent intent = getIntent();

        TextView itemListTextView = (TextView) findViewById(R.id.textView_ItemList);
        itemListTextView.setText(intent.getStringExtra("ITEM_LIST"));

        TextView addressTextView = (TextView) findViewById(R.id.textView_Address);
        addressTextView.setText(intent.getStringExtra("ADDRESS"));
    }

    public void completedRequest(View view) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("clientRequests/client");
        if(databaseReference != null) {
            databaseReference.removeValue(new DatabaseReference.CompletionListener() {

                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if(databaseError != null) {
                        LOGGER.info("Error is encountered...");
                        LOGGER.info(databaseError.getDetails());
                    }
                }
            });
        }
        setContentView(R.layout.activity_show_client_details);
        Intent intent = new Intent(this, CollectorActivity.class);
        startActivity(intent);
    }
}

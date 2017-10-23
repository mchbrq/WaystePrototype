package com.wayste.waysteprototype;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ShowClientDetails extends AppCompatActivity {

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
            databaseReference.removeValue();
        }

        Intent intent = new Intent(this, CollectorActivity.class);
        startActivity(intent);
    }
}

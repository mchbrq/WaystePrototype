package com.wayste.waysteprototype;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class CollectorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collector);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("clientRequests/client");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String status = ((Map) dataSnapshot.getValue()).get("status").toString();
                if(status.equals("assigned")) return;

                String itemList = ((Map) dataSnapshot.getValue()).get("itemList").toString();
                if(itemList != null) {
                    sendAlert((Map) dataSnapshot.getValue());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void sendAlert(final Map<String, String> clientRequest) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CollectorActivity.this);
        alertDialogBuilder.setNegativeButton("DECLINE",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialogBuilder.setPositiveButton("ACCEPT",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        updateClientRequestStatus(clientRequest);
                        saveCollectorDetails();
                        Intent intent = new Intent(CollectorActivity.this, ShowClientDetails.class);
                        intent.putExtra("ITEM_LIST", clientRequest.get("itemList"));
                        intent.putExtra("ADDRESS", clientRequest.get("address"));
                        intent.putExtra("LOCATION", clientRequest.get("location"));
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setTitle("Collection Request");
        alertDialog.setMessage("New collection request.");
        alertDialog.show();
    }

    private void saveCollectorDetails() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("collectorDetails/collector/location");
        reference.setValue("123 Buendia Street, corner Makati Ave, Makati City");
    }

    private void updateClientRequestStatus(Map<String, String> clientRequest) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("clientRequests/client/status");
        reference.setValue("assigned");
    }

}

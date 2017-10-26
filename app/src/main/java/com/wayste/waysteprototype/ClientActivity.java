package com.wayste.waysteprototype;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Visibility;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        CheckBox bioCheckBox= (CheckBox) findViewById (R.id.checkBox_biodegradable);
        bioCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = (TextView) findViewById(R.id.textView_InvalidMessage);
                if (((CheckBox) v).isChecked()) {
                    textView.setVisibility(View.INVISIBLE);
                }
            }
        });

        CheckBox electronicWasteCheckBox= (CheckBox) findViewById (R.id.checkBox_ElectronicWastes);
        electronicWasteCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = (TextView) findViewById(R.id.textView_InvalidMessage);
                if (((CheckBox) v).isChecked()) {
                    textView.setVisibility(View.INVISIBLE);
                }
            }
        });

        CheckBox bulkWasteCheckBox= (CheckBox) findViewById (R.id.checkBox_BulkWastes);
        bulkWasteCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = (TextView) findViewById(R.id.textView_InvalidMessage);
                if (((CheckBox) v).isChecked()) {
                    textView.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    public void saveRequest(View view) {
        List<String> disposeList = new ArrayList<>();
        CheckBox bio_checkBox = (CheckBox) findViewById(R.id.checkBox_biodegradable);
        if(bio_checkBox.isChecked()) {
            disposeList.add(bio_checkBox.getText().toString());
        }
        CheckBox bulkWaste_checkBox = (CheckBox) findViewById(R.id.checkBox_BulkWastes);
        if(bulkWaste_checkBox.isChecked()) {
            disposeList.add(bulkWaste_checkBox.getText().toString());
        }
        CheckBox electronic_checkBox = (CheckBox) findViewById(R.id.checkBox_ElectronicWastes);
        if(electronic_checkBox.isChecked()) {
            disposeList.add(electronic_checkBox.getText().toString());
        }

        if(disposeList.isEmpty()) {
            TextView textView = (TextView) findViewById(R.id.textView_InvalidMessage);
            textView.setVisibility(View.VISIBLE);
        } else {
            saveToDatabase(disposeList);
            showPopupWaitingBar();

        }
    }

    private void showPopupWaitingBar() {
        final ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Finding a collector for your garbage... ");
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setIndeterminate(true);
        progress.show();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("clientRequests/client/status");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String status = dataSnapshot.getValue().toString();
                if(status.equals("assigned")) {
                    progress.dismiss();
                    showSuccessPopupAlert();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                progress.dismiss();
            }
        });
    }

    private void showSuccessPopupAlert() {
        final AlertDialog alertDialog = new AlertDialog.Builder(ClientActivity.this).create();
        alertDialog.setTitle("We've found a collector!");
        alertDialog.setMessage("Please prepare PHP50 for the collector. Your PHP50 will go a long way for the sustainability of the project. Help us help Mother Earth.");
        alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
                Intent intent = new Intent(ClientActivity.this, ShowCollectorDetails.class);
                startActivity(intent);
            }
        });
        alertDialog.show();
    }

    private void saveToDatabase(List<String> disposeList) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("clientRequests/client");
        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setStatus("unassigned");
        clientRequest.setItemList(toString(disposeList));
        clientRequest.setAddress("123 Paseo de Roxas, Legazpi Village, Makati, 1229 Metro Manila, Philippines");
        databaseReference.setValue(clientRequest);
    }

    private String toString(List<String> disposeList) {
        StringBuilder sb = new StringBuilder();
        for(String item : disposeList) {
            if(sb.toString().length() > 0) {
                sb.append(",");
            }
            sb.append(item);
        }
        return sb.toString();
    }

    class ClientRequest {
        private String itemList;
        private String status;
        private String address;

        public String getItemList() {
            return itemList;
        }

        public void setItemList(String itemList) {
            this.itemList = itemList;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

}

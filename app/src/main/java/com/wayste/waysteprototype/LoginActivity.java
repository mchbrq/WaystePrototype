package com.wayste.waysteprototype;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void navigateToClientActivity(View view) {
        Intent intent = new Intent(this, ClientActivity.class);
        startActivity(intent);
    }

    public void navigateToCollectorActivity(View view) {
        Intent intent = new Intent(this, CollectorActivity.class);
        startActivity(intent);
    }
}

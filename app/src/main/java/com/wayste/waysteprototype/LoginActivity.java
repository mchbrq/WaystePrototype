package com.wayste.waysteprototype;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.logging.Logger;

public class LoginActivity extends AppCompatActivity {

    private static Logger LOGGER = Logger.getLogger(LoginActivity.class.getName());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button disposerBtn = findViewById(R.id.button_disposer);
        disposerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LOGGER.info("Logging in as waste disposer...");
                processLogin(ClientActivity.class, "wayste.client@gmail.com", "Password@123$");
            }
        });

        Button collectorBtn = findViewById(R.id.button_collector);
        collectorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LOGGER.info("Logging in as waste collector...");
                processLogin(CollectorActivity.class, "wayste.collector@gmail.com", "Password@123$");
            }
        });

        Button processorBtn = findViewById(R.id.button_processor);
        processorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LOGGER.info("Logging in as waste processor...");
                processLogin(ProcessorActivity.class, "wayste.collector@gmail.com", "Password@123$");
            }
        });

    }

    private void processLogin(Class clazz, String email, String password) {
        login(email, password);
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    private void login(String email, String password) {
        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            System.out.println("createUserWithEmail:success");
//                            FirebaseUser user = firebaseAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            System.out.println("createUserWithEmail:failure");

                        }
                    }
                });
    }
}

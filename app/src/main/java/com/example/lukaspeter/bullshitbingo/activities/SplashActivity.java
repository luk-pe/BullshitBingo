package com.example.lukaspeter.bullshitbingo.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.lukaspeter.bullshitbingo.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.internal.InternalTokenProvider;

public class SplashActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Init firebase
        FirebaseApp.initializeApp(getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            Intent main = new Intent(this, MainActivity.class);
            startActivity(main);
        } else {
            Intent login = new Intent(this, LoginActivity.class);
            startActivity(login);
        }
    }

}

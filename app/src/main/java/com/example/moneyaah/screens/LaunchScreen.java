package com.example.moneyaah.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.moneyaah.R;
import com.example.moneyaah.activity.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LaunchScreen extends AppCompatActivity {

    FirebaseUser mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance().getCurrentUser();
        setContentView(R.layout.activity_launch_screen);
        getSupportActionBar().hide();
        Handler handler = new Handler();
        handler.postDelayed(this::nextActivity, 1000);
    }

    public void nextActivity() {
        Intent intent;
        if (mAuth == null) {
            intent = new Intent(LaunchScreen.this, WelcomeScreen.class);
        } else {
            intent = new Intent(LaunchScreen.this, MainActivity.class);
        }
        startActivity(intent);
        finish();
    }
}
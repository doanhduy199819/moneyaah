package com.example.moneyaah.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.moneyaah.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class IntroActivity extends Activity {
    FirebaseUser mAuth = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        Handler handler = new Handler();
        handler.postDelayed(this::nextActivity, 1000);
    }

    public void nextActivity() {
        Intent intent;
        if (mAuth == null) {
            intent = new Intent(IntroActivity.this, LoginActivity.class);
        } else {
            intent = new Intent(IntroActivity.this, MainActivity.class);
        }
        startActivity(intent);
    }
}
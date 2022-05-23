package com.example.moneyaah.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.moneyaah.R;

public class WelcomeScreen extends AppCompatActivity {

    private ImageButton btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        getSupportActionBar().hide();

        btnSignIn = (ImageButton) findViewById(R.id.btn_sign_in);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInScreenIntent = new Intent(WelcomeScreen.this, SignInScreen.class);
                startActivity(signInScreenIntent);
            }
        });
    }
}
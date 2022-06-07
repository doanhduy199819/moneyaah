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
    private ImageButton btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        getSupportActionBar().hide();

        btnSignIn = (ImageButton) findViewById(R.id.btn_sign_in);
        btnSignUp = (ImageButton) findViewById(R.id.btn_sign_up);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInScreenIntent = new Intent(WelcomeScreen.this, SignInScreen.class);
                startActivity(signInScreenIntent);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpScreenIntent = new Intent(WelcomeScreen.this, SignUpScreen.class);
                startActivity(signUpScreenIntent);
            }
        });
    }
}
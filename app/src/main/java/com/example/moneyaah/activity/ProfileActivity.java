package com.example.moneyaah.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.moneyaah.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class ProfileActivity extends AppCompatActivity {

    BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setUpUI();
    }

    private void setUpUI() {

        // Bottom Navigation Bar
        mBottomNavigationView = findViewById(R.id.bottomNavigationView);
        mBottomNavigationView.setSelectedItemId(R.id.profile);
        mBottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.wallet:
                        Intent intent = new Intent(ProfileActivity.this, WalletActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.notification:
                        Intent intent2 = new Intent(ProfileActivity.this, MainActivity.class);
                        startActivity(intent2);
                        return true;
                    case R.id.profile:
                        return true;
                }
                return false;
            }
        });

        // Animate smoothly
        overridePendingTransition(0,0);
    }
}
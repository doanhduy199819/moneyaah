package com.example.moneyaah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class NotificationActivity extends AppCompatActivity {

    BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        setUpUI();
    }

    private void setUpUI() {

        // Bottom Navigation Bar
        mBottomNavigationView = findViewById(R.id.bottomNavigationView);
        mBottomNavigationView.setSelectedItemId(R.id.notification);
        mBottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.wallet:
                        Intent intent = new Intent(NotificationActivity.this, WalletActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.notification:
                        return true;
                    case R.id.profile:
                        Intent intent2 = new Intent(NotificationActivity.this, ProfileActivity.class);
                        startActivity(intent2);
                        return true;
                }
                return false;
            }
        });

        // Animate smoothly
        overridePendingTransition(0,0);
    }
}
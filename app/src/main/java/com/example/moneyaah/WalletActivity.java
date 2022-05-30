package com.example.moneyaah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class WalletActivity extends AppCompatActivity {

    BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        setUpUI();
    }

    private void setUpUI() {

        // Bottom Navigation Bar
        mBottomNavigationView = findViewById(R.id.bottomNavigationView);
        mBottomNavigationView.setSelectedItemId(R.id.wallet);
        mBottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.wallet:
                        return true;
                    case R.id.notification:
                        Intent intent = new Intent(WalletActivity.this, MainActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.profile:
                        Intent intent2 = new Intent(WalletActivity.this, ProfileActivity.class);
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
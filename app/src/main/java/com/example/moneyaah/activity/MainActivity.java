package com.example.moneyaah.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.moneyaah.Helper;
import com.example.moneyaah.Record;
import com.example.moneyaah.RecordData;
import com.example.moneyaah.StatisticsFragment;
import com.example.moneyaah.fragment.NotificationFragment;
import com.example.moneyaah.fragment.ProfileFragment;
import com.example.moneyaah.R;
import com.example.moneyaah.fragment.WalletFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView mBottomNavigationView;

    Fragment walletFragment;
    Fragment notiFragment;
    Fragment profileFragment;
    FirebaseUser mUser;
    Fragment statisticsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mUser != null) {
            String user = mUser.getEmail();
            Helper.saveUser(this, user);
        } else {
            Helper.navigate(MainActivity.this, LoginActivity.class, this);
        }

        RecordData r = RecordData.getInstance();
//        List<Record> todayList = r.getTodayList();
        List<Record> dayTwo = r.getList(2, Calendar.JUNE);
//        List<List<Record>> thisMonthList = r.getList(Calendar.JUNE);

        setUpUI();
    }

    private void setUpUI() {

        // Initialize Fragments
        walletFragment = new WalletFragment();
        notiFragment = new NotificationFragment();
        profileFragment = new ProfileFragment();
        statisticsFragment = new StatisticsFragment();

        // Set wallet by default
        loadFragment(walletFragment);

        // Bottom Navigation Bar
        mBottomNavigationView = findViewById(R.id.bottomNavigationView);
        mBottomNavigationView.setSelectedItemId(R.id.wallet);
        mBottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.wallet:
                        loadFragment(walletFragment);
                        return true;
                    case R.id.statistics:
                        loadFragment(statisticsFragment);
                        return true;
                    case R.id.notification:
                        loadFragment(notiFragment);
                        return true;
                    case R.id.profile:
                        loadFragment(profileFragment);
                        return true;
                }
                return false;
            }
        });
    }

    private void loadFragment(Fragment f) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment currentFragment = fm.findFragmentById(R.id.fragment_container);
        if (f.equals(currentFragment)) {
            Toast.makeText(MainActivity.this, "Same Screen", Toast.LENGTH_SHORT).show();
            return;
        }

        fm.beginTransaction()
                .replace(R.id.fragment_container, f)
                .commit();
    }

    private void logout() {
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                Helper.logout();
            }
        }, 200);

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
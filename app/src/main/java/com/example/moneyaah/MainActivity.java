//<<<<<<< HEAD
////package com.example.moneyaah;
////
////import androidx.appcompat.app.AppCompatActivity;
////
////import android.os.Bundle;
////
////public class MainActivity extends AppCompatActivity {
////
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        getSupportActionBar().hide();
////        setContentView(R.layout.activity_welcome_screen);
////    }
////}
//=======
package com.example.moneyaah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.moneyaah.NotificationFragment;
import com.example.moneyaah.ProfileFragment;
import com.example.moneyaah.R;
import com.example.moneyaah.Record;
import com.example.moneyaah.RecordData;
import com.example.moneyaah.StatisticsFragment;
import com.example.moneyaah.WalletFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView mBottomNavigationView;

    Fragment walletFragment;
    Fragment notiFragment;
    Fragment profileFragment;
    Fragment statisticsFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}
//>>>>>>> c7a3bd8e9df7a0ade5c51b23a04bace53636fd0d

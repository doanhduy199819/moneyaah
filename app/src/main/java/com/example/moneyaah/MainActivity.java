package com.example.moneyaah;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.moneyaah.classes.Record;
import com.example.moneyaah.classes.RecordData;
import com.example.moneyaah.classes.UserDData;
import com.example.moneyaah.fragment.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity {


    ProgressDialog mProgressDialog;

    BottomNavigationView mBottomNavigationView;

    Fragment walletFragment;
    Fragment notiFragment;
    Fragment profileFragment;
    Fragment statisticsFragment;
    Fragment goalFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecordData r = RecordData.getInstance();
        List<Record> dayTwo = r.getList(2, Calendar.JUNE);
        setUpUI();
    }

    @Override
    protected void onStart() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("LOADING");
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();

        handleStart();

        handleStart();
        super.onStart();
    }

    private void handleStart() {
        String username = Helper.getUsername(this);
        DatabaseReference recordDbRef = Helper.getDataRef("User/" + username + "/Records");
        recordDbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Record> records = new ArrayList<>();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Record record = postSnapshot.getValue(Record.class);
                    records.add(record);
                }
                UserDData.get().getData().setmRecList(records);
                if (mProgressDialog != null && mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void setUpUI() {

        // Initialize Fragments
        walletFragment = new WalletFragment();
        notiFragment = new com.example.moneyaah.NotificationFragment();
        profileFragment = new ProfileFragment();
        statisticsFragment = new StatisticsFragment();
        goalFragment = new GoalFragment();

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
                    case R.id.goal:
                        loadFragment(goalFragment);
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

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("LOADING");
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
        this.handleStart();
        super.onResume();
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

    private void reloadFragment(Fragment f) {
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

//    private void getListRecord() {
//        String username = Helper.getUsername(this);
//        DatabaseReference recordDbRef = Helper.getDataRef("User/" + username + "/Records");
//
//        recordDbRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                List<Record> records = new ArrayList<>();
//                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
//                    Record record = postSnapshot.getValue(Record.class);
//                    records.add(record);
//                }
//                UserDData.get().getData().setmRecList(records);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });
//    }

}
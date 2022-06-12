package com.example.moneyaah.activity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.moneyaah.GoalFragment;
import com.example.moneyaah.Helper;
import com.example.moneyaah.NotificationFragment;
import com.example.moneyaah.R;
import com.example.moneyaah.StatisticsFragment;
import com.example.moneyaah.classes.Record;
import com.example.moneyaah.classes.RecordData;
import com.example.moneyaah.fragment.ProfileFragment;
import com.example.moneyaah.fragment.WalletFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static double amount;
    public static ArrayList<Record> records = new ArrayList<>();
    BottomNavigationView mBottomNavigationView;

    Fragment walletFragment;
    Fragment notiFragment;
    Fragment profileFragment;
    FirebaseUser mUser;
    Fragment statisticsFragment;
    Fragment goalFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        getListRecord();
        getUserCurrentAmount();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            records.forEach(record -> Log.d("RECORD", record.toString()));
        }
        RecordData r = RecordData.getInstance();
//        List<Record> todayList = r.getTodayList();
        List<Record> dayTwo = r.getList(2, Calendar.JUNE);
//        List<List<Record>> thisMonthList = r.getList(Calendar.JUNE);

        setUpUI();
    }

    private void getUserCurrentAmount() {
        String username = Helper.getUsername();
        DatabaseReference db = Helper.getDataRef("User/"+username+"/Amount");
        ValueEventListener amountListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                amount = dataSnapshot.getValue(Double.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        db.addValueEventListener(amountListener);
    }

    private void getListRecord() {
        String username = Helper.getUsername();
        DatabaseReference recordDbRef = Helper.getDataRef("User/" + username + "/Records");
        recordDbRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Record record = snapshot.getValue(Record.class);
                records.add(record);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Record record = snapshot.getValue(Record.class);
                int index = -1;
                for (Record record1 : records) {
                    if (record.getId() == (record1.getId())) {
                        index = records.indexOf(record1);
                    }
                }
                records.set(index, record);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Record record = snapshot.getValue(Record.class);
                records.remove(record);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Failed to load comments.",
                        Toast.LENGTH_SHORT).show();
            }
        });
        recordDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                records.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Record record = postSnapshot.getValue(Record.class);
                    records.add(record);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


    private void setUpUI() {

        // Initialize Fragments
        walletFragment = new WalletFragment();
        notiFragment = new NotificationFragment();
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
    }
}
package com.example.moneyaah;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.moneyaah.classes.Record;
import com.example.moneyaah.classes.UserDData;
import com.example.moneyaah.fragment.ProfileFragment;
import com.example.moneyaah.screens.NoteScreen;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    public List<String> expenseCategory;
    public List<String> incomeCategory;
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
        setUpUI();
        getIncomeCategory();
        getExpenseCategory();
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
        mBottomNavigationView.setOnItemSelectedListener(item -> {
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
        });
    }

    @Override
    protected void onResume() {
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


    private void getExpenseCategory() {
        DatabaseReference expenseRef = Helper.getDataRef("Category/Expense");
        expenseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                expenseCategory = new ArrayList<>();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    String category = postSnapshot.getValue(String.class);
                    expenseCategory.add(category);
                }
                ((MyApplication) MainActivity.this.getApplication()).setExpenseCategory(expenseCategory);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void getIncomeCategory() {
        DatabaseReference incomeRef = Helper.getDataRef("Category/Income");
        incomeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                incomeCategory = new ArrayList<>();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    String category = postSnapshot.getValue(String.class);
                    incomeCategory.add(category);
                }
                ((MyApplication) MainActivity.this.getApplication()).setIncomeCategory(incomeCategory);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

}
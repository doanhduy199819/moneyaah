package com.example.moneyaah.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.moneyaah.Helper;
import com.example.moneyaah.R;
import com.example.moneyaah.MainActivity;
import com.example.moneyaah.classes.Record;
import com.example.moneyaah.classes.UserDData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LaunchScreen extends AppCompatActivity {

    FirebaseUser mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_screen);
        getSupportActionBar().hide();
        getListRecord();
    }


    private void getListRecord() {
        mAuth = FirebaseAuth.getInstance().getCurrentUser();
        if (mAuth != null) {
            String username = Helper.getUsername(this);
            DatabaseReference recordDbRef = Helper.getDataRef("User/" + username + "/Records");
            recordDbRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    List<Record> records = new ArrayList<>();
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        Record record = postSnapshot.getValue(Record.class);
                        records.add(record);
                    }
                    UserDData.get().getData().setmRecList(records);
                    nextActivity();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        } else {
            nextActivity();
        }
    }

    public void nextActivity() {
        Intent intent;
        if (mAuth == null) {
            intent = new Intent(LaunchScreen.this, WelcomeScreen.class);
        } else {
            intent = new Intent(LaunchScreen.this, MainActivity.class);
        }
        startActivity(intent);
        finish();
    }
}
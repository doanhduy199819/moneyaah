package com.example.moneyaah.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.style.UpdateLayout;
import android.util.Log;

import com.example.moneyaah.Helper;
import com.example.moneyaah.R;
import com.example.moneyaah.MainActivity;
import com.example.moneyaah.classes.Record;
import com.example.moneyaah.classes.UploadRecord;
import com.example.moneyaah.classes.UserDData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
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
                    List<UploadRecord> records = new ArrayList<>();
                    List<Record> lrecords = new ArrayList<>();
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        UploadRecord record = postSnapshot.getValue(UploadRecord.class);
                        records.add(record);
                    }
                    for (int i = 0; i < records.size(); i++) {
                        UploadRecord record = records.get(i);
                        Record irecord = new Record(new Date(record.getDate()), record.getType(), record.getMoney(), record.getCategory(), record.getDescription());
                        lrecords.add(irecord);
                    }
                    UserDData.get().getData().setmRecList(lrecords);
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
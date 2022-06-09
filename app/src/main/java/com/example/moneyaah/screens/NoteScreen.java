package com.example.moneyaah.screens;

import static com.example.moneyaah.activity.MainActivity.records;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.moneyaah.Helper;
import com.example.moneyaah.R;
import com.example.moneyaah.Record;
import com.example.moneyaah.ViewPagerAdapter;
import com.example.moneyaah.activity.MainActivity;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class NoteScreen extends AppCompatActivity {

    private TabLayout mtabLayout;
    private ViewPager mviewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        mtabLayout = findViewById(R.id.tabLayout);
        mviewPager = findViewById(R.id.viewpager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mviewPager.setAdapter(viewPagerAdapter);
        getListRecord();
        mtabLayout.setupWithViewPager(mviewPager);
    }

    private void getListRecord() {
        String username = Helper.getUsername(this);
        DatabaseReference recordDbRef = Helper.getDataRef("User/admin12345/Records");
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
                if (index != -1)
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
                Toast.makeText(NoteScreen.this, "Failed to load comments.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
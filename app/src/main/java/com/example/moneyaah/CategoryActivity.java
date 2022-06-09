package com.example.moneyaah;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class CategoryActivity extends AppCompatActivity {

    ViewPager mViewPager;
    TabLayout mTabLayout;
    FloatingActionButton mAddCategoryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        setUpUI();
        setUpEvent();
    }

    private void setUpUI() {
        mViewPager = findViewById(R.id.category_pager);
        mTabLayout = findViewById(R.id.category_tab);
        mAddCategoryButton = findViewById(R.id.button_add_category);
    }
    private void setUpEvent() {
        mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return 2;
            }

            @NonNull
            @Override
            public Fragment getItem(int position) {
                return new CategoryFragment(position);
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                String title = "";
                switch (position){
                    case 0:
                        title = "Income";
                        break;
                    case 1:
                        title = "Expense";
                        break;

                }
                return title;
            }
        });
        mTabLayout.setupWithViewPager(mViewPager);
        mAddCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
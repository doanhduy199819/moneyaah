package com.example.moneyaah;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryWalletFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryWalletFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ViewPager mViewPager;
    private int currentMonth;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HistoryWalletFragment() {
        // Required empty public constructor

        currentMonth = Calendar.getInstance().get(Calendar.MONTH);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryWalletFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryWalletFragment newInstance(String param1, String param2) {
        HistoryWalletFragment fragment = new HistoryWalletFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history_wallet, container, false);

        mViewPager = view.findViewById(R.id.history_pager);
        FragmentManager fm = getChildFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public int getCount() {
                return 12;  // 12 thang
            }

            @NonNull
            @Override
            public Fragment getItem(int position) {
                Fragment fragment = new MonthHistoryFragment(position);
                return fragment;
            }
        });
        mViewPager.setCurrentItem(currentMonth);
        return view;
    }
}
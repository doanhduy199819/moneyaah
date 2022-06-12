package com.example.moneyaah.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.moneyaah.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WalletFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WalletFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FloatingActionButton button;
    private String mParam1;
    private String mParam2;


    public WalletFragment() {
    }

    public static WalletFragment newInstance(String param1, String param2) {
        WalletFragment fragment = new WalletFragment();
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
        return inflater.inflate(R.layout.fragment_wallet, container, false);
    }

//    public void getAmount() {
//        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
//        DatabaseReference myRef = database.child("admin").getRef();
//        ValueEventListener amountListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                String value = dataSnapshot.getValue(String.class);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        };
//        myRef.addValueEventListener(amountListener);
//    }

}
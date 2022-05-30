package com.example.moneyaah;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    ImageButton editButton;
    Button updateButton;

    TextView email, password, reenterPassword, reenterLabel;
    EditText edit_email, edit_password, edit_reenter_password;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        // Set up UI
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        reenterPassword = view.findViewById(R.id.reenter_password);
        reenterLabel = view.findViewById(R.id.reenter_password_label);

        edit_email = view.findViewById(R.id.edit_email);
        edit_password = view.findViewById(R.id.edit_password);
        edit_reenter_password = view.findViewById(R.id.edit_reenter_password);


        editButton = view.findViewById(R.id.button_edit);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email.setVisibility(View.INVISIBLE);
                password.setVisibility(View.INVISIBLE);
//                reenterPassword.setVisibility(View.INVISIBLE);

                edit_email.setVisibility(View.VISIBLE);
                edit_password.setVisibility(View.VISIBLE);
                reenterLabel.setVisibility(View.VISIBLE);
                edit_reenter_password.setVisibility(View.VISIBLE);

                updateButton.setEnabled(true);
            }
        });

        updateButton = view.findViewById(R.id.button_update);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailText = edit_email.getText().toString();
                String passwordText = edit_password.getText().toString();
                String reenterText = edit_reenter_password.getText().toString();

                if (passwordText != reenterText) {
                    Toast.makeText(getActivity(), "Password does not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                email.setText(emailText);
                password.setText(passwordText);
                reenterPassword.setText(reenterText);

                email.setVisibility(View.VISIBLE);
                password.setVisibility(View.VISIBLE);
                reenterPassword.setVisibility(View.VISIBLE);

                edit_email.setVisibility(View.INVISIBLE);
                edit_password.setVisibility(View.INVISIBLE);
                reenterLabel.setVisibility(View.INVISIBLE);
                reenterPassword.setVisibility(View.INVISIBLE);
                edit_reenter_password.setVisibility(View.INVISIBLE);

                updateButton.setEnabled(false);
            }
        });
        return view;
    }
}
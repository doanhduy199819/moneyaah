package com.example.moneyaah.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moneyaah.Constants;
import com.example.moneyaah.Helper;
import com.example.moneyaah.R;
import com.example.moneyaah.screens.SignInScreen;
import com.example.moneyaah.screens.WelcomeScreen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    FirebaseUser mUser;

    ImageButton editButton;
    Button updateButton;
    Button signout;

    TextView emailContent;
    EditText editEmail, editOldPassword, editNewPassword, editConfirmPassword;

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
        mUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        setUpUI(view);
        setUpEvent(view);
        handleUsername();

        return view;
    }

    private void handleUsername() {
        String username = Helper.getUsername(requireActivity());
        editEmail.setText(username);
    }

    private void setUpUI(View mainView) {
        editButton = mainView.findViewById(R.id.button_new_goal);

        emailContent = mainView.findViewById(R.id.email_content);
        editEmail = mainView.findViewById(R.id.edit_email);

        editOldPassword = mainView.findViewById(R.id.edit_old_password);
        editNewPassword = mainView.findViewById(R.id.edit_new_password);
        editConfirmPassword = mainView.findViewById(R.id.edit_confirm_new_password);

        updateButton = mainView.findViewById(R.id.button_update);
        signout = mainView.findViewById(R.id.btn_sign_out);
    }

    private void setUpEvent(View mainView) {

        // Edit
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailContent.setVisibility(View.INVISIBLE);
                editEmail.setVisibility(View.VISIBLE);
                editEmail.setText(Helper.getUsername(requireActivity()));
                emailContent.setText(Helper.getUsername(requireActivity()));
            }
        });

        // Update
        updateButton.setOnClickListener(v -> {

            // Information
            emailContent.setText(editEmail.getText());
            emailContent.setVisibility(View.VISIBLE);
            editEmail.setVisibility(View.INVISIBLE);
            String email = editEmail.getText().toString();
            // Password
            String password = "1234";
            String oldPass = editOldPassword.getText().toString();
            String newPass = editNewPassword.getText().toString();
            String confirmPass = editConfirmPassword.getText().toString();

            if (true) {
                // Wrong password
            } else if (!newPass.equals(confirmPass)) {
                editNewPassword.setText("");
                editConfirmPassword.setText("");
            } else {
                // Change user password
                updatePassword(editNewPassword.getText().toString());
                editOldPassword.setText("");
                editNewPassword.setText("");
                editConfirmPassword.setText("");
            }
            if (!email.equals("")) {
                updateEmail(email);
            }
        });

        signout.setOnClickListener(v -> {
            Helper.logout();
            Toast.makeText(requireActivity(), "Logout successfully", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(requireActivity(), WelcomeScreen.class);
            startActivity(intent);

        });
    }

    public boolean updateEmail(String email) {
        Log.d("12312", email);
        final boolean[] flag = {false};
        mUser.updateEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Helper.getUsername(requireActivity());
                            flag[0] = true;
                        }
                    }
                });
        return flag[0];
    }

    public boolean updatePassword(String password) {
        final boolean[] flag = {false};
        mUser.updatePassword(password)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            flag[0] = true;
                            Toast.makeText(getActivity(), "Password Changed", Toast.LENGTH_LONG).show();
                        }
                    }
                });
        return flag[0];
    }
}
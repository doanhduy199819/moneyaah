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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        setUpUI(view);
        setUpEvent(view);

        return view;
    }

    private void setUpUI(View mainView) {
        editButton = mainView.findViewById(R.id.button_edit);

        emailContent = mainView.findViewById(R.id.email_content);
        editEmail = mainView.findViewById(R.id.edit_email);

        editOldPassword = mainView.findViewById(R.id.edit_old_password);
        editNewPassword = mainView.findViewById(R.id.edit_new_password);
        editConfirmPassword = mainView.findViewById(R.id.edit_confirm_new_password);

        updateButton = mainView.findViewById(R.id.button_update);
    }

    private void setUpEvent(View mainView) {

        // Edit
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailContent.setVisibility(View.INVISIBLE);
                editEmail.setVisibility(View.VISIBLE);

                editEmail.setText(emailContent.getText());
            }
        });

        // Update
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Information
                emailContent.setText(editEmail.getText());
                emailContent.setVisibility(View.VISIBLE);
                editEmail.setVisibility(View.INVISIBLE);

                // Password
                String password = "1234";
                String oldPass = editOldPassword.getText().toString();
                String newPass = editNewPassword.getText().toString();
                String confirmPass = editConfirmPassword.getText().toString();

                if (!oldPass.equals(password)) {
                    // Wrong password
                }
                else if (!newPass.equals(confirmPass)) {
                    editNewPassword.setText("");
                    editConfirmPassword.setText("");
                }
                else {
                    // Change user password
                    editOldPassword.setText("");
                    editNewPassword.setText("");
                    editConfirmPassword.setText("");
                }
            }
        });
    }
}
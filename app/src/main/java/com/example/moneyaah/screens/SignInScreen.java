package com.example.moneyaah.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.moneyaah.Helper;
import com.example.moneyaah.R;

import com.example.moneyaah.MainActivity;
import com.example.moneyaah.classes.Record;
import com.example.moneyaah.classes.UploadRecord;
import com.example.moneyaah.classes.UserDData;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.shobhitpuri.custombuttons.GoogleSignInButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SignInScreen extends AppCompatActivity {

    FirebaseAuth mAuth;

    GoogleSignInButton googleSignInButton;
    GoogleSignInClient mGoogleSignInClient;
    static final int RC_SIGN_IN = 0;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private ImageButton btnSignIn;
    private EditText edtEmail;
    private EditText edtPassword;
    private ImageView imgTick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_screen);
        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();

        googleSignInButton = (GoogleSignInButton) findViewById(R.id.sign_in_button);
        btnSignIn = findViewById(R.id.btn_sign_in);
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        imgTick = findViewById(R.id.img_tick);

        // Navigating to Note Screen to test
        btnSignIn.setOnClickListener(v -> {
            handleSignIn(edtEmail.getText().toString(), edtPassword.getText().toString());
        });


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;
                    // ...
                }
            }
        });

        edtEmail.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String email = edtEmail.getText().toString();
                if (email.matches(emailPattern) && s.length() > 0) {
                    imgTick.setVisibility(View.VISIBLE);
                } else {
                    imgTick.setVisibility(View.GONE);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

    }

    private void handleSignIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        String username = user.getEmail();
                        Helper.saveUser(this, username);
                        getRecord();
                    } else {
                        Toast.makeText(SignInScreen.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getRecord() {
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
                    nextIntent();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        } else nextIntent();
    }

    private void nextIntent() {
        Intent intent = new Intent(SignInScreen.this, MainActivity.class);
        startActivity(intent);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            Toast.makeText(this, "Sign in successful", Toast.LENGTH_SHORT).show();
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Error", "signInResult:failed code=" + e.getStatusCode());

        }
    }
}
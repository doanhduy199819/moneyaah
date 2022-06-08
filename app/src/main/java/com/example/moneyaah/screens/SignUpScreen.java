package com.example.moneyaah.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.moneyaah.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.shobhitpuri.custombuttons.GoogleSignInButton;

public class SignUpScreen extends AppCompatActivity {

    GoogleSignInButton signInButton;
    GoogleSignInClient mGoogleSignInClient;
    static final int RC_SIGN_IN = 0;

    private FirebaseAuth mAuth;

    private EditText edtEmail;
    private ImageView imgTick;
    private ImageView imgTick1;
    private ImageView img_tick2;
    private ImageView img_tick3;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private EditText edtReEnterPassword;
    private EditText edtpassword;
    private Button signupButton;
    String passwordStr;
    String reEnterPasswordStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);
        getSupportActionBar().hide();

        signInButton = (GoogleSignInButton) findViewById(R.id.sign_up_button);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        imgTick = findViewById(R.id.img_tick);
        imgTick1 = findViewById(R.id.img_tick1);
        img_tick2 = findViewById(R.id.img_tick2);
        img_tick3 = findViewById(R.id.img_tick3);
        edtEmail = findViewById(R.id.edt_email);
        edtReEnterPassword = findViewById(R.id.edt_repassword);
        edtpassword = findViewById(R.id.edt_password);
        signupButton = findViewById(R.id.sign_up_button);
        initListener();

    }

    private void initListener() {
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.sign_up_button:
                        signIn();
                        break;
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

        passwordStr = edtpassword.getText().toString();
        edtpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edtpassword.getText().toString().length() < 6) {
                    img_tick2.setVisibility(View.GONE);
                } else {
                    img_tick2.setVisibility(View.VISIBLE);
                }
            }
        });
        edtReEnterPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edtpassword.getText().toString().equals(edtReEnterPassword.getText().toString())) {
                    imgTick1.setVisibility(View.GONE);
                    img_tick3.setVisibility(View.VISIBLE);
                } else {
                    imgTick1.setVisibility(View.VISIBLE);
                }
            }
        });
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp(edtEmail.getText().toString(), edtpassword.getText().toString());
            }
        });
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
            Toast.makeText(this, "Sign up successful", Toast.LENGTH_SHORT).show();
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Error", "signInResult:failed code=" + e.getStatusCode());

        }
    }

    public void signUp(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            Toast.makeText(SignUpScreen.this, "Can't create new user.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });

    }

    public void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(SignUpScreen.this, HomeScreen.class);
            startActivity(intent);
        }
    }
}
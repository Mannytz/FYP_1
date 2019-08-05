package com.mwendwa.fyp_1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText pass, email;
    private Button login;
    private TextView redirect;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.loginEmail);
        pass = findViewById(R.id.loginPassword);
        login = findViewById(R.id.btnLogin);
        redirect = findViewById(R.id.redirect2SignUp);

        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            finish();
            startActivity(new Intent(LoginActivity.this, HostActivity.class));
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText().toString().equals("Admin")) {
                    if (pass.getText().toString().equals("pass123")) {
                        startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                        Toast.makeText(LoginActivity.this, "Logging in as Admin", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "Wrong admin password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    validate(email.getText().toString(), pass.getText().toString());
                }
            }
        });

        redirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        });

    }

    private void validate (String username, String password) {

        progressDialog.setMessage("Verifying your credentials...");
        progressDialog.show();

        if (username.isEmpty() || password.isEmpty()) {
            progressDialog.dismiss();
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        checkEmailVerification();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void checkEmailVerification () {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Boolean emailFlag = currentUser.isEmailVerified();

        if (emailFlag) {
            finish();
            startActivity(new Intent(LoginActivity.this, HostActivity.class));
            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Please verify your email", Toast.LENGTH_LONG).show();
            mAuth.signOut();
        }
    }

}

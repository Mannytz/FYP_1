package com.mwendwa.fyp_1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {

    private EditText name, orgName, email, password, confirm;
    private TextView redirect;
    private Button register;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        name = findViewById(R.id.regApplicant);
        orgName = findViewById(R.id.regOrganisation);
        email = findViewById(R.id.regEmail);
        password = findViewById(R.id.regPassword);
        confirm = findViewById(R.id.regConfirmPassword);
        register = findViewById(R.id.btnRegisterApplicant);
        redirect = findViewById(R.id.redirect2Login);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        progressDialog = new ProgressDialog(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean val;
                val = validate(name.getText().toString(), orgName.getText().toString(), email.getText().toString(), password.getText().toString(), confirm.getText().toString());
                progressDialog.setMessage("Registering...");
                progressDialog.show();

                if (val) {

                    mAuth.createUserWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                sendEmailVerification();

                            } else {
                                //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                progressDialog.dismiss();
                                Log.e("Whaaaaaaaaaaa",task.getException().toString());
                                Toast.makeText(RegistrationActivity.this, "Registration failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });


        redirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            }
        });

    }

    private boolean validate (String name, String organisation, String email, String password, String confirm) {

        boolean result = false;

        if ( name.isEmpty() || email.isEmpty() || password.isEmpty() || organisation.isEmpty() || confirm.isEmpty()) {
            progressDialog.dismiss();
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        } else {
            if (password.equals(confirm)) {
                result = true;
            } else {
                progressDialog.dismiss();
                Toast.makeText(this, "Passwords must match", Toast.LENGTH_SHORT).show();
            }
        }

        return result;
    }

    private void sendEmailVerification () {
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            currentUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    sendUserData();
                    progressDialog.dismiss();
                    Toast.makeText(RegistrationActivity.this, "Please check your email to verify", Toast.LENGTH_LONG).show();
                    mAuth.signOut();
                    finish();
                    startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                }
            });
        } else {
            progressDialog.dismiss();
            Toast.makeText(RegistrationActivity.this, "Verification Mail send failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendUserData () {
        DatabaseReference myRef = mDatabase.getReference("Applicants/" + mAuth.getUid());
        Applicant applicant = new Applicant(name.getText().toString(), orgName.getText().toString());
        myRef.setValue(applicant);
    }
}

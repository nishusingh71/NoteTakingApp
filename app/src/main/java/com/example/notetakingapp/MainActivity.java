package com.example.notetakingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    EditText editTextEmail, editTextPassword;
    TextView textViewSignUp, textViewForgot;
    Button buttonLogin;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextEmail = findViewById(R.id.Email_ET);
        editTextPassword = findViewById(R.id.Pass_ET);
        textViewSignUp = findViewById(R.id.SignUp_tx);
        buttonLogin = findViewById(R.id.bt_ET);
        textViewForgot = findViewById(R.id.forgot);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();
                String pass = editTextPassword.getText().toString();
                if (email.isEmpty()) {
                    editTextEmail.setError("Enter your Email");
                    editTextEmail.requestFocus();

                } else if (!email.matches(Patterns.EMAIL_ADDRESS.pattern())) {
                    editTextEmail.setError("Enter valid Email");
                    editTextEmail.requestFocus();


                } else if (pass.isEmpty()) {
                    editTextPassword.setError("Enter your Password");
                    editTextPassword.requestFocus();


                } else if (pass.length() < 6) {
                    editTextPassword.setError("Enter password greater than 6 digit");
                    editTextPassword.requestFocus();

                } else {
                    auth = FirebaseAuth.getInstance();
                    auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Toast.makeText(MainActivity.this, "Logged Successful", Toast.LENGTH_SHORT).show();
                                Intent intentUser = new Intent(MainActivity.this, UserAccount.class);
                                startActivity(intentUser);

                                finish();
                            } else {
                                Toast.makeText(MainActivity.this, "Logged not Successful" + task.getException(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }


            }
        });

        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);
                finish();

            }
        });
        //Forgot Password
        textViewForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();
                if (email.isEmpty()) {
                    editTextEmail.setError("Enter email");
                    editTextEmail.requestFocus();
                } else if (!email.matches(Patterns.EMAIL_ADDRESS.pattern())) {
                    editTextEmail.setError("Enter valid email");
                    editTextEmail.requestFocus();
                } else {
                    FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Password is updated", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "Some Error Occurred" + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
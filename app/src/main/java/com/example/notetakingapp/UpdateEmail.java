package com.example.notetakingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class UpdateEmail extends AppCompatActivity {
    EditText editTextEmail, editTextConfirmEmail;
    Button buttonEmailUpdate;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_email);
        editTextConfirmEmail = findViewById(R.id.EmailAgain_ET);
        editTextEmail = findViewById(R.id.Email_ET);
        buttonEmailUpdate = findViewById(R.id.EmailUpdate_bt);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        buttonEmailUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();
                String confirmEmail = editTextConfirmEmail.getText().toString();
                if (email.equals(confirmEmail)) {
                    editTextConfirmEmail.setError("Email is not match");
                    editTextConfirmEmail.requestFocus();
                } else if (!email.matches(Patterns.EMAIL_ADDRESS.pattern())) {
                    editTextEmail.setError("Enter valid Email");
                    editTextEmail.requestFocus();

                } else {
                    firebaseUser.updateEmail(confirmEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Intent i = new Intent(UpdateEmail.this, MainActivity.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(UpdateEmail.this, "Email is not Match", Toast.LENGTH_SHORT).show();
                            }
                        }

                    });

                }


            }
        });
    }
}
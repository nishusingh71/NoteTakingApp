package com.example.notetakingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UpdatePassword extends AppCompatActivity {
    EditText editTextPass, editTextPassAgain;
    Button buttonUpdate;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        editTextPass = findViewById(R.id.Password_ET);
        editTextPassAgain = findViewById(R.id.PasswordAgain_ET);
        buttonUpdate = findViewById(R.id.updatePass_bt);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass = editTextPass.getText().toString();
                String passConfirm = editTextPassAgain.getText().toString();
                if (pass.equals(passConfirm)) {
                    editTextPassAgain.setError("Not match password");
                    editTextPassAgain.requestFocus();
                } else if (pass.length() < 6) {
                    editTextPass.setError("Password atLeast 6 digit");
                    editTextPass.requestFocus();

                } else {
                    firebaseUser.updatePassword(passConfirm).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Intent loginIntent = new Intent(UpdatePassword.this, MainActivity.class);
                                startActivity(loginIntent);
                            } else {
                                Toast.makeText(UpdatePassword.this, "UpdatePassword is failed", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }


            }

        });


    }
}
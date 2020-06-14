package com.example.notetakingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignUp extends AppCompatActivity {
    EditText editTextEmail,editTextPassword,editName;
    TextView  textViewLogin;
    Button buttonSignUp;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
       editTextEmail=findViewById(R.id.Email_ET);
       editTextPassword=findViewById(R.id.Pass_ET);
       textViewLogin=findViewById(R.id.Login_tx);
       buttonSignUp=findViewById(R.id.SignUp_BT);
       editName=findViewById(R.id.Name_ET);

       buttonSignUp.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String email=editTextEmail.getText().toString();
               String pass = editTextPassword.getText().toString();
               final String name=editName.getText().toString();
               if(name.isEmpty()){
                   editName.setError("Enter your username ");
                   editName.requestFocus();

               }else if(email.isEmpty()){
                   editTextEmail.setError("Enter your Email");
                   editTextEmail.requestFocus();

               }else if(!email.matches(Patterns.EMAIL_ADDRESS.pattern())){
                   editTextEmail.setError("Enter valid Email");
                   editTextEmail.requestFocus();


               } else if(pass.isEmpty()){
                   editTextPassword.setError("Enter your Password");
                   editTextPassword.requestFocus();


               }else if(pass.length()<6){
                   editTextPassword.setError("Enter password greater than 6 digit");
                   editTextPassword.requestFocus();

               }else {
                   auth=FirebaseAuth.getInstance();
                   auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           if (task.isSuccessful()){
                               FirebaseUser user=auth.getCurrentUser();
                               UserProfileChangeRequest changeRequest=new UserProfileChangeRequest.Builder().setDisplayName(name).build();
                               auth.getCurrentUser().updateProfile(changeRequest);

                               assert user != null;
                               Toast.makeText(SignUp.this, "SignUp is Successful"+user.getEmail(), Toast.LENGTH_SHORT).show();
                           }else {
                               Toast.makeText(SignUp.this, "SignUp is not Completed"+task.getException(), Toast.LENGTH_SHORT).show();
                           }
                       }
                   });
               }

           }
       });

       textViewLogin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intentLogin=new Intent(SignUp.this,MainActivity.class);
               startActivity(intentLogin);
               finish();
           }
       });
    }
}
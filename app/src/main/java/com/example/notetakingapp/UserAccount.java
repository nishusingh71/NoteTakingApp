package com.example.notetakingapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;

public class UserAccount extends AppCompatActivity implements View.OnClickListener {
    TextView Welcome,emailText;
    Button LogoutButton, buttonUpdatePassword, buttonUpdateEmail;
    ImageView imageViewProfile;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    Context context;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);
        Welcome = findViewById(R.id.wel_tx);
        LogoutButton = findViewById(R.id.logOut);
        buttonUpdatePassword = findViewById(R.id.updatePass);
        buttonUpdateEmail = findViewById(R.id.updateEmail);
        emailText=findViewById(R.id.email_tx);
        imageViewProfile=findViewById(R.id.profile_image);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        assert user != null;
        Welcome.setText("Hi, " + user.getDisplayName());
        emailText.setText(user.getEmail());
        Uri uri=user.getPhotoUrl();
        Glide.with(this).load(uri).into(imageViewProfile);

        imageViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                startActivityForResult(intent,1);
            }
        });


        LogoutButton.setOnClickListener(this);
        buttonUpdatePassword.setOnClickListener(this);
        buttonUpdateEmail.setOnClickListener(this);

        context = this;



    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.logOut:
                showLogOutDialog();
                break;
            case R.id.updatePass:
                updatePassword();
                break;
            case R.id.updateEmail:
                updateEmail();
                break;
        }
    }

    private void updateEmail() {
        Intent emailIntent=new Intent(UserAccount.this,UpdateEmail.class);
        startActivity(emailIntent);
    }

    private void updatePassword() {
        Intent passIntent=new Intent(UserAccount.this,UpdatePassword.class);
        startActivity(passIntent);
    }

    private void showLogOutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure want to Logout").setPositiveButton("Yes Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                firebaseAuth.signOut();
                ((Activity) context).finish();
                Intent intent = new Intent(UserAccount.this, MainActivity.class);
                startActivity(intent);
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode ==1 && resultCode ==-1) {
            assert data != null;
            final Uri imageUri = data.getData();
            imageViewProfile.setImageURI(imageUri);
            final String email = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();

            // Upload to Fire baseStorage
            assert imageUri != null;
            FirebaseStorage.getInstance()
                    .getReference("userPics")
                    .child(email + ".jpeg")
                    .putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            FirebaseStorage.getInstance()
                                    .getReference("userPics")
                                    .child(email + ".jpeg")
                                    .getDownloadUrl()
                                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            UserProfileChangeRequest Request = new UserProfileChangeRequest
                                                    .Builder()
                                                    .setPhotoUri(uri)
                                                    .build();
                                            // Updating profile
                                            FirebaseAuth.getInstance().getCurrentUser()
                                                    .updateProfile(Request);

                                            Toast.makeText(getApplicationContext(), "Uploaded successfully", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                        }
                                    });


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
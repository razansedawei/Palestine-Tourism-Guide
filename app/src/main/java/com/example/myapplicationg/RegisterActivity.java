package com.example.myapplicationg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.util.ArrayList;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    public static final String TAG = "TAG";
    EditText mFirstName,mLastName,mEmail,mPassword,mbio;
    Button mRegister;
    FirebaseAuth fAuth;
    ProgressBar progressbar1;
    FirebaseFirestore db;
    String userID , fullname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirstName=findViewById(R.id.text1);
        mLastName=findViewById(R.id.text2);
        mEmail=findViewById(R.id.text3);
        mPassword=findViewById(R.id.text4);
        mRegister=findViewById(R.id.button1);
        mbio=findViewById(R.id.bio);



        fAuth = FirebaseAuth.getInstance();
        db= FirebaseFirestore.getInstance();
        progressbar1=findViewById(R.id.progressBar2);


        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                final String fname = mFirstName.getText().toString();
                final String lname = mLastName.getText().toString();
                final String bio = mbio.getText().toString();

                fullname = fname+" "+lname;


                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is Required. ");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("password is Required. ");
                    return;
                }

                if (password.length() < 6) {
                    mPassword.setError("password must be more than 6 characters");
                    return;
                }

                progressbar1.setVisibility(View.VISIBLE);

                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "User created.", Toast.LENGTH_SHORT).show();
                            userID=fAuth.getCurrentUser().getUid();
                            Map<String, Object> user = new HashMap<>();
                            user.put("Fullname", fullname);
                            user.put("Bio", bio);
                            user.put("email", email);

                            db.collection("User").document(userID)
                                    .set(user)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "DocumentSnapshot successfully written!");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w(TAG, "Error writing document", e);
                                        }
                                    });
                            Map<String, ArrayList<String>> userFavorite = new HashMap<>();
                            userFavorite.put("list", new ArrayList<String>());
                            db.collection("favoriteList").document(userID)
                                    .set(userFavorite);

                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(RegisterActivity.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressbar1.setVisibility(View.GONE);
                        }

                    }
                });

            }
        });
    }
}

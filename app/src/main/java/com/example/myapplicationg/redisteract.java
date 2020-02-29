package com.example.myapplicationg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class redisteract extends AppCompatActivity {

    EditText mFirstName,mLastName,mEmail,mPassword;
    Button mRegister;
    FirebaseAuth fAuth;
    ProgressBar progressbar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redisteract);

        mFirstName=findViewById(R.id.text1);
        mLastName=findViewById(R.id.text2);
        mEmail=findViewById(R.id.text3);
        mPassword=findViewById(R.id.text4);
        mRegister=findViewById(R.id.button1);

        fAuth = FirebaseAuth.getInstance();
        progressbar1=findViewById(R.id.progressBar2);

        if (fAuth.getCurrentUser()!= null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();


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
                            Toast.makeText(redisteract.this, "User created.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(redisteract.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressbar1.setVisibility(View.GONE);
                        }

                    }
                });

            }
        });
    }
}

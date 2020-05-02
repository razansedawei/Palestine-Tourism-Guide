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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText mEmail,mPassword;
    Button mlogin;
    FirebaseAuth fAlth;
    ProgressBar progressbar1;
    TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail=findViewById(R.id.t1);
        mPassword=findViewById(R.id.t2);
        mTextView=findViewById(R.id.tlogin);
        mlogin=findViewById(R.id.button1);

        fAlth = FirebaseAuth.getInstance();
        progressbar1=findViewById(R.id.progressBar2);


        mlogin.setOnClickListener(new View.OnClickListener() {
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

                fAlth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Log in seccessfully.", Toast.LENGTH_SHORT).show();
                            String user = fAlth.getCurrentUser().getUid();
                            Toast.makeText(LoginActivity.this, user, Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressbar1.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });


        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegisternActivity();
            }
        });
    }

    public void openRegisternActivity(){
        Intent intent2 = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent2);
    }


}

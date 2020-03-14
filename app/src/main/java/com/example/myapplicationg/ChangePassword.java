package com.example.myapplicationg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePassword extends AppCompatActivity {

    EditText changepassword1,changepassword2 ;
    Button savepassword;
    FirebaseAuth fauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        changepassword1=findViewById(R.id.rePassword1);
        changepassword2=findViewById(R.id.repasswoed2);
        savepassword=findViewById(R.id.savetherepassword);
        fauth= FirebaseAuth.getInstance();
    }
    public void change(View v){
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                user.updatePassword(changepassword1.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Your password has been changed", Toast.LENGTH_SHORT).show();
                                    fauth.signOut();
                                    finish();
                                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(i);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Your password can not be change", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }
    }
package com.example.myapplicationg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Settingprofile extends AppCompatActivity {
Button repassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settingprofile);
        repassword=findViewById(R.id.changepassword);
        repassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openchangepassword();
            }
        });


    }

    public void openchangepassword(){
        Intent ipassword = new Intent(this, ChangePassword.class);
        startActivity(ipassword);
    }
}

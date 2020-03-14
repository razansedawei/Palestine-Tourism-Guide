package com.example.myapplicationg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Settingprofile extends AppCompatActivity {
Button repassword , signout1 , deleteaccount;
FirebaseAuth firbas;
FirebaseUser fireuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settingprofile);

        deleteaccount=findViewById(R.id.delete);
        repassword=findViewById(R.id.changepassword);
        repassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openchangepassword();
            }
        });
        signout1=findViewById(R.id.signout);
        signout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opensignout();
            }
        });

        firbas=FirebaseAuth.getInstance();
        fireuser= firbas.getCurrentUser();



        deleteaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(Settingprofile.this);
                dialog.setTitle("Are you sure? ");
                dialog.setMessage("If you deleting this account you can not have it back!");
                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fireuser.delete()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(Settingprofile.this,"Account Deleted ", Toast.LENGTH_LONG).show();

                                        Intent intent = new Intent(Settingprofile.this,LoginActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        }
                                        else {
                                            Toast.makeText(Settingprofile.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                                        }

                                    }
                                });
                    }
                });

                dialog.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog alertDialog=dialog.create();
                alertDialog.show();

            }
        });




    }



    public void opensignout(){
        Intent isignout = new Intent(this, LoginActivity.class);
        startActivity(isignout);
    }

    public void openchangepassword(){
        Intent ipassword = new Intent(this, ChangePassword.class);
        startActivity(ipassword);
    }
}

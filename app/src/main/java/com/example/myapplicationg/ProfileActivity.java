package com.example.myapplicationg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.annotation.Nullable;

import io.grpc.Compressor;

public class ProfileActivity extends AppCompatActivity {

    TextView mfullname , memail , mbio;
    FirebaseAuth fAult;
    FirebaseFirestore fstore;
    String userId;
    ImageView reprofileimg;
    StorageReference firestorref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        BottomNavigationView bottomNavigationView =findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.profile);
        bottomNavigationView.setOnNavigationItemSelectedListener(new NavigationBarClickListener(this));

        BottomNavigationView bottomNavigationView1 =findViewById(R.id.navigationtop);
        bottomNavigationView1.setSelectedItemId(R.id.settingpro);
        bottomNavigationView1.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        startActivity(new Intent(getApplicationContext(), ProfileSettingsActivity.class));
                return true;
            }
        });


        mfullname=findViewById(R.id.user_name);
        memail=findViewById(R.id.user_email);
        mbio=findViewById(R.id.myBio);
        reprofileimg=findViewById(R.id.profpic);

        firestorref= FirebaseStorage.getInstance().getReference();
        fAult= FirebaseAuth.getInstance();
        fstore= FirebaseFirestore.getInstance();
        userId= fAult.getCurrentUser().getUid();

        DocumentReference documentReference = fstore.collection("User").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                mbio.setText(documentSnapshot.getString("Bio"));
                mfullname.setText(documentSnapshot.getString("Fullname"));
                memail.setText(documentSnapshot.getString("email"));


            }
        });


        reprofileimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGallery,1000);

            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( requestCode == 1000 ){
            if (resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();
                reprofileimg.setImageURI(imageUri);
            }
        }
    }

}


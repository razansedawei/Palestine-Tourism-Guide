package com.example.myapplicationg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import javax.annotation.Nullable;

public class ProfileActivity extends AppCompatActivity {

    TextView mfullname , memail , mbio;
    FirebaseAuth fAult;
    FirebaseFirestore fstore;
    String userId;

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


    }
}


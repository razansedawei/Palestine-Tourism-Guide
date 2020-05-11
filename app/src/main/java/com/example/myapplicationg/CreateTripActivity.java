package com.example.myapplicationg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.util.TypedValue;
import android.widget.Button;
import android.view.View;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class CreateTripActivity extends AppCompatActivity {
    TextView textView;
    Button mOrder;
    Button creatTrip;
    TextView mItemSelected;
    String[] listItems;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();
    ArrayList<String> selectedCities = new ArrayList<String>();
    HashMap<String, ArrayList<String>> trips = new HashMap<String, ArrayList<String>>();
    FirebaseFirestore db;
    NumberPicker numberPicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);

        db= FirebaseFirestore.getInstance();
        db.collection("Trips").get().addOnCompleteListener(
                new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                trips.put(document.getId(),(ArrayList<String>)document.get("trips"));
                            }
                        }
                    }
                });

        numberPicker=findViewById(R.id.number_picker);
        textView=findViewById(R.id.text);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(6);

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                textView.setText("Number of days: "+newVal);
            }
        });


        mOrder = (Button) findViewById(R.id.btnOrder);
        mItemSelected = (TextView) findViewById(R.id.tvItemSelected);

        listItems = getResources().getStringArray(R.array.cities);
        checkedItems = new boolean[listItems.length];

        mOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(CreateTripActivity.this);
                mBuilder.setTitle(R.string.dialog_title);
                mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
//                        if (isChecked) {
//                            if (!mUserItems.contains(position)) {
//                                mUserItems.add(position);
//                            }
//                        } else if (mUserItems.contains(position)) {
//                            mUserItems.remove(position);
//                        }
                        if(isChecked){
                            mUserItems.add(position);
                        }else{
                            mUserItems.remove((Integer.valueOf(position)));
                        }
                    }
                });

                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String item = "";
                        selectedCities.clear();
                        for (int i = 0; i < mUserItems.size(); i++) {
                            selectedCities.add(listItems[mUserItems.get(i)]);
                            item = item + listItems[mUserItems.get(i)];
                            if (i != mUserItems.size() - 1) {
                                item = item + ", ";
                            }
                        }
                        mItemSelected.setText(item);
                    }
                });

                mBuilder.setNegativeButton(R.string.dismiss_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                mBuilder.setNeutralButton(R.string.clear_all_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < checkedItems.length; i++) {
                            checkedItems[i] = false;
                            mUserItems.clear();
                            selectedCities.clear();
                            mItemSelected.setText("");
                        }
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });
        creatTrip = findViewById(R.id.show_creat_dialog);
        creatTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCreateDialog();
            }

        });

    }

    public void showCreateDialog(){
        final Dialog dialog=new Dialog(CreateTripActivity.this);
        dialog.setContentView(R.layout.dialog_creat);
        LinearLayout layout = dialog.findViewById(R.id.trips_list);
        layout.removeAllViews();

        int daysPerCity = numberPicker.getValue() / selectedCities.size();
        int remainingDays= numberPicker.getValue() % selectedCities.size();
        for(int i=0; i< selectedCities.size();i++){
            TextView cityName = new TextView(dialog.getContext());
            cityName.setText("At "+selectedCities.get(i));
            cityName.setTextColor(Color.WHITE);
            cityName.setPadding(10,10,10,10);
            cityName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            if(i!=0){
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(0,30,0,0);
                cityName.setLayoutParams(params);
            }
            layout.addView(cityName);

            if(i==selectedCities.size()-1){
                daysPerCity += remainingDays;
            }
            for(int j=0; j<daysPerCity; j++) {
                TextView dayNum = new TextView(dialog.getContext());
                dayNum.setText("Day "+ (j+1)+":");
                dayNum.setPadding(10,10,10,10);
                dayNum.setTextColor(Color.WHITE);
                dayNum.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                if(j!=0){
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(0,30,0,0);
                    dayNum.setLayoutParams(params);
                }
                layout.addView(dayNum);

                TextView trip = new TextView(dialog.getContext());
                trip.setText(trips.get(selectedCities.get(i)).get(j));
                trip.setPadding(10,10,10,10);
                trip.setTextColor(Color.WHITE);
                trip.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                layout.addView(trip);


            }


        }


        Button btn=dialog.findViewById(R.id.dialog_hide_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
            }
        });
        dialog.show();
    }






}





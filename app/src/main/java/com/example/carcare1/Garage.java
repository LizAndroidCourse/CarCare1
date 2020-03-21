package com.example.carcare1;

import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;

public class Garage extends AppCompatActivity implements Serializable {

    private FirebaseDatabase db;
    private DatabaseReference myRef;
    final String key = "Garage";
    Car car = new Car();
    Button BTN_backGrage,BTN_OK;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garage);
        car = (Car) getIntent().getSerializableExtra("CAR");
        TextView TV_carName = findViewById(R.id.car_name_title);
        TV_carName.setText(car.getMake() + " " + car.getModel());
         BTN_OK = findViewById(R.id.btn_done);
         BTN_backGrage = findViewById(R.id.goBackGarage);
        checkButtons();
    }

    public void checkButtons(){
        BTN_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDataBaseReport();
                updateDataBase();
                moveToProfileScreen();
            }
        });
        BTN_backGrage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToProfileScreen();
            }
        });

    }
    public void updateDataBase() {
        db = FirebaseDatabase.getInstance();
        myRef = db.getReference();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserID = user.getPhoneNumber();
        EditText kmStr = findViewById(R.id.Km);
        int km = Integer.parseInt(kmStr.getText().toString().trim());
        car.setKm(km);
        myRef.child("Users").child(currentUserID).child("Cars").child(car.getCar_number() + "").child("lastKmBeforeGarage").setValue(car.getKm());
        myRef.child("Users").child(currentUserID).child("Cars").child(car.getCar_number() + "").child("km").setValue(car.getKm());
        myRef.push();

    }
    public void updateDataBaseReport(){
        EditText editText = findViewById(R.id.price_garage);
        int current_cost = 0,cost;
        try {
            current_cost = Integer.parseInt(editText.getText().toString().trim());
        } catch (NumberFormatException e) {
            // Log error, change value of car number, or do nothing
        }
        if(car.getMapFinanc().isEmpty()){
            cost = 0;
        }else{
            cost = car.getSpecificCost(key);
        }
        cost+=current_cost;
        car.setMapFinanc(key,cost);
        db = FirebaseDatabase.getInstance();
        myRef = db.getReference();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserID = user.getPhoneNumber();
        myRef.child("Users").child(currentUserID).child("Cars").child(car.getCar_number() + "").child("mapFinanc").child(key).setValue(cost);
        myRef.push();
    }
    public void moveToProfileScreen(){
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("CAR",car);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("CAR",car);
        startActivity(intent);
        this.finish();
    }
}

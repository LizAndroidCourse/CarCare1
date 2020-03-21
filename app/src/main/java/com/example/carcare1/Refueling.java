package com.example.carcare1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class Refueling extends AppCompatActivity implements Serializable {

    private FirebaseDatabase db;
    private DatabaseReference myRef;
    Car car = new Car();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refueling);
        car = (Car) getIntent().getSerializableExtra("CAR");
        TextView TV_carName = findViewById(R.id.car_name_title);
        TV_carName.setText(car.getMake() + " " + car.getModel());
        Button BTN_OK = findViewById(R.id.btn_done);
        BTN_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDataBaseKM();
                updateDataBaseReport();
                moveToProfileScreen();
            }
        });
        Button Button = findViewById(R.id.goBackGas);
        Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToProfileScreen();
            }
        });

    }

    public void updateDataBaseKM() {
        EditText kmStr = findViewById(R.id.Km);
        int km = Integer.parseInt(kmStr.getText().toString().trim());
        db = FirebaseDatabase.getInstance();
        myRef = db.getReference();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserID = user.getPhoneNumber();
        car.setKm(km);
        myRef.child("Users").child(currentUserID).child("Cars").child(car.getCar_number() + "").child("km").setValue(car.getKm());
        myRef.push();

    }
public void updateDataBaseReport(){
        EditText editText = findViewById(R.id.Price);
    int current_cost = 0,cost;
    try {
        current_cost = Integer.parseInt(editText.getText().toString().trim());
    } catch (NumberFormatException e) {
        // Log error, change value of car number, or do nothing
    }
    if(car.getMapFinanc().isEmpty()){
        cost = 0;
    }else{
        cost = car.getSpecificCost("Gas");
    }
    cost+=current_cost;
    car.setMapFinanc("Gas",cost);
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String currentUserID = user.getPhoneNumber();
    myRef.child("Users").child(currentUserID).child("Cars").child(car.getCar_number() + "").child("mapFinanc").child("Gas").setValue(cost);
    myRef.push();
}
public void moveToProfileScreen(){
    Intent intent = new Intent(this, ProfileActivity.class);
    intent.putExtra("CAR",car);
    startActivity(intent);
    this.finish();
}
}

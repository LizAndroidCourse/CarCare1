package com.example.carcare1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
                updateDataBaseReport();
                updateDataBase();
            }
        });
    }
    public void updateDataBase() {
        EditText kmStr = findViewById(R.id.Km);
        int km = Integer.parseInt(kmStr.getText().toString().trim());
        car.setKm(km);
        db = FirebaseDatabase.getInstance();
        myRef = db.getReference();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserID = user.getPhoneNumber();
        myRef.child("Users").child(currentUserID).child("Cars").child(car.getCar_number() + "").child("KM").setValue(car.getKm());
        myRef.push();

    }
    public void updateDataBaseReport(){
        TextView cost_Labal =findViewById(R.id.grage_cost);
        int current_cost = Integer.parseInt(cost_Labal.getText().toString().trim());
        int cost = car.getSpecificCost(key);
        cost+=current_cost;
        car.setMapFinanc(key,cost);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserID = user.getPhoneNumber();
        myRef.child("Users").child(currentUserID).child("Cars").
                child(car.getCar_number() + "").child("mapFinanc").child(key).setValue(cost);
        myRef.push();
    }
}

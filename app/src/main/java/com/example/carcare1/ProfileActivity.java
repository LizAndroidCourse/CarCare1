package com.example.carcare1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Button BTN_addCar = findViewById(R.id.BTN_addCar);
        BTN_addCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, AddCarForm.class);
                startActivity(intent);
            }
        });
        Button BTN_gas = findViewById(R.id.BTN_Gas);
        BTN_gas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToGasScreen();
            }
        });
        Button BTN_Test = findViewById(R.id.BTN_Test);
        BTN_Test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToTestScreen();
            }
        });
        Button BTN_Alerts = findViewById(R.id.BTN_Alerts);
        BTN_Alerts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToAlertsScreen();
            }
        });
        Button BTN_garage = findViewById(R.id.BTN_Garage);
        BTN_garage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToGarageScreen();
            }
        });
        Button BTN_foundGarage = findViewById(R.id.BTN_foudGarag);
        BTN_foundGarage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToFoundGarageScreen();
            }
        });
        Button BTN_foundGasStation = findViewById(R.id.BTN_foundGasStation);
        BTN_foundGasStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToFoundGasStationScreen();
            }
        });
    }
    public void moveToGasScreen(){
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
        this.finish();
    }
    public void moveToTestScreen(){}
    public void moveToAlertsScreen(){}
    public void moveToGarageScreen(){}
    public void moveToFoundGarageScreen(){}
    public void moveToFoundGasStationScreen(){}
}

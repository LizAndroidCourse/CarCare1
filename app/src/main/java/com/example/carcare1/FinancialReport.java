package com.example.carcare1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Map;

public class FinancialReport extends AppCompatActivity {

    Car car;
    Map<String,Integer> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financial_report);
        car = (Car)getIntent().getSerializableExtra("CAR");
        TextView TV_carName = findViewById(R.id.car_name_title);
        TV_carName.setText(car.getMake()+" "+ car.getModel());
         setCostTextView();
        ImageButton BTN_back = findViewById(R.id.BTN_back);
        BTN_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToProfileScreen();
            }
        });
    }
    public void setCostTextView(){
        map = car.getMapFinanc();
        TextView gas = findViewById(R.id.fuel_cost);
        TextView garage = findViewById(R.id.grage_cost);
        TextView test = findViewById(R.id.test_cost);
        TextView insurance = findViewById(R.id.insurance_cost);
        if(map.isEmpty()) {
        } else {
            gas.setText(String.valueOf(map.get("Gas")));
            garage.setText(String.valueOf(map.get("Garage")));
            test.setText(String.valueOf(map.get("Test")));
            insurance.setText(String.valueOf(map.get("Insurance")));
        }
        setTotalCost();
    }
    public void setTotalCost(){
        int totalCost=0;
        for (String key: map.keySet()) {
            totalCost+= map.get(key);
        }
        TextView totalCost_Text =findViewById(R.id.total_cost);
        totalCost_Text.setText(String.valueOf(totalCost));
        }
    public void moveToProfileScreen(){
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("CAR",car);
        startActivity(intent);
        this.finish();
    }
}

package com.example.carcare1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TestAndInsurance extends AppCompatActivity {
    private FirebaseDatabase db;
    private DatabaseReference myRef;
    final String test = "Test";
    final String insurance = "Insurance";
    Car car = new Car();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_and_insurance);
        car = (Car) getIntent().getSerializableExtra("CAR");
        TextView TV_carName = findViewById(R.id.car_name_title);
        TV_carName.setText(car.getMake() + " " + car.getModel());

        Button BTN_OK = findViewById(R.id.btn_done);
        BTN_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAllDeatils();
                updateDataBaseReport();
                updateDataBase();
                moveToProfileScreen();
            }
        });
        Button Button = findViewById(R.id.goBackTest);
        Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToProfileScreen();
            }
        });
    }
    public void checkAllDeatils(){

    }
    public void updateDataBase() {
        EditText kmStr = findViewById(R.id.Km);
        EditText insuranceStr = findViewById(R.id.month_insurance);
        int insurance_month = Integer.parseInt(insuranceStr.getText().toString().trim());
        int km = Integer.parseInt(kmStr.getText().toString().trim());
        db = FirebaseDatabase.getInstance();
        myRef = db.getReference();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserID = user.getPhoneNumber();
        car.setKm(km);
        car.setInsurance_month(insurance_month);
        myRef.child("Users").child(currentUserID).child("Cars").child(car.getCar_number() + "").child("km").setValue(car.getKm());
        myRef.child("Users").child(currentUserID).child("Cars").child(car.getCar_number() + "").child("insurance month").setValue(insurance_month);
        myRef.push();

    }
    public void updateDataBaseReport(){
        TextView cost_insurance =findViewById(R.id.cost_insurance);
        TextView cost_test =findViewById(R.id.cost_test);
        int cost_test_total,cost_insurance_total;
        if(car.getMapFinanc().isEmpty()){
            cost_insurance_total = 0;
            cost_test_total=0;
        }else{
            cost_test_total = car.getSpecificCost(test);
            cost_insurance_total = car.getSpecificCost(test);
        }
        int current_cost_insurance = Integer.parseInt(cost_insurance.getText().toString().trim());
        int current_cost_test = Integer.parseInt(cost_test.getText().toString().trim());
        cost_insurance_total+=current_cost_insurance;
        cost_test_total+=current_cost_test;
        car.setMapFinanc(test,cost_test_total);
        car.setMapFinanc(insurance,cost_insurance_total);
        db = FirebaseDatabase.getInstance();
        myRef = db.getReference();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserID = user.getPhoneNumber();
        myRef.child("Users").child(currentUserID).child("Cars").child(car.getCar_number() + "").child("mapFinanc").child("Insurance").setValue(cost_insurance_total);
        myRef.child("Users").child(currentUserID).child("Cars").child(car.getCar_number() + "").child("mapFinanc").child("Test").setValue(cost_test_total);
        myRef.push();
    }
    public void moveToProfileScreen(){
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("CAR",car);
        startActivity(intent);
        this.finish();
    }
}

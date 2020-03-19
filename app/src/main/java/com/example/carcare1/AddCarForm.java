package com.example.carcare1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

public class AddCarForm extends AppCompatActivity {
    Car car;
    ArrayList<Car> carList;
    Button OK_BTN;

    //add firebase  Database stuff
    private FirebaseDatabase db;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car_form);
        car = new Car();
        car.setModel(findViewById(R.id.EditTextModel).toString());
        car.setCar_number(Integer.parseInt(findViewById(R.id.EditTextCarNumber).toString()));
        car.setKm(Integer.parseInt(findViewById(R.id.EditTextKM).toString()));
        car.setMake(findViewById(R.id.EditTextMake).toString());
        OK_BTN = findViewById(R.id.BTN_Done);
        OK_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!((car.getCar_number()==0) &&(car.getCar_year()==0)&&(car.getMake().isEmpty())&&
                        (car.getModel().isEmpty())&&(car.getTest_month()==0))) {
                    carList.add(car);
                    moveToVihicleMainPage(car);
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddCarForm.this);
                    builder.setMessage("Please fill right all the details")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //do things
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        myRef = db.getReference();
        addToDatabase();

    }


    public void moveToVihicleMainPage(Car car) {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("CAR_NAME", car.getModel()+ car.getMake());
        startActivity(intent);
        this.finish();
    }
    public void addToDatabase(){
        Gson gson = new Gson();
        String json = gson.toJson(carList);
        db = FirebaseDatabase.getInstance();
        myRef = db.getReference();
        myRef.child("Profiles").child("Cars").setValue(json);

    }
}

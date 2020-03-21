package com.example.carcare1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;

public class AddCarForm extends AppCompatActivity implements Serializable {
    Car car;
    ArrayList<Car> carList;
    Button OK_BTN;

    //add firebase  Database stuff
    private FirebaseDatabase db;
    private DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car_form);

        OK_BTN = findViewById(R.id.BTN_Done);
        OK_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewCarToCarList();
                if(!((car.getCar_number() == 0) &&(car.getCar_year()==0)&&(car.getMake().isEmpty())&&
                        (car.getModel().isEmpty())&&(car.getTest_month()==0))) {
                    if(carList==null){
                        carList = new ArrayList<>();
                    }
                    carList.add(car);

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

    }

    public void addNewCarToCarList(){
        car = new Car();
        EditText editTextCarNumber = findViewById(R.id.EditTextCarNumber);
        EditText editTextModel = findViewById(R.id.EditTextModel);
        EditText editTextTestMonth = findViewById(R.id.EditTextTestMonth);
        EditText editTextKM = findViewById(R.id.EditTextKM);
        EditText editTextMake = findViewById(R.id.EditTextMake);
        EditText editTextYear = findViewById(R.id.EditTextYear);
         car.setCar_number(0);
        try {
            car.setCar_number(Integer.parseInt(editTextCarNumber.getText().toString().trim()));
        } catch (NumberFormatException e) {
            // Log error, change value of car number, or do nothing
        }
        car.setTest_month(Integer.parseInt(editTextTestMonth.getText().toString().trim()));
        car.setModel(editTextModel.getText().toString().trim());
        car.setKm(Integer.parseInt(editTextKM.getText().toString().trim()));
        car.setCar_year(Integer.parseInt(editTextYear.getText().toString().trim()));
        car.setMake(editTextMake.getText().toString().trim());
        addToDatabase();
    }

    public void moveToVihicleMainPage(Car car) {
        Intent intent = new Intent(this, CarListActivity.class);
        intent.putExtra("CAR",car);
        startActivity(intent);
        this.finish();
    }
    public void addToDatabase(){
        db = FirebaseDatabase.getInstance();
        myRef = db.getReference();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String currentUserID = user.getPhoneNumber();
            myRef.child("Users").child(currentUserID).child("Cars").child(car.getCar_number()+ "").setValue(car);
            myRef.push();
        moveToVihicleMainPage(car);

        }
    }


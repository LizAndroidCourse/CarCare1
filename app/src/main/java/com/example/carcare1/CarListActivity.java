package com.example.carcare1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

public class CarListActivity extends AppCompatActivity {

    Button addNew_BTN ;
    AdapterCars adapter_cars;
    private RecyclerView  car_RV;
    FirebaseDatabase db;
    private DatabaseReference myRef;
    ArrayList<Car> carList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_list);
        car_RV = findViewById(R.id.table_car);
        addNew_BTN = findViewById(R.id.BTN_add_car);
        addNew_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToAddScreen();
            }
        });
        setTable();
    }


    public void setTable(){
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseDatabase.getInstance();
        myRef= db.getReference().child("Users").child(user.getPhoneNumber()).child("Cars");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    carList.add(child.getValue(Car.class));
                }
                renderCarList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void renderCarList(){
        adapter_cars = new AdapterCars(carList);
        car_RV.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        car_RV.setLayoutManager(layoutManager);
        car_RV.setAdapter(adapter_cars);
        adapter_cars.SetOnItemClickListener(new AdapterCars.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Car car) {
                moveToVehicleScreen(car);
            }
        });
    }

    public void moveToVehicleScreen(Car car){
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("CAR_NAME",car.getMake()+" "+ car.getModel());
        startActivity(intent);
        this.finish();

    }
    public void moveToAddScreen(){
        Intent intent = new Intent(this, AddCarForm.class);
        startActivity(intent);
        this.finish();
    }
}


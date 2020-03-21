package com.example.carcare1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DeleteCar extends AppCompatActivity {


    AdapterCars adapter_cars;
    private RecyclerView car_RV;
    FirebaseDatabase db;
    private DatabaseReference myRef;
    ArrayList<Car> carList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_car);
        car_RV = findViewById(R.id.table_car);
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
                carList.remove(car);
                updateCarList();
                goToCarListScreen();
            }
        });
    }
    public void goToCarListScreen(){
        Intent i = new Intent(this,CarListActivity.class);
        startActivity(i);
        this.finish();
    }
    public void updateCarList(){
        db = FirebaseDatabase.getInstance();
        myRef = db.getReference();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserID = user.getPhoneNumber();
        myRef.child("Users").child(currentUserID).child("Cars").setValue(carList);
        myRef.push();
    }
}

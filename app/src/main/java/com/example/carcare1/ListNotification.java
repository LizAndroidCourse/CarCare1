package com.example.carcare1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ListNotification extends AppCompatActivity {

    FirebaseDatabase db;
    private DatabaseReference myRef;
    ListView listView;
    ArrayList<String> notificationArrayList;
    Car car;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_notification);
        car = (Car)getIntent().getSerializableExtra("CAR");
        TextView TV_carName = findViewById(R.id.car_name_title);
        TV_carName.setText(car.getMake()+" "+ car.getModel());
      listView = findViewById(R.id.listNotification);
      notificationArrayList = new ArrayList<>();

        setNotifications();
        ListAdapter listAdapter = new ArrayAdapter<String>(this,R.layout.list_view,notificationArrayList);
        listView.setAdapter(listAdapter);

    }
public void setNotifications(){
        checkGarage();
        checkTestAndInsurance();
}
public void checkTestAndInsurance(){
    DateFormat dateFormat = new SimpleDateFormat("MM");
    Date date = new Date();
    if(car.getTest_month() == Integer.parseInt(dateFormat.format(date))){
        notificationArrayList.add("לשלם על רישיון רכב");
        notificationArrayList.add("לעשות טסט לרכב");
    }  if(car.getInsurance_month() == Integer.parseInt(dateFormat.format(date))){
        notificationArrayList.add("לחדש את הביטוח , ואל תשכח לעדכן כמה עלה");
    }

}
public void checkGarage(){
        if(car.getKm()-car.getLastKmBeforeGarage() > 10000)
        {
            notificationArrayList.add("הגיע הזמן לבקר במוסך עברת 10000 קילומטרים");
        }
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


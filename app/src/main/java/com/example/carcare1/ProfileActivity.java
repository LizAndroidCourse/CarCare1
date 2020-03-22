package com.example.carcare1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.Calendar;

public class ProfileActivity extends AppCompatActivity implements Serializable {
    PendingIntent pendingIntent;
    Car car;
    Intent intentAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
       car = (Car)getIntent().getSerializableExtra("CAR");
        TextView TV_carName = findViewById(R.id.car_name_title);
        TV_carName.setText(car.getMake()+" "+ car.getModel());
        Button BTN_addCar = findViewById(R.id.BTN_addCar);
        BTN_addCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, AddCarForm.class);
                startActivity(intent);
            }
        });
        Button BTN_changeCar = findViewById(R.id.BTN_changeCar);
        BTN_changeCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToCarListScreen();
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

        Button BTN_foundGarage = findViewById(R.id.BTN_updateCar);
        BTN_foundGarage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToUpdateCarScreen();
            }
        });

        Button BTN_financialReport = findViewById(R.id.BTN_financialReport);
        BTN_financialReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToFinancialReportScreen();
            }
        });

    }

    public void moveToGasScreen(){
        Intent intent = new Intent(this, Refueling.class);
        intent.putExtra("CAR",car);
        startActivity(intent);
        this.finish();
    }
    public void moveToTestScreen(){
        Intent intent = new Intent(this, TestAndInsurance.class);
        intent.putExtra("CAR",car);
        startActivity(intent);
        this.finish();
    }
    public void moveToAlertsScreen(){
        Intent intent = new Intent(this, ListNotification.class);
        intent.putExtra("CAR",car);
        startActivity(intent);
        this.finish();
    }


    public void moveToGarageScreen(){
        Intent intent = new Intent(this, Garage.class);
        intent.putExtra("CAR",car);
        startActivity(intent);
        this.finish();
    }
    public void moveToUpdateCarScreen(){
        Intent intent = new Intent(this, DeleteCar.class);
        intent.putExtra("CAR",car);
        startActivity(intent);
        this.finish();

    }


    public void moveToFinancialReportScreen(){
        Intent intent = new Intent(this, FinancialReport.class);
        intent.putExtra("CAR",car);
        startActivity(intent);
        this.finish();
    }
    public void moveToCarListScreen(){
        Intent intent = new Intent(this, CarListActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void setAlarm(){
        intentAlarm = new Intent(this, NotificationService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ProfileActivity.this, 0, intentAlarm, 0);
        AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
        // every day at 9 am
        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(System.currentTimeMillis());

        // if it's after or equal 9 am schedule for next day
        if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= 9) {
//            Log.e(TAG, "Alarm will schedule for next day!");
            calendar.add(Calendar.DAY_OF_YEAR, 1); // add, not set!
        }
        else{
 //           Log.e(TAG, "Alarm will schedule for today!");
        }
        calendar.set(Calendar.HOUR_OF_DAY, 10);
        calendar.set(Calendar.MINUTE, 27);
        calendar.set(Calendar.SECOND, 0);

        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);
        //am.setRepeating(am.RTC_WAKEUP, System.currentTimeMillis(), am.INTERVAL_DAY, pendingIntent);
    }


    @Override
    protected void onStop () {
        super .onStop() ;
        setAlarm();
        startService( intentAlarm) ;
    }
}

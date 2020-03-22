package com.example.carcare1;

import android.app.Notification;
import android.app.NotificationChannel ;
import android.app.NotificationManager ;
import android.app.PendingIntent;
import android.app.Service ;
import android.content.Intent ;
import android.os.Handler ;
import android.os.IBinder ;
import android.util.Log ;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer ;
import java.util.TimerTask ;
public class NotificationService extends Service {
    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;
    Timer timer ;
    TimerTask timerTask ;
    long currentDate;
    String TAG = "Timers" ;
    int Your_X_SECS = 5 ;
    private FirebaseDatabase db;
    private DatabaseReference myRef;
    ArrayList<Car> carList = new ArrayList<>();

    @Override
    public IBinder onBind (Intent arg0) {
        return null;
    }
    @Override
    public int onStartCommand (Intent intent , int flags , int startId) {
        Log. e ( TAG , "onStartCommand" ) ;
        setCarList();
        super .onStartCommand(intent , flags , startId) ;
        startTimer() ;
      //  initializeHandler() ;
        return START_STICKY ;
    }
    @Override
    public void onCreate () {
        Log. e ( TAG , "onCreate" ) ;
    }
    @Override
    public void onDestroy () {
        Log. e ( TAG , "onDestroy" ) ;
        stopTimerTask();
        super .onDestroy() ;
    }

    public void setCarList(){
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseDatabase.getInstance();
        Log.d("USER",user.getPhoneNumber());
        myRef= db.getReference().child("Users").child(user.getPhoneNumber()).child("Cars");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    carList.add(child.getValue(Car.class));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    //we are going to use a handler to be able to run in our TimerTask
    final Handler handler = new Handler() ;
    public void startTimer () {
        timer = new Timer() ;
        initializeTimerTask() ;
        currentDate = new Date().getTime();
        timer .schedule( timerTask , 5000) ;

        //
    }
    public void stopTimerTask () {
        if ( timer != null ) {
            timer .cancel() ;
            timer = null;
        }
    }
    public void initializeTimerTask () {
        timerTask = new TimerTask() {
            public void run () {
                handler .post( new Runnable() {
                    public void run () {
                        currentDate += Calendar.DATE*7;
                        createNotifications() ;
                    }
                }) ;
            }
        } ;
    }
    private void createNotifications () {
      createNotificationForTest();
      createNotificationForInsurance();
      createNotificationForOilAndWheels();
    }
    private void createNotificationForOilAndWheels(){
        Intent intent = new Intent(NotificationService.this, ProfileActivity.class);
        PendingIntent pi = PendingIntent.getActivity(NotificationService.this, 0, intent, 0);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService( NOTIFICATION_SERVICE ) ;
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext() , default_notification_channel_id ) ;
        mBuilder.setContentTitle( "הגיע הזמן לבדוק שמן ולנפח גלגלים " ) ;
        mBuilder.setContentText( " היכנס לאפליקציה לבדיקת התראות " ) ;
        mBuilder.setSmallIcon(R.drawable.carcareicon ) ;
        mBuilder.setContentIntent(pi);
        mBuilder.setDefaults(Notification.DEFAULT_SOUND);
        mBuilder.setAutoCancel( true ) ;
                if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
                    int importance = NotificationManager. IMPORTANCE_HIGH ;
                    NotificationChannel notificationChannel = new NotificationChannel( NOTIFICATION_CHANNEL_ID , "NOTIFICATION_CHANNEL_NAME" , importance) ;
                    mBuilder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
                    assert mNotificationManager != null;
                    mNotificationManager.createNotificationChannel(notificationChannel) ;
        }
        mNotificationManager.notify(1 , mBuilder.build()) ;
                stopTimerTask();
    }
    private void createNotificationForInsurance(){
        Intent intent = new Intent(NotificationService.this, ProfileActivity.class);
        PendingIntent pi = PendingIntent.getActivity(NotificationService.this, 0, intent, 0);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService( NOTIFICATION_SERVICE ) ;
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext() , default_notification_channel_id ) ;
        mBuilder.setContentTitle( "הגיע הזמן לחדש ביטוח " ) ;
        mBuilder.setContentText( " היכנס לאפליקציה לבדיקת התראות " ) ;
        mBuilder.setSmallIcon(R.drawable.carcareicon ) ;
        mBuilder.setContentIntent(pi);
        mBuilder.setDefaults(Notification.DEFAULT_SOUND);
        mBuilder.setAutoCancel( true ) ;
        for (int i=0;i<carList.size();i++){
            DateFormat dateFormat = new SimpleDateFormat("MM");
            Date date = new Date();
            if (Integer.parseInt(dateFormat.format(date)) == carList.get(i).getInsurance_month()){
                if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
                    int importance = NotificationManager. IMPORTANCE_HIGH ;
                    NotificationChannel notificationChannel = new NotificationChannel( NOTIFICATION_CHANNEL_ID , "NOTIFICATION_CHANNEL_NAME" , importance) ;
                    mBuilder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
                    assert mNotificationManager != null;
                    mNotificationManager.createNotificationChannel(notificationChannel) ;
                }
            }
        }
        mNotificationManager.notify(2 , mBuilder.build()) ;
//        stopTimerTask();

    }
    private void createNotificationForTest(){
        Intent intent = new Intent(NotificationService.this, ProfileActivity.class);
        PendingIntent pi = PendingIntent.getActivity(NotificationService.this, 0, intent, 0);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService( NOTIFICATION_SERVICE ) ;
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext() , default_notification_channel_id ) ;
        mBuilder.setContentTitle( "הגיע הזמן לטסט לאחד הרכבים " ) ;
        mBuilder.setContentText( "וכמובן לא לשכוח לשלם על הרישיון רכב , היכנס לאפליקציה לבדיקת התראות " ) ;
        mBuilder.setSmallIcon(R.drawable.carcareicon ) ;
        mBuilder.setContentIntent(pi);
        mBuilder.setDefaults(Notification.DEFAULT_SOUND);
        mBuilder.setAutoCancel( true ) ;

        for (int i=0;i<carList.size();i++){
            DateFormat dateFormat = new SimpleDateFormat("MM");
            Date date = new Date();
            Log.d("CAR" , carList.get(i).toString());
            Log.d("CAR", "       "+Integer.parseInt(dateFormat.format(date)) );
            if (Integer.parseInt(dateFormat.format(date)) == carList.get(i).getTest_month()){
                if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
                    int importance = NotificationManager. IMPORTANCE_HIGH ;
                    NotificationChannel notificationChannel = new NotificationChannel( NOTIFICATION_CHANNEL_ID , "NOTIFICATION_CHANNEL_NAME" , importance) ;
                    mBuilder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
                    assert mNotificationManager != null;
                    mNotificationManager.createNotificationChannel(notificationChannel) ;
                }
            }
        }
        mNotificationManager.notify(0 , mBuilder.build()) ;
//        stopTimerTask();

    }
}
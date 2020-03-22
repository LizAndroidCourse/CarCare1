package com.example.carcare1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class GetUserName extends AppCompatActivity {

    Button BTN_goToAddCar;
    EditText nickname;

    //add firebase  Database stuff
    private FirebaseDatabase db;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_user_name);
        nickname = findViewById(R.id.nickname);
        BTN_goToAddCar = findViewById(R.id.BTN_goToAddCar);
        BTN_goToAddCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nickname_str = nickname.toString();
                db = FirebaseDatabase.getInstance();
                myRef = db.getReference();
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(nickname_str)
                        .build();

                user.updateProfile(profileUpdates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                            myRef.child("Users").child(user.getPhoneNumber()).setValue(nickname_str);
                            Intent intent = new Intent(GetUserName.this, AddCarForm.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                    }
                });

            }
        });
     }
}

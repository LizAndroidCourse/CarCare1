package com.example.carcare1;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {


    private EditText editTextMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        editTextMobile = findViewById(R.id.editTextMobile);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Intent intent;
            if (user.getDisplayName().equals("") || user.getDisplayName() == null) {
                intent = new Intent(MainActivity.this, GetUserName.class);
            } else {
                intent = new Intent(MainActivity.this, CarListActivity.class);
            }
            startActivity(intent);
        } else {
            findViewById(R.id.buttonContinue).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String mobile = editTextMobile.getText().toString().trim();

                    if (mobile.isEmpty() || mobile.length() < 9) {
                        editTextMobile.setError("Enter a valid mobile");
                        editTextMobile.requestFocus();
                        return;
                    }

                    Intent intent = new Intent(MainActivity.this, VerifyPhoneActivity.class);
                    intent.putExtra("mobile", mobile);
                    startActivity(intent);
                }
            });
        }

    }}
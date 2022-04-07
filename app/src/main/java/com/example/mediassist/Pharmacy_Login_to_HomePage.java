package com.example.mediassist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Pharmacy_Login_to_HomePage extends AppCompatActivity {
    Handler h=new Handler();
    String PH_UserEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_login_to_home_page);


        Intent intent = getIntent();
        PH_UserEmail = intent.getStringExtra("PharmaEmail_LP");

        h.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent send = new Intent(getApplicationContext(), Pharmacy_menu.class);
                send.putExtra("PharmaEmail",PH_UserEmail);
                startActivity(send);
                finish();

            }
        },2000);
    }
}
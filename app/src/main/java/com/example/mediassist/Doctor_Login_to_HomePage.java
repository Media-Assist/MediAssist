package com.example.mediassist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Doctor_Login_to_HomePage extends AppCompatActivity {
    Handler h=new Handler();
    String D_UserEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_login_to_home_page);

        Intent intent = getIntent();
        D_UserEmail = intent.getStringExtra("DoctorEmail");

        h.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent send = new Intent(getApplicationContext(), Doctor_menu.class);
                send.putExtra("DoctorEmail_LP",D_UserEmail);
                startActivity(send);
                finish();

            }
        },2000);
    }
}
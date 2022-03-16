package com.example.mediassist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

public class Patient_Login_to_HomePage extends AppCompatActivity {
    Handler h=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_login_to_home_page);



        h.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent send = new Intent(getApplicationContext(), Patient_menu.class);
                startActivity(send);
                finish();

            }
        },2000);

    }


}
package com.example.mediassist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Splash_Screen extends AppCompatActivity {
    Handler h=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent send = new Intent(getApplicationContext(), Login_option.class);
                startActivity(send);
                finish();

            }
        },3000);

    }
}
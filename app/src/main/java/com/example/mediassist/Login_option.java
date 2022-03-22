package com.example.mediassist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Login_option extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_option);
    }

    public void DoctorLogin(View view) {
        Intent send = new Intent(Login_option.this, Doctor_Login.class);
        startActivity(send);
    }

    public void PatientLogin(View view) {
        Intent send = new Intent(Login_option.this, Patient_Login.class);
        startActivity(send);
    }

    public void PharmacyLogin(View view) {
        Intent send = new Intent(Login_option.this, Pharmacy_Login.class);
        startActivity(send);
    }
}
package com.example.mediassist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Patient_menu extends AppCompatActivity {
String PatientEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_menu);
    }

    public void ViewPrescription(View view) {
        SharedPreferences sp = view.getContext().getSharedPreferences("patientData", Context.MODE_PRIVATE);
        PatientEmail = sp.getString("patient_email", "");
        Intent send = new Intent(Patient_menu.this, View_Prescription.class);
        send.putExtra("PatientID",PatientEmail);
        startActivity(send);
    }

    public void ViewInvoice(View view) {
        SharedPreferences sp = view.getContext().getSharedPreferences("patientData", Context.MODE_PRIVATE);
        PatientEmail = sp.getString("patient_email", "");
        Intent send = new Intent(Patient_menu.this, View_Invoice.class);
        send.putExtra("PatientID",PatientEmail);
        startActivity(send);
    }

    public void viewsicknote(View view) {
        SharedPreferences sp = view.getContext().getSharedPreferences("patientData", Context.MODE_PRIVATE);
        PatientEmail = sp.getString("patient_email", "");
        Intent send = new Intent(Patient_menu.this, View_Sicknote.class);
        send.putExtra("PatientID",PatientEmail);
        startActivity(send);
    }

    public void pVideoCall(View view) {
        Intent send = new Intent(Patient_menu.this, Patient_VideoCall.class);
        startActivity(send);
    }

    public void PatientProfile(View view) {
        Intent send = new Intent(Patient_menu.this, MainActivity2.class);
        startActivity(send);
    }

    public void PatientLogout(View view) {
        Intent send = new Intent(Patient_menu.this, Patient_Login.class);
        startActivity(send);
        finish();
    }
}
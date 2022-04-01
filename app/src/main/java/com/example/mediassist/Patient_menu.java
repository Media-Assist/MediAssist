package com.example.mediassist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Patient_menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_menu);
    }

    public void ViewPrescription(View view) {
        Intent send = new Intent(Patient_menu.this, View_Prescription.class);
        startActivity(send);
    }

    public void ViewInvoice(View view) {
        Intent send = new Intent(Patient_menu.this, View_Invoice.class);
        startActivity(send);
    }

    public void viewsicknote(View view) {
        Intent send = new Intent(Patient_menu.this, View_Sicknote.class);
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
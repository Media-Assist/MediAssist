package com.example.mediassist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Doctor_menu extends AppCompatActivity {
    String D_UserEmail_VC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_menu);
        Intent intent = getIntent();
        D_UserEmail_VC = intent.getStringExtra("DoctorEmail_LP");
    }

    public void CreatePrescription(View view) {
        Intent send = new Intent(Doctor_menu.this, Doctor_basic_Info.class);
        send.putExtra("DoctorID",D_UserEmail_VC);
        //send.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(send);

    }

    public void CreateInvoice(View view) {
        Intent send = new Intent(Doctor_menu.this, Doctor_Manu_toCreate_Invoice.class);
        send.putExtra("DoctorID_TO_CIN",D_UserEmail_VC);
        startActivity(send);
    }

    public void Sicknote(View view) {
        Intent send = new Intent(Doctor_menu.this, Doctor_Manu_toCreate_SickNote.class);
        send.putExtra("DoctorID_TO_CSI",D_UserEmail_VC);
        startActivity(send);
    }

    public void VideoCall(View view) {
        Intent send = new Intent(Doctor_menu.this, Doctor_Videocall.class);
        send.putExtra("RoomCode",D_UserEmail_VC);
        startActivity(send);

    }

    public void DoctorProfile(View view) {
        Toast.makeText(getApplicationContext(),"Doctor Profile Not available",Toast.LENGTH_SHORT).show();
    }

    public void DoctorLogout(View view) {
        Intent send = new Intent(Doctor_menu.this, Doctor_Login.class);
        startActivity(send);
        finish();
    }
}
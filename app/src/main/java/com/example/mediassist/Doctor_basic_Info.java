package com.example.mediassist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Doctor_basic_Info extends AppCompatActivity {
EditText BI_CP_DID,BI_CP_PID;
String getDID;
String PatientEmail;
FirebaseFirestore db=FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_basic_info);

        Intent intent = getIntent();
        getDID = intent.getStringExtra("DoctorID");

        BI_CP_DID=findViewById(R.id.BI_CP_DID);
        BI_CP_PID=findViewById(R.id.BI_CP_PID);

        BI_CP_DID.setText(getDID);
    }

    public void gotoCreateMyPrescription(View view) {

        if (TextUtils.isEmpty(BI_CP_DID.getText().toString())) {
            BI_CP_DID.setError("Required");
            return;
        }

        if (TextUtils.isEmpty(BI_CP_PID.getText().toString())) {
            BI_CP_PID.setError("Required");
            return;
        }

        DocumentReference documentReference = db.collection("Patient").document(BI_CP_PID.getText().toString());

        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    PatientEmail=documentSnapshot.getString("Email");
                    Toast.makeText(getApplicationContext(),PatientEmail,Toast.LENGTH_SHORT).show();

                    if(PatientEmail.isEmpty()) {
                        BI_CP_DID.setError("Invalid Information");
                        Toast.makeText(getApplicationContext(),PatientEmail,Toast.LENGTH_SHORT).show();
                    }else {
                        Intent send = new Intent(Doctor_basic_Info.this, Create_Prescription.class);
                        send.putExtra("D_DoctorID",BI_CP_DID.getText().toString());
                        send.putExtra("P_PatientID",BI_CP_PID.getText().toString());
                        startActivity(send);
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"wrong ID", Toast.LENGTH_SHORT).show();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Data Not Found", Toast.LENGTH_SHORT).show();
            }
        });



    }
}
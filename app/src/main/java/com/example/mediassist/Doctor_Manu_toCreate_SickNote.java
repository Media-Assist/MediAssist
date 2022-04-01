package com.example.mediassist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class Doctor_Manu_toCreate_SickNote extends AppCompatActivity {
    EditText BI_CSI_Date,BI_CSI_DID,BI_CSI_PID;
    DatePickerDialog picker;
    String BI_CSI_DIN;
    String CIN_PatientEmail;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_manu_to_create_sick_note);

        BI_CSI_Date=findViewById(R.id.BI_CSI_Date);
        BI_CSI_DID=findViewById(R.id.BI_CSI_DID);
        BI_CSI_PID=findViewById(R.id.BI_CSI_PID);


        Intent intent = getIntent();
        BI_CSI_DIN = intent.getStringExtra("DoctorID_TO_CSI");

        BI_CSI_DID.setText(BI_CSI_DIN);

        BI_CSI_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar=Calendar.getInstance();
                int day=calendar.get(Calendar.DAY_OF_MONTH);
                int month=calendar.get(Calendar.MONTH);
                int year=calendar.get(Calendar.YEAR);

                picker=new DatePickerDialog(Doctor_Manu_toCreate_SickNote.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        BI_CSI_Date.setText(i2+"-"+(i1+1)+"-"+i);
                    }
                }, year,month,day);
                picker.show();
            }
        });

    }

    public void gotoCreateMySickNote(View view) {
        if (TextUtils.isEmpty(BI_CSI_Date.getText().toString())) {
            BI_CSI_Date.setError("Required");
            return;
        }

        if (TextUtils.isEmpty(BI_CSI_DID.getText().toString())) {
            BI_CSI_DID.setError("Required");
            return;
        }

        if (TextUtils.isEmpty(BI_CSI_PID.getText().toString())) {
            BI_CSI_PID.setError("Required");
            return;
        }


        DocumentReference documentReference = db.collection("Patient").document(BI_CSI_PID.getText().toString());

        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    CIN_PatientEmail=documentSnapshot.getString("Email");
                    Toast.makeText(getApplicationContext(),CIN_PatientEmail,Toast.LENGTH_SHORT).show();

                    if(CIN_PatientEmail.isEmpty()) {
                        BI_CSI_PID.setError("Invalid Information");
                        Toast.makeText(getApplicationContext(),CIN_PatientEmail,Toast.LENGTH_SHORT).show();
                    }else {
                        Intent send = new Intent(Doctor_Manu_toCreate_SickNote.this,Create_Sicknote.class);
                        send.putExtra("D_DoctorID_CSI",BI_CSI_DID.getText().toString());
                        send.putExtra("P_PatientID_CSI",BI_CSI_PID.getText().toString());
                        send.putExtra("Date_CSI",BI_CSI_Date.getText().toString());
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
package com.example.mediassist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Calendar;

public class View_Prescription extends AppCompatActivity {
    EditText VP_Name,VP_date;
    String VP_full_details,VP_PatientName="";
    DatePickerDialog picker;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_prescription);

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);


        VP_Name=findViewById(R.id.VP_Name);
        VP_date=findViewById(R.id.VP_Date);

        VP_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar=Calendar.getInstance();
                int day=calendar.get(Calendar.DAY_OF_MONTH);
                int month=calendar.get(Calendar.MONTH);
                int year=calendar.get(Calendar.YEAR);

                picker=new DatePickerDialog(View_Prescription.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        VP_date.setText(i2+"-"+(i1+1)+"-"+i);
                    }
                }, year,month,day);
                picker.show();
            }
        });

    }
    public void detailPrescription(View view) {


        if (TextUtils.isEmpty(VP_Name.getText().toString())) {
            VP_Name.setError("Required");
            return;
        }

        if (TextUtils.isEmpty(VP_date.getText().toString())) {
            VP_date.setError("Required");
            return;
        }

        VP_full_details=VP_Name.getText().toString()+VP_date.getText().toString().trim();



        /*firebase part
        DocumentReference documentReference = db.collection("Prescription").document(VP_full_details);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                VP_PatientName=value.getString("PatientName");

                if(VP_PatientName.isEmpty()) {
                    VP_Name.setError("Invalid Information");
                    VP_date.setError("Invalid Information");
                    Toast.makeText(getApplicationContext(),VP_PatientName,Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(getApplicationContext(), Detail_Prescription.class);
                    intent.putExtra("Username", VP_full_details);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(),VP_PatientName,Toast.LENGTH_SHORT).show();
                }
            }
        });*/



        DocumentReference documentReference = db.collection("Prescription").document(VP_full_details);

        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    VP_PatientName=documentSnapshot.getString("PatientName");


                        Intent intent = new Intent(getApplicationContext(), Detail_Prescription.class);
                        intent.putExtra("Username", VP_full_details);
                        startActivity(intent);


                }else {
                    VP_Name.setError("Invalid Information");
                    VP_date.setError("Invalid Information");
                    Toast.makeText(getApplicationContext(),"Wrong Info",Toast.LENGTH_SHORT).show();
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
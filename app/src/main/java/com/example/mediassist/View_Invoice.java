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

public class View_Invoice extends AppCompatActivity {
    EditText VIN_Date,VIN_Name;
    String VIN_Usename,VIN_PID_Email;
    DatePickerDialog picker;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_invoice);
        VIN_Date=findViewById(R.id.VIN_Date);
        VIN_Name=findViewById(R.id.VIN_Name);

        VIN_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar=Calendar.getInstance();
                int day=calendar.get(Calendar.DAY_OF_MONTH);
                int month=calendar.get(Calendar.MONTH);
                int year=calendar.get(Calendar.YEAR);

                picker=new DatePickerDialog(View_Invoice.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        VIN_Date.setText(i2+"-"+(i1+1)+"-"+i);
                    }
                }, year,month,day);
                picker.show();
            }
        });
    }

    public void detailInvoice(View view) {
        if (TextUtils.isEmpty(VIN_Name.getText().toString())) {
            VIN_Name.setError("Required");
            return;
        }
        if (TextUtils.isEmpty(VIN_Date.getText().toString())) {
            VIN_Date.setError("Required");
            return;
        }


        VIN_Usename=VIN_Name.getText().toString()+VIN_Date.getText().toString();




        DocumentReference documentReference = db.collection("Invoice").document(VIN_Usename);

        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    VIN_PID_Email=documentSnapshot.getString("PatientID");


                    Intent intent = new Intent(getApplicationContext(), Detail_Invoice.class);
                    intent.putExtra("Username_VIN", VIN_Usename);
                    intent.putExtra("PatientMail_VIN",VIN_PID_Email);
                    startActivity(intent);


                }else {
                    VIN_Name.setError("Invalid Information");
                    VIN_Date.setError("Invalid Information");
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
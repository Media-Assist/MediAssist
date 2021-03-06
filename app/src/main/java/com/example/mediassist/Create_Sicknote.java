package com.example.mediassist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Create_Sicknote extends AppCompatActivity {
    EditText SN_Date,SN_hospital,SN_Did,SN_DName,SN_DNo,SN_Pid,SN_Pname,SN_sick_type,SN_Note;
    Button SN_Create_btn;
    //String date;
    String get_CSI_DID,get_CSI_PID,get_CSI_Date;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    DatePickerDialog picker;
    String FB_D_Firstname_CSI,FB_D_LastName_CSI,FB_D_Hospital_CSI,FB_D_MobileNo_CSI;
    String FB_P_Firstname_CSI,FB_P_LastName_CSI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_sicknote);

        Intent intent = getIntent();

        get_CSI_DID=intent.getStringExtra("D_DoctorID_CSI");
        get_CSI_PID=intent.getStringExtra("P_PatientID_CSI");
        get_CSI_Date=intent.getStringExtra("Date_CSI");

        SN_Date=findViewById(R.id.SN_Date);
        SN_hospital=findViewById(R.id.SN_hospital);
        SN_Did=findViewById(R.id.SN_Did);
        SN_DName=findViewById(R.id.SN_DName);
        SN_DNo=findViewById(R.id.SN_DNo);
        SN_Pid=findViewById(R.id.SN_Pid);
        SN_Pname=findViewById(R.id.SN_Pname);
        SN_sick_type=findViewById(R.id.SN_sick_type);
        SN_Note=findViewById(R.id.SN_Note);

        SN_Create_btn=findViewById(R.id.Sn_Create_btn);


        SN_Date.setText(get_CSI_Date);
        SN_Did.setText(get_CSI_DID);
        SN_Pid.setText(get_CSI_PID);

//get Docotr And Patient data from Firebase
        //ger doctor data
        DocumentReference documentReference_D = db.collection("Doctors").document(get_CSI_DID);

        documentReference_D.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    FB_D_Firstname_CSI=documentSnapshot.getString("FirstName");
                    FB_D_LastName_CSI=documentSnapshot.getString("LastName");
                    FB_D_Hospital_CSI=documentSnapshot.getString("Clinic Name");
                    FB_D_MobileNo_CSI=documentSnapshot.getString("Mobile No");
                    SN_DName.setText(FB_D_Firstname_CSI+" "+FB_D_LastName_CSI);
                    SN_hospital.setText(FB_D_Hospital_CSI);
                    SN_DNo.setText(FB_D_MobileNo_CSI);
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Data Not Found", Toast.LENGTH_SHORT).show();
            }
        });

        //get Patient data
        DocumentReference documentReference_P = db.collection("Patient").document(get_CSI_PID);

        documentReference_P.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    FB_P_Firstname_CSI=documentSnapshot.getString("FirstName");
                    FB_P_LastName_CSI=documentSnapshot.getString("LastName");
                    SN_Pname.setText(FB_P_Firstname_CSI+" "+FB_P_LastName_CSI);
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Data Not Found", Toast.LENGTH_SHORT).show();
            }
        });

//get Doctor Patient info end


        // date = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
        SN_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar=Calendar.getInstance();
                int day=calendar.get(Calendar.DAY_OF_MONTH);
                int month=calendar.get(Calendar.MONTH);
                int year=calendar.get(Calendar.YEAR);

                picker=new DatePickerDialog(Create_Sicknote.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        SN_Date.setText(i2+"-"+(i1+1)+"-"+i);
                    }
                }, year,month,day);
                picker.show();
            }
        });

        SN_Create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(SN_Date.getText().toString())) {
                    SN_hospital.setError("Required");
                    return;
                }

                if (TextUtils.isEmpty(SN_hospital.getText().toString())) {
                    SN_hospital.setError("Required");
                    return;
                }
                if (TextUtils.isEmpty(SN_Did.getText().toString())) {
                    SN_Did.setError("Required");
                    return;
                }
                if (TextUtils.isEmpty(SN_DName.getText().toString())) {
                    SN_DName.setError("Required");
                    return;
                }
                if (TextUtils.isEmpty(SN_DNo.getText().toString())) {
                    SN_DNo.setError("Required");
                    return;
                }
                if (TextUtils.isEmpty(SN_Pid.getText().toString())) {
                    SN_Pid.setError("Required");
                    return;
                }
                if (TextUtils.isEmpty(SN_Pname.getText().toString())) {
                    SN_Pname.setError("Required");
                    return;
                }
                if (TextUtils.isEmpty(SN_sick_type.getText().toString())) {
                    SN_sick_type.setError("Required");
                    return;
                }

                if (TextUtils.isEmpty(SN_Note.getText().toString())) {
                    SN_Note.setError("Required");
                    return;
                }
                insertSickNoteData();
                Toast.makeText(getApplicationContext(),"SickNote Created", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void insertSickNoteData() {

        DocumentReference documentReference = db.collection("Sicknote").document(SN_Pid.getText().toString()+SN_Date.getText().toString());
        Map<String,String> items=new HashMap<>();
        items.put("Date",SN_Date.getText().toString());
        items.put("HospitalName",SN_hospital.getText().toString());
        items.put("DoctorID",SN_Did.getText().toString());
        items.put("Doctor Name",SN_DName.getText().toString());
        items.put("Doctor No",SN_DNo.getText().toString());
        items.put("PatientID",SN_Pid.getText().toString());
        items.put("PatientName",SN_Pname.getText().toString());
        items.put("SickOfType",SN_sick_type.getText().toString());
        items.put("SickNote",SN_Note.getText().toString());


        documentReference.set(items).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                SN_Date.setText("");
                SN_hospital.setText("");
                SN_Did.setText("");
                SN_DName.setText("");
                SN_DNo.setText("");
                SN_Pid.setText("");
                SN_Pname.setText("");
                SN_sick_type.setText("");
                SN_Note.setText("");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"OnFailure: "+e.toString(),Toast.LENGTH_SHORT).show();
            }
        });

    }
}
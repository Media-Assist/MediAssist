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
import android.widget.TextView;
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

public class Create_Invoice extends AppCompatActivity {
    EditText CI_HospitalName,CI_date,CI_DID,CI_DName,CI_PDI,CI_PName,CI_Particulars,CI_Amount;
    TextView CI_List_OF_Particulars_txt,CI_Total_txt;
    Button CI_ADDInvoice_btn,CI_CreateInvoice_btn;
    String Particulars,Amount,Invoice_Particulars_Details="";
    String get_CIN_DID,get_CIN_PID,get_CIN_Date;
    String FB_D_Firstname_CIN,FB_D_LastName_CIN,FB_D_Hospital_CIN;
    String FB_P_Firstname_CIN,FB_P_LastName_CIN;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    //String date;
    DatePickerDialog picker;
    int Total=0,T=0;
    int addinvoice_flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_invoice);

        Intent intent = getIntent();

        get_CIN_DID=intent.getStringExtra("D_DoctorID_CIN");
        get_CIN_PID=intent.getStringExtra("P_PatientID_CIN");
        get_CIN_Date=intent.getStringExtra("Date_CIN");

        CI_HospitalName=findViewById(R.id.CI_HospitalName);
        CI_date=findViewById(R.id.CI_date);
        CI_DID=findViewById(R.id.CI_DID);
        CI_DName=findViewById(R.id.CI_DName);
        CI_PDI=findViewById(R.id.CI_PDI);
        CI_PName=findViewById(R.id.CI_PName);
        CI_Particulars=findViewById(R.id.CI_Particulars);
        CI_Amount=findViewById(R.id.CI_Amount);

        CI_List_OF_Particulars_txt=findViewById(R.id.CI_List_OF_Particulars_txt);
        CI_Total_txt=findViewById(R.id.CI_Total_txt);

        CI_ADDInvoice_btn=findViewById(R.id.CI_ADDInvoice_btn);
        CI_CreateInvoice_btn=findViewById(R.id.CI_CreateInvoice_btn);

        CI_date.setText(get_CIN_Date);
        CI_DID.setText(get_CIN_DID);
        CI_PDI.setText(get_CIN_PID);


//get Docotr And Patient data from Firebase
        //ger doctor data
        DocumentReference documentReference_D = db.collection("Doctors").document(get_CIN_DID);

        documentReference_D.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    FB_D_Firstname_CIN=documentSnapshot.getString("FirstName");
                    FB_D_LastName_CIN=documentSnapshot.getString("LastName");
                    FB_D_Hospital_CIN=documentSnapshot.getString("Clinic Name");
                    CI_DName.setText(FB_D_Firstname_CIN+" "+FB_D_LastName_CIN);
                    CI_HospitalName.setText(FB_D_Hospital_CIN);
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Data Not Found", Toast.LENGTH_SHORT).show();
            }
        });
        //get Patient data
        DocumentReference documentReference_P = db.collection("Patient").document(get_CIN_PID);

        documentReference_P.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    FB_P_Firstname_CIN=documentSnapshot.getString("FirstName");
                    FB_P_LastName_CIN=documentSnapshot.getString("LastName");
                    CI_PName.setText(FB_P_Firstname_CIN+" "+FB_P_LastName_CIN);
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Data Not Found", Toast.LENGTH_SHORT).show();
            }
        });

//get Doctor Patient info end

        //  date = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
        CI_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar=Calendar.getInstance();
                int day=calendar.get(Calendar.DAY_OF_MONTH);
                int month=calendar.get(Calendar.MONTH);
                int year=calendar.get(Calendar.YEAR);

                picker=new DatePickerDialog(Create_Invoice.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        CI_date.setText(i2+"-"+(i1+1)+"-"+i);
                    }
                }, year,month,day);
                picker.show();
            }
        });

        CI_CreateInvoice_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(CI_HospitalName.getText().toString())) {
                    CI_HospitalName.setError("Required");
                    return;
                }
                if (TextUtils.isEmpty(CI_date.getText().toString())) {
                    CI_date.setError("Required");
                    return;
                }
                if (TextUtils.isEmpty(CI_DID.getText().toString())) {
                    CI_DID.setError("Required");
                    return;
                }
                if (TextUtils.isEmpty(CI_DName.getText().toString())) {
                    CI_DName.setError("Required");
                    return;
                }
                if (TextUtils.isEmpty(CI_PDI.getText().toString())) {
                    CI_PDI.setError("Required");
                    return;
                }
                if (TextUtils.isEmpty(CI_PName.getText().toString())) {
                    CI_PName.setError("Required");
                    return;
                }
                if (addinvoice_flag==0){
                    if (TextUtils.isEmpty(CI_Particulars.getText().toString())) {
                        CI_Particulars.setError("Required");
                        return;
                    }
                }
                if (addinvoice_flag==0){
                    if (TextUtils.isEmpty(CI_Amount.getText().toString())) {
                        CI_Amount.setError("Required");
                        return;
                    }
                }

                insertInviceData();
                Toast.makeText(getApplicationContext(),"Invoice Created", Toast.LENGTH_SHORT).show();
            }
        });

        CI_ADDInvoice_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(CI_Particulars.getText().toString())) {
                    CI_Particulars.setError("Required");
                    return;
                }
                if (TextUtils.isEmpty(CI_Amount.getText().toString())) {
                    CI_Amount.setError("Required");
                    return;
                }
                Particulars=CI_Particulars.getText().toString();
                Amount=CI_Amount.getText().toString();
                Total=Integer.parseInt(CI_Amount.getText().toString());
                Total=Total+T;
                T=Total;

                Invoice_Particulars_Details=Invoice_Particulars_Details+Particulars+" | "+Amount+" | "+"\n";
                CI_List_OF_Particulars_txt.setText(Invoice_Particulars_Details);
                CI_Particulars.setText("");
                CI_Amount.setText("");
                CI_Total_txt.setText("RS : "+String.valueOf(Total));


                Toast.makeText(getApplicationContext(),"Done", Toast.LENGTH_SHORT).show();
                addinvoice_flag=1;
            }
        });


    }

    private void insertInviceData() {

        DocumentReference documentReference = db.collection("Invoice").document(CI_PDI.getText().toString()+CI_date.getText().toString());
        Map<String,String> items=new HashMap<>();
        items.put("HospitalName",CI_HospitalName.getText().toString());
        items.put("Date",CI_date.getText().toString());
        items.put("DoctorID",CI_DID.getText().toString());
        items.put("DoctorName",CI_DName.getText().toString());
        items.put("PatientID",CI_PDI.getText().toString());
        items.put("PatientName",CI_PName.getText().toString());
        items.put("Invoice_Particulars_Details",CI_List_OF_Particulars_txt.getText().toString());
        items.put("Total",CI_Total_txt.getText().toString());

        documentReference.set(items).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                CI_HospitalName.setText("");
                CI_date.setText("");
                CI_DID.setText("");
                CI_DName.setText("");
                CI_PDI.setText("");
                CI_PName.setText("");
                CI_List_OF_Particulars_txt.setText("Covid test 1000");
                CI_Total_txt.setText("1000/-");
                Particulars="";
                Amount="";
                Invoice_Particulars_Details="";
                Total=0;
                T=0;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"OnFailure: "+e.toString(),Toast.LENGTH_SHORT).show();
            }
        });

    }

}
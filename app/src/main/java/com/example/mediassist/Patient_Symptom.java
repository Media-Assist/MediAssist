package com.example.mediassist;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mediassist.RealtimeDB.BookAppointment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Patient_Symptom extends AppCompatActivity {
    TextView CI_txt;
    CheckBox symptom1,symptom2,symptom3,symptom4,symptom5,symptom6,symptom7,symptom8,symptom9,symptom10,symptom11,symptom12,symptom13;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    String PatientEmail;
    String Symptoms="";
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_symptom);



        symptom1=findViewById(R.id.symptom1);
        symptom2=findViewById(R.id.symptom2);
        symptom3=findViewById(R.id.symptom3);
        symptom4=findViewById(R.id.symptom4);
        symptom5=findViewById(R.id.symptom5);
        symptom6=findViewById(R.id.symptom6);
        symptom7=findViewById(R.id.symptom7);
        symptom8=findViewById(R.id.symptom8);
        symptom9=findViewById(R.id.symptom9);
        symptom10=findViewById(R.id.symptom10);
        symptom11=findViewById(R.id.symptom11);
        symptom12=findViewById(R.id.symptom12);
        symptom13=findViewById(R.id.symptom13);


        CI_txt=findViewById(R.id.CI_txt);
    }

    public void gettext(View view) {
        progressDialog =new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();


        if(symptom1.isChecked())
            Symptoms = Symptoms + " Fever ,";
        if(symptom2.isChecked())
            Symptoms = Symptoms + " Headache ,";
        if(symptom3.isChecked())
            Symptoms = Symptoms + " Dry cough ,";
        if(symptom4.isChecked())
            Symptoms = Symptoms + " Sore throat ,";
        if(symptom5.isChecked())
            Symptoms = Symptoms + " Shortness of breath ,";
        if(symptom6.isChecked())
            Symptoms = Symptoms + " Chest Pain ,";
        if(symptom7.isChecked())
            Symptoms = Symptoms + " Tiredness ,";
        if(symptom8.isChecked())
            Symptoms = Symptoms + " vomiting ,";
        if(symptom9.isChecked())
            Symptoms = Symptoms + " Anxiety ,";
        if(symptom10.isChecked())
            Symptoms = Symptoms + " Body Pain ,";
        if(symptom11.isChecked())
            Symptoms = Symptoms + " Sweating ,";
        if(symptom12.isChecked())
            Symptoms = Symptoms + " Eye pain ,";
        if(symptom13.isChecked())
            Symptoms = Symptoms + " Eye redness ";
        // Toast is created to display the
        // message using show() method.
        if(Symptoms.isEmpty()){
            CI_txt.setTextColor(this.getResources().getColor(R.color.red));
            CI_txt.setText("Not Selected");
            Toast.makeText(Patient_Symptom.this, "Symptom Not Selected", Toast.LENGTH_SHORT).show();
            if (progressDialog.isShowing())
                progressDialog.dismiss();
        }else {

            SharedPreferences sp = view.getContext().getSharedPreferences("patientData", Context.MODE_PRIVATE);
            PatientEmail = sp.getString("patient_email", "");

            DocumentReference documentReference = db.collection("Symptoms").document(PatientEmail);
            Map<String,String> items=new HashMap<>();
            items.put("Symptom",Symptoms);



            documentReference.set(items).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Symptoms="";
                    Intent send = new Intent(Patient_Symptom.this, BookAppointment.class);

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    startActivity(send);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"OnFailure: "+e.toString(),Toast.LENGTH_SHORT).show();
                }
            });
        }




    }

}
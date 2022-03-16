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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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
public class Create_Prescription extends AppCompatActivity {
    EditText CP_Date,CP_Did,CP_DName,CP_PID,CP_PName,CP_P_age,CP_DoctorNote,CP_Diagnosis,CP_Day_ET,CP_ET_medicinces;
    TextView CP_txt_Symtoms,CP_List_Of_Medicines_txt;
    Spinner CP_medicinces_spinner,CP_time_Spinner,CP_Day_Spinner;
    RadioGroup Cp_Meal_RG;
    RadioButton before,after;
    Button CP_ADDMedicine_Btn,CP_Submit;
    String medi,meal,time,days_spinner,day_et;
    String getDID,getPID;
    //String date;
    String All_details = "";
    int addmedicine_flag=0;
    DatePickerDialog picker;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    String FB_D_Firstname,FB_D_LastName,FB_P_Firstname,FB_P_LastName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_prescription);

        Intent intent = getIntent();

        getDID=intent.getStringExtra("D_DoctorID");
        getPID=intent.getStringExtra("P_PatientID");

        CP_Date=findViewById(R.id.CP_Date);
        CP_Did=findViewById(R.id.CP_Did);
        CP_DName=findViewById(R.id.CP_DName);
        CP_PID=findViewById(R.id.CP_PID);
        CP_PName=findViewById(R.id.CP_PName);
        CP_P_age=findViewById(R.id.Cp_P_age);
        CP_DoctorNote=findViewById(R.id.CP_DoctorNote);
        CP_Diagnosis=findViewById(R.id.CP_Diagnosis);
        CP_Day_ET=findViewById(R.id.CP_Day_ET);
        CP_ET_medicinces=findViewById(R.id.CP_ET_medicinces);

        CP_txt_Symtoms=findViewById(R.id.Cp_txt_Symtoms);
        CP_List_Of_Medicines_txt=findViewById(R.id.CP_List_Of_Medicines_txt);

        CP_medicinces_spinner=findViewById(R.id.CP_medicinces_spinner);
        CP_time_Spinner=findViewById(R.id.CP_time_Spinner);
        CP_Day_Spinner=findViewById(R.id.CP_Day_Spinner);

        Cp_Meal_RG=findViewById(R.id.Cp_Meal_RG);

        before=findViewById(R.id.Before);
        after=findViewById(R.id.after);
        after.setChecked(true);

        CP_ADDMedicine_Btn=findViewById(R.id.CP_ADDMedicine_Btn);
        CP_Submit=findViewById(R.id.CP_Submit);

        CP_Did.setText(getDID);
        CP_PID.setText(getPID);



//get Docotr And Patient data from Firebase
        //ger doctor data
        DocumentReference documentReference_D = db.collection("Doctors").document(getDID);

        documentReference_D.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    FB_D_Firstname=documentSnapshot.getString("FirstName");
                    FB_D_LastName=documentSnapshot.getString("LastName");
                    CP_DName.setText(FB_D_Firstname+" "+FB_D_LastName);
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Data Not Found", Toast.LENGTH_SHORT).show();
            }
        });
        //get Patient data
        DocumentReference documentReference_P = db.collection("Patient").document(getPID);

        documentReference_P.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    FB_P_Firstname=documentSnapshot.getString("FirstName");
                    FB_P_LastName=documentSnapshot.getString("LastName");
                    CP_PName.setText(FB_P_Firstname+" "+FB_P_LastName);
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Data Not Found", Toast.LENGTH_SHORT).show();
            }
        });

        //  date = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
        CP_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar=Calendar.getInstance();
                int day=calendar.get(Calendar.DAY_OF_MONTH);
                int month=calendar.get(Calendar.MONTH);
                int year=calendar.get(Calendar.YEAR);

                picker=new DatePickerDialog(Create_Prescription.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        CP_Date.setText(i2+"-"+(i1+1)+"-"+i);
                    }
                }, year,month,day);
                picker.show();
            }
        });

        CP_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(CP_Date.getText().toString())) {
                    CP_Date.setError("Required");
                    return;
                }

                if (TextUtils.isEmpty(CP_Date.getText().toString())) {
                    CP_Date.setError("Required");
                    return;
                }
                if (TextUtils.isEmpty(CP_Did.getText().toString())) {
                    CP_Did.setError("Required");
                    return;
                }
                if (TextUtils.isEmpty(CP_DName.getText().toString())) {
                    CP_DName.setError("Required");
                    return;
                }
                if (TextUtils.isEmpty(CP_PID.getText().toString())) {
                    CP_PID.setError("Required");
                    return;
                }
                if (TextUtils.isEmpty(CP_PName.getText().toString())) {
                    CP_PName.setError("Required");
                    return;
                }
                if (TextUtils.isEmpty(CP_P_age.getText().toString())) {
                    CP_P_age.setError("Required");
                    return;
                }

                if (TextUtils.isEmpty(CP_Diagnosis.getText().toString())) {
                    CP_Diagnosis.setError("Required");
                    return;
                }
                if (addmedicine_flag==0){
                    if (TextUtils.isEmpty(CP_Day_ET.getText().toString())) {
                        CP_Day_ET.setError("Required");
                        return;
                    }
                }
                if (addmedicine_flag==0){
                    if (TextUtils.isEmpty(CP_ET_medicinces.getText().toString())) {
                        CP_ET_medicinces.setError("Required");
                        return;
                    }
                }
                if (TextUtils.isEmpty(CP_DoctorNote.getText().toString())) {
                    CP_DoctorNote.setError("Required");
                    return;
                }
                if (All_details.isEmpty()){
                    CP_ET_medicinces.setError("Add Details Required");
                }

                insertPresctiptionData();

            }
        });

        CP_ADDMedicine_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(CP_Day_ET.getText().toString())) {
                    CP_Day_ET.setError("add medicine Required");
                    return;
                }

                medi=CP_medicinces_spinner.getSelectedItem().toString();
                meal = ((RadioButton)findViewById(Cp_Meal_RG.getCheckedRadioButtonId())).getText().toString();
                time=CP_time_Spinner.getSelectedItem().toString();
                days_spinner=CP_Day_Spinner.getSelectedItem().toString();
                day_et=CP_Day_ET.getText().toString();

                if(CP_ET_medicinces.getText().toString().isEmpty()) {
                    // editText is empty
                    All_details=All_details+medi+" | "+meal+" | "+time+" | "+day_et+" | "+days_spinner+" | "+"\n";
                    CP_List_Of_Medicines_txt.setText(All_details);
                    CP_Day_ET.setText("");
                } else {
                    // editText is not empty
                    All_details=All_details+CP_ET_medicinces.getText().toString()+" | "+meal+" | "+time+" | "+day_et+" | "+days_spinner+" | "+"\n";
                    CP_List_Of_Medicines_txt.setText(All_details);
                    CP_ET_medicinces.setText("");
                    CP_Day_ET.setText("");

                }



                Toast.makeText(getApplicationContext(),"Done", Toast.LENGTH_SHORT).show();
                addmedicine_flag=1;
            }
        });
    }

    private void insertPresctiptionData() {

        DocumentReference documentReference = db.collection("Prescription").document(CP_PID.getText().toString()+CP_Date.getText().toString());
        Map<String,String> items=new HashMap<>();
        items.put("Date",CP_Date.getText().toString());
        items.put("DoctorID",CP_Did.getText().toString());
        items.put("DoctorName",CP_DName.getText().toString());
        items.put("PatientID",CP_PID.getText().toString());
        items.put("PatientName",CP_PName.getText().toString());
        items.put("PatientAge",CP_P_age.getText().toString());
        items.put("Diagnosis",CP_Diagnosis.getText().toString());
        items.put("Symtoms",CP_txt_Symtoms.getText().toString());
        items.put("MedicinceList",CP_List_Of_Medicines_txt.getText().toString());
        items.put("DoctorNote",CP_DoctorNote.getText().toString());

        documentReference.set(items).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                CP_Date.setText("");
                CP_Did.setText("");
                CP_DName.setText("");
                CP_PID.setText("");
                CP_PName.setText("");
                CP_P_age.setText("");
                CP_Diagnosis.setText("");
                CP_txt_Symtoms.setText("Fever");
                CP_List_Of_Medicines_txt.setText("Dolo 650");
                CP_DoctorNote.setText("");
                CP_Day_ET.setText("");
                medi="";
                meal="";
                time="";
                days_spinner="";
                day_et="";
                All_details = "";
                Toast.makeText(getApplicationContext(),"Insert successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"OnFailure: "+e.toString(),Toast.LENGTH_SHORT).show();
            }
        });

    }

}
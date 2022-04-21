package com.example.mediassist;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class Doctor_Registration extends AppCompatActivity {
    EditText DR_FirstName,DR_LastName,DR_Email,DR_MobileNo,DR_DateOfBirth,DR_Specialization,DR_Education,DR_MCR_no,DR_Clinic_Name,DR_Experience,DR_Address,DR_City,DR_State,DR_Password,DR_Confirm_Password;
    RadioGroup DR_D_Gender;
    int password_flag=0;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore db;
    String gender;
    DatePickerDialog picker;
    DatabaseReference realtimedata;
    int is8L=0,isnum=0,isupper=0,isspecial=0;
    TextView DR_worning;
    String dp_msg="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_registration);


        DR_FirstName=findViewById(R.id.DR_FirstName);
        DR_LastName=findViewById(R.id.DR_LastName);
        DR_Email=findViewById(R.id.DR_Email);
        DR_MobileNo=findViewById(R.id.DR_MobileNo);
        DR_DateOfBirth=findViewById(R.id.DR_DateOfBirth);
        DR_Specialization=findViewById(R.id.DR_Specialization);
        DR_Experience=findViewById(R.id.DR_Experience);
        DR_Address=findViewById(R.id.DR_Address);
        DR_City=findViewById(R.id.DR_City);
        DR_State=findViewById(R.id.DR_State);
        DR_Password=findViewById(R.id.DR_Password);
        DR_Confirm_Password=findViewById(R.id.DR_Confirm_Password);
        DR_Education=findViewById(R.id.DR_Education);
        DR_MCR_no=findViewById(R.id.DR_MCR_no);
        DR_Clinic_Name=findViewById(R.id.DR_Clinic_Name);
        DR_D_Gender=findViewById(R.id.DR_D_Gender);

        DR_worning=findViewById(R.id.DR_worning);

        DR_DateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar=Calendar.getInstance();
                int day=calendar.get(Calendar.DAY_OF_MONTH);
                int month=calendar.get(Calendar.MONTH);
                int year=calendar.get(Calendar.YEAR);

                picker=new DatePickerDialog(Doctor_Registration.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        DR_DateOfBirth.setText(i2+"-"+(i1+1)+"-"+i);
                    }
                }, year,month,day);
                picker.show();
            }
        });

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        db=FirebaseFirestore.getInstance();

        realtimedata = FirebaseDatabase.getInstance().getReference("AppointmentDoctor");


    }

    public void DR_Register_Doctor(View view) {
        if (TextUtils.isEmpty(DR_FirstName.getText().toString())) {
            DR_FirstName.setError("Required");
            DR_FirstName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(DR_LastName.getText().toString())) {
            DR_LastName.setError("Required");
            DR_LastName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(DR_Email.getText().toString())) {
            DR_Email.setError("Required");
            DR_Email.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(DR_MobileNo.getText().toString())) {
            DR_MobileNo.setError("Required");
            DR_MobileNo.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(DR_DateOfBirth.getText().toString())) {
            DR_DateOfBirth.setError("Required");
            DR_DateOfBirth.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(DR_Specialization.getText().toString())) {
            DR_Specialization.setError("Required");
            DR_Specialization.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(DR_Education.getText().toString())) {
            DR_Education.setError("Required");
            DR_Education.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(DR_MCR_no.getText().toString())) {
            DR_MCR_no.setError("Required");
            DR_MCR_no.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(DR_Clinic_Name.getText().toString())) {
            DR_Clinic_Name.setError("Required");
            DR_Clinic_Name.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(DR_Experience.getText().toString())) {
            DR_Experience.setError("Required");
            DR_Experience.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(DR_Address.getText().toString())) {
            DR_Address.setError("Required");
            DR_Address.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(DR_City.getText().toString())) {
            DR_City.setError("Required");
            DR_City.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(DR_State.getText().toString())) {
            DR_State.setError("Required");
            DR_State.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(DR_Password.getText().toString())) {
            DR_Password.setError("Required");
            DR_Password.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(DR_Confirm_Password.getText().toString())) {
            DR_Confirm_Password.setError("Required");
            DR_Confirm_Password.requestFocus();
            return;
        }

        if (DR_Password.getText().toString().equals(DR_Confirm_Password.getText().toString())){
            password_flag=1;
            checkpass();
        }else {
            DR_Confirm_Password.setError("Not Equal");
        }

        if(password_flag==1 && is8L==1 && isnum==1 && isupper==1 && isspecial==1){
            // Create User
            createUserDoctor();
        }else {
            DR_Password.setError(dp_msg);
            DR_Password.requestFocus();
            //Toast.makeText(getApplicationContext(),password_flag+is8L+isnum+isupper+isspecial,Toast.LENGTH_SHORT).show();

        }


    }

    private void checkpass() {
        String password=DR_Password.getText().toString();
        dp_msg="";
        password_flag=0;
        is8L=0;
        isnum=0;
        isupper=0;
        isspecial=0;


        if (password.length()>=8){
            is8L=1;
        }

        if (is8L==0){
            // DR_Confirm_Password.setError("Not Equal");
            DR_worning.setTextColor(this.getResources().getColor(R.color.red));
            dp_msg=dp_msg+"Atleat 8 character\n";
            DR_worning.setText(dp_msg);
            DR_Password.setError(dp_msg);
            DR_Password.requestFocus();
        }

        if(password.matches(".*[0-9].*")){
            isnum=1;
        }

        if (isnum==0){
            // DR_Confirm_Password.setError("Not Equal");
            DR_worning.setTextColor(this.getResources().getColor(R.color.red));
            dp_msg=dp_msg+"minimum one Number(0-9)\n";
            DR_worning.setText(dp_msg);
            DR_Password.setError(dp_msg);
            DR_Password.requestFocus();
        }

        if(password.matches(".*[A-Z].*")){
            isupper=1;
        }

        if (isupper==0){
            // DR_Confirm_Password.setError("Not Equal");
            DR_worning.setTextColor(this.getResources().getColor(R.color.red));
            dp_msg=dp_msg+"minimum one Uppercase (A-Z)\n";
            DR_worning.setText(dp_msg);
            DR_Password.setError(dp_msg);
            DR_Password.requestFocus();
        }

        if(password.matches("^(?=.*[_.()$&@#]).*$")){
            isspecial=1;
        }

        if (isspecial==0){
            // DR_Confirm_Password.setError("Not Equal");
            DR_worning.setTextColor(this.getResources().getColor(R.color.red));
            dp_msg=dp_msg+"minimum one Special Symbol ( _ . ( ) $ & @ #)";
            DR_worning.setText(dp_msg);
            DR_Password.setError(dp_msg);
            DR_Password.requestFocus();
        }

        if (DR_Password.getText().toString().equals(DR_Confirm_Password.getText().toString())){
            password_flag=1;
            DR_worning.setText("");
        }else{
            password_flag=0;
        }
        if (DR_Confirm_Password.getText().toString().equals(DR_Password.getText().toString())){
            password_flag=1;
            DR_worning.setText("");
        }else {
            password_flag=0;
        }



    }

    private void createUserDoctor() {
        String email=DR_Email.getText().toString();
        String password=DR_Password.getText().toString();

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"User Created Successfully", Toast.LENGTH_SHORT).show();
                    storeDoctorData();
                    startActivity(new Intent(Doctor_Registration.this,Doctor_Login.class));

                }else{
                    Toast.makeText(getApplicationContext(),"Registration Error "+email+" : "+password, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void storeDoctorData() {



        gender = ((RadioButton)findViewById(DR_D_Gender.getCheckedRadioButtonId())).getText().toString();
        DocumentReference documentReference = db.collection("Doctors").document(DR_Email.getText().toString());
        Map<String,String> items=new HashMap<>();
        items.put("FirstName",DR_FirstName.getText().toString());
        items.put("LastName",DR_LastName.getText().toString());
        items.put("Email",DR_Email.getText().toString());
        items.put("Mobile No",DR_MobileNo.getText().toString());
        items.put("Date Of Birth",DR_DateOfBirth.getText().toString());
        items.put("Specialization",DR_Specialization.getText().toString());
        items.put("Education",DR_Education.getText().toString());
        items.put("MCR No",DR_MCR_no.getText().toString());
        items.put("Clinic Name",DR_Clinic_Name.getText().toString());
        items.put("Experience",DR_Experience.getText().toString());
        items.put("Address",DR_Address.getText().toString());
        items.put("City",DR_City.getText().toString());
        items.put("State",DR_State.getText().toString());
        items.put("Gender",gender);
        items.put("Password",DR_Password.getText().toString());
        items.put("TypeOfUser","Doctor");
        items.put("UserCredential","True");


        //realtime data
        String updated_email=DR_Email.getText().toString();
        updated_email=updated_email.replace('.',',');
        HashMap<String, String> data = new HashMap<>();
        data.put("Email",DR_Email.getText().toString());
        realtimedata.child(updated_email).setValue(data);
        //end
        documentReference.set(items).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                DR_FirstName.setText("");
                DR_LastName.setText("");
                DR_Email.setText("");
                DR_MobileNo.setText("");
                DR_DateOfBirth.setText("");
                DR_Specialization.setText("");
                DR_Education.setText("");
                DR_MCR_no.setText("");
                DR_Clinic_Name.setText("");
                DR_Experience.setText("");
                DR_Address.setText("");
                DR_City.setText("");
                DR_State.setText("");
                DR_Password.setText("");
                DR_Confirm_Password.setText("");
                password_flag=1;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Doctor Data Not Store "+e.toString(),Toast.LENGTH_SHORT).show();
            }
        });



    }





    public void DR_BackToLogin(View view) {
        Intent send = new Intent(Doctor_Registration.this, Doctor_Login.class);
        startActivity(send);
    }
}
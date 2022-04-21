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

public class Patient_Registration extends AppCompatActivity {
    EditText PR_FirstName,PR_LastName,PR_Email,PR_MobileNo,PR_DateOfBirth,PR_Address,PR_City,PR_State,PR_Password,PR_Confirm_Password;
    RadioGroup PR_D_Gender;
    DatePickerDialog picker;
    int password_flag=0;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore db;
    String gender;
    DatabaseReference db1;
    int is8L=0,isnum=0,isupper=0,isspecial=0;
    TextView PR_worning;
    String dp_msg="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_registration);

        PR_FirstName=findViewById(R.id.PR_FirstName);
        PR_LastName=findViewById(R.id.PR_LastName);
        PR_Email=findViewById(R.id.PR_Email);
        PR_MobileNo=findViewById(R.id.PR_MobileNo);
        PR_DateOfBirth=findViewById(R.id.PR_DateOfBirth);
        PR_Address=findViewById(R.id.PR_Address);
        PR_City=findViewById(R.id.PR_City);
        PR_State=findViewById(R.id.PR_State);

        PR_Password=findViewById(R.id.PR_Password);
        PR_Confirm_Password=findViewById(R.id.PR_Confirm_Password);

        PR_worning=findViewById(R.id.PR_worning);

        PR_D_Gender=findViewById(R.id.PR_D_Gender);

        PR_DateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar=Calendar.getInstance();
                int day=calendar.get(Calendar.DAY_OF_MONTH);
                int month=calendar.get(Calendar.MONTH);
                int year=calendar.get(Calendar.YEAR);

                picker=new DatePickerDialog(Patient_Registration.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        PR_DateOfBirth.setText(i2+"-"+(i1+1)+"-"+i);
                    }
                }, year,month,day);
                picker.show();
            }
        });

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        db=FirebaseFirestore.getInstance();

        db1= FirebaseDatabase.getInstance().getReference("AppointmentPatient");
    }

    public void PR_Register_Patient(View view) {
        if (TextUtils.isEmpty(PR_FirstName.getText().toString())) {
            PR_FirstName.setError("Required");
            PR_FirstName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(PR_LastName.getText().toString())) {
            PR_LastName.setError("Required");
            PR_LastName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(PR_Email.getText().toString())) {
            PR_Email.setError("Required");
            PR_Email.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(PR_MobileNo.getText().toString())) {
            PR_MobileNo.setError("Required");
            PR_MobileNo.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(PR_DateOfBirth.getText().toString())) {
            PR_DateOfBirth.setError("Required");
            PR_DateOfBirth.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(PR_Address.getText().toString())) {
            PR_Address.setError("Required");
            PR_Address.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(PR_City.getText().toString())) {
            PR_City.setError("Required");
            PR_City.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(PR_State.getText().toString())) {
            PR_State.setError("Required");
            PR_State.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(PR_Password.getText().toString())) {
            PR_Password.setError("Required");
            PR_Password.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(PR_Confirm_Password.getText().toString())) {
            PR_Confirm_Password.setError("Required");
            PR_Confirm_Password.requestFocus();
            return;
        }

        if (PR_Password.getText().toString().equals(PR_Confirm_Password.getText().toString())){
            password_flag=1;
            checkpass();
        }
        if (password_flag==0){
            PR_Confirm_Password.setError("Not Equal");
        }


        if(password_flag==1 && is8L==1 && isnum==1 && isupper==1 && isspecial==1){
            // Create User
            createUserPatient();

        }else {
            PR_Password.setError(dp_msg);
            PR_Password.requestFocus();
            //Toast.makeText(getApplicationContext(),password_flag+is8L+isnum+isupper+isspecial,Toast.LENGTH_SHORT).show();

        }


    }

    private void checkpass() {

        String password=PR_Password.getText().toString();
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
            PR_worning.setTextColor(this.getResources().getColor(R.color.red));
            dp_msg=dp_msg+"Atleat 8 character\n";
            PR_worning.setText(dp_msg);
            PR_Password.setError(dp_msg);
            PR_Password.requestFocus();
        }

        if(password.matches(".*[0-9].*")){
            isnum=1;
        }

        if (isnum==0){
            // DR_Confirm_Password.setError("Not Equal");
            PR_worning.setTextColor(this.getResources().getColor(R.color.red));
            dp_msg=dp_msg+"minimum one Number(0-9)\n";
            PR_worning.setText(dp_msg);
            PR_Password.setError(dp_msg);
            PR_Password.requestFocus();
        }

        if(password.matches(".*[A-Z].*")){
            isupper=1;
        }

        if (isupper==0){
            // DR_Confirm_Password.setError("Not Equal");
            PR_worning.setTextColor(this.getResources().getColor(R.color.red));
            dp_msg=dp_msg+"minimum one Uppercase (A-Z)\n";
            PR_worning.setText(dp_msg);
            PR_Password.setError(dp_msg);
            PR_Password.requestFocus();
        }

        if(password.matches("^(?=.*[_.()$&@#]).*$")){
            isspecial=1;
        }

        if (isspecial==0){
            // DR_Confirm_Password.setError("Not Equal");
            PR_worning.setTextColor(this.getResources().getColor(R.color.red));
            dp_msg=dp_msg+"minimum one Special Symbol ( _ . ( ) $ & @ #)";
            PR_worning.setText(dp_msg);
            PR_Password.setError(dp_msg);
            PR_Password.requestFocus();
        }

        if (PR_Password.getText().toString().equals(PR_Confirm_Password.getText().toString())){
            password_flag=1;
            PR_worning.setText("");
        }else{
            password_flag=0;
        }
        if (PR_Confirm_Password.getText().toString().equals(PR_Password.getText().toString())){
            password_flag=1;
            PR_worning.setText("");
        }else {
            password_flag=0;
        }


    }

    private void createUserPatient() {
        String email=PR_Email.getText().toString();
        String password=PR_Password.getText().toString();

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"User Created Successfully", Toast.LENGTH_SHORT).show();
                    storePatientData();

                    startActivity(new Intent(Patient_Registration.this,Patient_Login.class));
                }else{
                    Toast.makeText(getApplicationContext(),"Registration Error "+email+" : "+password, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void storePatientData() {

        gender = ((RadioButton)findViewById(PR_D_Gender.getCheckedRadioButtonId())).getText().toString();

        DocumentReference documentReference = db.collection("Patient").document(PR_Email.getText().toString());

        Map<String,String> items=new HashMap<>();
        items.put("FirstName",PR_FirstName.getText().toString());
        items.put("LastName",PR_LastName.getText().toString());
        items.put("Email",PR_Email.getText().toString());
        items.put("Mobile No",PR_MobileNo.getText().toString());
        items.put("Date Of Birth",PR_DateOfBirth.getText().toString());
        items.put("Address",PR_Address.getText().toString());
        items.put("City",PR_City.getText().toString());
        items.put("State",PR_State.getText().toString());
        items.put("Gender",gender);
        items.put("Password",PR_Password.getText().toString());
        items.put("TypeOfUser","Patient");
        items.put("UserCredential","True");

        //realtime data
        String T_Email=PR_Email.getText().toString();
        T_Email=T_Email.replace('.',',');

        HashMap<String, String> data = new HashMap<>();
        data.put("Email",PR_Email.getText().toString());
        db1.child(T_Email).setValue(data);
        //end

        documentReference.set(items).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                PR_FirstName.setText("");
                PR_LastName.setText("");
                PR_Email.setText("");
                PR_MobileNo.setText("");
                PR_Address.setText("");
                PR_City.setText("");
                PR_State.setText("");
                PR_Password.setText("");
                PR_Confirm_Password.setText("");
                password_flag=1;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Patient Data Not Store "+e.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void PR_BackToLogin(View view) {
        startActivity(new Intent(Patient_Registration.this,Patient_Login.class));
    }
}
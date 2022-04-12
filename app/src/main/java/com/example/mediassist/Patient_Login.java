package com.example.mediassist;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Patient_Login extends AppCompatActivity {
    EditText PL_Email,PL_Password;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    String Patient_mail_detail="";
    String email,password;
    SharedPreferences sp;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_login);


        PL_Email=findViewById(R.id.PL_Email);
        PL_Password=findViewById(R.id.PL_Password);

        firebaseAuth=FirebaseAuth.getInstance();
    }

    public void PL_Login(View view) {
        if (TextUtils.isEmpty(PL_Email.getText().toString())) {
            PL_Email.setError("Required");
            PL_Email.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(PL_Password.getText().toString())) {
            PL_Password.setError("Required");
            PL_Password.requestFocus();
            return;
        }
        patientLogin();
    }

    private void patientLogin() {
        progressDialog =new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Login...");
        progressDialog.show();

        email=PL_Email.getText().toString();
        password=PL_Password.getText().toString();

        DocumentReference documentReference = db.collection("Patient").document(email);

        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    Patient_mail_detail=documentSnapshot.getString("Email");
                   // Toast.makeText(getApplicationContext(),Patient_mail_detail, Toast.LENGTH_SHORT).show();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Data Not Found", Toast.LENGTH_SHORT).show();
            }
        });

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    //check doctor details in firebase firestore
                    if (Patient_mail_detail.equals(email)){
                    //    Toast.makeText(getApplicationContext(),Patient_mail_detail+"data from firestore", Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(),"Patient Login Successfully", Toast.LENGTH_SHORT).show();
                        PL_Email.setText("");
                        PL_Password.setText("");


                        sp = getSharedPreferences("patientData", Context.MODE_PRIVATE);
                        //startActivity(intent);
                        //Toast.makeText(Login.this, "email sent is: " + email, Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("patient_email", email);
                        editor.commit();

                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                        Intent send = new Intent(Patient_Login.this, Patient_Login_to_HomePage.class);
                        startActivity(send);
                        finish();
                    }else {
                        PL_Email.setError("Invalid Detail");
                        PL_Password.requestFocus();
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                    }

                }else{
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Login Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void PR_CreateAccoount(View view) {
        startActivity(new Intent(Patient_Login.this,Patient_Registration.class));
    }

    public void gotoDoctor_Login(View view) {
        startActivity(new Intent(Patient_Login.this,Doctor_Login.class));
    }


    public void forgot_password_oncliclk(View view) {
        Intent send = new Intent(Patient_Login.this, Patient_ForgotPassword.class);
        startActivity(send);
        finish();
    }
}
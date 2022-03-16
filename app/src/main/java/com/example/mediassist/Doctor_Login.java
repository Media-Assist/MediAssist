package com.example.mediassist;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class Doctor_Login extends AppCompatActivity {
    EditText DL_Email,DL_Password;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    String Doctor_mail_detail="";
    String email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_login);

        DL_Email=findViewById(R.id.DL_Email);
        DL_Password=findViewById(R.id.DL_Password);

        firebaseAuth=FirebaseAuth.getInstance();


    }
    public void DL_Login(View view) {

        if (TextUtils.isEmpty(DL_Email.getText().toString())) {
            DL_Email.setError("Required");
            return;
        }
        if (TextUtils.isEmpty(DL_Password.getText().toString())) {
            DL_Password.setError("Required");
            return;
        }
        doctorLogin();

    }

    private void doctorLogin() {
        email=DL_Email.getText().toString();
        password=DL_Password.getText().toString();


        DocumentReference documentReference = db.collection("Doctors").document(email);

        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    Doctor_mail_detail=documentSnapshot.getString("Email");
                    Toast.makeText(getApplicationContext(),Doctor_mail_detail, Toast.LENGTH_SHORT).show();
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
                    if (Doctor_mail_detail.equals(email)){
                       // Toast.makeText(getApplicationContext(),Doctor_mail_detail+"data from firestore", Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(),"Doctor Login Successfully", Toast.LENGTH_SHORT).show();
                        DL_Email.setText("");
                        DL_Password.setText("");

                        Intent send = new Intent(Doctor_Login.this, Doctor_Login_to_HomePage.class);
                        send.putExtra("DoctorEmail",email);
                        startActivity(send);
                        finish();




                    }else {
                        DL_Email.setError("Invalid Detail");
                        DL_Email.requestFocus();
                    }


                }else{
                    Toast.makeText(getApplicationContext(),"Login Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void DR_CreateAccoount(View view) {
        Intent send = new Intent(Doctor_Login.this, Doctor_Registration.class);
        startActivity(send);
    }

    public void gotoPatient_Login(View view) {

    }


}
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

public class Pharmacy_Login extends AppCompatActivity {
EditText Pharma_L_Email,Pharma_L_Password;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    String Doctor_mail_detail="";
    String email,password;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_login);

        Pharma_L_Email=findViewById(R.id.Pharma_L_Email);
        Pharma_L_Password=findViewById(R.id.Pharma_L_Password);

        firebaseAuth=FirebaseAuth.getInstance();

    }

    public void PH_Login(View view) {

        if (TextUtils.isEmpty(Pharma_L_Email.getText().toString())) {
            Pharma_L_Email.setError("Required");
            return;
        }
        if (TextUtils.isEmpty(Pharma_L_Password.getText().toString())) {
            Pharma_L_Password.setError("Required");
            return;
        }
        PharmacyLogin();
    }

    private void PharmacyLogin() {

        email=Pharma_L_Email.getText().toString();
        password=Pharma_L_Password.getText().toString();


        DocumentReference documentReference = db.collection("Pharmacys").document(email);

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
                        Toast.makeText(getApplicationContext(),"Pharma Login Successfully", Toast.LENGTH_SHORT).show();
                        Pharma_L_Email.setText("");
                        Pharma_L_Password.setText("");

                        Intent send = new Intent(Pharmacy_Login.this, Pharmacy_Login_to_HomePage.class);
                        send.putExtra("PharmaEmail_LP",email);
                        startActivity(send);
                        finish();




                    }else {
                        Pharma_L_Email.setError("Invalid Detail");
                        Pharma_L_Email.requestFocus();
                    }


                }else{
                    Toast.makeText(getApplicationContext(),"Login Error", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void PH_CreateAccoount(View view) {
        Intent send = new Intent(Pharmacy_Login.this, Pharmacy_Registration.class);
        startActivity(send);
    }



}
package com.example.mediassist;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Pharma_UserProfile extends AppCompatActivity {
TextView Pharma_ViewProfile_ShopName,Pharma_ViewProfile_OwnName,Pharma_ViewProfile_MobileNo,Pharma_ViewProfile_Email,Pharma_ViewProfile_Address;
String Pharma_ProfileEmail;
String ShopName,OwnName,MobileNo,UserEmail,UserLandmark,UserAddress,UserCity,UserState;
FirebaseFirestore db=FirebaseFirestore.getInstance();
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharma_user_profile);

        progressDialog =new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        Intent intent = getIntent();
        Pharma_ProfileEmail = intent.getStringExtra("Pharma_Profile_Email");

        Pharma_ViewProfile_ShopName=findViewById(R.id.Pharma_ViewProfile_ShopName);
        Pharma_ViewProfile_OwnName=findViewById(R.id.Pharma_ViewProfile_OwnName);
        Pharma_ViewProfile_MobileNo=findViewById(R.id.Pharma_ViewProfile_MobileNo);
        Pharma_ViewProfile_Email=findViewById(R.id.Pharma_ViewProfile_Email);
        Pharma_ViewProfile_Address=findViewById(R.id.Pharma_ViewProfile_Address);

        GetPharmaData();
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    private void GetPharmaData() {

        DocumentReference documentReference_PI = db.collection("Pharmacys").document(Pharma_ProfileEmail);

        documentReference_PI.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){

                    ShopName=documentSnapshot.getString("Shop Name");
                    OwnName=documentSnapshot.getString("Own Name");
                    MobileNo=documentSnapshot.getString("Mobile No");
                    UserEmail=documentSnapshot.getString("Email");
                    UserLandmark=documentSnapshot.getString("Landmark");
                    UserAddress=documentSnapshot.getString("Address");
                    UserCity=documentSnapshot.getString("City");
                    UserState=documentSnapshot.getString("State");

                    Pharma_ViewProfile_ShopName.setText(ShopName);
                    Pharma_ViewProfile_OwnName.setText(OwnName);
                    Pharma_ViewProfile_MobileNo.setText("No. "+MobileNo);
                    Pharma_ViewProfile_Email.setText("Email : "+UserEmail);
                    Pharma_ViewProfile_Address.setText("Address : "+UserLandmark+","+UserAddress+","+UserCity+","+UserState);
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                }else {
                    Toast.makeText(getApplicationContext(),"Not Found", Toast.LENGTH_SHORT).show();
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Data Not Found", Toast.LENGTH_SHORT).show();
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        });
    }
}
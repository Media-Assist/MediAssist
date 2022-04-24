package com.example.mediassist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Pharmacy_menu extends AppCompatActivity {
    TextView pharma_name_mn;
    String PharmaEmail;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    String D9;
       @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_menu);

        pharma_name_mn=findViewById(R.id.pharma_name_mn);

        Intent intent = getIntent();
        PharmaEmail = intent.getStringExtra("PharmaEmail");

        showUsedata();



        // Showdata();
    }

    private void showUsedata() {

        DocumentReference documentReference_PI = db.collection("Pharmacys").document(PharmaEmail);

        documentReference_PI.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){

                    D9=documentSnapshot.getString("Pharmacy List Name");
                    pharma_name_mn.setText(D9);
                }else {
                    Toast.makeText(getApplicationContext(),"Not Found", Toast.LENGTH_SHORT).show();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Data Not Found", Toast.LENGTH_SHORT).show();
            }
        });

    }




    public void Pharma_View_Prescription(View view) {
        Intent intent = getIntent();
        PharmaEmail = intent.getStringExtra("PharmaEmail");
        Intent send = new Intent(Pharmacy_menu.this,Pharmacy_View_Prescription.class);
        send.putExtra("Pharma_name",D9);
        startActivity(send);

    }

    public void Pharma_View_Profile(View view) {
        Intent send = new Intent(Pharmacy_menu.this,Pharma_UserProfile.class);
        send.putExtra("Pharma_Profile_Email",PharmaEmail);
        startActivity(send);
    }

    public void Pharma_Logout(View view) {
        Intent send = new Intent(Pharmacy_menu.this,Pharmacy_Login.class);
        startActivity(send);
        finish();
    }
}
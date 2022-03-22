package com.example.mediassist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Pharmacy_Registration extends AppCompatActivity {
EditText Pharma_R_OwnName,Pharma_R_ShopName,Pharma_R_Email,Pharma_R_MobileNo,Pharma_R_Address,Pharma_R_Landmark,Pharma_R_City,Pharma_R_State,Pharma_R_Password,Pharma_R_Confirm_Password;
    int password_flag=0;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore db;
    DatabaseReference spinnerRef;
    ArrayList<String> spinnerList;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_registration);

        Pharma_R_OwnName=findViewById(R.id.Pharma_R_OwnName);
        Pharma_R_ShopName=findViewById(R.id.Pharma_R_ShopName);
        Pharma_R_Email=findViewById(R.id.Pharma_R_Email);
        Pharma_R_MobileNo=findViewById(R.id.Pharma_R_MobileNo);
        Pharma_R_Address=findViewById(R.id.Pharma_R_Address);
        Pharma_R_Landmark=findViewById(R.id.Pharma_R_Landmark);
        Pharma_R_City=findViewById(R.id.Pharma_R_City);
        Pharma_R_State=findViewById(R.id.Pharma_R_State);
        Pharma_R_Password=findViewById(R.id.Pharma_R_Password);
        Pharma_R_Confirm_Password=findViewById(R.id.Pharma_R_Confirm_Password);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        db=FirebaseFirestore.getInstance();

        spinnerRef= FirebaseDatabase.getInstance().getReference("Pharmarcy_List");

        spinnerList=new ArrayList<>();
        adapter=new ArrayAdapter<String>(Pharmacy_Registration.this, android.R.layout.simple_spinner_dropdown_item,spinnerList);


    }

    public void Pharma_Register_Pharmarcy(View view) {
        if (TextUtils.isEmpty(Pharma_R_OwnName.getText().toString())) {
            Pharma_R_OwnName.setError("Required");
            Pharma_R_OwnName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(Pharma_R_ShopName.getText().toString())) {
            Pharma_R_ShopName.setError("Required");
            Pharma_R_ShopName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(Pharma_R_Email.getText().toString())) {
            Pharma_R_Email.setError("Required");
            Pharma_R_Email.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(Pharma_R_MobileNo.getText().toString())) {
            Pharma_R_MobileNo.setError("Required");
            Pharma_R_MobileNo.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(Pharma_R_Address.getText().toString())) {
            Pharma_R_Address.setError("Required");
            Pharma_R_Address.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(Pharma_R_Landmark.getText().toString())) {
            Pharma_R_Landmark.setError("Required");
            Pharma_R_Landmark.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(Pharma_R_City.getText().toString())) {
            Pharma_R_City.setError("Required");
            Pharma_R_City.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(Pharma_R_State.getText().toString())) {
            Pharma_R_State.setError("Required");
            Pharma_R_State.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(Pharma_R_Password.getText().toString())) {
            Pharma_R_Password.setError("Required");
            Pharma_R_Password.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(Pharma_R_Confirm_Password.getText().toString())) {
            Pharma_R_Confirm_Password.setError("Required");
            Pharma_R_Confirm_Password.requestFocus();
            return;
        }
        Pharmacy_spinner_list();
        // Create User
        createUserPharmacy();
        Toast.makeText(getApplicationContext(),"Done", Toast.LENGTH_SHORT).show();

    }

    private void Pharmacy_spinner_list() {
        String value=Pharma_R_ShopName.getText().toString()+"("+Pharma_R_Landmark.getText().toString()+","+Pharma_R_City.getText().toString()+")";
        String key=spinnerRef.push().getKey();

        spinnerRef.child(key).setValue(value);
        spinnerList.clear();
        adapter.notifyDataSetChanged();

        Toast.makeText(getApplicationContext(),"saved",Toast.LENGTH_SHORT).show();

    }

    private void createUserPharmacy() {
        String email=Pharma_R_Email.getText().toString();
        String password=Pharma_R_Password.getText().toString();

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"User Created Successfully", Toast.LENGTH_SHORT).show();
                    storePharmacyData();
                    startActivity(new Intent(Pharmacy_Registration.this,Pharmacy_Login.class));
                }else{
                    Toast.makeText(getApplicationContext(),"Registration Error "+email+" : "+password, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void storePharmacyData() {
        String value1=Pharma_R_ShopName.getText().toString()+"("+Pharma_R_Landmark.getText().toString()+","+Pharma_R_City.getText().toString()+")";
        DocumentReference documentReference = db.collection("Pharmacys").document(Pharma_R_Email.getText().toString());
        Map<String,String> items=new HashMap<>();
        items.put("Own Name",Pharma_R_OwnName.getText().toString());
        items.put("Shop Name",Pharma_R_ShopName.getText().toString());
        items.put("Email",Pharma_R_Email.getText().toString());
        items.put("Mobile No",Pharma_R_MobileNo.getText().toString());
        items.put("Address",Pharma_R_Address.getText().toString());
        items.put("Landmark",Pharma_R_Landmark.getText().toString());
        items.put("City",Pharma_R_City.getText().toString());
        items.put("State",Pharma_R_State.getText().toString());
        items.put("Password",Pharma_R_Password.getText().toString());
        items.put("Pharmacy List Name",value1);
        items.put("TypeOfUser","Pharmacy");
        items.put("UserCredential","True");

        documentReference.set(items).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Pharma_R_OwnName.setText("");
                Pharma_R_ShopName.setText("");
                Pharma_R_Email.setText("");
                Pharma_R_MobileNo.setText("");
                Pharma_R_Address.setText("");
                Pharma_R_Landmark.setText("");
                Pharma_R_City.setText("");
                Pharma_R_State.setText("");
                Pharma_R_Password.setText("");
                Pharma_R_Confirm_Password.setText("");
                password_flag=1;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Pharmacy Data Not Store "+e.toString(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void Pharma_BackToLogin(View view) {
        Intent send = new Intent(Pharmacy_Registration.this, Pharmacy_Login.class);
        startActivity(send);
    }
}
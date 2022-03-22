package com.example.mediassist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Pharmacy_View_Prescription extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<MyUser_PH> myUser_phArrayList;
    MyAdapter_PH myAdapter_ph;
    FirebaseFirestore db;
    String Pharama_Name;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_view_prescription);

        db=FirebaseFirestore.getInstance();
        Intent intent = getIntent();
        Pharama_Name = intent.getStringExtra("Pharma_name");


        progressDialog =new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));






        myUser_phArrayList=new ArrayList<MyUser_PH>();
        myAdapter_ph=new MyAdapter_PH(Pharmacy_View_Prescription.this,myUser_phArrayList);

        recyclerView.setAdapter(myAdapter_ph);

        Pharama_EventChangeListener();
    }



    private void Pharama_EventChangeListener() {
    db.collection(Pharama_Name).orderBy("Date", Query.Direction.ASCENDING)
            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (error != null){
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                        Log.e("Firebase error",error.getMessage());
                        return;
                    }
                    for (DocumentChange dc : value.getDocumentChanges()){
                        if (dc.getType() ==DocumentChange.Type.ADDED){

                            myUser_phArrayList.add(dc.getDocument().toObject(MyUser_PH.class));

                        }

                        myAdapter_ph.notifyDataSetChanged();
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                    }

                }
            });
    }
}
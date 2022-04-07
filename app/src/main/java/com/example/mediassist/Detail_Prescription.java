package com.example.mediassist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Detail_Prescription extends AppCompatActivity {
    TextView DP_Date_txt,DP_Patient_Name_txt,DP_Patient_Age_txt,DP_Diagnosis_txt,DP_Symtoms_txt,DP_MedicinceList_txt,DP_DoctorNote_txt;
    TextView DP_D_Clinic,DP_D_data1,DP_D_data2,DP_D_data3,DP_D_data4;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    String VP_All_P_Details,VP_Date,VP_DoctorID,VP_DoctorName,VP_PatientId,VP_PatientName,VP_PatientAge,VP_Diagnosis,VP_Symtoms,VP_MedicinceList,VP_DoctorNote;
    String VP_Username,VP_PatientMail;
    Bitmap btm,btm1;
    String FBFS_FirstName,FBFS_LastName,FBFS_Mobile_No,FBFS_Specialization,FBFS_Education,FBFS_MCR_No,FBFS_Clinic_Name,FBFS_Address,FBFS_City,FBFS_State;
    String time,time1;
    Spinner DP_Spinner;
    DatabaseReference spinnerRef;
    ArrayList<String> spinnerList;
    ArrayAdapter<String> adapter;
    String data1,data2,data3,data4;
    String PI_Contact,PI_Adress,PI_City,PI_State;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_prescription);


        time = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());

        btm= BitmapFactory.decodeResource(getResources(), R.drawable.redcross3);
        btm1= Bitmap.createScaledBitmap(btm,100,100,false );

        DP_Date_txt=findViewById(R.id.DP_Date_txt);

        DP_Patient_Name_txt=findViewById(R.id.DP_Patient_Name_txt);
        DP_Patient_Age_txt=findViewById(R.id.DP_Patient_Age_txt);
        DP_Diagnosis_txt=findViewById(R.id.DP_Diagnosis_txt);
        DP_Symtoms_txt=findViewById(R.id.DP_Symtoms_txt);
        DP_MedicinceList_txt=findViewById(R.id.DP_MedicinceList_txt);
        DP_DoctorNote_txt=findViewById(R.id.DP_DoctorNote_txt);

        DP_D_Clinic=findViewById(R.id.DP_D_Clinic);
        DP_D_data1=findViewById(R.id.DP_D_data1);
        DP_D_data2=findViewById(R.id.DP_D_data2);
        DP_D_data3=findViewById(R.id.DP_D_data3);
        DP_D_data4=findViewById(R.id.DP_D_data4);

        DP_Spinner=findViewById(R.id.DP_Spinner);

        Intent intent = getIntent();
        VP_PatientMail= intent.getStringExtra("PatientMail");
        VP_Username = intent.getStringExtra("Username");


        getPatientInfo();
        spinnerRef= FirebaseDatabase.getInstance().getReference("Pharmarcy_List");

        spinnerList=new ArrayList<>();
        adapter=new ArrayAdapter<String>(Detail_Prescription.this, android.R.layout.simple_spinner_dropdown_item,spinnerList);
        DP_Spinner.setAdapter(adapter);
        ShowPharmacyList();
        getPatientBasicInfo();
        //Toast.makeText(getApplicationContext(),VP_DoctorID,Toast.LENGTH_SHORT).show();


    }

    private void getPatientBasicInfo() {
        //patient info start here

        DocumentReference documentReference_PI = db.collection("Patient").document(VP_PatientMail);

        documentReference_PI.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){

                    PI_Contact=documentSnapshot.getString("Mobile No");
                    PI_Adress=documentSnapshot.getString("Address");
                    PI_City=documentSnapshot.getString("City");
                    PI_State=documentSnapshot.getString("State");

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
        //end
    }

    private void ShowPharmacyList() {


        spinnerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot item:snapshot.getChildren()){
                    spinnerList.add(item.getValue().toString());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getPatientInfo() {

        //firebase part
        DocumentReference documentReference = db.collection("Prescription").document(VP_Username);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                VP_Date=value.getString("Date");
                VP_DoctorID=value.getString("DoctorID");
                VP_DoctorName=value.getString("DoctorName");
                VP_PatientName=value.getString("PatientName");
                VP_PatientAge=value.getString("PatientAge");
                VP_Diagnosis=value.getString("Diagnosis");
                VP_Symtoms=value.getString("Symtoms");
                VP_MedicinceList=value.getString("MedicinceList");
                VP_DoctorNote=value.getString("DoctorNote");
                VP_PatientId=value.getString("PatientID");
                //doctor info start here

                DocumentReference documentReference_D = db.collection("Doctors").document(VP_DoctorID);

                documentReference_D.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            FBFS_FirstName=documentSnapshot.getString("FirstName");
                            FBFS_LastName=documentSnapshot.getString("LastName");
                            FBFS_Mobile_No=documentSnapshot.getString("Mobile No");
                            FBFS_Specialization=documentSnapshot.getString("Specialization");
                            FBFS_Education=documentSnapshot.getString("Education");
                            FBFS_MCR_No=documentSnapshot.getString("MCR No");
                            FBFS_Clinic_Name=documentSnapshot.getString("Clinic Name");
                            FBFS_Address=documentSnapshot.getString("Address");
                            FBFS_City=documentSnapshot.getString("City");
                            FBFS_State=documentSnapshot.getString("State");


                            DP_D_Clinic.setText(FBFS_Clinic_Name);
                            data1="Dr. "+FBFS_FirstName+" "+FBFS_LastName+", ("+FBFS_Specialization+", "+FBFS_Education+")";
                            DP_D_data1.setText(data1);
                            data2="Contact No. "+FBFS_Mobile_No;
                            DP_D_data2.setText(data2);
                            data3="MCR No : "+FBFS_MCR_No;
                            DP_D_data3.setText(data3);
                            data4="Address : "+FBFS_Address+", City : "+FBFS_City+", "+FBFS_State;
                            DP_D_data4.setText(data4);
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Data Not Found", Toast.LENGTH_SHORT).show();
                    }
                });

                //doctor info end here

                DP_Date_txt.setText(VP_Date);

                DP_Patient_Name_txt.setText(VP_PatientName);
                DP_Patient_Age_txt.setText(VP_PatientAge);
                DP_Diagnosis_txt.setText(VP_Diagnosis);
                DP_Symtoms_txt.setText(VP_Symtoms);
                DP_MedicinceList_txt.setText(VP_MedicinceList);
                DP_DoctorNote_txt.setText(VP_DoctorNote);

                //VP_All_P_Details="Date : "+VP_Date+"\n"+"Doctor ID :"+VP_DoctorID+"\n"+"Doctor Name  :"+VP_DoctorName+"\n"+"Patient Name :"+VP_PatientName+"\n"+"Patient Age :"+VP_PatientAge+"\n"+"Diagnosis :"+VP_Diagnosis+"\n"+"Symtoms :"+VP_Symtoms+"\n"+"MedicinceList :\n"+VP_MedicinceList+"\n"+"DoctorNote :"+VP_DoctorNote;


               // Toast.makeText(getApplicationContext(),VP_All_P_Details,Toast.LENGTH_SHORT).show();

            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void DownloadPrescription(View view) {

        PdfDocument document = new PdfDocument();

        // crate a page description
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(600, 1000, 1).create();

        // start a page
        PdfDocument.Page page = document.startPage(pageInfo);
        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        canvas.drawBitmap(btm1,40,40,paint);
        paint.setTextSize(50.0f);
        canvas.drawText(FBFS_Clinic_Name,220,100,paint);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(10.0f);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(1);
        canvas.drawLine(5, 180, 590, 180, paint);



        canvas.drawLine(5, 10, 590, 10, paint);
        canvas.drawLine(5, 995, 590, 995, paint);

       canvas.drawLine(5, 10, 5, 995, paint);
       canvas.drawLine(590, 10, 590, 995, paint);

     //   canvas.drawLine(10, 180, 580, 180, paint);
     //   canvas.drawLine(10, 180, 10, 230, paint);

      //  canvas.drawLine(10, 230, 580, 230, paint);
      //  canvas.drawLine(580, 180, 580, 230, paint);

        canvas.drawText(data1,220,130,paint);
        paint.setColor(Color.BLACK);

        canvas.drawText(data2,220,145,paint);
        paint.setColor(Color.BLACK);

        canvas.drawText(data3,220,160,paint);
        paint.setColor(Color.BLACK);

        paint.setTextSize(15.0f);
        canvas.drawText("Date :"+VP_Date,480,200,paint);
        paint.setColor(Color.BLACK);

        canvas.drawText("Name : "+VP_PatientName,20,220,paint);
        paint.setColor(Color.BLACK);

        canvas.drawText("Age : "+VP_PatientAge,20,240,paint);
        paint.setColor(Color.BLACK);

        paint.setTextSize(15.0f);
        canvas.drawText("Diagnosis : ",20,280,paint);
        paint.setTextSize(10.0f);
        canvas.drawText(VP_Diagnosis,20,295,paint);
        paint.setColor(Color.BLACK);

        paint.setTextSize(15.0f);
        canvas.drawText("Symtoms : ",20,335,paint);
        paint.setTextSize(10.0f);
        canvas.drawText(VP_Symtoms,20,350,paint);
        paint.setColor(Color.BLACK);

        paint.setTextSize(15.0f);
        canvas.drawText("MedicinceList : ",20,390,paint);
        paint.setTextSize(10.0f);
        canvas.drawText(VP_MedicinceList,20,405,paint);
        paint.setColor(Color.BLACK);

        paint.setTextSize(15.0f);
        canvas.drawText("Doctor Note : ",20,445,paint);
        paint.setTextSize(10.0f);
        canvas.drawText(VP_DoctorNote,20,460,paint);
        paint.setColor(Color.BLACK);


        canvas.drawText("Authentic By : ",480,930,paint);
        paint.setColor(Color.BLACK);
        canvas.drawText("Admin ",495,945,paint);

        paint.setColor(Color.BLACK);
        canvas.drawLine(5, 960, 590, 960, paint);

        paint.setTextSize(15.0f);
        canvas.drawText(data4,30,985,paint);






        //canvas.drawt
        // finish the page
        document.finishPage(page);

        // write the document content
        String directory_path = Environment.getExternalStorageDirectory().getPath() + "/Medi Assistant/";
        File file = new File(directory_path);
        if (!file.exists()) {
            file.mkdirs();
        }
        String targetPdf = directory_path+"Prescription_"+VP_Date+"_"+VP_PatientName+"_"+"T:"+"_"+time+".pdf";
        File filePath = new File(targetPdf);

        try {
            document.writeTo(new FileOutputStream(filePath));
            Toast.makeText(Detail_Prescription.this, "PDF Download In MediAssist Folder", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Log.e("main", "error "+e.toString());
            Toast.makeText(Detail_Prescription.this, "Something wrong: " + e.toString(),  Toast.LENGTH_LONG).show();
        }

        // close the document
        document.close();

    }

    public void ShareWithPharmacy(View view) {
        String value1;
        value1=DP_Spinner.getSelectedItem().toString();
        time1 = new SimpleDateFormat("dd-M-yyyy hh:mm:ss").format(Calendar.getInstance().getTime());
        DocumentReference documentReference1 = db.collection(value1).document(VP_PatientId+VP_Date);

        Map<String,String> items=new HashMap<>();
        items.put("Date",VP_Date);
        items.put("Doctor_Name",VP_DoctorName);
        items.put("Patient_Id",VP_PatientId);
        items.put("Patient_No",PI_Contact);
        items.put("Patient_Name",VP_PatientName);
        items.put("List_Of_Medicinces",VP_MedicinceList);
        items.put("Address",PI_Adress);
        items.put("City",PI_City);
        items.put("State",PI_State);
        items.put("Shate_Date_Time",time1);



        documentReference1.set(items).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(),"Shared With Pharmacy",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Pharmacy Data Not Store "+e.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Detail_Prescription extends AppCompatActivity {
    TextView DP_Date_txt,DP_Patient_Name_txt,DP_Patient_Age_txt,DP_Diagnosis_txt,DP_Symtoms_txt,DP_MedicinceList_txt,DP_DoctorNote_txt;
    TextView DP_D_Clinic,DP_D_data1,DP_D_data2,DP_D_data3,DP_D_data4;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    String VP_All_P_Details,VP_Date,VP_DoctorID,VP_DoctorName,VP_PatientName,VP_PatientAge,VP_Diagnosis,VP_Symtoms,VP_MedicinceList,VP_DoctorNote;
    String VP_Username;
    Bitmap btm,btm1;
    String FBFS_FirstName,FBFS_LastName,FBFS_Mobile_No,FBFS_Specialization,FBFS_Education,FBFS_MCR_No,FBFS_Clinic_Name,FBFS_Address,FBFS_City,FBFS_State;
    String time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_prescription);


        time = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());

        btm= BitmapFactory.decodeResource(getResources(),R.drawable.prescription);
        btm1= Bitmap.createScaledBitmap(btm,50,50,false );

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

        Intent intent = getIntent();
        VP_Username = intent.getStringExtra("Username");


        getPatientInfo();


        Toast.makeText(getApplicationContext(),VP_DoctorID,Toast.LENGTH_SHORT).show();


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
                            String data1="Dr. "+FBFS_FirstName+" "+FBFS_LastName+", ("+FBFS_Specialization+", "+FBFS_Education+")";
                            DP_D_data1.setText(data1);
                            String data2="Contact No "+FBFS_Mobile_No;
                            DP_D_data2.setText(data2);
                            String data3="MCR No : "+FBFS_MCR_No;
                            DP_D_data3.setText(data3);
                            String data4="Address : "+FBFS_Address+", City : "+FBFS_City+", "+FBFS_State;
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
        canvas.drawBitmap(btm1,300,5,paint);
        paint.setTextSize(20.0f);
        canvas.drawText(FBFS_Clinic_Name,260,73,paint);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(10.0f);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(2);
        canvas.drawLine(0, 90, 985, 90, paint);

        canvas.drawText("Date : ",10,120,paint);
        paint.setColor(Color.BLACK);
        canvas.drawText(VP_Date, 80, 120, paint);

        canvas.drawText("Doctor ID : ",10,135,paint);
        paint.setColor(Color.BLACK);
        canvas.drawText(VP_DoctorID, 80, 135, paint);

        canvas.drawText("Doctor Name : ",10,150,paint);
        paint.setColor(Color.BLACK);
        canvas.drawText(VP_DoctorName, 80, 150, paint);

        canvas.drawText("Patient Name : ",10,165,paint);
        paint.setColor(Color.BLACK);
        canvas.drawText(VP_PatientName, 80, 165, paint);

        canvas.drawText("Patient Age : ",10,180,paint);
        paint.setColor(Color.BLACK);
        canvas.drawText(VP_PatientAge, 80, 180, paint);

        canvas.drawText("Diagnosis : ",10,195,paint);
        paint.setColor(Color.BLACK);
        canvas.drawText(VP_Diagnosis, 80, 195, paint);

        canvas.drawText("Symtoms : ",10,210,paint);
        paint.setColor(Color.BLACK);
        canvas.drawText(VP_Symtoms, 80, 210, paint);

        canvas.drawText("DoctorNote : ",10,225,paint);
        paint.setColor(Color.BLACK);
        canvas.drawText(VP_DoctorNote,10,240,paint);

        canvas.drawText("Medicince List : ",10,270,paint);
        paint.setColor(Color.BLACK);
        canvas.drawText(VP_MedicinceList,10,285,paint);

        canvas.drawText("Authentic By : ",500,930,paint);
        paint.setColor(Color.BLACK);
        canvas.drawText("Admin ",515,945,paint);

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
}
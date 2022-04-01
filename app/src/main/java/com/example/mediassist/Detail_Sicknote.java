package com.example.mediassist;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class Detail_Sicknote extends AppCompatActivity {
    TextView DSI_D_Clinic,DSI_D_data1,DSI_D_data2,DSI_D_data3,DSI_Date_txt,DSI_Patient_Name_txt,DSI_Patient_ID_txt,DSI_TypeOfSickNote_txt,DSI_SickNote_txt,DSI_D_data4;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    String DINV_All_sick_Details,DSN_Date,DSN_HospitalName,DSN_DoctorID,DSN_DoctorName,DSN_DoctorNo,DSN_PatientID,DSN_PatientName,DSN_SickOfType,DSN_SickNote;
    Bitmap btm,btm1;
    String time;
    String PatientMail_VSI,Username_VSI;
    String FBFS_FirstName,FBFS_LastName,FBFS_Mobile_No,FBFS_Specialization,FBFS_Education,FBFS_MCR_No,FBFS_Clinic_Name,FBFS_Address,FBFS_City,FBFS_State;
    String data1,data2,data3,data4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_sicknote);

        time = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());

        btm= BitmapFactory.decodeResource(getResources(), R.drawable.redcross3);
        btm1= Bitmap.createScaledBitmap(btm,100,100,false );

        DSI_D_Clinic=findViewById(R.id.DSI_D_Clinic);
        DSI_D_data1=findViewById(R.id.DSI_D_data1);
        DSI_D_data2=findViewById(R.id.DSI_D_data2);
        DSI_D_data3=findViewById(R.id.DSI_D_data3);
        DSI_Date_txt=findViewById(R.id.DSI_Date_txt);
        DSI_Patient_Name_txt=findViewById(R.id.DSI_Patient_Name_txt);
        DSI_Patient_ID_txt=findViewById(R.id.DSI_Patient_ID_txt);
        DSI_TypeOfSickNote_txt=findViewById(R.id.DSI_TypeOfSickNote_txt);
        DSI_SickNote_txt=findViewById(R.id.DSI_SickNote_txt);
        DSI_D_data4=findViewById(R.id.DSI_D_data4);

        Intent intent = getIntent();
        PatientMail_VSI= intent.getStringExtra("PatientMail_VSI");
        Username_VSI = intent.getStringExtra("Username_VSI");
        getPatientInfo();



    }

    private void getPatientInfo() {
        DocumentReference documentReference = db.collection("Sicknote").document(Username_VSI);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                DSN_Date=value.getString("Date");
                DSN_HospitalName=value.getString("HospitalName");
                DSN_DoctorID=value.getString("DoctorID");
                DSN_DoctorName=value.getString("Doctor Name");
                DSN_DoctorNo=value.getString("Doctor No");
                DSN_PatientID=value.getString("PatientID");
                DSN_PatientName=value.getString("PatientName");
                DSN_SickOfType=value.getString("SickOfType");
                DSN_SickNote=value.getString("SickNote");


                DSI_Date_txt.setText(DSN_Date);
                DSI_D_Clinic.setText(DSN_HospitalName);
                DSI_Patient_Name_txt.setText(DSN_PatientName);
                DSI_Patient_ID_txt.setText(DSN_PatientID);
                DSI_TypeOfSickNote_txt.setText(DSN_SickOfType);
                DSI_SickNote_txt.setText(DSN_SickNote);




                //doctor info start here

                DocumentReference documentReference_D = db.collection("Doctors").document(DSN_DoctorID);

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


                            DSI_D_Clinic.setText(FBFS_Clinic_Name);
                            data1="Dr. "+FBFS_FirstName+" "+FBFS_LastName+", ("+FBFS_Specialization+", "+FBFS_Education+")";
                            DSI_D_data1.setText(data1);
                            data2="Contact No. "+FBFS_Mobile_No;
                            DSI_D_data2.setText(data2);
                            data3="MCR No : "+FBFS_MCR_No;
                            DSI_D_data3.setText(data3);
                            data4="Address : "+FBFS_Address+", City : "+FBFS_City+", "+FBFS_State;
                            DSI_D_data4.setText(data4);



                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Data Not Found", Toast.LENGTH_SHORT).show();
                    }
                });

                //doctor info end here





            }
        });

    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void DownloadSicknote(View view) {


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

        canvas.drawLine(5, 10, 590, 10, paint);
        canvas.drawLine(5, 995, 590, 995, paint);

        canvas.drawLine(5, 10, 5, 995, paint);
        canvas.drawLine(590, 10, 590, 995, paint);

        canvas.drawLine(10, 180, 580, 180, paint);
        canvas.drawLine(10, 180, 10, 230, paint);

        canvas.drawLine(10, 230, 580, 230, paint);
        canvas.drawLine(580, 180, 580, 230, paint);

        canvas.drawText(data1,220,130,paint);
        paint.setColor(Color.BLACK);

        canvas.drawText(data2,220,145,paint);
        paint.setColor(Color.BLACK);

        canvas.drawText(data3,220,160,paint);
        paint.setColor(Color.BLACK);

        paint.setTextSize(15.0f);
        canvas.drawText("Date :"+DSN_Date,460,200,paint);
        paint.setColor(Color.BLACK);

        canvas.drawText("Name : "+DSN_PatientName,20,200,paint);
        paint.setColor(Color.BLACK);

        canvas.drawText("Patient ID : "+DSN_PatientID,20,220,paint);
        paint.setColor(Color.BLACK);



        canvas.drawLine(10, 260, 580, 260, paint);
        canvas.drawLine(10, 290, 580, 290, paint);
        canvas.drawLine(10, 260, 10, 970, paint);
        canvas.drawLine(580, 260, 580, 970, paint);
        canvas.drawLine(10, 970, 580, 970, paint);


        paint.setTextSize(20.0f);
        canvas.drawText("Sick Note ",20,280,paint);

        paint.setTextSize(15.0f);
        canvas.drawText("Type Of Sick Note :",20,315,paint);
        paint.setColor(Color.BLACK);

        paint.setTextSize(13.0f);
        canvas.drawText(DSN_SickOfType,20,340,paint);
        paint.setColor(Color.BLACK);

        paint.setTextSize(15.0f);
        canvas.drawText("Sick Note :",20,375,paint);
        paint.setColor(Color.BLACK);

        paint.setTextSize(13.0f);
        canvas.drawText(DSN_SickNote,20,400,paint);
        paint.setColor(Color.BLACK);



        canvas.drawText("Authentic By : ",480,930,paint);
        paint.setColor(Color.BLACK);
        canvas.drawText("Admin ",495,945,paint);

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
        String targetPdf = directory_path+"SickNote_"+DSN_Date+"_"+DSN_PatientName+"_"+"T:"+"_"+time+".pdf";
        File filePath = new File(targetPdf);

        try {
            document.writeTo(new FileOutputStream(filePath));
            Toast.makeText(Detail_Sicknote.this, "PDF Download In MediAssist Folder", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Log.e("main", "error "+e.toString());
            Toast.makeText(Detail_Sicknote.this, "Something wrong: " + e.toString(),  Toast.LENGTH_LONG).show();
        }

        // close the document
        document.close();



    }
}
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

public class Detail_Invoice extends AppCompatActivity {
    TextView DIN_D_Clinic,DIN_D_data1,DIN_D_data2,DIN_D_data3,DIN_Date_txt,DIN_Patient_Name_txt,DIN_Patient_ID_txt,DIN_Particulars_txt,DIN_Total_txt,DIN_D_data4;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    String DINV_Date,DINV_DoctorID,DINV_DoctorName,DINV_HospitalName,DINV_Invoice_Particulars_Details,DINV_PatientID,DINV_PatientName,DINV_Total;
    String DINV_Username="",Username_VIN,PatientMail_VIN;
    Bitmap btm,btm1;
    String FBFS_FirstName,FBFS_LastName,FBFS_Mobile_No,FBFS_Specialization,FBFS_Education,FBFS_MCR_No,FBFS_Clinic_Name,FBFS_Address,FBFS_City,FBFS_State;
    String time;
    String data1,data2,data3,data4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_invoice);

        Intent intent = getIntent();
        PatientMail_VIN= intent.getStringExtra("PatientMail_VIN");
        Username_VIN = intent.getStringExtra("Username_VIN");


        time = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());

        btm= BitmapFactory.decodeResource(getResources(), R.drawable.redcross3);
        btm1= Bitmap.createScaledBitmap(btm,100,100,false );


        DIN_D_Clinic=findViewById(R.id.DIN_D_Clinic);
        DIN_D_data1=findViewById(R.id.DIN_D_data1);
        DIN_D_data2=findViewById(R.id.DIN_D_data2);
        DIN_D_data3=findViewById(R.id.DIN_D_data3);
        DIN_Date_txt=findViewById(R.id.DIN_Date_txt);
        DIN_Patient_Name_txt=findViewById(R.id.DIN_Patient_Name_txt);
        DIN_Patient_ID_txt=findViewById(R.id.DIN_Patient_ID_txt);
        DIN_Particulars_txt=findViewById(R.id.DIN_Particulars_txt);
        DIN_Total_txt=findViewById(R.id.DIN_Total_txt);
        DIN_D_data4=findViewById(R.id.DIN_D_data4);

        getPatientInfo();
    }

    private void getPatientInfo() {
        //firebase part
        DocumentReference documentReference = db.collection("Invoice").document(Username_VIN);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                DINV_Date=value.getString("Date");
                DINV_DoctorID=value.getString("DoctorID");
                DINV_DoctorName=value.getString("DoctorName");
                DINV_PatientID=value.getString("PatientID");
                DINV_PatientName=value.getString("PatientName");
                DINV_HospitalName=value.getString("HospitalName");
                DINV_Invoice_Particulars_Details=value.getString("Invoice_Particulars_Details");
                DINV_Total=value.getString("Total");


                DIN_D_Clinic.setText(DINV_HospitalName);
                DIN_Date_txt.setText(DINV_Date);
                DIN_Patient_Name_txt.setText(DINV_PatientName);
                DIN_Patient_ID_txt.setText(DINV_PatientID);
                DIN_Particulars_txt.setText(DINV_Invoice_Particulars_Details);
                DIN_Total_txt.setText(DINV_Total);

                //doctor info start here

                DocumentReference documentReference_D = db.collection("Doctors").document(DINV_DoctorID);

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


                            DIN_D_Clinic.setText(FBFS_Clinic_Name);
                            data1="Dr. "+FBFS_FirstName+" "+FBFS_LastName+", ("+FBFS_Specialization+", "+FBFS_Education+")";
                            DIN_D_data1.setText(data1);
                            data2="Contact No. "+FBFS_Mobile_No;
                            DIN_D_data2.setText(data2);
                            data3="MCR No : "+FBFS_MCR_No;
                            DIN_D_data3.setText(data3);
                            data4="Address : "+FBFS_Address+", City : "+FBFS_City+", "+FBFS_State;
                            DIN_D_data4.setText(data4);



                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Data Not Found", Toast.LENGTH_SHORT).show();
                    }
                });

                //doctor info end here


                //VP_All_P_Details="Date : "+VP_Date+"\n"+"Doctor ID :"+VP_DoctorID+"\n"+"Doctor Name  :"+VP_DoctorName+"\n"+"Patient Name :"+VP_PatientName+"\n"+"Patient Age :"+VP_PatientAge+"\n"+"Diagnosis :"+VP_Diagnosis+"\n"+"Symtoms :"+VP_Symtoms+"\n"+"MedicinceList :\n"+VP_MedicinceList+"\n"+"DoctorNote :"+VP_DoctorNote;


                // Toast.makeText(getApplicationContext(),VP_All_P_Details,Toast.LENGTH_SHORT).show();

            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void DownloadInvoice(View view) {

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
        canvas.drawText("Date :"+DINV_Date,460,200,paint);
        paint.setColor(Color.BLACK);

        canvas.drawText("Name : "+DINV_PatientName,20,200,paint);
        paint.setColor(Color.BLACK);

        canvas.drawText("Patient ID : "+DINV_PatientID,20,220,paint);
        paint.setColor(Color.BLACK);



        canvas.drawLine(10, 260, 580, 260, paint);
        canvas.drawLine(10, 290, 580, 290, paint);
        canvas.drawLine(10, 260, 10, 490, paint);
        canvas.drawLine(580, 260, 580, 490, paint);
        canvas.drawLine(10, 490, 580, 490, paint);

        paint.setTextSize(15.0f);
        canvas.drawText("Particulars ",20,280,paint);
        paint.setTextSize(10.0f);
        canvas.drawText(DINV_Invoice_Particulars_Details,20,310,paint);
        paint.setColor(Color.BLACK);

        canvas.drawLine(10, 510, 580, 510, paint);
        canvas.drawLine(10, 540, 580, 540, paint);
        canvas.drawLine(10, 565, 580, 565, paint);

        canvas.drawLine(10, 510, 10, 565, paint);
        canvas.drawLine(580, 510, 580, 565, paint);

        paint.setTextSize(15.0f);
        canvas.drawText("Total : ",20,530,paint);
        paint.setTextSize(10.0f);
        canvas.drawText(DINV_Total,20,555,paint);
        paint.setColor(Color.BLACK);



        canvas.drawText("Authentic By : ",500,930,paint);
        paint.setColor(Color.BLACK);
        canvas.drawText("Admin ",515,945,paint);

        paint.setColor(Color.BLACK);
        canvas.drawLine(0, 960, 985, 960, paint);

        paint.setTextSize(15.0f);
        canvas.drawText(data4,30,980,paint);


        //canvas.drawt
        // finish the page
        document.finishPage(page);

        // write the document content
        String directory_path = Environment.getExternalStorageDirectory().getPath() + "/Medi Assistant/";
        File file = new File(directory_path);
        if (!file.exists()) {
            file.mkdirs();
        }
        String targetPdf = directory_path+"Invoice_"+DINV_Date+"_"+DINV_PatientName+"_"+"T:"+"_"+time+".pdf";
        File filePath = new File(targetPdf);

        try {
            document.writeTo(new FileOutputStream(filePath));
            Toast.makeText(Detail_Invoice.this, "PDF Download In MediAssist Folder", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Log.e("main", "error "+e.toString());
            Toast.makeText(Detail_Invoice.this, "Something wrong: " + e.toString(),  Toast.LENGTH_LONG).show();
        }

        // close the document
        document.close();

    }
}
package com.example.mediassist;


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
    TextView DSN_Username,DSN_Details;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    String DINV_All_sick_Details,DSN_Date,DSN_HospitalName,DSN_DoctorID,DSN_DoctorNo,DSN_PatientID,DSN_PatientName,DSN_SickOfType,DSN_SickNote;
    Bitmap btm,btm1;
    String time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_sicknote);

        time = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());

        btm= BitmapFactory.decodeResource(getResources(), R.drawable.notes);
        btm1= Bitmap.createScaledBitmap(btm,50,50,false );

        DSN_Username=findViewById(R.id.DSN_Username);
        DSN_Details=findViewById(R.id.DSN_Details);

        Intent intent = getIntent();
        String DSN_Username = intent.getStringExtra("Sicknote_Username");

        DocumentReference documentReference = db.collection("Sicknote").document(DSN_Username);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                DSN_Date=value.getString("Date");
                DSN_HospitalName=value.getString("HospitalName");
                DSN_DoctorID=value.getString("DoctorID");
                DSN_DoctorNo=value.getString("Doctor No");
                DSN_PatientID=value.getString("PatientID");
                DSN_PatientName=value.getString("PatientName");
                DSN_SickOfType=value.getString("SickOfType");
                DSN_SickNote=value.getString("SickNote");


                DINV_All_sick_Details=DSN_Date+"\n"+DSN_HospitalName+"\n"+DSN_DoctorID+"\n"+DSN_DoctorNo+"\n"+DSN_PatientID+"\n"+DSN_PatientName+"\n"+DSN_SickOfType+"\n"+DSN_SickNote;
                SicknoteDetails(DSN_Username,DINV_All_sick_Details);



            }
        });


    }

    private void SicknoteDetails(String dsn_username, String dinv_all_sick_details) {
        DSN_Username.setText("Username : "+dsn_username);
        DSN_Details.setText(dinv_all_sick_details);
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
        canvas.drawBitmap(btm1,300,5,paint);
        paint.setTextSize(20.0f);
        canvas.drawText("Sicknote",260,73,paint);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(10.0f);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(2);
        canvas.drawLine(0, 90, 985, 90, paint);

        canvas.drawText("Date : ",10,120,paint);
        paint.setColor(Color.BLACK);
        canvas.drawText(DSN_Date, 80, 120, paint);

        canvas.drawText("Doctor ID : ",10,135,paint);
        paint.setColor(Color.BLACK);
        canvas.drawText(DSN_DoctorID, 80, 135, paint);

        canvas.drawText("Doctor Name : ",10,150,paint);
        paint.setColor(Color.BLACK);
        canvas.drawText(DSN_DoctorNo, 80, 150, paint);

        canvas.drawText("Hospital Name : ",10,165,paint);
        paint.setColor(Color.BLACK);
        canvas.drawText(DSN_HospitalName, 80, 165, paint);

        canvas.drawText("DSN_PatientID : ",10,180,paint);
        paint.setColor(Color.BLACK);
        canvas.drawText(DSN_PatientID, 80, 180, paint);

        canvas.drawText("Patient Name : ",10,195,paint);
        paint.setColor(Color.BLACK);
        canvas.drawText(DSN_PatientName, 80, 195, paint);

        canvas.drawText("Sick of Type : ",10,210,paint);
        paint.setColor(Color.BLACK);
        canvas.drawText(DSN_SickOfType, 80, 210, paint);

        canvas.drawText("SickNote  : ",10,225,paint);
        paint.setColor(Color.BLACK);
        canvas.drawText(DSN_SickNote,10,240,paint);

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
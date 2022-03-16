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

public class Detail_Invoice extends AppCompatActivity {
    TextView DIN_Username,DIN_Details;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    String DINV_All_in_Details,DINV_Date,DINV_DoctorID,DINV_DoctorName,DINV_HospitalName,DINV_Invoice_Particulars_Details,DINV_MobileNo,DINV_PatientID,DINV_PatientName,DINV_SR_No,DINV_Total;
    String DINV_Username="";
    Bitmap btm,btm1;
    String time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_invoice);

        time = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());

        btm= BitmapFactory.decodeResource(getResources(), R.drawable.invoice1);
        btm1= Bitmap.createScaledBitmap(btm,50,50,false );

        DIN_Username=findViewById(R.id.DIN_Username);
        DIN_Details=findViewById(R.id.DIN_Details);

        Intent intent = getIntent();
        DINV_Username = intent.getStringExtra("Invoice_Username");

        DocumentReference documentReference = db.collection("Invoice").document(DINV_Username);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                DINV_Date=value.getString("Date");
                DINV_DoctorID=value.getString("DoctorID");
                DINV_DoctorName=value.getString("DoctorName");
                DINV_HospitalName=value.getString("HospitalName");
                DINV_Invoice_Particulars_Details=value.getString("Invoice_Particulars_Details");
                DINV_MobileNo=value.getString("MobileNo");
                DINV_PatientID=value.getString("PatientID");
                DINV_PatientName=value.getString("PatientName");
                DINV_SR_No=value.getString("SR_No");

                DINV_All_in_Details=DINV_SR_No+"\n"+DINV_HospitalName+"\n"+DINV_Date+"\n"+DINV_DoctorID+"\n"+DINV_DoctorName+"\n"+DINV_MobileNo+"\n"+DINV_PatientID+"\n"+DINV_PatientName+"\n"+DINV_Invoice_Particulars_Details+"\n"+DINV_Total;

                InvoiceDetails();


            }
        });


    }

    private void InvoiceDetails() {
        DIN_Username.setText("Username :"+DINV_Username);
        DIN_Details.setText(DINV_All_in_Details);
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
        canvas.drawBitmap(btm1,300,5,paint);
        paint.setTextSize(20.0f);
        canvas.drawText("Invoice",260,73,paint);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(10.0f);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(2);
        canvas.drawLine(0, 90, 985, 90, paint);

        canvas.drawText("SR No : ",10,120,paint);
        paint.setColor(Color.BLACK);
        canvas.drawText(DINV_SR_No, 80, 120, paint);


        canvas.drawText("Date : ",10,135,paint);
        paint.setColor(Color.BLACK);
        canvas.drawText(DINV_Date, 80, 135, paint);

        canvas.drawText("Doctor ID : ",10,150,paint);
        paint.setColor(Color.BLACK);
        canvas.drawText(DINV_DoctorID, 80, 150, paint);

        canvas.drawText("Doctor Name : ",10,165,paint);
        paint.setColor(Color.BLACK);
        canvas.drawText(DINV_DoctorName, 80, 165, paint);

        canvas.drawText("Hospital Name : ",10,180,paint);
        paint.setColor(Color.BLACK);
        canvas.drawText(DINV_HospitalName, 80, 180, paint);

        canvas.drawText("Mobile No : ",10,195,paint);
        paint.setColor(Color.BLACK);
        canvas.drawText(DINV_MobileNo, 80, 195, paint);

        canvas.drawText("Patient ID : ",10,210,paint);
        paint.setColor(Color.BLACK);
        canvas.drawText(DINV_PatientID, 80, 210, paint);

        canvas.drawText("Patient Name : ",10,225,paint);
        paint.setColor(Color.BLACK);
        canvas.drawText(DINV_PatientName,10,240,paint);

        canvas.drawText("Particulars Details List : ",10,270,paint);
        paint.setColor(Color.BLACK);
        canvas.drawText(DINV_Invoice_Particulars_Details,10,285,paint);

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
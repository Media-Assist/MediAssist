package com.example.mediassist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class View_Invoice extends AppCompatActivity {
    EditText VIN_Date,VIN_Name;
    String VIN_Usename;
    DatePickerDialog picker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_invoice);
        VIN_Date=findViewById(R.id.VIN_Date);
        VIN_Name=findViewById(R.id.VIN_Name);

        VIN_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar=Calendar.getInstance();
                int day=calendar.get(Calendar.DAY_OF_MONTH);
                int month=calendar.get(Calendar.MONTH);
                int year=calendar.get(Calendar.YEAR);

                picker=new DatePickerDialog(View_Invoice.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        VIN_Date.setText(i2+"-"+(i1+1)+"-"+i);
                    }
                }, year,month,day);
                picker.show();
            }
        });
    }

    public void detailInvoice(View view) {
        if (TextUtils.isEmpty(VIN_Name.getText().toString())) {
            VIN_Name.setError("Required");
            return;
        }
        if (TextUtils.isEmpty(VIN_Date.getText().toString())) {
            VIN_Date.setError("Required");
            return;
        }


        VIN_Usename=VIN_Name.getText().toString()+VIN_Date.getText().toString();

        Intent intent = new Intent(getApplicationContext(), Detail_Invoice.class);
        intent.putExtra("Invoice_Username",VIN_Usename);
        startActivity(intent);


        Toast.makeText(getApplicationContext(),"Done", Toast.LENGTH_SHORT).show();
    }


}
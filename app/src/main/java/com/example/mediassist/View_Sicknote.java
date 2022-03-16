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
public class View_Sicknote extends AppCompatActivity {
    EditText VS_name,VS_date;
    String VSN_Username;
    DatePickerDialog picker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sicknote);


        VS_name=findViewById(R.id.VS_name);
        VS_date=findViewById(R.id.VS_date);

        VS_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar=Calendar.getInstance();
                int day=calendar.get(Calendar.DAY_OF_MONTH);
                int month=calendar.get(Calendar.MONTH);
                int year=calendar.get(Calendar.YEAR);

                picker=new DatePickerDialog(View_Sicknote.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        VS_date.setText(i2+"-"+(i1+1)+"-"+i);
                    }
                }, year,month,day);
                picker.show();
            }
        });

    }

    public void detailSickNote(View view) {
        if (TextUtils.isEmpty(VS_name.getText().toString())) {
            VS_name.setError("Required");
            return;
        }

        if (TextUtils.isEmpty(VS_date.getText().toString())) {
            VS_date.setError("Required");
            return;
        }
        VSN_Username=VS_name.getText().toString()+VS_date.getText().toString();

        Intent intent = new Intent(getApplicationContext(), Detail_Sicknote.class);
        intent.putExtra("Sicknote_Username",VSN_Username);
        startActivity(intent);
    }



}
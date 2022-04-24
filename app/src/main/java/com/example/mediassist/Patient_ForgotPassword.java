package com.example.mediassist;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Patient_ForgotPassword extends AppCompatActivity {
    EditText P_Forgot_Password;
    String email;
    FirebaseAuth auth;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_forgot_password);

        P_Forgot_Password=findViewById(R.id.P_Forgot_Password);
        auth=FirebaseAuth.getInstance();
    }

    public void forgotPassword(View view) {
        progressDialog =new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        email=P_Forgot_Password.getText().toString();
        if (email.isEmpty()){
            P_Forgot_Password.setError("Required");
            if (progressDialog.isShowing())
                progressDialog.dismiss();
        }else {
            forgotpassword2();

        }
    }

    private void forgotpassword2() {

        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Check Your Email",Toast.LENGTH_SHORT).show();
                            Intent send = new Intent(Patient_ForgotPassword.this, Patient_Login.class);
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                            startActivity(send);
                            finish();

                        }else {
                            Toast.makeText(getApplicationContext(),"Erorr",Toast.LENGTH_SHORT).show();
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                        }
                    }
                });

    }
}
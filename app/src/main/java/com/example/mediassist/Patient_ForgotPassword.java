package com.example.mediassist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Patient_ForgotPassword extends AppCompatActivity {
    EditText P_Forgot_Password;
    String email;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_forgot_password);

        P_Forgot_Password=findViewById(R.id.P_Forgot_Password);
        auth=FirebaseAuth.getInstance();
    }

    public void forgotPassword(View view) {
        email=P_Forgot_Password.getText().toString();
        if (email.isEmpty()){
            P_Forgot_Password.setError("Required");
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
                            startActivity(send);
                            finish();

                        }else {
                            Toast.makeText(getApplicationContext(),"Erorr",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}
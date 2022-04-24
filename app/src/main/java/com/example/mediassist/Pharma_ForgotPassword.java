package com.example.mediassist;

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

public class Pharma_ForgotPassword extends AppCompatActivity {
    EditText PH_Forgot_Password;
    String email;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pharma_forgot_password);

        PH_Forgot_Password=findViewById(R.id.PH_Forgot_Password);
        auth=FirebaseAuth.getInstance();
    }

    public void forgotPassword(View view) {
        email=PH_Forgot_Password.getText().toString();
        if (email.isEmpty()){
            PH_Forgot_Password.setError("Required");
        }else {
            forgotpassword3();

        }
    }

    private void forgotpassword3() {

        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Check Your Email",Toast.LENGTH_SHORT).show();
                            Intent send = new Intent(Pharma_ForgotPassword.this, Pharmacy_Login.class);
                            startActivity(send);
                            finish();

                        }else {
                            Toast.makeText(getApplicationContext(),"Erorr",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
package com.hoaitu.qlntusers.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.hoaitu.qlntusers.MainActivity;
import com.hoaitu.qlntusers.R;


public class Reset extends AppCompatActivity {
//    private EditText inputEmail;
//    private Button btnReset, btnBack;
//    private FirebaseAuth auth;
    private ProgressBar progressBar;
    //
    private EditText edt_EmailText;
    private Button btn_resetPass , btn_exit;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

//        inputEmail = (EditText) findViewById(R.id.email);
//        btnReset = (Button) findViewById(R.id.btn_reset_password);
//        btnBack = (Button) findViewById(R.id.btn_back);
//        progressBar = (ProgressBar) findViewById(R.id.progressBar);

//        auth = FirebaseAuth.getInstance();

//        btnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
        //////////////////////////////


        edt_EmailText= findViewById(R.id.email);
        btn_resetPass = findViewById(R.id.btn_reset_password);
        //
        btn_exit= findViewById(R.id.btn_back);
        //
        auth = FirebaseAuth.getInstance();
        btn_resetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassWord ();

            }
        });
        ///
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Reset.this, MainActivity.class) );

            }
        });
    }
    /////



    ///////

    private void resetPassWord() {
        String Email = edt_EmailText.getText().toString().trim();
        ///
        if (Email.isEmpty()) {
            edt_EmailText.setError("Email is required");
            edt_EmailText.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            edt_EmailText.setError("Please provice valid Email");
            edt_EmailText.requestFocus();
            return;

        }

        ///
        auth.sendPasswordResetEmail(Email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(Reset.this, "Check your email to reset your PassWord!!" , Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(Reset.this, "Try again! Something wrong happen!!!!" , Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}
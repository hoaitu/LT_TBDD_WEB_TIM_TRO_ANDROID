package com.hoaitu.qlntusers.ui.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hoaitu.qlntusers.MainActivity;
import com.hoaitu.qlntusers.R;

public class Login extends AppCompatActivity implements View.OnClickListener {
    EditText et_email, et_pass;
    Button bt_login;
    TextView tv_register,tv_forgot;

    String strEmail;
    private FirebaseAuth mAuth;
//    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
//            Intent intent = new Intent(this, ProfileActivity.class);
//            startActivity(intent);
            finish();
        }
//        et_email = findViewById(R.id.email);
//        et_pass = findViewById(R.id.pass);
//        bt_login = findViewById(R.id.button);
//        tv_register = findViewById(R.id.textView);
//        tv_forgot = findViewById(R.id.textView1);
//        firebaseAuth = FirebaseAuth.getInstance();
////////////

        et_email = findViewById(R.id.email);
        et_pass = findViewById(R.id.pass);
        bt_login = findViewById(R.id.button);
        tv_register = findViewById(R.id.textView);
        tv_forgot = findViewById(R.id.textView1);
        tv_register = findViewById(R.id.textView);
       
/////////////
        tv_register.setOnClickListener(this);
        bt_login.setOnClickListener(this);
        mAuth= FirebaseAuth.getInstance();

        tv_forgot.setOnClickListener(this);
////////////////////////
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this, Register.class);
                startActivity(i);
            }
        });

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.button:
                        userLogin();
                        break;
                }
//
            }
        });

    }

    private void userLogin() {
        String email = et_email.getText().toString().trim();
        String password = et_pass.getText().toString().trim();
        //
        if (email.isEmpty()) {
            et_email.setError("Email is required");
            et_email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            et_email.setError("Please provice valid Email");
            et_email.requestFocus();
            return;

        }

        //
        if (password.isEmpty()) {
            et_pass.setError("PAss is required");
            et_pass.requestFocus();
            return;
        }

        if (password.length() < 6) {
            et_pass.setError("Min password length should be 6 chracter!!!");
            et_pass.requestFocus();
            return;

        }
        ////////

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
//////////--------------------
                if(task.isSuccessful()){
                    //redirect to User profile::
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if(user.isEmailVerified()){
                        onBackPressed();
//                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        // Toast.makeText(Login.this, "OK vaof ddc nha  :D!", Toast.LENGTH_LONG).show();
                    }else {
                        user.sendEmailVerification();
                        Toast.makeText(Login.this, "Please check Email :D!", Toast.LENGTH_LONG).show();
                        onBackPressed();
                    }

                }else {
                    Toast.makeText(Login.this, "Failed to Login!!Please check again!!", Toast.LENGTH_LONG).show();
                }
            }

        });
        //////////////////////////////////////// /////////////
    }
    //    public void rememberUser(String email, String pass, boolean status){
//        SharedPreferences sharedPreferences = getSharedPreferences("USER_FILE", MODE_PRIVATE) ;
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        if (!status){
//            editor.clear();
//        } else {
//            editor.putString("EMAIL", email);
//            editor.putString("PASSWORD", pass);
//            editor.putBoolean("LOGIN", status);
//        }
//                editor.commit();
//    }
    public int checkLogin(){
        SharedPreferences sharedPreferences = getSharedPreferences("USER_FILE", MODE_PRIVATE) ;
        boolean chk =sharedPreferences.getBoolean("LOGIN", false);
        if (chk){
            strEmail = sharedPreferences.getString("EMAIL","");
            return 1;

        }
        return -1;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textView:


                Intent i = new Intent(Login.this, Register.class);
                startActivity(i);
                break;
            /////
            case R.id.button:
                userLogin();
                break;
            /////RsetPass
            case R.id.textView1:
                startActivity(new Intent(Login.this, Reset.class));
                break;

        }


        ///////////


//        tv_register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(Login.this, Register.class);
//                startActivity(i);
//            }
//        });
//        tv_forgot.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(Login.this, Reset.class);
//                startActivity(i);
//            }
//        });

    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(Login.this, MainActivity.class));
    }
}

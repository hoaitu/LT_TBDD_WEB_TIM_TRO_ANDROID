package com.hoaitu.qlntusers.ui.login;

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
import com.google.firebase.database.FirebaseDatabase;
import com.hoaitu.qlntusers.R;


public class Register extends AppCompatActivity implements View.OnClickListener{
    EditText et_name, et_email, et_pass;
    Button bt_register;
    TextView tv_login;
    private EditText edt_name , edt_pass1, edt_email1, edt_level;
    private Button bt_regissterUser;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_register);
//        et_name = findViewById(R.id.name);
//        et_email= findViewById(R.id.email1);
//        et_pass = findViewById(R.id.pass1);
//        bt_register = findViewById(R.id.button1);
//        tv_login = findViewById(R.id.textView1);
//        firebaseAuth = FirebaseAuth.getInstance();

        mAuth = FirebaseAuth.getInstance();
        ////////
//        cho banner bấm vào hiện tại trang đó:
        bt_regissterUser = findViewById(R.id.button1);
        bt_regissterUser.setOnClickListener(this);
        ///
        edt_name= findViewById(R.id.name);
        edt_email1= findViewById(R.id.email1);
        edt_pass1= findViewById(R.id.pass1);
//        edt_level = findViewById(R.id.level);
//        edt_level.setText("user");
        //Nếu có Progessbar:
//        progessbar = findViewById(R.id.progessbar);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button1:
                registerUser ();
                break;
        }
    }

    private void registerUser() {
        String name = edt_name.getText().toString().trim();
        String email = edt_email1.getText().toString().trim();
        String pass = edt_pass1.getText().toString().trim();
        int Level = 1;
        ///
        if (name.isEmpty()) {
            edt_name.setError("Name is required");
            edt_name.requestFocus();
            return;
        }
        //
        if (email.isEmpty()) {
            edt_email1.setError("Email is required");
            edt_name.requestFocus();
            return;
        }

        //
        if (pass.isEmpty()) {
            edt_pass1.setError("PAss is required");
            edt_name.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edt_email1.setError("Please provice valid Email");
            edt_name.requestFocus();
            return;

        }
        if (pass.length() < 6) {
            edt_pass1.setError("Min password length should be 6 chracter!!!");
            edt_pass1.requestFocus();
            return;

        }


        ///////
//
//        progessBar.setVisibility(View.VISBLE);
        mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    User1 user = new User1(name,email,Level);
                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance()
                            .getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){
                                Toast.makeText(Register.this
                                        , "Sussces", Toast.LENGTH_LONG).show();
                                onBackPressed();

//progessBar.setVisibility(View.Gone);
                            }else {
                                Toast.makeText(Register.this
                                        , "Failed to Register!! Try again !!", Toast.LENGTH_LONG).show();
//progessBar.setVisibility(View.Gone);
                            }


                        }
                    });
                }else {
                    Toast.makeText(Register.this
                            , "Failed to Register!! Try again !!", Toast.LENGTH_LONG).show();
                }



            }
        });

    }

}
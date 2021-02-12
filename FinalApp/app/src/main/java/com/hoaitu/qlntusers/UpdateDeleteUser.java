package com.hoaitu.qlntusers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class UpdateDeleteUser extends AppCompatActivity {
TextView txname, txemail;
Button update, delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_user);

        txname = findViewById(R.id.txtnameuser);
        txname = findViewById(R.id.txtemails);
        Intent intent = getIntent();

        String name = intent.getStringExtra("name");
        String email = intent.getStringExtra("email");

        txname.setText(name);
//        txemail.setText(email);
    }
}
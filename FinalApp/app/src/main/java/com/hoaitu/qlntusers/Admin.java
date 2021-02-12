package com.hoaitu.qlntusers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.hoaitu.qlntusers.ui.login.User;

public class Admin extends AppCompatActivity {
    Button qluser;
    Button qlbv, home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        qluser = findViewById(R.id.btnqluser);
        qlbv = findViewById(R.id.btnqlbv);
        home = findViewById(R.id.btnhome);
        qlbv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), QLBV.class);
                startActivity(intent);
            }
        });
        qluser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), QLUser.class);
                startActivity(intent1);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), User.class);
                startActivity(i);
            }
        });
    }
}
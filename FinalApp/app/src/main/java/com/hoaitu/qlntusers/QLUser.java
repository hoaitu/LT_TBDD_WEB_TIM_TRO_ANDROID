package com.hoaitu.qlntusers;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hoaitu.qlntusers.custom.userAdapter;
import com.hoaitu.qlntusers.ui.login.User1;

import java.util.ArrayList;

public class QLUser extends AppCompatActivity {
    ListView listView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private userAdapter adapter;
    private ArrayList<User1> listuser;
    EditText mname, memail;
    Button update, delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_l_user);

        listView = findViewById(R.id.list_item_user);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        ////Arraylisst
        listuser = new ArrayList<>();
        /// Clear ArrayList
        ClearAll();
        //get data method
        GetDataFromFirebase();

       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               User1 u = listuser.get(position);
               Intent i = new Intent(getApplicationContext(), UpdateDeleteUser.class);
               i.putExtra("name", u.getName());
               i.putExtra("email", u.getEmail());

               startActivity(i);
           }
       });
    }



    private void GetDataFromFirebase() {
        Query query = databaseReference.child("Users");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    User1 u = new User1();
                    u.setName(dataSnapshot.child("name").getValue().toString());
                    u.setEmail(dataSnapshot.child("email").getValue().toString());
//                    u.setLevel(dataSnapshot.child("level").getValue().toString());
                listuser.add(u);
                }
                adapter = new userAdapter(listuser, getApplicationContext());
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void ClearAll() {
        if(listuser!= null){
            listuser.clear();
            if(adapter != null){
                adapter.notifyDataSetChanged();
            }
        }
        listuser = new ArrayList<>();
    }
}
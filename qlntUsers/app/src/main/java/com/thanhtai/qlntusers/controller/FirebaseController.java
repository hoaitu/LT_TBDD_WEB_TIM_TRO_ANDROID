package com.thanhtai.qlntusers.controller;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.thanhtai.qlntusers.model.Motel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FirebaseController {
    List<Motel> motels;
    FirebaseDatabase database;
    DatabaseReference myRef;
    public FirebaseController() {
        motels = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
//        getch();
        getch1();

    }

    public void post(Motel motel){
        String loai = motel.getTitle();
        myRef.child("Motel1").child(loai).push().setValue(motel);

    }

    private void getch1() {

        Query allPost = myRef.child("Motel1").child("timtro");
        allPost.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren())
                {
                    Motel motel = item.getValue(Motel.class);
                    Log.i("nbnbnbnbnb",motel.toString());
                    motels.add(motel);
                }
                Collections.reverse(motels);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void getch(){
//        myRef.child("Motel").child("nhatro").addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                Motel motel = dataSnapshot.getValue(Motel.class);
//                motels.add(motel);
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
    }

    public List<Motel> getMotels() {
//        Collections.reverse(motels);
        return motels;
    }

    public void setMotels(List<Motel> motels) {
        this.motels = motels;
    }
}

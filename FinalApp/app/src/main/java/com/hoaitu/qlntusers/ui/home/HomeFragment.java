package com.hoaitu.qlntusers.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hoaitu.qlntusers.R;
import com.hoaitu.qlntusers.custom.CustomListAdapter;
import com.hoaitu.qlntusers.model.BaiDang;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private RecyclerView listView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private CustomListAdapter adapter;
    private ArrayList<BaiDang> listmotels;
    private Context mcontext;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        listView = root.findViewById(R.id.list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        listView.setLayoutManager(layoutManager);
        listView.setHasFixedSize(true);

        listView.setLayoutManager(new LinearLayoutManager(getActivity()));

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        ////Arraylisst
        listmotels = new ArrayList<>();

        /// Clear ArrayList

        ClearAll();

        //get data method

        GetDataFromFirebase();
        return root;
    }

    private void GetDataFromFirebase() {

        Query query = databaseReference.child("DangBai");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    BaiDang baiDang = new BaiDang();
                    baiDang.setImg(dataSnapshot.child("img").getValue().toString());
                    baiDang.setTieude(dataSnapshot.child("tieude").getValue().toString());
                    baiDang.setMota(dataSnapshot.child("mota").getValue().toString());
                    baiDang.setAddress(dataSnapshot.child("address").getValue().toString());
                    baiDang.setTitle(dataSnapshot.child("title").getValue().toString());
                    baiDang.setTinh(dataSnapshot.child("tinh").getValue().toString());
                    baiDang.setHuyen(dataSnapshot.child("huyen").getValue().toString());
                    baiDang.setPhone(dataSnapshot.child("phone").getValue().toString());
                    baiDang.setPrice(dataSnapshot.child("price").getValue().toString());
                    baiDang.setDientich(dataSnapshot.child("dientich").getValue().toString());

                    listmotels.add(baiDang);
                }
                adapter = new CustomListAdapter(getActivity(), listmotels);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void ClearAll(){
        if(listmotels!= null){
            listmotels.clear();
            if(adapter != null){
                adapter.notifyDataSetChanged();
            }
        }
        listmotels = new ArrayList<>();
    }
}

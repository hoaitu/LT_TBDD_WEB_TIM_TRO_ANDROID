package com.thanhtai.qlntusers.ui.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.thanhtai.qlntusers.Detail;
import com.thanhtai.qlntusers.R;
import com.thanhtai.qlntusers.controller.FirebaseController;
import com.thanhtai.qlntusers.custom.CustomListAdapter;
import com.thanhtai.qlntusers.model.Motel;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {
    ListView listView;
    CustomListAdapter cus;
    List<Motel> motels;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        listView = (ListView) root.findViewById(R.id.list);
        motels = new ArrayList<>();
        cus = new CustomListAdapter(getActivity(), motels);
        listView.setAdapter(cus);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        Query allPost = myRef.child("Motel1");
        allPost.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren())
                {
                    for (DataSnapshot item1 : item.getChildren())
                    {
                        Motel motel = item1.getValue(Motel.class);
                        Log.i("nbnbnbnbnb",motel.toString());
                        Date date = new Date();
                        Long thisTime = date.getTime();
                        if(motel.isView() && thisTime<motel.getDatelt()) {
                            motels.add(motel);
                        }
                    }

                }
                Collections.reverse(motels);
                cus.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), Detail.class);
                Motel motel =(Motel)parent.getAdapter().getItem(position);
                intent.putExtra("img",motel.getImg());
                intent.putExtra("address",motel.getAddress());
                intent.putExtra("sex",motel.getSex());
                intent.putExtra("price",motel.getPrice()+"");
                intent.putExtra("hinhthuc",motel.getTitle());
                intent.putExtra("name",motel.getName());
                intent.putExtra("phone",motel.getPhone());
                intent.putExtra("tinh",motel.getTinh());
                intent.putExtra("huyen",motel.getHuyen());
                startActivity(intent);
            }
        });
        return root;
    }

}
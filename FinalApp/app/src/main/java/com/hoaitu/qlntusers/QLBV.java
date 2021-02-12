package com.hoaitu.qlntusers;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hoaitu.qlntusers.custom.NewAdapter;
import com.hoaitu.qlntusers.model.BaiDang;

import java.util.ArrayList;

public class QLBV extends AppCompatActivity {
    ListView listView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private NewAdapter adapter;
    private ArrayList<BaiDang> listnew;
    EditText ten, mota, sdtlh, diachi, giatien, dientich;
    Spinner hinhthuc, tinh, huyen;
    ImageView imageView;
    Button update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_l_b_v);
        listView =  findViewById(R.id.list_item_new);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        ////Arraylisst
        listnew = new ArrayList<>();
        /// Clear ArrayList
        ClearAll();
        //get data method
        GetDataFromFirebase();
//listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//    @Override
//    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//        BaiDang baiDang = listnew.get(position);
//        showUpdatePost(baiDang.getTieude());
//        return false;
//    }
//});
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BaiDang baiDang = listnew.get(position);
                Intent intent = new Intent(getApplicationContext(), UpdateDelete.class);
                intent.putExtra("img",baiDang.getImg());
                intent.putExtra("tieude", baiDang.getTieude());
                intent.putExtra("mota",baiDang.getMota());
                intent.putExtra("tinh", baiDang.getTinh());
                intent.putExtra("huyen",baiDang.getHuyen());
                intent.putExtra("address", baiDang.getAddress());
                intent.putExtra("price", baiDang.getPrice());
                intent.putExtra("title", baiDang.getTitle());
                intent.putExtra("phone", baiDang.getPhone());
                intent.putExtra("dientich",baiDang.getDientich());
                startActivity(intent);
            }
        });
    }
    private void GetDataFromFirebase() {
        Query query = databaseReference.child("DangBai");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
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

                    listnew.add(baiDang);
                }
                adapter = new NewAdapter( listnew, getApplicationContext());
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

//    private void showUpdatePost(String titile){
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        LayoutInflater inflater = getLayoutInflater();
//        final View dialogView = inflater.inflate(R.layout.update_post,null);
//        builder.setView(dialogView);
//
//        ten = dialogView.findViewById(R.id.tieude);
//        mota = dialogView.findViewById(R.id.mota);
//        sdtlh = dialogView.findViewById(R.id.phone);
//        tinh = dialogView.findViewById(R.id.spiner_tinh);
//        huyen = dialogView.findViewById(R.id.spiner_huyen);
//        diachi= dialogView.findViewById(R.id.diachi);
//        giatien = dialogView.findViewById(R.id.gia);
//        dientich = dialogView.findViewById(R.id.dientich);
//        hinhthuc = dialogView.findViewById(R.id.spiner_hinhthucdang);
//        imageView = dialogView.findViewById(R.id.img);
//
//        update = dialogView.findViewById(R.id.btupdate);
//        update.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        builder.setTitle("Tieu de" + titile);
//
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//    }

//    private boolean updatePost(String mtieude, String mmota, String msdt, String mdiachi, String mgia, String mdientich, String st_huyen, String st_tinh, String st_hinhthuc, String mimage){
//        databaseReference = FirebaseDatabase.getInstance().getReference("DangBai").child(mtieude);
//
//        BaiDang baiDang = new BaiDang(mtieude, mmota, msdt, mdiachi, mgia, mdientich, st_huyen, st_tinh, st_hinhthuc, mimage);
//    databaseReference.setValue(baiDang);
//
//        Toast.makeText(this,"Update Successfully", Toast.LENGTH_LONG).show();
//
//        return true;
//    }


    private void ClearAll() {
        if(listnew!= null){
            listnew.clear();
            if(adapter != null){
                adapter.notifyDataSetChanged();
            }
        }
        listnew = new ArrayList<>();
    }
}



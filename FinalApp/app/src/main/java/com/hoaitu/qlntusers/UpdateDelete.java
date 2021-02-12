package com.hoaitu.qlntusers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class UpdateDelete extends AppCompatActivity {
    ImageView imageView;
    TextView tv_diachi,tv_gia,tv_ten,tv_sdt, tv_mota, tv_dt;
    TextView mhinhthuc, mtinh, mhuyen;
    Button update, delete;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);
        anhxa();
        Intent intent = getIntent();
        String img = intent.getStringExtra("img");
        String diachi = intent.getStringExtra("address");
        String gia = intent.getStringExtra("price");
        String hinhthuc = intent.getStringExtra("title");
        String mota = intent.getStringExtra("mota");
        String tieude = intent.getStringExtra("tieude");
        String tinh = intent.getStringExtra("tinh");
        String huyen = intent.getStringExtra("huyen");
        String phone = intent.getStringExtra("phone");
        String dt = intent.getStringExtra("dientich");

        Picasso.get().load(img).into(imageView);
        tv_diachi.setText(diachi);
        tv_gia.setText(gia);
        mhinhthuc.setText(hinhthuc);
        tv_mota.setText(mota);
        tv_ten.setText(tieude);
        mtinh.setText(tinh);
        mhuyen.setText(huyen);
        tv_sdt.setText(phone);
        tv_dt.setText(dt);

        String key = intent.getExtras().get("tieude").toString();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("DangBai").child(key);

    }

    public void btnUpdate(View view){
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                snapshot.getRef().child("tieude").setValue(tv_ten.getText().toString());
                snapshot.getRef().child("mota").setValue(tv_mota.getText().toString());
                snapshot.getRef().child("tinh").setValue(mtinh.getText().toString());
                snapshot.getRef().child("huyen").setValue(mhuyen.getText().toString());
                snapshot.getRef().child("title").setValue(mhinhthuc.getText().toString());
                snapshot.getRef().child("phone").setValue(tv_sdt.getText().toString());
                snapshot.getRef().child("address").setValue(tv_diachi.getText().toString());
//                snapshot.getRef().child("img").setValue(tv_ten.getText().toString());
                snapshot.getRef().child("price").setValue(tv_gia.getText().toString());
                snapshot.getRef().child("dientich").setValue(tv_dt.getText().toString());
                Toast.makeText(UpdateDelete.this, "Data Update Successfully...!", Toast.LENGTH_LONG).show();
                UpdateDelete.this.finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void btnDelete(View view){

        databaseReference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(UpdateDelete.this,"Recored Delete successfully...!", Toast.LENGTH_LONG).show();
                UpdateDelete.this.finish();
                }else{
                    Toast.makeText(UpdateDelete.this,"Recored Delete Error...!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void anhxa(){
        tv_ten = findViewById(R.id.tieude);
        tv_mota =findViewById(R.id.mota);
        tv_sdt = findViewById(R.id.phone);
        mtinh = findViewById(R.id.spiner_tinh);
        mhuyen = findViewById(R.id.spiner_huyen);
        tv_diachi= findViewById(R.id.diachi);
        tv_gia = findViewById(R.id.giatien);
        tv_dt = findViewById(R.id.dientich);
        mhinhthuc = findViewById(R.id.spiner_hinhthucdang);
        imageView = findViewById(R.id.img);

        update = findViewById(R.id.btnupdate);
        delete = findViewById(R.id.btnDelete);
    }
}
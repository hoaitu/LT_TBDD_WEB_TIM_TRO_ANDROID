package com.thanhtai.qlntusers;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Detail extends AppCompatActivity {
ImageView imageView;
TextView tv_diachi,tv_doituong,tv_gia,tv_hinhthuc,tv_ten,tv_sdt,tv_tinh,tv_huyen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail);
        anhxa();
        Intent intent = this.getIntent();
        String img = intent.getStringExtra("img");
        String diachi = intent.getStringExtra("address");
        String doituong = intent.getStringExtra("sex");
        String gia = intent.getStringExtra("price");
        String hinhthuc = intent.getStringExtra("hinhthuc");
        String ten = intent.getStringExtra("name");
        String sdt = intent.getStringExtra("phone");
        String tinh = intent.getStringExtra("tinh");
        String huyen = intent.getStringExtra("huyen");
        //fire base
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference mountainImagesRef = storage.getReference().child(img);

        mountainImagesRef.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Use the bytes to display the image
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                imageView.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
                tv_diachi.setText(diachi);
                tv_doituong.setText(doituong);
                tv_gia.setText(gia);
                tv_hinhthuc.setText(hinhthuc);
                tv_ten.setText(ten);
                tv_sdt.setText(sdt);
                tv_tinh.setText(tv_tinh.getText().toString()+": "+tinh);
                tv_huyen.setText(tv_huyen.getText().toString()+": "+huyen);


    }
    public void anhxa(){
        imageView = (ImageView)findViewById(R.id.image_detail);
        tv_diachi = (TextView) findViewById(R.id.diachi_detail);
        tv_doituong = (TextView) findViewById(R.id.doituong_detail);
        tv_gia = (TextView) findViewById(R.id.gia_detail);
        tv_hinhthuc = (TextView) findViewById(R.id.hinhthuc_detail);
        tv_ten = (TextView) findViewById(R.id.ten_detail);
        tv_sdt = (TextView) findViewById(R.id.sdt_detail);
        tv_tinh = (TextView) findViewById(R.id.tinh_detail);
        tv_huyen = (TextView) findViewById(R.id.huyen_detail);

    }
}

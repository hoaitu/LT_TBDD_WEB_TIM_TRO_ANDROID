package com.hoaitu.qlntusers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.hoaitu.qlntusers.ui.ggMap.MapsActivity2;
import com.hoaitu.qlntusers.ui.ggMap.MapsActivity3;
import com.squareup.picasso.Picasso;

public class Detail extends AppCompatActivity implements View.OnClickListener {
ImageView imageView;
TextView tv_tv_ttchutro, tv_diachi,tv_doituong,tv_gia,tv_hinhthuc,tv_ten,tv_sdt,tv_tinh,tv_huyen, tv_mota, tv_dt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail);
        anhxa();
        Intent intent = getIntent();
        String img = intent.getStringExtra("img");
        String diachi = intent.getStringExtra("address");
        String doituong = intent.getStringExtra("tieude");
        String gia = intent.getStringExtra("price");
        String hinhthuc = intent.getStringExtra("title");
        String mota = intent.getStringExtra("mota");
        String sdt = intent.getStringExtra("phone");
        String tinh = intent.getStringExtra("tinh");
        String huyen = intent.getStringExtra("huyen");
        String phone = intent.getStringExtra("phone");
        String dt = intent.getStringExtra("dientich");

                Picasso.get().load(img).into(imageView);
                tv_diachi.setText(diachi);
                tv_doituong.setText(doituong);
                tv_gia.setText(gia);
                tv_hinhthuc.setText(hinhthuc);
                tv_mota.setText(mota);
                tv_sdt.setText(sdt);
                tv_tinh.setText(tinh);
                tv_huyen.setText(huyen);
                tv_sdt.setText(phone);
                tv_dt.setText(dt);


        tv_tv_ttchutro = findViewById(R.id.tv_ttchutro);
        tv_tv_ttchutro.setOnClickListener(this);
        imageView = (ImageView)findViewById(R.id.image_detail);
        imageView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_ttchutro:
                Intent i = new Intent(Detail.this, MapsActivity2.class);
                startActivity(i);
                break;
            ////
            case R.id.image_detail:


                Intent i2 = new Intent(Detail.this, MapsActivity3.class);
                startActivity(i2);
                break;
        }
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
        tv_mota = findViewById(R.id.mota_detail);
        tv_dt = findViewById(R.id.txt_dt);
    }
}

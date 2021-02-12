package com.thanhtai.qlntusers.ui.post;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.thanhtai.qlntusers.R;
import com.thanhtai.qlntusers.config.Config;
import com.thanhtai.qlntusers.controller.FirebaseController;
import com.thanhtai.qlntusers.model.Motel;
import com.thanhtai.qlntusers.model.QuanHuyen;
import com.thanhtai.qlntusers.model.TinhTP;
import com.thanhtai.qlntusers.sqlite.SQLite_QuanHuyen;
import com.thanhtai.qlntusers.sqlite.SQLite_TinhTP;
import com.thanhtai.qlntusers.ui.search.SearchFragment;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PostFragment extends Fragment {

    private static final int RESULT_OK = -1;
    SQLite_TinhTP sqLite_tinhTP;
    SQLite_QuanHuyen sqLite_quanHuyen;
    ArrayAdapter<TinhTP> adapter_Tinh;
    ArrayAdapter<QuanHuyen> adapter_QH;
    String amount = "";
    EditText ten, tuoi, sdtlh, diachi, giatien, songay;
    Spinner gioitinh, hinhthuc, loaitin, loaingay,tinh,huyen;
    Button chontep, post;
    FirebaseController controller;
    private static final int RESULT_LOAD_IMAGE = 1;
    public static final int PAYPAL_REQUEST_CODE = 7171;
    ImageView imageView;
    FirebaseDatabase firebaseDatabase;
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Config.PAYPAL_CLIENT_ID);

    //
    @Override
    public void onDestroy() {
        getActivity().stopService(new Intent(getActivity(), PayPalService.class));
        super.onDestroy();
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_post, container, false);
        controller = new FirebaseController();
        Intent intent = new Intent(getActivity(), PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        getActivity().startService(intent);
        anhxa(root);
        actionSql();
        actionImage();
        actionPost();
        return root;
    }

    private void actionImage() {
        chontep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
    }

    private void actionPost() {
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(ten.getText().toString())) {
                    Toast.makeText(getActivity(), "Vui lòng nhập tên!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(tuoi.getText().toString())) {
                    Toast.makeText(getActivity(), "Vui lòng nhập tuổi!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(sdtlh.getText().toString())) {
                    Toast.makeText(getActivity(), "Vui lòng nhập Sđt liên hệ!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(diachi.getText().toString())) {
                    Toast.makeText(getActivity(), "Vui lòng nhập địa chỉ!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(giatien.getText().toString())) {
                    Toast.makeText(getActivity(), "Vui lòng nhập giá tiền!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(songay.getText().toString())) {
                    Toast.makeText(getActivity(), "Vui lòng nhập số ngày!", Toast.LENGTH_SHORT).show();
                    return;
                }
                String st_loaingay;

                st_loaingay = loaingay.getSelectedItem().toString();

                int ngay = 1;
                int vnd = 1;
                if (st_loaingay.equalsIgnoreCase("/Ngày")) {
                    ngay = 1;
                    vnd = 3000;
                } else if (st_loaingay.equalsIgnoreCase("/Tuần")) {
                    ngay = 7;
                    vnd = 20000;
                } else if (st_loaingay.equalsIgnoreCase("/Tháng")) {
                    ngay = 30;
                    vnd = 60000;
                }

                int tmp = Integer.parseInt(songay.getText().toString());
                vnd = vnd * tmp;

                //

//                controller.postNhaTro(motel);
                processPayment(vnd);
            }
        });
    }
    public void actionSql(){
        sqLite_tinhTP = new SQLite_TinhTP(getActivity());
        sqLite_quanHuyen = new SQLite_QuanHuyen(getActivity());
        List<TinhTP> tinhTPS = sqLite_tinhTP.getDSTP();
//idtinh = tinhTPS.get(1).getId();
        adapter_Tinh = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, tinhTPS);
        tinh.setAdapter(adapter_Tinh);

        tinh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TinhTP tinhTP1 = (TinhTP)parent.getAdapter().getItem(position);
                Log.i("abcabcabc",tinhTP1.toString());
                int idtinh = tinhTP1.getId();
                Log.i("abcabcabc",idtinh+"");
                huyen.invalidate();
                List<QuanHuyen> quanHuyens = sqLite_quanHuyen.getDSQH(idtinh);
                adapter_QH = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, quanHuyens);
                huyen.setAdapter(adapter_QH);
                String text = huyen.getSelectedItem().toString();
                Log.i("abcabcabc",text);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    public void anhxa(View root) {
        ten = (EditText) root.findViewById(R.id.ten);
        tuoi = (EditText) root.findViewById(R.id.tuoi);
        sdtlh = (EditText) root.findViewById(R.id.sdtlh);
        diachi = (EditText) root.findViewById(R.id.diachi);
        giatien = (EditText) root.findViewById(R.id.giatien);
        songay = (EditText) root.findViewById(R.id.songay);
        gioitinh = (Spinner) root.findViewById(R.id.spiner_gioitinh);
        hinhthuc = (Spinner) root.findViewById(R.id.spiner_hinhthucdang);
        loaitin = (Spinner) root.findViewById(R.id.spiner_loaitin);
        loaingay = (Spinner) root.findViewById(R.id.spiner_loaingay);
        tinh = (Spinner) root.findViewById(R.id.spiner_tinh);
        huyen = (Spinner) root.findViewById(R.id.spiner_huyen);

        chontep = (Button) root.findViewById(R.id.btchontep);
        post = (Button) root.findViewById(R.id.btluu);
        imageView = (ImageView) root.findViewById(R.id.imv);
    }

    private void processPayment(int vnd) {
        double vn = (double) vnd / 22000;
        amount = vn + "";
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(amount)), "USD",
                "Donate for motelUser", PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(getActivity(), PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = this.getContext().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
//            Log.e("sncncnnccnnc","path :"+picturePath);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        } else if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation != null) {
                    Date date = new Date();
                    Long datest = date.getTime();
                    String st_ten, st_tuoi, st_gioitinh, st_sdt,st_tinh,st_huyen, st_diachi, st_loaingay, st_hinhthuc, st_loaitin;
                    st_ten = ten.getText().toString();
                    st_tuoi = tuoi.getText().toString();
                    st_gioitinh = gioitinh.getSelectedItem().toString();
                    st_sdt = sdtlh.getText().toString();
                    st_tinh = tinh.getSelectedItem().toString();
                    st_huyen = huyen.getSelectedItem().toString();
                    st_diachi = diachi.getText().toString();
                    st_loaingay = loaingay.getSelectedItem().toString();
                    st_hinhthuc = hinhthuc.getSelectedItem().toString();
                    st_loaitin = loaitin.getSelectedItem().toString();
                    int tien = Integer.parseInt(giatien.getText().toString());
                    int ngay = 1;
                    int vnd = 1;
                    if (st_loaingay.equalsIgnoreCase("/Ngày")) {
                        ngay = 1;
                        vnd = 3000;
                    } else if (st_loaingay.equalsIgnoreCase("/Tuần")) {
                        ngay = 7;
                        vnd = 20000;
                    } else if (st_loaingay.equalsIgnoreCase("/Tháng")) {
                        ngay = 30;
                        vnd = 60000;
                    }

                    int tmp = Integer.parseInt(songay.getText().toString());
                    vnd = vnd * tmp;
                    Long datelt = tmp * ngay * 24 * 60 * 60 * 1000 + datest;
                    //Storage upload
                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference mountainImagesRef = storage.getReference().child("mipmap/"+datest+".jpg");
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
                    Bitmap bitmap = bitmapDrawable.getBitmap();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] imgs = byteArrayOutputStream.toByteArray();
                    mountainImagesRef.putBytes(imgs);
                    String img ="mipmap/"+datest+".jpg";
                    //
                    Motel motel = new Motel(st_ten, st_tuoi, st_gioitinh, st_sdt,st_tinh,st_huyen, st_diachi, tien,
                            datest, datelt, st_hinhthuc, st_loaitin, img, false);
                    controller.post(motel);
                    Toast.makeText(getActivity(),"Đăng tin thành công",Toast.LENGTH_LONG).show();
//                    startActivity(new Intent(getActivity(), SearchFragment.class));

                }
            } else if (resultCode == Activity.RESULT_CANCELED)
                Toast.makeText(getActivity(), "Cancel", Toast.LENGTH_SHORT).show();
        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID)
            Toast.makeText(getActivity(), "Invalid", Toast.LENGTH_SHORT).show();


    }
}

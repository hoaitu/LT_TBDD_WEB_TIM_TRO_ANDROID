package com.hoaitu.qlntusers.ui.post;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hoaitu.qlntusers.R;
import com.hoaitu.qlntusers.model.BaiDang;
import com.hoaitu.qlntusers.model.QuanHuyen;
import com.hoaitu.qlntusers.model.TinhTP;
import com.hoaitu.qlntusers.sqlite.SQLite_QuanHuyen;
import com.hoaitu.qlntusers.sqlite.SQLite_TinhTP;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

///////////

public class PostFragment extends Fragment {

    private static final int RESULT_OK = -1;
    SQLite_TinhTP sqLite_tinhTP;
    SQLite_QuanHuyen sqLite_quanHuyen;
    ArrayAdapter<TinhTP> adapter_Tinh;
    ArrayAdapter<QuanHuyen> adapter_QH;
    String amount = "";
    EditText ten, mota, sdtlh, diachi, giatien, songay, dientich;
    Spinner gioitinh, hinhthuc, loaitin, loaingay, tinh, huyen;
    Button chontep, post;
//    FirebaseController controller;
    private static final int RESULT_LOAD_IMAGE = 1;
    public static final int PAYPAL_REQUEST_CODE = 7171;
    ImageView imageView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    private Uri mImageUri;


    String strEmail;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_post, container, false);
//        controller = new FirebaseController();
//        Intent intent = new Intent(getActivity(), PayPalService.class);
//        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
//        getActivity().startService(intent);
        anhxa(root);
//                if (checkLogin() == 1){
        actionSql();
        //// XU LY LAY HINH ANH
                    //////
        actionImage();
/// XU LY NUT DANG BAI
        actionPost();
        /////
//    }else{
//            Intent intent = new Intent(getActivity().getApplication(), Login.class);
//            startActivity(intent);
//        }
        return root;
    }

    private void actionImage() {
        chontep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // UPLOAD ANH
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, RESULT_LOAD_IMAGE);
            }
        });
    }
    // UPLOAD ANH
        @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode == RESULT_LOAD_IMAGE || resultCode == RESULT_OK || data!= null || data.getData() != null){
                mImageUri = data.getData();
                Picasso.get().load(mImageUri).into(imageView);
            }
        }
        private String getImage(Uri uri){
        ContentResolver contentResolver = getActivity().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
        }

        ////Kiem tra dang nhap

    public int checkLogin(){
//        SharedPreferences sharedPreferences = getSharedPreferences("USER_FILE", MODE_PRIVATE) ;
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("USER_FILE", MODE_PRIVATE) ;
        boolean chk =sharedPreferences.getBoolean("LOGIN", false);
        if (chk){
            strEmail = sharedPreferences.getString("EMAIL","");
            return 1;

        }
        return -1;
    }

    private void actionPost() {
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                    if (TextUtils.isEmpty(ten.getText().toString())) {
                        Toast.makeText(getActivity(), "Vui lòng nhập tiêu đề!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.isEmpty(mota.getText().toString())) {
                        Toast.makeText(getActivity(), "Vui lòng nhập mô tả!", Toast.LENGTH_SHORT).show();
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

                    /////////////
                    //// INSERT DL VAO FIREBASE
                    firebaseDatabase = FirebaseDatabase.getInstance();
                    databaseReference = firebaseDatabase.getReference("DangBai");

                    storageReference = FirebaseStorage.getInstance().getReference("DangBai");
//                databaseReference = FirebaseDatabase.getInstance().getReference("DangBai");

                    String mtieude = ten.getText().toString().trim();
                    String mmota = mota.getText().toString().trim();
                    String msdt = sdtlh.getText().toString().trim();
                    String mdiachi = diachi.getText().toString().trim();
                    String mgia = giatien.getText().toString().trim();
                    String mdientich = dientich.getText().toString().trim();
                    String mngay = songay.getText().toString().trim();
                    String st_gioitinh = gioitinh.getSelectedItem().toString();
                    String st_huyen = huyen.getSelectedItem().toString();
                    String st_tinh = tinh.getSelectedItem().toString();
                    String st_ngay = loaingay.getSelectedItem().toString();
                    String st_hinhthuc = hinhthuc.getSelectedItem().toString();
                    String st_loaitin = loaitin.getSelectedItem().toString();


                    if (mImageUri != null) {
                        StorageReference storage = storageReference.child(getImage(mImageUri));
                        storage.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Toast.makeText(getActivity(), "Upload Successfull", Toast.LENGTH_LONG).show();
//                                String mimage = taskSnapshot.getUploadSessionUri().toString();
                                String mimage = "https://firebasestorage.googleapis.com/v0/b/authencation-6d285.appspot.com/o/DangBai%2Fpng?alt=media&token=25a19deb-5bd3-4754-9a4d-ed6bae514a50";
//                                String Id = databaseReference.push().getKey();
                                BaiDang baiDang = new BaiDang(mtieude, mmota, msdt, mdiachi, mgia, mdientich, mngay, st_gioitinh, st_huyen, st_tinh, st_ngay, st_hinhthuc, st_loaitin, mimage);
                                databaseReference.child(mtieude).setValue(baiDang);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), "Upload Eror", Toast.LENGTH_LONG).show();
                            }
                        });
                    }

//                BaiDang baiDang = new BaiDang(mtieude,mmota,msdt,mdiachi,mgia, mdientich, mngay, st_gioitinh, st_huyen, st_tinh, st_ngay, st_hinhthuc, st_loaitin);

//                databaseReference.child(mtieude).setValue(baiDang);


//                controller.postNhaTro(motel);
//                processPayment(vnd);


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
                Log.i("CHON TINH: ",tinhTP1.toString());
                int idtinh = tinhTP1.getId();
                Log.i("CHON QUAN: ",idtinh+"");
                huyen.invalidate();
                List<QuanHuyen> quanHuyens = sqLite_quanHuyen.getDSQH(idtinh);
                adapter_QH = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, quanHuyens);
                huyen.setAdapter(adapter_QH);
                String text = huyen.getSelectedItem().toString();
                Log.i("CHON----",text);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    public void anhxa(View root) {
        ten = (EditText) root.findViewById(R.id.ten);
        mota = (EditText) root.findViewById(R.id.tuoi);
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
        dientich = root.findViewById(R.id.dientich);

        chontep = (Button) root.findViewById(R.id.btchontep);
        post = (Button) root.findViewById(R.id.btluu);
        imageView = (ImageView) root.findViewById(R.id.imv);
    }

//    private void processPayment(int vnd) {
//        double vn = (double) vnd / 22000;
//        amount = vn + "";
//        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(amount)), "USD",
//                "Donate for motelUser", PayPalPayment.PAYMENT_INTENT_SALE);
//        Intent intent = new Intent(getActivity(), PaymentActivity.class);
//        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
//        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
//        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
//    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
//            Uri selectedImage = data.getData();
//            String[] filePathColumn = {MediaStore.Images.Media.DATA};
//            Cursor cursor = this.getContext().getContentResolver().query(selectedImage,
//                    filePathColumn, null, null, null);
//            cursor.moveToFirst();
//
//            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//            String picturePath = cursor.getString(columnIndex);
//            cursor.close();
////            Log.e("sncncnnccnnc","path :"+picturePath);
//            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
//        } else if (requestCode == PAYPAL_REQUEST_CODE) {
//            if (resultCode == RESULT_OK) {
////                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
////                if (confirmation != null) {
//                    Date date = new Date();
//                    Long datest = date.getTime();
//                    String st_ten, st_tuoi, st_gioitinh, st_sdt,st_tinh,st_huyen, st_diachi, st_loaingay, st_hinhthuc, st_loaitin;
//                    st_ten = ten.getText().toString();
//                    st_tuoi = tuoi.getText().toString();
//                    st_gioitinh = gioitinh.getSelectedItem().toString();
//                    st_sdt = sdtlh.getText().toString();
//                    st_tinh = tinh.getSelectedItem().toString();
//                    st_huyen = huyen.getSelectedItem().toString();
//                    st_diachi = diachi.getText().toString();
//                    st_loaingay = loaingay.getSelectedItem().toString();
//                    st_hinhthuc = hinhthuc.getSelectedItem().toString();
//                    st_loaitin = loaitin.getSelectedItem().toString();
//                    int tien = Integer.parseInt(giatien.getText().toString());
//                    int ngay = 1;
//                    int vnd = 1;
//                    if (st_loaingay.equalsIgnoreCase("/Ngày")) {
//                        ngay = 1;
//                        vnd = 3000;
//                    } else if (st_loaingay.equalsIgnoreCase("/Tuần")) {
//                        ngay = 7;
//                        vnd = 20000;
//                    } else if (st_loaingay.equalsIgnoreCase("/Tháng")) {
//                        ngay = 30;
//                        vnd = 60000;
//                    }
//
//                    int tmp = Integer.parseInt(songay.getText().toString());
//                    vnd = vnd * tmp;
//                    Long datelt = tmp * ngay * 24 * 60 * 60 * 1000 + datest;
//                    //Storage upload
//                    FirebaseStorage storage = FirebaseStorage.getInstance();
//                    StorageReference mountainImagesRef = storage.getReference().child("mipmap/"+datest+".jpg");
//                    BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
//                    Bitmap bitmap = bitmapDrawable.getBitmap();
//                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
//                    byte[] imgs = byteArrayOutputStream.toByteArray();
//                    mountainImagesRef.putBytes(imgs);
//                    String img ="mipmap/"+datest+".jpg";
//                    //
//                    Motel motel = new Motel(st_ten, st_tuoi, st_gioitinh, st_sdt,st_tinh,st_huyen, st_diachi, tien,
//                            datest, datelt, st_hinhthuc, st_loaitin, img, false);
//                    controller.post(motel);
//                    Toast.makeText(getActivity(),"Đăng tin thành công", Toast.LENGTH_LONG).show();
////                    startActivity(new Intent(getActivity(), SearchFragment.class));
//
//                }
//            } else if (resultCode == Activity.RESULT_CANCELED)
//                Toast.makeText(getActivity(), "Cancel", Toast.LENGTH_SHORT).show();
////        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID)
////            Toast.makeText(getActivity(), "Invalid", Toast.LENGTH_SHORT).show();
//
//
//    }
}

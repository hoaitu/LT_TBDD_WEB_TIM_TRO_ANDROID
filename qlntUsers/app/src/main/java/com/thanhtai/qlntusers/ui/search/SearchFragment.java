package com.thanhtai.qlntusers.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.thanhtai.qlntusers.Detail;
import com.thanhtai.qlntusers.R;
import com.thanhtai.qlntusers.custom.CustomListAdapter;
import com.thanhtai.qlntusers.model.Motel;
import com.thanhtai.qlntusers.model.QuanHuyen;
import com.thanhtai.qlntusers.model.TinhTP;
import com.thanhtai.qlntusers.sqlite.SQLite_QuanHuyen;
import com.thanhtai.qlntusers.sqlite.SQLite_TinhTP;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchFragment extends Fragment {
    SQLite_TinhTP sqLite_tinhTP;
    SQLite_QuanHuyen sqLite_quanHuyen;
    ArrayAdapter<TinhTP> adapter_Tinh;
    ArrayAdapter<QuanHuyen> adapter_QH;
    RadioButton timtro, oghep, tinhhuyen;
    Spinner tinh, huyen;
    Button button;
    boolean check;
    List<Motel> motels;
    ListView listView;
    CustomListAdapter cus;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        anhxa(root);
        check = tinhhuyen.isChecked();
        motels = new ArrayList<>();
        cus = new CustomListAdapter(getActivity(), motels);
        listView.setAdapter(cus);
        tinhhuyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check) {
                    tinhhuyen.setChecked(false);
                    adapter_Tinh.clear();
                    adapter_QH.clear();
                    check = false;
                } else {
                    tinhhuyen.setChecked(true);
                    actionSql();
                    check= true;
                }
            }
        });
//if(tinhhuyen.isChecked()) {
//    actionSql();
//}
        actionTK();
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

    private void actionTK() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timtro.isChecked()) {
                    motels.clear();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference();
                    Query allPost = myRef.child("Motel1").child("Cho thuê phòng trọ");
                    allPost.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot item : dataSnapshot.getChildren())
                            {

                                    Motel motel = item.getValue(Motel.class);
                                    if(motel.isView()) {
                                        if(tinhhuyen.isChecked()){
                                            String st_tinh = tinh.getSelectedItem().toString();
                                            String st_huyen = huyen.getSelectedItem().toString();
                                            if(st_tinh.equalsIgnoreCase(motel.getTinh())&& st_huyen.equalsIgnoreCase(motel.getHuyen())){
                                                motels.add(motel);
                                            }

                                        }else motels.add(motel);

                                    }


                            }
                            Collections.reverse(motels);
                            cus.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                } else if (oghep.isChecked()) {
                    motels.clear();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference();
                    Query allPost = myRef.child("Motel1").child("Tìm người ở ghép");
                    allPost.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot item : dataSnapshot.getChildren())
                            {

                                Motel motel = item.getValue(Motel.class);
                                if(motel.isView()) {
                                    if(tinhhuyen.isChecked()){
                                        String st_tinh = tinh.getSelectedItem().toString();
                                        String st_huyen = huyen.getSelectedItem().toString();
                                        if(st_tinh.equalsIgnoreCase(motel.getTinh())&& st_huyen.equalsIgnoreCase(motel.getHuyen())){
                                            motels.add(motel);
                                        }

                                    }else motels.add(motel);

                                }


                            }
                            Collections.reverse(motels);
                            cus.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
    }

    private void anhxa(View root) {
        tinh = (Spinner) root.findViewById(R.id.spiner_tinh_search);
        huyen = (Spinner) root.findViewById(R.id.spiner_huyen_search);
        timtro = (RadioButton) root.findViewById(R.id.rbtn_timtro);
        oghep = (RadioButton) root.findViewById(R.id.rbtn_oghep);
        button = (Button) root.findViewById(R.id.btn_timkiem);
        tinhhuyen = (RadioButton) root.findViewById(R.id.rbtn_tinhhuyen);
        listView = (ListView) root.findViewById(R.id.list_item_search);
    }

    public void actionSql() {
        sqLite_tinhTP = new SQLite_TinhTP(getActivity());
        sqLite_quanHuyen = new SQLite_QuanHuyen(getActivity());
        List<TinhTP> tinhTPS = sqLite_tinhTP.getDSTP();
//idtinh = tinhTPS.get(1).getId();
        adapter_Tinh = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, tinhTPS);
        tinh.setAdapter(adapter_Tinh);

        tinh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TinhTP tinhTP1 = (TinhTP) parent.getAdapter().getItem(position);
                Log.i("abcabcabc", tinhTP1.toString());
                int idtinh = tinhTP1.getId();
                Log.i("abcabcabc", idtinh + "");
                huyen.invalidate();
                List<QuanHuyen> quanHuyens = sqLite_quanHuyen.getDSQH(idtinh);
                adapter_QH = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, quanHuyens);
                huyen.setAdapter(adapter_QH);
                String text = huyen.getSelectedItem().toString();
                Log.i("abcabcabc", text);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}
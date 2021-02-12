package com.hoaitu.qlntusers.ui.search;

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
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hoaitu.qlntusers.Detail;
import com.hoaitu.qlntusers.R;
import com.hoaitu.qlntusers.custom.SearchAdapter;
import com.hoaitu.qlntusers.model.BaiDang;
import com.hoaitu.qlntusers.model.Motel;
import com.hoaitu.qlntusers.model.QuanHuyen;
import com.hoaitu.qlntusers.model.TinhTP;
import com.hoaitu.qlntusers.sqlite.SQLite_QuanHuyen;
import com.hoaitu.qlntusers.sqlite.SQLite_TinhTP;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchFragment extends Fragment {
    ///////////

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
//    CustomListAdapter cus;
    SearchView searchView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private SearchAdapter adapter;
    private ArrayList<BaiDang> listmotels;
    private BaiDang baiDang;




//////////
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
//    public static com.thanhtai.qlntusers.ui.search.SearchFragment newInstance(String param1, String param2) {
//        com.example.test_search_2.ui.search.SearchFragment fragment = new com.example.test_search_2.ui.search.SearchFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        anhxa(root);
        check = tinhhuyen.isChecked();
        motels = new ArrayList<>();

//        adapter = new SearchAdapter(listmotels, getActivity());
//        listView.setAdapter(adapter);
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

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        ////Arraylisst
        listmotels = new ArrayList<>();

        /// Clear ArrayList
        ClearAll();
        GetDataFromFirebase();

        ///
        tinhhuyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check = tinhhuyen.isChecked();
                if(!check) {
                    adapter_Tinh.clear();
                    adapter_QH.clear();
                    check=false;
                }else {
                    tinhhuyen.setChecked(true);
                    actionSql();
                    check=true;
                }
            }
        });
        actionTK();
        if(searchView!= null){
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    search(s);
                    return true;
                }
            });


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    BaiDang baiDang = listmotels.get(position);
                    Intent intent = new Intent(getContext(), Detail.class);
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
        return root;
    }
    private void search(String s) {
        listmotels = new ArrayList<>();
        for (BaiDang b : listmotels){
            if(b.getTinh().toLowerCase().contains(s.toLowerCase())){
                listmotels.add(b);
            }
        }
//        GetDataFromFirebase();
        adapter = new SearchAdapter(listmotels, getActivity());
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    private void actionTK() {
        button.setOnClickListener(v -> {
            if (timtro.isChecked()) {
                listmotels.clear();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference();
                Query allPost = myRef.child("DangBai");
                allPost.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot item : snapshot.getChildren()) {

                            BaiDang baiDang = new BaiDang();
                            baiDang.setImg(item.child("img").getValue().toString());
                            baiDang.setTieude(item.child("tieude").getValue().toString());
                            baiDang.setMota(item.child("mota").getValue().toString());
                            baiDang.setAddress(item.child("address").getValue().toString());
                            baiDang.setTitle(item.child("title").getValue().toString());
                            baiDang.setTinh(item.child("tinh").getValue().toString());
                            baiDang.setHuyen(item.child("huyen").getValue().toString());
                            baiDang.setPhone(item.child("phone").getValue().toString());
                            baiDang.setPrice(item.child("price").getValue().toString());
                            baiDang.setDientich(item.child("dientich").getValue().toString());


                            if (baiDang.isView()) {
                                if (tinhhuyen.isChecked()) {
                                    String st_tinh = tinh.getSelectedItem().toString();
                                    String st_huyen = huyen.getSelectedItem().toString();
                                    if (st_tinh.equals(baiDang.getTinh()) && st_huyen.equals(baiDang.getHuyen())) {
                                        listmotels.add(baiDang);
                                    }

                                } else listmotels.add(baiDang);

                            }

                        }
                        Collections.reverse(listmotels);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
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
                adapter = new SearchAdapter(listmotels, getActivity());
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

    private void anhxa(View root) {
        tinh = (Spinner) root.findViewById(R.id.spiner_tinh_search);
        huyen = (Spinner) root.findViewById(R.id.spiner_huyen_search);
        timtro = (RadioButton) root.findViewById(R.id.rbtn_timtro);
        oghep = (RadioButton) root.findViewById(R.id.rbtn_oghep);
        button = (Button) root.findViewById(R.id.btn_timkiem);
        tinhhuyen = (RadioButton) root.findViewById(R.id.rbtn_tinhhuyen);
        listView =  root.findViewById(R.id.list_item_search);
        searchView = root.findViewById(R.id.search);
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
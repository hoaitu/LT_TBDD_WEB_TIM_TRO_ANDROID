package com.hoaitu.qlntusers.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hoaitu.qlntusers.Admin;
import com.hoaitu.qlntusers.MainActivity;
import com.hoaitu.qlntusers.QLBV;
import com.hoaitu.qlntusers.R;

public class User extends Fragment {
    Button bt_login;
    RelativeLayout layoutNonLogin, layoutLogin;
    TextView tv_name, tv_address, email;

    //  ImageView imgLogout;

    private FirebaseUser user;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_user, container, false);

        layoutNonLogin = root.findViewById(R.id.layoutNonLogin);
        layoutLogin = root.findViewById(R.id.layoutLogin);

        tv_name = root.findViewById(R.id.tv_name);
        tv_address = root.findViewById(R.id.tv_address);
        email = root.findViewById(R.id.email);

//        imgLogout = root.findViewById(R.id.imgLogout);
//        imgLogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(user != null){
//                    FirebaseAuth.getInstance().signOut();
//                    OnDataChanged();
//                }
//            }
//        });

        bt_login = root.findViewById(R.id.button);
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
            }
        });
        setHasOptionsMenu(true);
        return root;
    }

    void OnDataChanged() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            layoutNonLogin.setVisibility(View.VISIBLE);
            layoutLogin.setVisibility(View.GONE);
        } else {
            layoutNonLogin.setVisibility(View.GONE);
            layoutLogin.setVisibility(View.VISIBLE);

            tv_name.setText(user.getEmail());
            tv_address.setText(user.getEmail() + "..");
            email.setText(user.getEmail());
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        OnDataChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        OnDataChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.user_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
            case R.id.admin:
                Intent intent1 = new Intent(getActivity(), Admin.class);
                startActivity(intent1);
                break;
            case R.id.qlbv:
                Intent intent = new Intent(getActivity(), QLBV.class);
                startActivity(intent);
                break;
            case R.id.logout:

                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), MainActivity.class));

                break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + item.getItemId());
            }
            return super.onOptionsItemSelected(item);

        }


}
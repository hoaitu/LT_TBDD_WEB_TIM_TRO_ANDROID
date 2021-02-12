package com.hoaitu.qlntusers.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hoaitu.qlntusers.R;
import com.hoaitu.qlntusers.ui.login.User1;

import java.util.List;

public class userAdapter extends BaseAdapter {
    private List<User1> list;
    private Context context;
    private LayoutInflater layoutInflater;

    public userAdapter(List<User1> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_accout, parent,false);
            viewHolder = new ViewHolder(convertView);

            viewHolder.txtUser.setText(list.get(position).getName());
            viewHolder.txtemail.setText(list.get(position).getEmail());
//            viewHolder.txtlevel.setText(list.get(position).getLevel());

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }
    public class ViewHolder{
        TextView txtUser, txtlevel, txtemail;
        public ViewHolder(View itemView){
            txtUser = itemView.findViewById(R.id.txtnameuser);
            txtlevel = itemView.findViewById(R.id.txtlevel);
            txtemail = itemView.findViewById(R.id.txtemail);
        }
    }

}

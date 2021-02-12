package com.hoaitu.qlntusers.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hoaitu.qlntusers.R;
import com.hoaitu.qlntusers.model.BaiDang;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewAdapter extends BaseAdapter {
    private List<BaiDang> list;
    private Context context;
    private LayoutInflater layoutInflater;

    public NewAdapter(List<BaiDang> list, Context context) {
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
       ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_baiviet,parent,false);
            holder = new ViewHolder(convertView);

            holder.mtieude.setText(list.get(position).getTieude());
            holder.mmota.setText(list.get(position).getMota());
            holder.mdiachi.setText(list.get(position).getAddress());
            holder.mhinhthuc.setText(list.get(position).getTitle());
            holder.mgia.setText(list.get(position).getPrice());
            holder.mtinh.setText(list.get(position).getTinh());
            holder.mhuyen.setText(list.get(position).getHuyen());
            holder.mgia.setText(list.get(position).getPrice());
            holder.mphone.setText(list.get(position).getPhone());

            Picasso.get().load(list.get(position).getImg()).into(holder.image);
            convertView.setTag(holder);

        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }
    public class ViewHolder {
        TextView mtieude, mmota, mdiachi, mhinhthuc, mtinh, mhuyen, mphone, mdientich, mgia;
        ImageView image;
        public ViewHolder( View itemView) {
            image = itemView.findViewById(R.id.image);
            mtieude = itemView.findViewById(R.id.text_title);
            mmota = itemView.findViewById(R.id.text_content);
            mdiachi = itemView.findViewById(R.id.diachi);
            mhinhthuc = itemView.findViewById(R.id.hinhthuc);
            mtinh = itemView.findViewById(R.id.tinh);
            mhuyen = itemView.findViewById(R.id.huyen);
            mphone = itemView.findViewById(R.id.phone);
            mdientich = itemView.findViewById(R.id.txt_dientich);
            mgia = itemView.findViewById(R.id.gia);

        }
    }
}

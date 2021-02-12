package com.hoaitu.qlntusers.custom;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hoaitu.qlntusers.Detail;
import com.hoaitu.qlntusers.R;
import com.hoaitu.qlntusers.model.BaiDang;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomListAdapter extends RecyclerView.Adapter<CustomListAdapter.ViewHolder> {
//    View view;
    private Context mcontext;
    private ArrayList<BaiDang> list;
    public CustomListAdapter(Context mcontext, ArrayList<BaiDang> list){
        this.mcontext = mcontext;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_motel,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaiDang baiDang = list.get(position);
                Intent intent = new Intent(v.getContext(), Detail.class);
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
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView mtieude, mmota, mdiachi, mhinhthuc, mtinh, mhuyen, mphone, mdientich, mgia;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
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

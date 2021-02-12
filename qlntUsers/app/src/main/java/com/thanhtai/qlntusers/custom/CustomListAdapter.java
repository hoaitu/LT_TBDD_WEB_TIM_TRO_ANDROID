package com.thanhtai.qlntusers.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.thanhtai.qlntusers.R;
import com.thanhtai.qlntusers.model.Motel;

import java.util.List;

public class CustomListAdapter extends BaseAdapter {
    private List<Motel> listData;
    private LayoutInflater layoutInflater;
    private Context context;
    public List<Motel> getListData() {
        return listData;
    }

    public CustomListAdapter(Context context, List<Motel> listData) {
    this.context =context;
    this.listData =listData;
    layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder1 holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_view_motel, null);
            holder = new ViewHolder1();
            holder.img = (ImageView) convertView.findViewById(R.id.img_motel);
            holder.title = (TextView) convertView.findViewById(R.id.text_motel_title);
            holder.address = (TextView) convertView.findViewById(R.id.text_motel_author);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder1) convertView.getTag();
        }

        Motel motel = this.listData.get(position);
        holder.title.setText(motel.getTitle());
        holder.address.setText(motel.getAddress());

//        int imageId = this.getMipmapResIdByName(Motel.getImg());
//        holder.img.setImageResource(imageId);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        String img = motel.getImg();
        StorageReference mountainImagesRef = storage.getReference().child(img);

        mountainImagesRef.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Use the bytes to display the image
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                holder.img.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });


        return convertView;
    }
    public int getMipmapResIdByName(String resName)  {
        String pkgName = context.getPackageName();
        // Trả về 0 nếu không tìm thấy.
        int resID = context.getResources().getIdentifier(resName , "mipmap", pkgName);
        Log.i("CustomListView", "Res Name: "+ resName+"==> Res ID = "+ resID);
        return resID;
    }
    static class ViewHolder1 {
        ImageView img;
        TextView title;
        TextView address;
    }

}

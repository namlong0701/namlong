package com.example.btlapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btlapp.R;
import com.example.btlapp.activity.ChiTietSanPham;
import com.example.btlapp.model.Sanpham;
import com.example.btlapp.ultil.checkconection;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SanphamAdapter extends RecyclerView.Adapter<SanphamAdapter.ItemHolder> {
    Context context;
    ArrayList<Sanpham> arraysanpham;

    public SanphamAdapter(Context context, ArrayList<Sanpham> arraysanpham) {
        this.context = context;
        this.arraysanpham = arraysanpham;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       @SuppressLint("InflateParams") View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_sanphammoinhat,null);
        return new ItemHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        Sanpham sanpham = arraysanpham.get(position);
        holder.txttensanpham.setText(sanpham.getTensanpham());
        String pattern = "###,###.###";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        String format = decimalFormat.format(sanpham.getGiasanpham());

        holder.txtgiasanpham.setText("Price: " + format + " Đ");
        Picasso.with(context).load(sanpham.getHinhanhsanpham())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(holder.imghinhsanpham);


    }
    @Override
    public int getItemCount() {
        return arraysanpham.size();
    }

    /** @noinspection deprecation*/
    public class ItemHolder extends RecyclerView.ViewHolder{
        public ImageView imghinhsanpham;
        public TextView txttensanpham,txtgiasanpham;

        public ItemHolder( View itemView) {
            super(itemView);
            imghinhsanpham = itemView.findViewById(R.id.imageviewsanpham);
            txttensanpham=itemView.findViewById(R.id.textviewtensanpham);
            txtgiasanpham=itemView.findViewById((R.id.textviewgiasanpham));
            itemView.setOnClickListener(v -> {
                Intent intent =new Intent(context, ChiTietSanPham.class);
                intent.putExtra("thongtinsanpham",arraysanpham.get(getPosition()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                checkconection.ShowToast_Short(context,arraysanpham.get(getPosition()).getTensanpham());
                context.startActivity(intent);
            });
        }
    }

}

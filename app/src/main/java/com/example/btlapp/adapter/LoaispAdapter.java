package com.example.btlapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.btlapp.R;
import com.example.btlapp.model.Loaisp;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LoaispAdapter extends BaseAdapter {
    ArrayList<Loaisp> arrayListloaisp;
    Context context;
    public LoaispAdapter(ArrayList<Loaisp> arrayListloaisp,Context context){
        this.arrayListloaisp=arrayListloaisp;
        this.context=context;
    }
    @Override
    public int getCount() {
        return arrayListloaisp.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListloaisp.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public static class ViewHolder{
        TextView txttenloaisp;
        ImageView imgloaisp;
    }
    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_listview_loaisp, null);
            viewHolder.txttenloaisp = (TextView) convertView.findViewById(R.id.textviewloaisp);
            viewHolder.imgloaisp = (ImageView) convertView.findViewById(R.id.imageviewloaisp);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Đặt giá trị cho ViewHolder bất kể convertView đã được tạo mới hay không
        Loaisp loaisp = (Loaisp) getItem(position);

//        if (loaisp != null) {
//            if (viewHolder.txttenloaisp != null) {
//                viewHolder.txttenloaisp.setText(loaisp.getTenloaisp());
//            }
            viewHolder.txttenloaisp.setText(loaisp.getTenloaisp());
            Picasso.with(context).load(loaisp.getHinhanhloaisp())
                    .placeholder(R.drawable.noimage)
                    .error(R.drawable.error)
                    .into(viewHolder.imgloaisp);

//        }

        return convertView;
    }


}
